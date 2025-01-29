package org.mmi.MMIPlatform.infrastructure.xls.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mmi.MMIPlatform.domain.models.Internship;
import org.mmi.MMIPlatform.infrastructure.dao.InternshipDao;
import org.mmi.MMIPlatform.infrastructure.dao.ModuleDao;
import org.mmi.MMIPlatform.infrastructure.dao.NoteDao;
import org.mmi.MMIPlatform.infrastructure.dao.StudentDao;
import org.mmi.MMIPlatform.infrastructure.dao.enums.PromoEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.TypeEnum;
import org.mmi.MMIPlatform.infrastructure.dao.enums.UEEnum;
import org.mmi.MMIPlatform.infrastructure.db.adapter.InternshipDBAdapter;
import org.mmi.MMIPlatform.infrastructure.db.repository.InternshipDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.ModuleDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.NoteDaoRepository;
import org.mmi.MMIPlatform.infrastructure.db.repository.StudentDaoRepository;
import org.mmi.MMIPlatform.infrastructure.xls.adapter.models.StudentsRank;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class XlsAdapter {

    private final StudentDaoRepository studentDaoRepository;
    private final ModuleDaoRepository moduleDaoRepository;
    private final NoteDaoRepository noteDaoRepository;
    private final InternshipDBAdapter internshipDBAdapter;

    public byte[] exportStudentToExcel(String promo, String semester) {
        validateInput(promo, semester);

        List<StudentDao> studentDaoList = getStudentList(promo);
        List<ModuleDao> moduleList = getModuleList(promo, semester);
        List<StudentDao> internshipList = internshipDBAdapter.getInternshipsByPromo(promo);

        try (Workbook workbook = new XSSFWorkbook()) {
            XSSFCellStyle headerStyle = createHeaderStyle(workbook);
            XSSFCellStyle cellStyleRed = createCellStyleRed(workbook);
            XSSFCellStyle cellStyleGreen = createCellStyleGreen(workbook);
            XSSFCellStyle centeredStyle = createCenteredCellStyle(workbook);

            for (UEEnum ue : UEEnum.values()) {
                XSSFSheet sheet = (XSSFSheet) workbook.createSheet(ue.name());
                createHeaderRow(sheet, headerStyle, ue, semester, moduleList);

                fillStudentData(sheet, studentDaoList, ue, semester, moduleList, cellStyleRed, cellStyleGreen, centeredStyle);

                autoSizeColumns(sheet, moduleList.size() + 3);
            }

            createSummarySheet((XSSFWorkbook) workbook, studentDaoList, moduleList, internshipList);

            return writeWorkbookToByteArray(workbook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createSummarySheet(XSSFWorkbook workbook, List<StudentDao> studentDaoList, List<ModuleDao> moduleList, List<StudentDao> internshipList) {
        XSSFSheet summarySheet = workbook.createSheet("Synthèse");
        XSSFCellStyle headerStyle = createHeaderStyle(workbook);
        XSSFCellStyle cellStyleRed = createCellStyleRed(workbook);
        XSSFCellStyle cellStyleGreen = createCellStyleGreen(workbook);
        XSSFCellStyle centeredStyle = createCenteredCellStyle(workbook);

        // Create header row
        Row headerRow = summarySheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nom & Prénom de l'étudiant");
        headerRow.getCell(0).setCellStyle(headerStyle);
        headerRow.createCell(1).setCellValue("N° étudiant");
        headerRow.getCell(1).setCellStyle(headerStyle);
        headerRow.createCell(2).setCellValue("Groupe");
        headerRow.getCell(2).setCellStyle(headerStyle);

        int colIndex = 3;
        for (UEEnum ue : UEEnum.values()) {
            headerRow.createCell(colIndex).setCellValue(ue.name());
            headerRow.getCell(colIndex).setCellStyle(headerStyle);
            colIndex++;
        }

        headerRow.createCell(colIndex).setCellValue("Moyenne du semestre");
        headerRow.getCell(colIndex).setCellStyle(headerStyle);

        headerRow.createCell(colIndex + 1).setCellValue("Classement");
        headerRow.getCell(colIndex + 1).setCellStyle(headerStyle);

        headerRow.createCell(colIndex + 2).setCellValue("Nombre de semaine de stage");
        headerRow.getCell(colIndex + 2).setCellStyle(headerStyle);

        List<StudentsRank> overallUnsortedStudents = new ArrayList<>();

        for (int i = 0; i < studentDaoList.size(); i++) {
            Row row = summarySheet.createRow(i + 1);
            StudentDao student = studentDaoList.get(i);

            row.createCell(0).setCellValue(student.getFirstName() + " " + student.getLastName());
            row.createCell(1).setCellValue(student.getNumEtu());
            row.createCell(2).setCellValue(student.getGroup());

            double totalAverage = 0;
            int ueCount = 0;

            colIndex = 3;
            double totalUeCoeff = 0;
            for (UEEnum ue : UEEnum.values()) {
                double totalGrades = 0;
                double totalCoeffModule = 0;

                List<ModuleDao> filteredModules = moduleList.stream()
                        .filter(module -> module.getUeName().equals(ue.name()))
                        .toList();

                for (ModuleDao module : filteredModules) {
                    double totalGradesMultiply = student.getNotes().stream()
                            .filter(note -> note.getModule().getId().equals(module.getId()))
                            .mapToDouble(note -> note.getNote() * note.getCoeff())
                            .sum();

                    double totalCoeffs = student.getNotes().stream()
                            .filter(note -> note.getModule().getId().equals(module.getId()))
                            .mapToDouble(NoteDao::getCoeff)
                            .sum();

                    if (totalCoeffs == 0) {
                        totalCoeffs = module.getCoeff();
                    }

                    double averageGrade = totalCoeffs > 0 ? totalGradesMultiply / totalCoeffs : 0.00;
                    totalGrades += averageGrade * module.getCoeff();
                    totalCoeffModule += module.getCoeff();
                }

                double ueAverage = totalCoeffModule > 0 ? totalGrades / totalCoeffModule : 0.00;
                row.createCell(colIndex).setCellValue(String.format("%.2f", ueAverage));
                if (ueAverage < 10) {
                    row.getCell(colIndex).setCellStyle(cellStyleRed);
                } else {
                    row.getCell(colIndex).setCellStyle(cellStyleGreen);
                }

                totalAverage += ueAverage * totalCoeffModule;
                totalUeCoeff += totalCoeffModule;
                ueCount++;
                colIndex++;
            }

            double overallAverage = totalUeCoeff > 0 ? totalAverage / totalUeCoeff : 0.00;
            row.createCell(colIndex).setCellValue(String.format("%.2f", overallAverage));
            if (overallAverage < 10) {
                row.getCell(colIndex).setCellStyle(cellStyleRed);
            } else {
                row.getCell(colIndex).setCellStyle(cellStyleGreen);
            }

            List<InternshipDao> studentInternships = internshipList.stream()
                    .filter(studentInternshipList -> studentInternshipList.getNumEtu().equals(student.getNumEtu()))
                    .map(StudentDao::getInternships)
                    .flatMap(List::stream)
                    .toList();

            int totalInternshipsWeeks = 0;
            boolean isAlternance = false;
            for (InternshipDao internship : studentInternships) {
                if (internship.getType().equals(TypeEnum.ALTERNANCE)){
                    isAlternance = true;
                }
                totalInternshipsWeeks += internship.getWeekCount();
            }

            if (isAlternance) {
                row.createCell(colIndex + 2).setCellValue("Valider par alternance");
                row.getCell(colIndex + 2).setCellStyle(cellStyleGreen);
            } else {
                if (totalInternshipsWeeks <= 25){
                    row.createCell(colIndex + 2).setCellValue(totalInternshipsWeeks + " semaines");
                    row.getCell(colIndex + 2).setCellStyle(cellStyleRed);
                } else {
                    row.createCell(colIndex + 2).setCellValue(totalInternshipsWeeks + " semaines");
                    row.getCell(colIndex + 2).setCellStyle(cellStyleGreen);
                }
            }

            StudentsRank unsortedStudent = StudentsRank.builder()
                    .numEtu(student.getNumEtu())
                    .average(overallAverage)
                    .build();
            overallUnsortedStudents.add(unsortedStudent);
        }

        List<StudentsRank> overallSortedStudents = sortStudentsByGrades(overallUnsortedStudents);
        int colIndexRanking = UEEnum.values().length + 4;
        createRankingColumn(summarySheet, colIndexRanking, 1, overallSortedStudents, studentDaoList, centeredStyle);

        autoSizeColumns(summarySheet, UEEnum.values().length + 4);
        autoSizeColumns(summarySheet, colIndex + 2);
    }

    private void createRankingColumn(XSSFSheet summarySheet, int colIndexRanking, int rowIndexRanking, List<StudentsRank> overallSortedStudents, List<StudentDao> studentDaoList, XSSFCellStyle centeredStyle) {
        int currentRank = 1;
        double previousAverage = -1;

        for (int j = 0; j < studentDaoList.size(); j++) {
            StudentsRank sortedStudent = overallSortedStudents.get(j);

            if (sortedStudent.getAverage() != previousAverage) {
                currentRank = j + 1;
            }

            for (int k = 0; k < studentDaoList.size(); k++) {
                StudentDao student = studentDaoList.get(k);
                if (sortedStudent.getNumEtu().equals(student.getNumEtu())) {
                    Row row = summarySheet.getRow(k + rowIndexRanking);
                    row.createCell(colIndexRanking).setCellValue(String.valueOf(currentRank));
                    row.getCell(colIndexRanking).setCellStyle(centeredStyle);
                }
            }
            previousAverage = sortedStudent.getAverage();
        }
    }

    private List<StudentsRank> sortStudentsByGrades(List<StudentsRank> unsortedStudents) {
        return unsortedStudents.stream()
                .sorted((student1, student2) -> student2.getAverage().compareTo(student1.getAverage()))
                .collect(Collectors.toList());
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

        headerRow.createCell(filteredModules.size() + 4).setCellValue("Classement");
        headerRow.getCell(filteredModules.size() + 4).setCellStyle(headerStyle);

        Row subHeaderRow = sheet.createRow(1);
        for (int i = 0; i < filteredModules.size(); i++) {
            subHeaderRow.createCell(i + 3).setCellValue("Coeff: " + filteredModules.get(i).getCoeff());
            subHeaderRow.getCell(i + 3).setCellStyle(headerStyle);
        }
    }

    private void fillStudentData(XSSFSheet sheet, List<StudentDao> studentDaoList, UEEnum ue, String semester, List<ModuleDao> moduleList, XSSFCellStyle cellStyleRed, XSSFCellStyle cellStyleGreen, XSSFCellStyle centeredStyle) {
        List<ModuleDao> filteredModules = moduleList.stream()
                .filter(module -> module.getUeName().equals(ue.name()))
                .toList();

        List<StudentsRank> overallUnsortedStudents = new ArrayList<>();

        for (int i = 0; i < studentDaoList.size(); i++) {
            Row row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue(studentDaoList.get(i).getFirstName() + " " + studentDaoList.get(i).getLastName());
            row.createCell(1).setCellValue(studentDaoList.get(i).getNumEtu());
            row.createCell(2).setCellValue(studentDaoList.get(i).getGroup());

            double totalGrades = 0;
            double totalCoeffModule = 0;

            for (int j = 0; j < filteredModules.size(); j++) {
                ModuleDao module = filteredModules.get(j);
                double totalGradesMultiply = studentDaoList.get(i).getNotes().stream()
                        .filter(note -> note.getModule().getId().equals(module.getId()))
                        .mapToDouble(note -> note.getNote() * note.getCoeff())
                        .sum();

                double totalCoeffs = studentDaoList.get(i).getNotes().stream()
                        .filter(note -> note.getModule().getId().equals(module.getId()))
                        .mapToDouble(NoteDao::getCoeff)
                        .sum();

                if (totalCoeffs == 0) {
                    totalCoeffs = module.getCoeff();
                }

                double averageGrade = totalCoeffs > 0 ? totalGradesMultiply / totalCoeffs : 0.00;
                row.createCell(j + 3).setCellValue(String.format("%.2f", averageGrade));
                if (averageGrade < 10) {
                    row.getCell(j + 3).setCellStyle(cellStyleRed);
                } else {
                    row.getCell(j + 3).setCellStyle(cellStyleGreen);
                }

                totalGrades += averageGrade * module.getCoeff();
                totalCoeffModule += module.getCoeff();
            }

            double ueAverage = totalCoeffModule > 0 ? totalGrades / totalCoeffModule : 0.00;
            row.createCell(filteredModules.size() + 3).setCellValue(String.format("%.2f", ueAverage));
            if (ueAverage < 10) {
                row.getCell(filteredModules.size() + 3).setCellStyle(cellStyleRed);
            } else {
                row.getCell(filteredModules.size() + 3).setCellStyle(cellStyleGreen);
            }

            StudentsRank unsortedStudent = StudentsRank.builder()
                    .numEtu(studentDaoList.get(i).getNumEtu())
                    .average(ueAverage)
                    .build();
            overallUnsortedStudents.add(unsortedStudent);
        }
        List<StudentsRank> overallSortedStudents = sortStudentsByGrades(overallUnsortedStudents);
        int colIndexRanking = filteredModules.size() + 4;
        createRankingColumn(sheet, colIndexRanking, 2, overallSortedStudents, studentDaoList, centeredStyle);
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

    private XSSFCellStyle createCenteredCellStyle(Workbook workbook) {
        XSSFCellStyle centeredStyle = (XSSFCellStyle) workbook.createCellStyle();
        centeredStyle.setAlignment(HorizontalAlignment.CENTER);
        centeredStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        centeredStyle.setWrapText(true);
        return centeredStyle;
    }

    private void applyCenteredStyleToAllCells(XSSFSheet sheet, XSSFCellStyle centeredStyle) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                cell.setCellStyle(centeredStyle);
            }
        }
    }

    public byte[] exportModuleGradesToExcel(String promo, String semester, String ueName, String moduleName) {
        validateInput(promo, semester);

        List<StudentDao> studentDaoList = getStudentList(promo);
        ModuleDao module = getModule(promo, semester, ueName, moduleName);

        try (Workbook workbook = new XSSFWorkbook()) {
            XSSFCellStyle centeredStyle = createCenteredCellStyle(workbook);
            XSSFCellStyle headerStyle = createHeaderStyle(workbook);
            XSSFCellStyle cellStyleRed = createCellStyleRed(workbook);
            XSSFCellStyle cellStyleGreen = createCellStyleGreen(workbook);

            XSSFSheet sheet = (XSSFSheet) workbook.createSheet(module.getName());

            Map<String, Integer> evalColumnMap = createHeaderRowForModule(sheet, headerStyle, module, studentDaoList);

            fillStudentDataForModule(sheet, studentDaoList, module, evalColumnMap, cellStyleRed, cellStyleGreen, centeredStyle);
            autoSizeColumns(sheet, evalColumnMap.size() + 4);

            return writeWorkbookToByteArray(workbook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Integer> createHeaderRowForModule(XSSFSheet sheet, XSSFCellStyle headerStyle, ModuleDao module, List<StudentDao> studentDaoList) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Liste des étudiants");
        headerRow.getCell(0).setCellStyle(headerStyle);

        headerRow.createCell(1).setCellValue("N° étudiant");
        headerRow.getCell(1).setCellStyle(headerStyle);
        headerRow.createCell(2).setCellValue("Groupe");
        headerRow.getCell(2).setCellStyle(headerStyle);

        Map<String, Integer> evalColumnMap = new HashMap<>();
        int colIndex = 3;

        for (StudentDao student : studentDaoList) {
            for (NoteDao note : student.getNotes()) {
                if (note.getModule().getId().equals(module.getId())) {
                    String evalKey = note.getName() + " (Coeff: " + note.getCoeff() + ")";
                    if (!evalColumnMap.containsKey(evalKey)) {
                        evalColumnMap.put(evalKey, colIndex);
                        headerRow.createCell(colIndex).setCellValue(evalKey);
                        headerRow.getCell(colIndex).setCellStyle(headerStyle);
                        colIndex++;
                    }
                }
            }
        }


        headerRow.createCell(colIndex).setCellValue("Moyenne de " + module.getName());
        headerRow.getCell(colIndex).setCellStyle(headerStyle);

        headerRow.createCell(colIndex + 1).setCellValue("Classement");
        headerRow.getCell(colIndex + 1).setCellStyle(headerStyle);

        return evalColumnMap;
    }


    private ModuleDao getModule(String promo, String semester, String ueName, String moduleName) {
        return moduleDaoRepository.findAll().stream()
                .filter(module -> module.getPromo().equals(String.valueOf(PromoEnum.valueOf(promo))))
                .filter(module -> module.getSemester().equals(semester))
                .filter(module -> module.getUeName().equals(ueName))
                .filter(module -> module.getName().equals(moduleName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Module not found for the given parameters"));
    }

    private void fillStudentDataForModule(XSSFSheet sheet, List<StudentDao> studentDaoList, ModuleDao module,
                                          Map<String, Integer> evalColumnMap, XSSFCellStyle cellStyleRed, XSSFCellStyle cellStyleGreen, XSSFCellStyle centeredStyle) {
        List<StudentsRank> overallUnsortedStudents = new ArrayList<>();
        for (int i = 0; i < studentDaoList.size(); i++) {
            Row row = sheet.createRow(i + 1);
            StudentDao student = studentDaoList.get(i);

            row.createCell(0).setCellValue(student.getFirstName() + " " + student.getLastName());
            row.createCell(1).setCellValue(student.getNumEtu());
            row.createCell(2).setCellValue(student.getGroup());

            double totalGrades = 0;
            double totalCoeff = 0;

            for (String evalKey : evalColumnMap.keySet()) {
                int colIndex = evalColumnMap.get(evalKey);
                row.createCell(colIndex).setCellValue("0.00");
            }


            for (NoteDao note : student.getNotes()) {
                if (note.getModule().getId().equals(module.getId())) {
                    String evalKey = note.getName() + " (Coeff: " + note.getCoeff() + ")";
                    Integer colIndex = evalColumnMap.get(evalKey);

                    if (colIndex != null) {
                        double grade = note.getNote();
                        row.getCell(colIndex).setCellValue(String.format("%.2f", grade));
                        if (grade < 10) {
                            row.getCell(colIndex).setCellStyle(cellStyleRed);
                        } else {
                            row.getCell(colIndex).setCellStyle(cellStyleGreen);
                        }
                        totalGrades += grade * note.getCoeff();
                        totalCoeff += note.getCoeff();
                    }
                }
            }
            double averageGrade = totalCoeff > 0 ? totalGrades / totalCoeff : 0.00;
            int avgColIndex = evalColumnMap.size() + 3;
            row.createCell(avgColIndex).setCellValue(String.format("%.2f", averageGrade));
            if (averageGrade < 10) {
                row.getCell(avgColIndex).setCellStyle(cellStyleRed);
            } else {
                row.getCell(avgColIndex).setCellStyle(cellStyleGreen);
            }
            StudentsRank unsortedStudent = StudentsRank.builder()
                    .numEtu(student.getNumEtu())
                    .average(averageGrade)
                    .build();
            overallUnsortedStudents.add(unsortedStudent);
        }
        List<StudentsRank> overallSortedStudents = sortStudentsByGrades(overallUnsortedStudents);
        int colIndexRanking = evalColumnMap.size() + 4;
        createRankingColumn(sheet, colIndexRanking, 1, overallSortedStudents, studentDaoList, centeredStyle);
    }
}