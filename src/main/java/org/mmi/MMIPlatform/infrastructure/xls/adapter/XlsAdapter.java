package org.mmi.MMIPlatform.infrastructure.xls.adapter;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.ModuleDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class XlsAdapter {

    private final StudentDaoRepository studentDaoRepository;
    private final ModuleDaoRepository moduleDaoRepository;

    public byte[] exportStudentToExcel(String promo, String semester) throws IOException {
        List<StudentDao> studentDaoList = this.studentDaoRepository.findAll().stream()
                .filter(student -> student.getPromo().equals(PromoEnum.valueOf(promo)))
                .toList();

        try (Workbook workbook = new XSSFWorkbook()) {
            for (UEEnum ue : UEEnum.values()) {
                XSSFSheet sheet = (XSSFSheet) workbook.createSheet(ue.name());
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Liste des étudiants");

                List<ModuleDao> moduleList = moduleDaoRepository.findAll().stream()
                        .filter(module -> module.getUeName().equals(UEEnum.valueOf(ue.name())) && module.getSemester().equals(semester))
                        .toList();

                for (int i = 0; i < moduleList.size(); i++) {
                    headerRow.createCell(i + 1).setCellValue(moduleList.get(i).getName());
                }

                for (int i = 0; i < studentDaoList.size(); i++) {
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(studentDaoList.get(i).getFirstName() + " " + studentDaoList.get(i).getLastName());

                    for (int j = 0; j < moduleList.size(); j++) {
                        ModuleDao module = moduleList.get(j);
                        double averageGrade = studentDaoList.get(i).getModules().stream()
                                .filter(m -> m.getId().equals(module.getId()))
                                .flatMap(m -> m.getNotes().stream())
                                .mapToDouble(note -> note.getNote() * module.getCoeff())
                                .average()
                                .orElse(0.00);
                        row.createCell(j + 1).setCellValue(String.format("%.2f", averageGrade));
                    }
                }
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}