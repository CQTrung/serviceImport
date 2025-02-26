package org.example.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class ImportExcelService {

    public HashMap<Integer, ArrayList<Object>> importFileExcelBySheet(MultipartFile file, Integer sheetIndex)
            throws IOException {
        HashMap<Integer, ArrayList<Object>> data = new HashMap<>();

        try (InputStream fis = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            int endCell = sheet.getRow(0).getLastCellNum();
            int endRow = sheet.getLastRowNum();
            int rowIndex = 0;

            for (int i = 0; i <= endRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue; // Skip empty rows
                }

                ArrayList<Object> columns = new ArrayList<>();
                boolean isRowEmpty = true;

                for (int j = 0; j < endCell; j++) {
                    Cell cell = row.getCell(j);
                    Object cellValue = getCellValue(cell);

                    if (cellValue != null && cellValue.toString().startsWith("http")) {
                        // Handle URL separately if needed
                        columns.add(cellValue.toString());
                    } else {
                        columns.add(cellValue);
                    }

                    if (cellValue != null && !cellValue.toString().isEmpty()) {
                        isRowEmpty = false;
                    }
                }

                if (!isRowEmpty) {
                    data.put(rowIndex, columns);
                    rowIndex++;
                }
            }
        } catch (Exception e) {
            log.error("Error reading Excel file", e);
            throw e; // rethrow the exception after logging it
        }

        return data;
    }

    public HashMap<Integer, ArrayList<Object>> importFileExcelBySheet2(MultipartFile file, Integer sheetIndex)
            throws IOException {
        HashMap<Integer, ArrayList<Object>> data = new HashMap<>();

        try (InputStream fis = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            int endCell = sheet.getRow(0).getLastCellNum();
            int endRow = sheet.getLastRowNum();
            int rowIndex = 1;

            for (int i = 2; i <= endRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue; // Skip empty rows
                }

                ArrayList<Object> columns = new ArrayList<>();
                boolean isRowEmpty = true;

                for (int j = 0; j < endCell; j++) {
                    Cell cell = row.getCell(j);
                    Object cellValue = getCellValue(cell);

                    if (cellValue != null && cellValue.toString().startsWith("http")) {
                        // Handle URL separately if needed
                        columns.add(cellValue.toString());
                    } else {
                        columns.add(cellValue);
                    }

                    if (cellValue != null && !cellValue.toString().isEmpty()) {
                        isRowEmpty = false;
                    }
                }

                if (!isRowEmpty) {
                    data.put(rowIndex, columns);
                    rowIndex++;
                }
            }
        } catch (Exception e) {
            log.error("Error reading Excel file", e);
            throw e; // rethrow the exception after logging it
        }

        return data;
    }
    public HashMap<Integer, ArrayList<Object>> importFileExcelBySheet3(MultipartFile file, Integer sheetIndex)
            throws IOException {
        HashMap<Integer, ArrayList<Object>> data = new HashMap<>();

        try (InputStream fis = file.getInputStream(); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            int endCell = sheet.getRow(0).getLastCellNum();
            int endRow = sheet.getLastRowNum();
            int rowIndex = 1;

            for (int i = 2; i <= endRow; i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue; // Skip empty rows
                }

                ArrayList<Object> columns = new ArrayList<>();
                boolean isRowEmpty = true;

                for (int j = 0; j < endCell; j++) {
                    Cell cell = row.getCell(j);
                    Object cellValue = getCellValue(cell);

                    if (cellValue != null && cellValue.toString().startsWith("http")) {
                        // Handle URL separately if needed
                        columns.add(cellValue.toString());
                    } else {
                        columns.add(cellValue);
                    }

                    if (cellValue != null && !cellValue.toString().isEmpty()) {
                        isRowEmpty = false;
                    }
                }

                if (!isRowEmpty) {
                    data.put(rowIndex, columns);
                    rowIndex++;
                }
            }
        } catch (Exception e) {
            log.error("Error reading Excel file", e);
            throw e; // rethrow the exception after logging it
        }

        return data;
    }

    private Object getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case NUMERIC:
                        return cell.getNumericCellValue();
                    case STRING:
                        return cell.getStringCellValue();
                    default:
                        return "";
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case BLANK:
            case _NONE:
            case ERROR:
            default:
                return "";
        }
    }
}


