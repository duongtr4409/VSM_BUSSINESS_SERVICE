package com.vsm.business.utils;

import com.vsm.business.domain.BusinessPartner;
import com.vsm.business.domain.MallAndStall;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.common.Strings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class ExcelUtils implements IBaseFileUtils{

    private final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

    private static final Map<CellType, Function<Cell, String>> handlerGetValueCell = new HashMap<>();
    private static final Map<CellType, Function<Cell, Object>> handlerGetValueCell_v1 = new HashMap<>();
    static {
        handlerGetValueCell.put(CellType.NUMERIC, (cell) -> {
            if(HSSFDateUtil.isCellDateFormatted(cell)){
                cell.getLocalDateTimeCellValue().toString();
            }
            return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
        });
        handlerGetValueCell.put(CellType.STRING, (cell) -> cell.getStringCellValue());
        handlerGetValueCell.put(CellType.BOOLEAN, (cell) -> String.valueOf(cell.getBooleanCellValue()));
        handlerGetValueCell.put(CellType.BLANK, (cell) -> "" /*null*/);
        handlerGetValueCell.put(CellType.FORMULA, (cell) -> {
            switch (cell.getCachedFormulaResultType()){
                case NUMERIC:
                    return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return cell.getStringCellValue();
            }
        });
        handlerGetValueCell.put(CellType._NONE, (cell) -> cell.getStringCellValue());
        handlerGetValueCell.put(CellType.ERROR, (cell) -> null);



        handlerGetValueCell_v1.put(CellType.NUMERIC, (cell) -> {
            if(HSSFDateUtil.isCellDateFormatted(cell)){
                return cell.getLocalDateTimeCellValue().toInstant(ZoneOffset.UTC);
            }
            return Double.valueOf(cell.getNumericCellValue());
        });
        handlerGetValueCell_v1.put(CellType.STRING, (cell) -> cell.getStringCellValue());
        handlerGetValueCell_v1.put(CellType.BOOLEAN, (cell) -> cell.getBooleanCellValue());
        handlerGetValueCell_v1.put(CellType.BLANK, (cell) -> null /*null*/);
        handlerGetValueCell_v1.put(CellType.FORMULA, (cell) -> {
            switch (cell.getCachedFormulaResultType()){
                case NUMERIC:
                    return Double.valueOf(cell.getNumericCellValue());
                case BOOLEAN:
                    return cell.getBooleanCellValue();
                default:
                    return cell.getStringCellValue();
            }
        });
        handlerGetValueCell_v1.put(CellType._NONE, (cell) -> cell.getStringCellValue());
        handlerGetValueCell_v1.put(CellType.ERROR, (cell) -> null);
    }

    public JSONObject readExcel(File file) throws IOException, InvalidFormatException {
        String fileType = Files.probeContentType(file.toPath());
        FileInputStream fileInputStream = new FileInputStream(new File((new File(".").getAbsolutePath() + "/temp/" + file.getName()).replace("/", PATH_SEPARATOR)));
        XSSFWorkbook excelWorkBook = new XSSFWorkbook(fileInputStream);
        int totalWorkSheet = excelWorkBook.getNumberOfSheets();

        JSONObject result = new JSONObject();

        for (int i = 0; i < totalWorkSheet; i++) {
            Sheet sheet = excelWorkBook.getSheetAt(i);
            if (sheet == null) continue;
            if (sheet.getSheetName() != null && !sheet.getSheetName().isEmpty()) {
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                if (lastRow >= firstRow) {

                    JSONArray sheetDataJson = new JSONArray();

                    for (int rowNum = firstRow; rowNum <= lastRow; rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        int firstCell = row.getFirstCellNum();
                        int lastCell = row.getLastCellNum();
                        if (lastCell >= firstCell) {

                            JSONObject rowDataJson = new JSONObject();

                            for (int cellNum = firstCell; cellNum < lastCell; cellNum++) {
                                Cell cell = row.getCell(cellNum);
                                if (cell == null) continue;
//                                String value = "";
//                                String fieldName = this.getFieldName(handlerGetValueCell.get(sheet.getRow(0).getCell(cellNum).getCellType()).apply(sheet.getRow(0).getCell(cellNum)));
//                                if (handlerGetValueCell.containsKey(cell.getCellType())) {
//                                    value = handlerGetValueCell.get(cell.getCellType()).apply(cell);
//                                } else {
//                                    value = String.format("");
//                                }
//
//                                rowDataJson.put(fieldName, value);

                            }

                            sheetDataJson.put(rowDataJson);

                        }
                    }

                    result.put(sheet.getSheetName(), sheetDataJson);

                }
            }
        }
        return result;
    }



    public <T> List<T> readAllExcelV1(File folder, List<String> listFieldName, Class<T> tclass) throws IOException, InvalidFormatException {
        if(!folder.exists()) folder.mkdir();
        List<T> result = new ArrayList<>();
        Stream<Path> paths = Files.walk(Paths.get(folder.getAbsolutePath()));
        paths.filter(ele -> {
            return Files.isRegularFile(ele);
        }).forEach(ele -> {
            File excelFile = ele.toFile();
            try {
                result.addAll(this.readExcelV1(excelFile, listFieldName, tclass));
            } catch (IOException e) {throw new RuntimeException(e);} catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
               excelFile.delete();
            }
        });
        return result;
    }

    public <T> List<T> readExcelV1(File file, List<String> listFieldName, Class<T> tclass) throws IOException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<T> result = new ArrayList<>();
        String[] fileName = file.getAbsolutePath().split("\\.");
        String fileExtenstion = fileName[fileName.length-1];
        List<String> supportType = Arrays.asList("xlsx", "xlsb", "xls");
        if(!supportType.stream().anyMatch(ele -> ele.equalsIgnoreCase(fileExtenstion))){
            log.info("File Type: {} not support.", fileExtenstion);
            return null;
        }

        FileInputStream fileInputStream = new FileInputStream(file);

        XSSFWorkbook excelWorkBook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = excelWorkBook.getSheetAt(0);
        if(Strings.isNullOrEmpty(sheet.getSheetName())){
            log.info("SheetName is null or empty");
            return null;
        }
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        if(lastRow < firstRow){
            log.info("lastRow less than firstRow");
        }
        for(int rowNum = firstRow; rowNum <= lastRow; rowNum++){
            if(rowNum == 0) continue;
            try{
                Row row = sheet.getRow(rowNum);
                int firstCell = row.getFirstCellNum();
                int lastCell = row.getLastCellNum();
                if(lastCell >= firstCell){
                    T data = tclass.newInstance();
                    Class<?> dataClass = data.getClass();
                    for(int cellNum = firstCell; cellNum <= lastCell; cellNum++){
                        Cell cell = row.getCell(cellNum);
                        if(cell == null) continue;
                        Object value = "";
                        if(cellNum < listFieldName.size()){
                            String fieldName = listFieldName.get(cellNum);
                            if(handlerGetValueCell_v1.containsKey(cell.getCellType())){
                                value = handlerGetValueCell_v1.get(cell.getCellType()).apply(cell);
                            }else{
                                value = null;
                            }
                            Field field = dataClass.getDeclaredField(fieldName);
                            field.setAccessible(true);
                            Object newValue = null;
                            if(field.getType().isAssignableFrom(Long.class)){
                                newValue = ((Double) value).longValue();
                            }else if(field.getType().isAssignableFrom(Integer.class)){
                                newValue = ((Double) value).intValue();
                            }else if(field.getType().isAssignableFrom(Double.class)){
                                newValue = (Double) value;
                            }else if(field.getType().isAssignableFrom(Instant.class)){
                                newValue = (Instant) value;
                            }else if(field.getType().isAssignableFrom(Boolean.class)){
                                newValue = (Boolean) value;
                            }else {
                                newValue = String.valueOf(value);
                            }
                            field.set(data, newValue);
                        }
                    }
                    result.add(data);
                }
            }catch(Exception e){
                log.error("Exeption when read row {} of file {}: {}", rowNum, file.getAbsolutePath(), e);
            }
        }
        fileInputStream.close();
        return result;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        File projectFolder = new File("./");
        File MallAndStallFolder = new File(projectFolder.getAbsolutePath()+"/temp/excel/MallAndStall");
        List<String> listFieldName = Arrays.asList("stt", "companyCode", "profitCenter", "profitCenterName", "businessEntity", "maKhachHang", "tenKhachHang", "tenNVKD", "soLo", "tenGianHang", "dienTich", "maHopDong", "ngayKetThucHD");
        ExcelUtils excelUtils = new ExcelUtils();
        List<MallAndStall> mallAndStalls = excelUtils.readAllExcelV1(MallAndStallFolder, listFieldName, MallAndStall.class);

        List<String> listFileNameOfBp = Arrays.asList("bpCode" , "bpName" , "addressNumber" , "customer" , "name" , "street" , "telephone" , "vatTegistrationNo" , "eMailAddress1" , "eMailAddress2");
        File BPFolder = new File(projectFolder.getAbsolutePath()+"/temp/excel/BussinessPartner");
        List<BusinessPartner> bussinessPartners = excelUtils.readAllExcelV1(BPFolder, listFileNameOfBp, BusinessPartner.class);

        System.out.println("mallAndStalls: " + mallAndStalls);
        System.out.println("bussinessPartners: " + bussinessPartners);

        System.out.println("End.");
    }


    @Autowired
    public VNCharacterUtils vnCharacterUtils;
    private String getFieldName(String field){
        if(field == null) return "";
        String result = this.vnCharacterUtils.removeAccent(field);
        result = result.replaceAll("\\s", "_");
        return result;
    }









    @Override
    public OutputStream writeTextToFile(Map<String, Object> props, InputStream inputStream) throws IOException {
        Set<String> listProp = props.keySet();
        OutputStream result = new ByteArrayOutputStream();
        XSSFWorkbook excelWorkBook = new XSSFWorkbook(inputStream);
        int totalWorkSheet = excelWorkBook.getNumberOfSheets();
        for(int i=0; i<totalWorkSheet; i++){
            Sheet sheet = excelWorkBook.getSheetAt(i);
            if(sheet == null) continue;
            if(sheet.getSheetName() != null && !sheet.getSheetName().isEmpty()){
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                if(lastRow >= firstRow){
                    for(int rowNum=firstRow; rowNum<=lastRow; rowNum++){
                        Row row = sheet.getRow(rowNum);
                        int firstCell = row.getFirstCellNum();
                        int lastCell = row.getLastCellNum();
                        if(lastCell >= firstCell){
                            for(int cellNum=firstCell; cellNum<lastCell; cellNum++){
                                Cell cell = row.getCell(cellNum);
                                if(cell == null) continue;
                                String value = "";
                                value = handlerGetValueCell.get(cell.getCellType()).apply(cell).toString();
                                String finalValue = value;
                                String key = listProp.stream().filter(ele -> ele.equals(finalValue)).findFirst().orElse(null);
                                if(key != null){
                                    cell.setCellValue(props.get(key).toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        excelWorkBook.write(result);
        result.flush();
        result.close();
        return result;
    }
}
