package com.vsm.business.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WordUtils implements IBaseFileUtils {

    private Logger log = LoggerFactory.getLogger(WordUtils.class);

    @Override
    public OutputStream writeTextToFile(Map<String, Object> props, InputStream inputStream) {
        Set<String> listProp = props.keySet();
        OutputStream result = new ByteArrayOutputStream();
        try {
            XWPFDocument doc = new XWPFDocument(OPCPackage.open(inputStream));

            List<XWPFParagraph> paragraphs = doc.getParagraphs();

            for (XWPFParagraph xwpfParagraph : paragraphs) {
                for (String ele : listProp) {
                    String searchValue = ele;
                    Object value = props.get(ele);
                    String replacement = ele;
                    if (List.class.isAssignableFrom(value.getClass())) {
                        replacement = String.join(", ", (List) value);
                    } else {
                        replacement = String.valueOf(value);
                    }
                    replace(xwpfParagraph, searchValue, replacement);
                }
            }


            for (XWPFParagraph p : doc.getParagraphs()) {
                StringBuilder lineText = new StringBuilder();
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        lineText.append(text);
                        if (text != null) {
//                            listProp.stream().forEach(ele -> {
                            for (String ele : listProp) {
                                if (text.contains(ele)) {
                                    Object value = props.get(ele);
                                    if (List.class.isAssignableFrom(value.getClass())) {
                                        r.setText(String.join(", ", (List) value), 0);
                                    } else {
                                        r.setText(String.valueOf(value), 0);
                                    }
                                }
                            }
//                            });
                        }
                    }
                }
            }
            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            List<XWPFRun> runs = p.getRuns();
                            if (runs != null) {
                                for (XWPFRun r : runs) {
                                    String text = r.getText(0);
                                    if (text != null) {
                                        listProp.stream().forEach(ele -> {
                                            if (text.contains(ele)) {
                                                Object value = props.get(ele);
                                                if (List.class.isAssignableFrom(value.getClass())) {
                                                    r.setText(String.join(", ", (List) value), 0);
                                                } else {
                                                    r.setText(String.valueOf(value), 0);
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }
            doc.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void replace(XWPFParagraph paragraph, String searchValue, String replacement) {
        if (hasReplaceableItem(paragraph.getText(), searchValue)) {
            String replacedText = StringUtils.replace(paragraph.getText(), searchValue, replacement);

            removeAllRuns(paragraph);

            insertReplacementRuns(paragraph, replacedText);
        }
    }

    private boolean hasReplaceableItem(String runText, String searchValue) {
        return StringUtils.contains(runText, searchValue);
    }

    private void insertReplacementRuns(XWPFParagraph paragraph, String replacedText) {
        String[] replacementTextSplitOnCarriageReturn = StringUtils.split(replacedText);

        for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
            String part = replacementTextSplitOnCarriageReturn[j];

            XWPFRun newRun = paragraph.insertNewRun(j);
            newRun.setText(part);

            if (j + 1 < replacementTextSplitOnCarriageReturn.length) {
                newRun.addCarriageReturn();
            }
        }
    }


    private void removeAllRuns(XWPFParagraph paragraph) {
        int size = paragraph.getRuns().size();
        for (int i = 0; i < size; i++) {
            paragraph.removeRun(0);
        }
    }


    public OutputStream writeTextToFile_bak(Map<String, Object> props, InputStream inputStream) {
        Set<String> listProp = props.keySet();
        OutputStream result = new ByteArrayOutputStream();
        boolean check = false;
        boolean readNext = false;
        List<XWPFRun> queue = new ArrayList<>();
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(OPCPackage.open(inputStream));
            for (XWPFParagraph p : doc.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null) {
//                            listProp.stream().forEach(ele -> {
                            if (readNext) {
                                if (text.contains("}")) {
                                    String strTmp = String.join("", queue.stream().map(e -> e.getText(0)).collect(Collectors.toList())) + text;
                                    strTmp = strTmp.replaceAll("[^a-zA-Z{} ]", "").trim();
                                    int firstIndex = strTmp.indexOf("{");
                                    if (firstIndex >= 0) {
                                        strTmp = strTmp.substring(firstIndex);
                                    }
                                    int lastIndex = strTmp.lastIndexOf("}");
                                    if (lastIndex >= 0) {
                                        strTmp = strTmp.substring(0, lastIndex + 1);
                                    }
                                    String content;
                                    int firstIndex2;
                                    String raw = " ";
                                    for (XWPFRun tmp : queue) {
                                        content = tmp.getText(0);
                                        if(content.contains("{")){
                                            firstIndex2 = content.indexOf("{");
                                            if(firstIndex2 >= 0){
                                                raw = content.substring(0, firstIndex2);
                                            }
                                            tmp.setText(raw, 0);
                                            raw = "";
                                        }else{
                                            tmp.setText(" ", 0);
                                            raw = "";
                                        }
                                    }
                                    int maxRate = 0;
                                    String strValue = "";
                                    boolean matched = false;
                                    for (String ele : listProp) {
                                        if (strTmp.contains(ele)) {
                                            Object value = props.get(ele);
                                            if (List.class.isAssignableFrom(value.getClass())) {
                                                r.setText(String.join(", ", (List) value), 0);
                                            } else {
                                                r.setText(String.valueOf(value), 0);
                                            }
                                            matched = true;
                                            queue = new ArrayList<>();
                                            break;
                                        }else{
                                            int rate = sameRate(strTmp, ele);
                                            if(rate >= maxRate){
                                                maxRate = rate;
                                                strValue = String.valueOf(props.get(ele));
                                            }
                                        }
                                    }
//                                    if(!matched){
//                                        r.setText(strValue, 0);
//                                        queue = new ArrayList<>();
//                                    }
                                    readNext = false;
                                } else {
                                    queue.add(r);

                                }
                                continue;
                            }
                            check = false;
                            readNext = false;
                            String strTmp2 = "";
                            if (text.contains("{")) {
                                int firstIndex = text.indexOf("{");
                                if (firstIndex >= 0) {
                                    strTmp2 = text.substring(firstIndex);
                                }
                            }
                            if (strTmp2.contains("}")) {
                                int lastIndex = strTmp2.lastIndexOf("}");
                                if (lastIndex >= 0) {
                                    strTmp2 = strTmp2.substring(0, lastIndex + 1);
                                }
                            }
                            for (String ele : listProp) {
                                if (strTmp2.contains(ele.trim())) {
                                    Object value = props.get(ele);
                                    if (List.class.isAssignableFrom(value.getClass())) {
                                        r.setText(String.join(", ", (List) value), 0);
                                    } else {
                                        r.setText(text.replace(strTmp2, String.valueOf(value)), 0);
                                    }
                                    check = true;
                                    queue = new ArrayList<>();
                                }
                            }
//                            });
                            if (!check) {
                                if (text.contains("{")) {
                                    readNext = true;
                                    queue = new ArrayList<>();
                                    queue.add(r);
                                }
                            }
                        }
                    }
                }
            }
            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            List<XWPFRun> runs = p.getRuns();
                            if (runs != null) {
                                for (XWPFRun r : runs) {
                                    String text = r.getText(0);
                                    if (text != null) {
                                        listProp.stream().forEach(ele -> {
                                            if (text.contains(ele)) {
                                                Object value = props.get(ele);
                                                if (List.class.isAssignableFrom(value.getClass())) {
                                                    r.setText(String.join(", ", (List) value), 0);
                                                } else {
                                                    r.setText(String.valueOf(value), 0);
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }
            doc.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }


    private int sameRate(String value, String target) {
        if (value.trim().contains(target.trim()) || target.trim().contains(value.trim())) {
            return 100;
        }
        String[] values = value.split("[^a-zA-Z]");
        int count = 0;
        for (String e : values) {
            if (target.contains(e)) {
                count++;
            }
        }
        return count / values.length * 100;
    }

    // hàm này đang chạy chưa ổn lắm -> do làm thay đổi cấu trúc file
    public OutputStream writeTextToDOCFile(Map<String, Object> props, InputStream inputStream) throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        Set<String> listProp = props.keySet();
        POIFSFileSystem fs = new POIFSFileSystem(inputStream);
        HWPFDocument doc = new HWPFDocument(fs);
        Range r1 = doc.getRange();
        for (int i = 0; i < r1.numSections(); ++i) {
            Section s = r1.getSection(i);
            for (int x = 0; x < s.numParagraphs(); x++) {
                Paragraph p = s.getParagraph(x);
                for (int z = 0; z < p.numCharacterRuns(); z++) {
                    CharacterRun run = p.getCharacterRun(z);
                    String text = run.text();
                    listProp.stream().forEach(ele -> {
                        if (text.contains(ele)) {
                            Object value = props.get(ele);
                            if (List.class.isAssignableFrom(value.getClass())) {
                                run.replaceText(ele, String.join(", ", (List) value));
                            } else {
                                run.replaceText(ele, String.valueOf(value));
                            }
                        }
                    });
                }
            }
        }
        doc.write(outputStream);
        return outputStream;
    }


    /**
     * Hàm thực hiện binding dữ liệu bảng vào trong file docx
     *
     * @param symbol      : ký hiệu sẽ thay thế bằng bảng trong file
     * @param headerTable : tiêu đề của bảng (dạng JSONARRRAY)
     * @param dataTable   : dữ liệu của bảng (dạng JSONARRRAY)
     * @param inputStream : dữ liệu file cần chèn bảng vào
     */
    private void writeTableToWord(String symbol, String headerTable, String dataTable, InputStream inputStream) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\Admin\\Desktop\\Result 1. Mau OTL_QC LED_(E).docx"));

        XWPFDocument document = new XWPFDocument(inputStream);
        JSONArray jsonArrayData = new JSONArray();
        JSONArray jsonArrayHeader = new JSONArray();
        try {
            jsonArrayData = new JSONArray(dataTable);
            jsonArrayHeader = new JSONArray(headerTable);
        } catch (Exception e) {
            log.error("{}", e);
            return;
        }

        try {
            int totalColumn = jsonArrayHeader.length();
            int totalRow = jsonArrayData.length() + 1;
            XWPFTable table = document.createTable(totalRow, totalColumn);

            // build header table \\
            XWPFTableRow firstRow = table.getRow(0);
            for (int i = 0; i < jsonArrayHeader.length(); i++) {
                XWPFParagraph paragraph = firstRow.getCell(i).getParagraphs().get(0);
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = paragraph.createRun();
                run.setBold(true);
                run.setText(jsonArrayHeader.getJSONObject(i).getString("text"));
            }

            for (int i = 1; i <= jsonArrayData.length(); i++) {
                XWPFTableRow row = table.getRow(i);
                for (int j = 0; j < jsonArrayHeader.length(); j++) {
                    JSONObject rowData = jsonArrayData.getJSONObject(i - 1);
                    String fieldName = jsonArrayHeader.getJSONObject(j).getString("value");
                    String value = rowData.getString(fieldName);

                    XWPFParagraph paragraph = row.getCell(j).getParagraphs().get(0);
                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun run = paragraph.createRun();
                    run.setText(value);
                }
            }

            document.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (Exception e) {

        }
    }


    public void writeTableToWordV1(String symbol, String headerTable, String dataTable, InputStream inputStream) throws IOException, InvalidFormatException {
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(inputStream));
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null) {
                        if (text.contains(symbol)) {
                            r.setText("\\n\\n", 0);

                            JSONArray jsonArrayData = new JSONArray();
                            JSONArray jsonArrayHeader = new JSONArray();
                            try {
                                jsonArrayData = new JSONArray(dataTable);
                                jsonArrayHeader = new JSONArray(headerTable);
                            } catch (Exception e) {
                                log.error("{}", e);
                                return;
                            }

                            try {
                                int totalColumn = jsonArrayHeader.length();
                                int totalRow = jsonArrayData.length() + 1;
//                                XWPFTable table = doc.createTable(totalRow, totalColumn);

                                XmlCursor cursor = p.getCTP().newCursor();
                                XWPFTable table = p.getBody().insertNewTbl(cursor);
                                table.setWidth("100%");
                                table.removeRow(0);

                                // build header table \\
                                XWPFTableRow rowOfNestedTable = table.createRow();
                                for (int i = 0; i < jsonArrayHeader.length(); i++) {
                                    XWPFTableCell cellOfNestedTable = rowOfNestedTable.createCell();
                                    XWPFParagraph paragraph = cellOfNestedTable.getParagraphs().get(0);
                                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                                    XWPFRun run = paragraph.createRun();
                                    run.setBold(true);
                                    run.setText(jsonArrayHeader.getJSONObject(i).getString("text"));
                                }

                                for (int i = 1; i <= jsonArrayData.length(); i++) {
                                    XWPFTableRow row = table.createRow();
                                    for (int j = 0; j < jsonArrayHeader.length(); j++) {
                                        JSONObject rowData = jsonArrayData.getJSONObject(i - 1);
                                        String fieldName = jsonArrayHeader.getJSONObject(j).getString("value");
                                        String value = rowData.getString(fieldName);

                                        XWPFTableCell cell = row.getCell(j);
                                        XWPFParagraph paragraph = cell.getParagraphs().get(0);
                                        paragraph.setAlignment(ParagraphAlignment.CENTER);
                                        XWPFRun run = paragraph.createRun();
                                        run.setText(value);
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }
            }
        }


        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\Admin\\Desktop\\Result 1. Mau OTL_QC LED_(E).docx"));
        doc.write(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }


    public static void main(String[] args) throws IOException, InvalidFormatException {
//        WordUtils wordUtils = new WordUtils();
//        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\Admin\\Desktop\\1. Mau OTL_QC LED_(E).docx"));
//        String headerTable = "[{\"text\":\"STT\",\"value\":\"stt\",\"type\":\"string\",\"title\":\"\"},{\"text\":\"Tên\",\"value\":\"ten\",\"type\":\"string\",\"title\":\"\"},{\"text\":\"Tuổi\",\"value\":\"tuoi\",\"type\":\"string\",\"title\":\"\"},{\"text\":\"Vị Trí Làm Việc\",\"value\":\"vitrilamviec\",\"type\":\"string\",\"title\":\"\"},{\"text\":\"Trung Tâm\",\"value\":\"trungtam\",\"type\":\"string\",\"title\":\"\"}]";
//        String dataTable = "[{\"stt\":\"1\",\"ten\":\"Nguyễn Văn A\",\"tuoi\":\"23\",\"vitrilamviec\":\"DEV\",\"trungtam\":\"VCR\"},{\"stt\":\"2\",\"ten\":\"Nguyễn Văn B\",\"tuoi\":\"23\",\"vitrilamviec\":\"DEV\",\"trungtam\":\"VCR\"},{\"stt\":\"3\",\"ten\":\"Nguyễn Thị C\",\"tuoi\":\"23\",\"vitrilamviec\":\"TESTER\",\"trungtam\":\"VCR\"}]";
//
//        wordUtils.writeTableToWordV1("{{DuowngTora}}", headerTable, dataTable, fileInputStream);

        WordUtils wordUtils = new WordUtils();

    }
}

