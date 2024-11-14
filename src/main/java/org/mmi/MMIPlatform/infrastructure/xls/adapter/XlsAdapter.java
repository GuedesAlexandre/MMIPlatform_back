package org.mmi.MMIPlatform.infrastructure.xls.adapter;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
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
            // Create a CellStyle for the header with padding
            XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
            XSSFFont font = (XSSFFont) workbook.createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setWrapText(true);

            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            for (UEEnum ue : UEEnum.values()) {
                XSSFSheet sheet = (XSSFSheet) workbook.createSheet(ue.name());
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Liste des étudiants");
                headerRow.getCell(0).setCellStyle(headerStyle);

                List<ModuleDao> moduleList = moduleDaoRepository.findAll().stream()
                        .filter(module -> module.getUeName().equals(UEEnum.valueOf(ue.name())) && module.getSemester().equals(semester))
                        .toList();

                for (int i = 0; i < moduleList.size(); i++) {
                    headerRow.createCell(i + 1).setCellValue(moduleList.get(i).getName());
                    headerRow.getCell(i + 1).setCellStyle(headerStyle);
                }

                // Add a header for the average grade column
                headerRow.createCell(moduleList.size() + 1).setCellValue("Moyenne");
                headerRow.getCell(moduleList.size() + 1).setCellStyle(headerStyle);

                for (int i = 0; i < studentDaoList.size(); i++) {
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue(studentDaoList.get(i).getFirstName() + " " + studentDaoList.get(i).getLastName());

                    double totalGrades = 0;
                    int gradeCount = 0;

                    for (int j = 0; j < moduleList.size(); j++) {
                        ModuleDao module = moduleList.get(j);
                        double averageGrade = studentDaoList.get(i).getModules().stream()
                                .filter(m -> m.getId().equals(module.getId()))
                                .flatMap(m -> m.getNotes().stream())
                                .mapToDouble(note -> note.getNote() * module.getCoeff())
                                .average()
                                .orElse(0.00);
                        row.createCell(j + 1).setCellValue(String.format("%.2f", averageGrade));
                        totalGrades += averageGrade;
                        gradeCount++;
                    }

                    // Calculate and set the average grade for the student
                    double averageGrade = gradeCount > 0 ? totalGrades / gradeCount : 0.00;
                    row.createCell(moduleList.size() + 1).setCellValue(String.format("%.2f", averageGrade));
                }

                for (int i = 0; i <= moduleList.size() + 1; i++) {
                    sheet.autoSizeColumn(i);
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