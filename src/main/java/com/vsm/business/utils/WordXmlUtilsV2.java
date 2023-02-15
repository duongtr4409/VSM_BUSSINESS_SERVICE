package com.vsm.business.utils;

import com.google.common.io.Files;
import com.vsm.business.service.custom.RequestDataCustomService;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.elasticsearch.common.Strings;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.file.FileSystems;
import java.util.*;

@Component
public class WordXmlUtilsV2 implements IBaseFileUtils{

    private Logger log = LoggerFactory.getLogger(WordXmlUtilsV2.class);

    @Value("${system.folder.TEMP_FOLDER:./temp/}")
    public String tempFolder;

    private static final String PATH_SEPARATOR = FileSystems.getDefault().getSeparator();

//    @Override
//    public OutputStream writeTextToFile(Map<String, Object> props, InputStream inputStream) throws IOException {
//        OutputStream result = new ByteArrayOutputStream();
//
//        Map<String, String> placeholderMap = new HashMap<>();
//        Map<String, RequestDataCustomService.TableFieldDuowngTora> tablesMap = new HashMap<>();
//        Set<String> listProps = props.keySet();
//        listProps.forEach(ele -> {
//            try {
//                Object value = props.get(ele);
//                if(value instanceof RequestDataCustomService.TableFieldDuowngTora){     // nếu là field dạng bảng
//                    RequestDataCustomService.TableFieldDuowngTora tableFieldDuowngTora = (RequestDataCustomService.TableFieldDuowngTora) value;
//                    tablesMap.put(ele, tableFieldDuowngTora);
//                }else{
//                    String newValue;
//                    if(List.class.isAssignableFrom(value.getClass())){
//                        newValue = String.join(", ", (List) value);
//                    }else if(Set.class.isAssignableFrom(value.getClass())){
//                        newValue = String.join(", ", (Set) value);
//                    }else{
//                        newValue = String.valueOf(value);
//                    }
//                    placeholderMap.put(ele, newValue);
//                }
//            }catch (Exception e){log.debug("{}", e);}
//        });
//
//        String tempFolderPath = (tempFolder + String.valueOf(System.currentTimeMillis()) + PATH_SEPARATOR).replace("//", PATH_SEPARATOR);
//        File folderTemp = new File(tempFolderPath);
//        if(!folderTemp.exists()) folderTemp.mkdir();
//
//        String timeMillisTemp = String.valueOf(System.currentTimeMillis());
//        File sourceFileTemp = new File(folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngTora_" + timeMillisTemp + ".docx");
//        File resultFileTemp = new File(folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngToraResult_" + timeMillisTemp + ".docx");
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(sourceFileTemp);
//            byte[] bytes = IOUtils.toByteArray(inputStream);
//            fileOutputStream.write(bytes);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//
//            //this.writeTextToDocx_v2(sourceFileTemp, placeholderMap, resultFileTemp);
//            this.replaceTextInDocFile(sourceFileTemp, placeholderMap, tablesMap, resultFileTemp);
//
////            FileInputStream fileInputStream = new FileInputStream(resultFileTemp);
//            byte[] byteResult = Files.toByteArray(resultFileTemp);
//            result.write(byteResult);
//            result.flush();
//            result.close();
//        }catch (Exception e){
//            log.error("{}", e);
//        }finally {
//            sourceFileTemp.delete();
//            resultFileTemp.delete();
//            folderTemp.delete();
//        }
//        return result;
//    }
//
//    private void replaceTextInDocFile(File fileSource, Map<String, String> placeholderMap, Map<String, RequestDataCustomService.TableFieldDuowngTora> tableMap, File fileResult) throws IOException, Docx4JException {
//        WordprocessingMLPackage template = getTemplate(fileSource);
//        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
//        searchAndReplace2(texts, placeholderMap);
//        writeDocxToStream(template, fileResult);
//    }
//
//    private WordprocessingMLPackage getTemplate(File file) throws Docx4JException, FileNotFoundException {
//        WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(file));
//        return template;
//    }
//
//    private List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
//        List<Object> result = new ArrayList<Object>();
//        if (obj instanceof JAXBElement)
//            obj = ((JAXBElement<?>) obj).getValue();
//
//        if (obj.getClass().equals(toSearch))
//            result.add(obj);
//        else if (obj instanceof ContentAccessor) {
//            List<?> children = ((ContentAccessor) obj).getContent();
//            for (Object child : children) {
//                result.addAll(getAllElementFromObject(child, toSearch));
//            }
//
//        }
//        return result;
//    }
//
//
//    public static void searchAndReplace2(List<Object> texts, Map<String, String> values) {
//        // -- scan all expressions
//        // Will later contain all the expressions used though not used at the
//        // moment
//        List<String> els = new ArrayList<String>();
//
//        StringBuilder sb = new StringBuilder();
//        int PASS = 0;
//        int PREPARE = 1;
//        int READ = 2;
//        int PREEND = 3;
//        int mode = PASS;
//
//        // to nullify
//        List<int[]> toNullify = new ArrayList<int[]>();
//        int[] currentNullifyProps = new int[4];
//
//        // Do scan of els and immediately insert value
//        for (int i = 0; i < texts.size(); i++) {
//            Object text = texts.get(i);
//            Text textElement = (Text) text;
//            String newVal = "";
//            String v = textElement.getValue();
//            // System.out.println("text: "+v);
//            StringBuilder textSofar = new StringBuilder();
//            int extra = 0;
//            char[] vchars = v.toCharArray();
//            for (int col = 0; col < vchars.length; col++) {
//                char c = vchars[col];
//                textSofar.append(c);
//                switch (c) {
//                    // case '$': {
//                    // mode=PREPARE;
//                    // sb.append(c);
//                    //// extra = 0;
//                    // } break;
//                    case '{': {
//                        if (mode == PASS) {
//                            mode = PREPARE;
//                            sb.append(c);
//                            break;
//                        } else if (mode == PREPARE) {
//                            sb.append(c);
//                            mode = READ;
//                            currentNullifyProps[0] = i;
//                            currentNullifyProps[1] = col + extra - 1;
//                            System.out.println("extra-- " + extra);
//                        } else {
//                            if (mode == READ) {
//                                // consecutive opening curl found. just read it
//                                // but supposedly throw error
//                                sb = new StringBuilder();
//                                mode = PASS;
//                            }
//                        }
//                    }
//                    break;
//                    case '}': {
//                        if (mode == READ) {
//                            mode = PREEND;
//                            sb.append(c);
//                            break;
//                        } else if (mode == PREEND) {
//                            mode = PASS;
//                            sb.append(c);
//                            els.add(sb.toString());
//                            newVal += textSofar.toString()
//                                + (null == values.get(sb.toString()) ? sb.toString() : values.get(sb.toString()));
//                            textSofar = new StringBuilder();
//                            currentNullifyProps[2] = i;
//                            currentNullifyProps[3] = col + extra;
//                            toNullify.add(currentNullifyProps);
//                            currentNullifyProps = new int[4];
//                            extra += sb.toString().length();
//                            sb = new StringBuilder();
//                        } else if (mode == PREPARE) {
//                            mode = PASS;
//                            sb = new StringBuilder();
//                        }
//                    }
//                    default: {
//                        if (mode == READ)
//                            sb.append(c);
//                        else if (mode == PREPARE) {
//                            mode = PASS;
//                            sb = new StringBuilder();
//                        }
//                    }
//                }
//            }
//
//            newVal += textSofar.toString();
//            textElement.setValue(newVal);
////            System.out.println("newVal:  "+newVal);
////            System.out.println("currentNullifyProps Array: " + Arrays.toString(currentNullifyProps));
//        }
//
//        for (int[] elm : toNullify)
//            System.out.println("toNullify Array: " + Arrays.toString(elm));
//
//        // remove original expressions
//        if (toNullify.size() > 0) {
//            for (int i = 0; i < texts.size(); i++) {
//                if (toNullify.size() == 0)
//                    break;
//                currentNullifyProps = toNullify.get(0);
//                Object text = texts.get(i);
//                Text textElement = (Text) text;
//                String v = textElement.getValue();
//                StringBuilder nvalSB = new StringBuilder();
//                char[] textChars = v.toCharArray();
//                for (int j = 0; j < textChars.length; j++) {
//                    char c = textChars[j];
//                    if (null == currentNullifyProps) {
//                        nvalSB.append(c);
//                        continue;
//                    }
//                    // I know 100000 is too much!!! And so what???
//                    int floor = currentNullifyProps[0] * 100000 + currentNullifyProps[1];
//                    int ceil = currentNullifyProps[2] * 100000 + currentNullifyProps[3];
//                    int head = i * 100000 + j;
//                    if (!(head >= floor && head <= ceil)) {
//                        nvalSB.append(c);
//                    }
//
//                    if (j > currentNullifyProps[3] && i >= currentNullifyProps[2]) {
//                        toNullify.remove(0);
//                        if (toNullify.size() == 0) {
//                            currentNullifyProps = null;
//                            continue;
//                        }
//                        currentNullifyProps = toNullify.get(0);
//                    }
//                }
//                textElement.setValue(nvalSB.toString());
//            }
//        }
//    }
//
//    private void writeDocxToStream(WordprocessingMLPackage template, File file)
//        throws IOException, Docx4JException {
//        template.save(file);
//    }



