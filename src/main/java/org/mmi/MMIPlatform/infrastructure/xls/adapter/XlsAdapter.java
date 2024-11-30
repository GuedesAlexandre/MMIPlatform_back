package org.mmi.MMIPlatform.infrastructure.xls.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.NoteDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;
import org.mmi.MMIPlatform.infrastructure.db.repository.ModuleDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.NoteDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class XlsAdapter {

    private final StudentDaoRepository studentDaoRepository;
    private final ModuleDaoRepository moduleDaoRepository;
    private final NoteDaoRepository noteDaoRepository;

    public byte[] exportStudentToExcel(String promo, String semester) {
        validateInput(promo, semester);

        List<StudentDao> studentDaoList = getStudentList(promo);
        List<ModuleDao> moduleList = getModuleList(promo, semester);

        try (Workbook workbook = new XSSFWorkbook()) {
            XSSFCellStyle headerStyle = createHeaderStyle(workbook);
            XSSFCellStyle cellStyleRed = createCellStyleRed(workbook);
            XSSFCellStyle cellStyleGreen = createCellStyleGreen(workbook);

            for (UEEnum ue : UEEnum.values()) {
                XSSFSheet sheet = (XSSFSheet) workbook.createSheet(ue.name());
                createHeaderRow(sheet, headerStyle, ue, semester, moduleList);

                fillStudentData(sheet, studentDaoList, ue, semester, moduleList, cellStyleRed, cellStyleGreen);

                autoSizeColumns(sheet, moduleList.size() + 3);
            }

            return writeWorkbookToByteArray(workbook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateInput(String promo, String semester) {
        switch (promo) {
            case "MMI01":
                if (!semester.equals("1") && !semester.equals("2")) {
                    throw new IllegalArgumentException("For MMI01, semester must be 1 or 2.");
                }
                break;
            case "MMI02":
                if (!semester.equals("3") && !semester.equals("4")) {
                    throw new IllegalArgumentException("For MMI02, semester must be 3 or 4.");
                }
                break;
            case "MMI03":
                if (!semester.equals("5") && !semester.equals("6")) {
                    throw new IllegalArgumentException("For MMI03, semester must be 5 or 6.");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid promo value.");
        }
    }

    private List<StudentDao> getStudentList(String promo) {
        return this.studentDaoRepository.findAll().stream()
                .filter(student -> student.getPromo().equals(PromoEnum.valueOf(promo)))
                .toList();
    }

    private List<ModuleDao> getModuleList(String promo, String semester) {
        return this.moduleDaoRepository.findAll().stream()
                .filter(module -> module.getPromo().equals(String.valueOf(PromoEnum.valueOf(promo))) && module.getSemester().equals(semester))
                .toList();
    }

    private XSSFCellStyle createHeaderStyle(Workbook workbook) {
        XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setFontName("Calibri");
        font.setColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFont(font);

        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setWrapText(true);

        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);

        headerStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        headerStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        return headerStyle;
    }

    private XSSFCellStyle createCellStyleRed(Workbook workbook) {
        XSSFCellStyle cellStyleRed = (XSSFCellStyle) workbook.createCellStyle();
        cellStyleRed.setAlignment(HorizontalAlignment.CENTER);
        cellStyleRed.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleRed.setWrapText(true);
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setColor(IndexedColors.RED.getIndex());
        cellStyleRed.setFont(font);

        return cellStyleRed;
    }

    private XSSFCellStyle createCellStyleGreen(Workbook workbook) {
        XSSFCellStyle cellStyleGreen = (XSSFCellStyle) workbook.createCellStyle();
        cellStyleGreen.setAlignment(HorizontalAlignment.CENTER);
        cellStyleGreen.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleGreen.setWrapText(true);
        cellStyleGreen.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setColor(IndexedColors.GREEN.getIndex());
        cellStyleGreen.setFont(font);
        return cellStyleGreen;
    }

    private void createHeaderRow(XSSFSheet sheet, XSSFCellStyle headerStyle, UEEnum ue, String semester, List<ModuleDao> moduleList) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Liste des étudiants");
        headerRow.getCell(0).setCellStyle(headerStyle);

        headerRow.createCell(1).setCellValue("N° étudiant");
        headerRow.getCell(1).setCellStyle(headerStyle);
        headerRow.createCell(2).setCellValue("Groupe");
        headerRow.getCell(2).setCellStyle(headerStyle);

        List<ModuleDao> filteredModules = moduleList.stream()
                .filter(module -> module.getUeName().equals(ue.name()))
                .toList();

        for (int i = 0; i < filteredModules.size(); i++) {
            headerRow.createCell(i + 3).setCellValue(filteredModules.get(i).getName());
            headerRow.getCell(i + 3).setCellStyle(headerStyle);
        }

        headerRow.createCell(filteredModules.size() + 3).setCellValue("Moyenne de l'UE");
        headerRow.getCell(filteredModules.size() + 3).setCellStyle(headerStyle);

        Row subHeaderRow = sheet.createRow(1);
        for (int i = 0; i < filteredModules.size(); i++) {
            subHeaderRow.createCell(i + 3).setCellValue("Coeff: " + filteredModules.get(i).getCoeff());
            subHeaderRow.getCell(i + 3).setCellStyle(headerStyle);
        }
    }

    private void fillStudentData(XSSFSheet sheet, List<StudentDao> studentDaoList, UEEnum ue, String semester, List<ModuleDao> moduleList, XSSFCellStyle cellStyleRed, XSSFCellStyle cellStyleGreen) {
        List<ModuleDao> filteredModules = moduleList.stream()
                .filter(module -> module.getUeName().equals(ue.name()))
                .toList();

        for (int i = 0; i < studentDaoList.size(); i++) {
            Row row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(studentDaoList.get(i).getFirstName() + " " + studentDaoList.get(i).getLastName());
            row.createCell(1).setCellValue(studentDaoList.get(i).getNumEtu());
            row.createCell(2).setCellValue(studentDaoList.get(i).getGroup());

            double totalGrades = 0;
            int gradeCount = 0;

            for (int j = 0; j < filteredModules.size(); j++) {
                ModuleDao module = filteredModules.get(j);
                double averageGrade = studentDaoList.get(i).getNotes().stream()
                        .filter(note -> note.getModule().getId().equals(module.getId()))
                        .mapToDouble(NoteDao::getNote)
                        .average()
                        .orElse(0.00);
                row.createCell(j + 3).setCellValue(String.format("%.2f", averageGrade));
                if (averageGrade < 10) {
                    row.getCell(j + 3).setCellStyle(cellStyleRed);
                } else {
                    row.getCell(j + 3).setCellStyle(cellStyleGreen);
                }
                totalGrades += averageGrade;
                gradeCount++;
            }
            double averageGrade = gradeCount > 0 ? totalGrades / gradeCount : 0.00;
            row.createCell(filteredModules.size() + 3).setCellValue(String.format("%.2f", averageGrade));
        }
    }

    private void autoSizeColumns(XSSFSheet sheet, int columnCount) {
        for (int i = 0; i <= columnCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private byte[] writeWorkbookToByteArray(Workbook workbook) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
}