    @Override
    public OutputStream writeTextToFile(Map<String, Object> props, InputStream inputStream) throws IOException {
        OutputStream result = new ByteArrayOutputStream();

        Map<String, String> placeholderMap = new HashMap<>();
        Map<String, RequestDataCustomService.TableFieldDuowngTora> tablesMap = new HashMap<>();
        Set<String> listProps = props.keySet();
        listProps.forEach(ele -> {
            try {
                Object value = props.get(ele);
                if(value instanceof RequestDataCustomService.TableFieldDuowngTora){     // nếu là field dạng bảng
                    RequestDataCustomService.TableFieldDuowngTora tableFieldDuowngTora = (RequestDataCustomService.TableFieldDuowngTora) value;
                    tablesMap.put(ele, tableFieldDuowngTora);
                }else{
                    String newValue;
                    if(List.class.isAssignableFrom(value.getClass())){
                        newValue = String.join(", ", (List) value);
                    }else if(Set.class.isAssignableFrom(value.getClass())){
                        newValue = String.join(", ", (Set) value);
                    }else{
                        newValue = String.valueOf(value);
                    }

                    if(!Strings.isNullOrEmpty(newValue))
                        placeholderMap.put(ele, newValue);
                    else
                        placeholderMap.put(ele, "");

                }
            }catch (Exception e){log.debug("{}", e);}
        });

        String tempFolderPath = (tempFolder + String.valueOf(System.currentTimeMillis()) + PATH_SEPARATOR).replace("//", PATH_SEPARATOR);
        File folderTemp = new File(tempFolderPath);
        if(!folderTemp.exists()) folderTemp.mkdir();

        String timeMillisTemp = String.valueOf(System.currentTimeMillis());
        File sourceFileTemp = new File(folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngTora_" + timeMillisTemp + ".docx");
        File resultFileTemp = new File(folderTemp.getAbsolutePath() + PATH_SEPARATOR + "DuowngToraResult_" + timeMillisTemp + ".docx");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(sourceFileTemp);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();

            //this.writeTextToDocx_v2(sourceFileTemp, placeholderMap, resultFileTemp);
            this.replaceTextInDocFile(sourceFileTemp, placeholderMap, tablesMap, resultFileTemp);

//            FileInputStream fileInputStream = new FileInputStream(resultFileTemp);
            byte[] byteResult = Files.toByteArray(resultFileTemp);
            result.write(byteResult);
            result.flush();
            result.close();
        }catch (Exception e){
            log.error("{}", e);
        }finally {
            inputStream.close();
            sourceFileTemp.delete();
            resultFileTemp.delete();
            folderTemp.delete();
        }
        return result;
    }


    private void replaceTextInDocFile(File fileSource, Map<String, String> placeholderMap, Map<String, RequestDataCustomService.TableFieldDuowngTora> tablesMap, File fileResult) throws Docx4JException, IOException, JAXBException {
        WordprocessingMLPackage wordprocessingMLPackage = WordprocessingMLPackage.load(fileSource);
        MainDocumentPart documentPart = wordprocessingMLPackage.getMainDocumentPart();
        //replaceParagraph(placeholderMap, wordprocessingMLPackage, wordprocessingMLPackage.getMainDocumentPart());
        replaceChildTable(tablesMap, wordprocessingMLPackage);
        writeDocxToStream(wordprocessingMLPackage, fileResult);
    }


    private void replaceParagraph(Map<String, String> placeholderMap, WordprocessingMLPackage template, ContentAccessor addTo){
        List<Object> paragraphs = getAllElementFromObject(template.getMainDocumentPart(), P.class);
        Set<String> keys = placeholderMap.keySet();
        for (Object p : paragraphs) {
            List<Object> texts = getAllElementFromObject(p, Text.class);
            StringBuilder contentOfParaGraphs = new StringBuilder();
            for (Object t : texts) {
                Text content = (Text) t;
                contentOfParaGraphs.append(content.getValue());
            }
            keys.forEach(placeholder -> {
                if (contentOfParaGraphs.toString().contains(placeholder)) {
                    P toReplace = (P) p;
                    String  textToAdd = placeholderMap.get(placeholder);
                    String as[] = StringUtils.splitPreserveAllTokens(textToAdd, '\n');
                    for (int i = 0; i < as.length; i++) {
                        String ptext = as[i];
                        // 3. copy the found paragraph to keep styling correct
                        P copy = (P) XmlUtils.deepCopy(toReplace);
                        // replace the text elements from the copy
                        List<?> textsOfParagraph = getAllElementFromObject(copy, Text.class);
                        if (textsOfParagraph.size() > 0) {
                            Text textToReplace = (Text) textsOfParagraph.get(0);
                            textToReplace.setValue(ptext);
                        }
                        // add the paragraph to the document
                        addTo.getContent().add(copy);
                    }
                    // 4. remove the original one
                    ((ContentAccessor)toReplace.getParent()).getContent().remove(toReplace);
                }
            });

        }
    }

    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if(obj == null) return result;
        if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();

        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;
    }

    private void replaceChildTable(Map<String, RequestDataCustomService.TableFieldDuowngTora> tablesMap, WordprocessingMLPackage template) throws JAXBException, Docx4JException, IOException {
        if(tablesMap == null || tablesMap.isEmpty()) return;

        List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);
        List<Object> childTable = new ArrayList<>();
        for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
            Tbl tbl = (Tbl) iterator.next();
            walkTable(tbl, childTable);
        }

        Map<String, List<String>> allHeaders = new HashMap<>();
        Map<String, List<Map<String, String>>> allBodyTables = new HashMap<>();

        Set<String> keys = tablesMap.keySet();
        for(String key : keys){
            RequestDataCustomService.TableFieldDuowngTora tableFieldDuowngTora = tablesMap.get(key);
            if(tableFieldDuowngTora != null){
                List<String> headerKey = new ArrayList<>();
                for(int i=0; i<tableFieldDuowngTora.getHeaders().size(); i++){
                    try {
                        JSONObject jsonObjectHeader = new JSONObject(tableFieldDuowngTora.getHeaders().get(i));
                        headerKey.add(jsonObjectHeader.getString("value"));
                    }catch (Exception e){
                        log.error("{}", e);
                        headerKey.add("");
                    }
                }

                List<Map<String, String>> bodyTable = new ArrayList<>();
                for(String rowData : tableFieldDuowngTora.getBody()){
                    if(Strings.isNullOrEmpty(rowData)) continue;
                    try {
                        JSONObject jsonObjectRowData = new JSONObject(rowData);
                        Map<String, String> mapRowData = new HashMap<>();
                        for(int i=0; i<headerKey.size(); i++){
                            String keyData = headerKey.get(i);
                            String valueData = null;
                            try {
                                valueData = jsonObjectRowData.getString(keyData);
                            }catch (JSONException jsonExceptione) {
                                log.error("JSONException: {}", jsonExceptione);
                                valueData = jsonObjectRowData.get(keyData).toString();
                            }
                            if(Strings.isNullOrEmpty(valueData)) valueData = "-";
                            mapRowData.put(keyData, valueData);
                        }
                        bodyTable.add(mapRowData);
                    }catch (Exception e){
                        log.error("{}", e);
                    }
                }
                if(bodyTable != null /*&& !bodyTable.isEmpty()*/){
                    if(bodyTable.isEmpty()){
                        Map<String, String> emptyData = new HashMap<>();
                        for(int i=0; i<headerKey.size(); i++){
                            emptyData.put(headerKey.get(i), "");
                        }
                        bodyTable.add(emptyData);
                    }

                    allHeaders.put(key, headerKey);
                    allBodyTables.put(key, bodyTable);

                    if(childTable != null && !childTable.isEmpty())
                        replaceTable(headerKey, bodyTable, template, childTable);
                    //tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);
                    //replaceTable(headerKey, bodyTable, template, tables);
                }
            }
        }
        replaceTable(allHeaders, allBodyTables, template);
    }

    private void replaceTable(Map<String, List<String>> allHeaders, Map<String, List<Map<String, String>>> allBodyTables, WordprocessingMLPackage template) throws JAXBException, Docx4JException {
        List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);
        if(allHeaders != null && !allHeaders.isEmpty()){
            Set<String> keys = allHeaders.keySet();
            for(String key : keys){
                List<Map<String, String>> bodyTable = allBodyTables.get(key);
                replaceTable(allHeaders.get(key), bodyTable, template, tables);
            }
        }
    }

    private void replaceTable(List<String> placeholders, List<Map<String, String>> textToAdd, WordprocessingMLPackage template, List<Object> listTable) throws JAXBException, Docx4JException {
        // 1. find the table
        Tbl tempTable = getTemplateTable(listTable, placeholders.get(0));
        List<Object> rows = getAllElementFromObject(tempTable, Tr.class);
        // first row is header, second row is content
        if (rows.size() >= 2) {
            // this is our template row
            Tr templateRow = (Tr) rows.get(1);
            for (Map<String, String> replacements : textToAdd) {
                // 2 and 3 are done in this method
                addRowToTable(tempTable, templateRow, replacements);
            }
            // 4. remove the template row
            tempTable.getContent().remove(templateRow);
            // 5. move old row of table to last row table
            if(rows.size() > 2) {
                for (int i = 2; i < rows.size(); i++) {
                    tempTable.getContent().remove(rows.get(i));
                    tempTable.getContent().add(rows.get(i));
                }
            }
        }
    }

    private static void addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements) {
        Tr workingRow = (Tr) XmlUtils.deepCopy(templateRow);
        List<?> textElements = getAllElementFromObject(workingRow, Text.class);
        for (Object object : textElements) {
            Text text = (Text) object;
            String replacementValue = (String) replacements.get(text.getValue());
            if (replacementValue != null)
                text.setValue(replacementValue);
        }

        reviewtable.getContent().add(workingRow);
    }

    private Tbl getTemplateTable(List<Object> tables, String templateKey) throws Docx4JException, JAXBException {
        for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
            Object tbl = iterator.next();
            List<?> textElements = getAllElementFromObject(tbl, Text.class);
            for (Object text : textElements) {
                Text textElement = (Text) text;
                if (textElement.getValue() != null && textElement.getValue().equals(templateKey))
                    return (Tbl) tbl;
            }
        }
        return null;
    }


    // backup ngày 28/07/2022: tìm cách fix lỗi làm hỏng bảng nếu có table trong table
    private Tbl getTemplateTable_bak(List<Object> tables, String templateKey) throws Docx4JException, JAXBException {
        for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
            Object tbl = iterator.next();
            List<?> textElements = getAllElementFromObject(tbl, Text.class);
            for (Object text : textElements) {
                Text textElement = (Text) text;
                if (textElement.getValue() != null && textElement.getValue().equals(templateKey))
                    return (Tbl) tbl;
            }
        }
        return null;
    }

    private void writeDocxToStream(WordprocessingMLPackage template, File fileTarget) throws IOException, Docx4JException {
        template.save(fileTarget);
    }

    private void writeDocxToByteArrayStream(WordprocessingMLPackage template, OutputStream outputStream) throws IOException, Docx4JException {
        template.save(outputStream);
    }

    private static void walkTable(Tbl table, List<Object> nestedtables) {
        //onTable(table);
        //System.out.println("walkTable ");
        for (Object contentElement : table.getContent()) {
            if (XmlUtils.unwrap(contentElement) instanceof Tr) {
                Tr row = (Tr) contentElement;
                walkTableRow(row, nestedtables);
            }
        }
    }

    private static void walkTableRow(Tr row, List<Object> nestedtables) {
        //onTableRow(row);
        //System.out.println("walkTableRow ");
        for (Object rowContentElement : row.getContent()) {
            if (XmlUtils.unwrap(rowContentElement) instanceof Tc) {
                Tc cell = rowContentElement instanceof Tc ? (Tc) rowContentElement : (Tc) ((JAXBElement) rowContentElement).getValue();
                walkTableCell(cell, nestedtables);
            }
        }
    }

    private static void walkTableCell(Tc cell, List<Object> nestedtables) {
        //onTableCell(cell);
        //System.out.println("walkTableCell ");
        for (Object cellContentElement : cell.getContent()) {
            if (XmlUtils.unwrap(cellContentElement) instanceof P) {
                P p = (P) cellContentElement;
                walkParagraph(p);
            } else if (XmlUtils.unwrap(cellContentElement) instanceof Tbl) {
                Tbl nestedTable = (Tbl) ((JAXBElement) cellContentElement).getValue();
                nestedtables.add(nestedTable);
                //walkTable(nestedTable);
            }
        }
    }

    private static void walkParagraph(P p) {
        //onParagraph(p);
        for (Object element : p.getContent()) {
            if (XmlUtils.unwrap(element) instanceof CommentRangeStart) {
                CommentRangeStart commentRangeStart = (CommentRangeStart) element;

            } else if (XmlUtils.unwrap(element) instanceof CommentRangeEnd) {
                CommentRangeEnd commentRangeEnd = (CommentRangeEnd) element;

            }
        }
    }
}
