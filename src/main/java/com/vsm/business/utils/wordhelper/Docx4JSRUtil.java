package com.vsm.business.utils.wordhelper;

import com.google.common.base.Strings;
import com.vsm.business.service.custom.RequestDataCustomService;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Component
public class Docx4JSRUtil {

    @Autowired
    public StringFindUtil stringFindUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(Docx4JSRUtil.class);

    /**
     * Searches for all occurrences of the placeholders in the parsed docx-document and replaces all of them
     * inside the {@link WordprocessingMLPackage}-object.
     *
     * @param docxDocument Docx4J-parsed document
     * @param replaceMap Map with all placeholders and their new values.
     */
    // more info: https://stackoverflow.com/questions/19676282/docx4j-find-and-replace/60384502#60384502
    public static void searchAndReplace(WordprocessingMLPackage docxDocument, Map<String, String> replaceMap) {
        // All Text-objects in correct order
        List<Text> texts = getAllElementsOfType(docxDocument.getMainDocumentPart(), Text.class);
        String completeString = getCompleteString(texts);
        if (completeString.isEmpty()) {
            return;
        }

        List<TextMetaItem> metaItemList = buildMetaItemList(texts);

        // with this array we can lookup for each index in completeString what is the corresponding
        // TextMetaItem
        TextMetaItem[] stringIndicesLookupArray = buildIndexToTextMetaItemArray(metaItemList);

        // build a list of all replace commands that is ordered from the last one to the first
        // this is important so that replacements won't invalidate indices of other ReplaceCommands
        List<ReplaceCommand> replaceCommandList = buildAllReplaceCommands(completeString, replaceMap);

        // execute all (in correct order)
        replaceCommandList.forEach(rc -> executeReplaceCommand(texts, rc, stringIndicesLookupArray));

        MainDocumentPart part = docxDocument.getMainDocumentPart();
        String xml = XmlUtils.marshaltoString(part.getJaxbElement(), true);
        xml = xml.replaceAll("\n", "</w:t><w:br/><w:t>");
        Object obj = null;
        try {
            obj = XmlUtils.unmarshalString(xml);
            part.setJaxbElement((Document) obj);
        } catch (JAXBException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**         DuowngTora: Thêm table
     * Searches for all occurrences of the placeholders in the parsed docx-document and replaces all of them
     * inside the {@link WordprocessingMLPackage}-object.
     *
     * @param docxDocument Docx4J-parsed document
     * @param replaceMap Map with all placeholders and their new values.
     */
    // more info: https://stackoverflow.com/questions/19676282/docx4j-find-and-replace/60384502#60384502
    public static void searchAndReplace_v2(WordprocessingMLPackage docxDocument, Map<String, String> replaceMap, Map<String, RequestDataCustomService.TableFieldDuowngTora> tablesMap) throws ParserConfigurationException, IOException, SAXException, JAXBException {
        // All Text-objects in correct order
        List<Text> texts = getAllElementsOfType(docxDocument.getMainDocumentPart(), Text.class);
        String completeString = getCompleteString(texts);
        if (completeString.isEmpty()) {
            return;
        }

        List<TextMetaItem> metaItemList = buildMetaItemList(texts);

        // with this array we can lookup for each index in completeString what is the corresponding
        // TextMetaItem
        TextMetaItem[] stringIndicesLookupArray = buildIndexToTextMetaItemArray(metaItemList);

        // build a list of all replace commands that is ordered from the last one to the first
        // this is important so that replacements won't invalidate indices of other ReplaceCommands
        List<ReplaceCommand> replaceCommandList = buildAllReplaceCommands(completeString, replaceMap);

        // execute all (in correct order)
        replaceCommandList.forEach(rc -> executeReplaceCommand(texts, rc, stringIndicesLookupArray));


        MainDocumentPart part = docxDocument.getMainDocumentPart();

//        // table \\
//        if(tablesMap != null && !tablesMap.isEmpty()){
//            ObjectFactory factory = Context.getWmlObjectFactory();
//            int writableWidthTwips = docxDocument.getDocumentModel().getSections()
//                .get(0).getPageDimensions()
//                .getWritableWidthTwips();
//            Set<String> keys = tablesMap.keySet();
////            keys.forEach(key -> {
//            for(String key : keys) {
//                RequestDataCustomService.TableFieldDuowngTora table = tablesMap.get(key);
//                if (table != null) {
//                    List<JSONObject> headerTableXml = new ArrayList<>();
//                    int cellWidthTwips = new Double(Math.floor((writableWidthTwips / table.getHeaders().size()))).intValue();
//                    Tbl tableDocx4j = TblFactory.createTable(table.getBody().size() + 1, table.getHeaders().size(), cellWidthTwips);
//                    Tr header = (Tr) tableDocx4j.getContent().get(0);
//                    for (int f = 0; f < table.getHeaders().size(); f++) {
//                        Tc column = (Tc) header.getContent().get(f);
//                        P columnParam = (P) column.getContent().get(0);
//                        Text text = factory.createText();
//                        try {
//                            JSONObject jsonObjectHeader = new JSONObject(table.getHeaders().get(f));
//                            text.setValue(jsonObjectHeader.getString("text"));
//                            headerTableXml.add(jsonObjectHeader);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            text.setValue("");
//                        };
//                        R run = factory.createR();
//                        run.getContent().add(text);
//                        columnParam.getContent().add(run);
//                    }
//
//                    int i = 1;
//                    for (String data : table.getBody()) {
//                        Tr row = (Tr) tableDocx4j.getContent().get(i);
//                        try {
//                            JSONObject jsonObjectData = new JSONObject(data);
//                            for (int j = 0; j < headerTableXml.size(); j++) {
//                                Tc column = (Tc) row.getContent().get(j);
//                                P columnParam = (P) column.getContent().get(0);
//                                Text text = factory.createText();
//                                R run = factory.createR();
//                                String value = jsonObjectData.getString(headerTableXml.get(j).getString("value"));
//                                text.setValue( (Strings.isNullOrEmpty(value)) ? "-" : value);
//                                run.getContent().add(text);
//                                columnParam.getContent().add(run);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        i++;
//                    }
//
////                    String xmlTableTmp = XmlUtils.marshaltoString(tablexml, true, false);
////                    xml = xml.replaceAll(Pattern.quote(key), "</w:t></w:r></w:p><w:br/>" + xmlTableTmp + "<w:p><w:r><w:t>");
////                    try {
////                        List<Object> textNodes = part.getJAXBNodesViaXPath("//w:t[contains(text(),'" + key + "')]", false);
////                        System.out.println(textNodes);
////                        JAXBElement node = (JAXBElement) textNodes.get(0);
////                        int index = part.getContent().indexOf(node);
////                        part.getContent().add(tablexml);
////                    } catch (XPathBinderAssociationIsPartialException e) {
////                        e.printStackTrace();
////                    }
//
//                    List<Object> paragraphs = getAllElementFromObject(part, P.class);
//                    for(Object par : paragraphs){
//                        P p = (P) par;
//                        List<Object> textsOfParagraphs = getAllElementFromObject(p, Text.class);
//                        for(Object text : textsOfParagraphs){
//                            Text t = (Text)text;
//                            if(t.getValue().contains(key)){
//                                t.setValue(t.getValue().replace(key, ""));
//
//                                int index = part.getContent().indexOf(p);
//                                if(index >= 0){
//                                    part.getContent().add(index + 1, tableDocx4j);
//                                }else{
//                                    part.getContent().add(tableDocx4j);
//                                }
//
//                            }
//                        }
//                    }
//                }
//            }
////            });
//        }

        String xml = XmlUtils.marshaltoString(part.getJaxbElement(), true, false);
        xml = xml.replaceAll("\n", "</w:t><w:br/><w:t>");

        try {
            Object obj = null;
            obj = XmlUtils.unmarshalString(xml);
            Document document = (Document) obj;
            part.setJaxbElement(document);
        } catch (JAXBException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * DuowngTora
     * @param obj
     * @param toSearch
     * @return
     */
    public static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
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

    /**
     *
     * @param xml    DuowngTora
     */
    public static void wipeRootNamespaces(org.w3c.dom.Document xml) {
        Node root = xml.getDocumentElement();
        NodeList rootchildren = root.getChildNodes();
        org.w3c.dom.Element newroot = xml.createElement(root.getNodeName());

        for (int i=0;i<rootchildren.getLength();i++) {
            newroot.appendChild(rootchildren.item(i).cloneNode(true));
        }
        xml.replaceChild(newroot, root);
    }

    public static String documentToString(org.w3c.dom.Document doc){
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DuowngTora
     * @param doc
     * @return
     */
    public static org.w3c.dom.Node getNode(org.w3c.dom.Document doc, String key){
        javax.xml.namespace.NamespaceContext ctx = new javax.xml.namespace.NamespaceContext() {
            public String getNamespaceURI(String prefix) {
                if (prefix == null) return "";
                return prefix.equals("w") ? "http://schemas.openxmlformats.org/wordprocessingml/2006/main" : null;
            }
            public Iterator getPrefixes(String val) {
                return null;
            }
            public String getPrefix(String uri) {
                return null;
            }
        };

        javax.xml.xpath.XPath xPath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
        //xPath.setNamespaceContext(ctx);


        NodeList nodes = null;
        try {
//            nodes = (NodeList)xPath.evaluate("/w:document/w:body/w:p/w:r/w:t[text()='" + key + "']", doc, javax.xml.xpath.XPathConstants.NODESET);
            //nodes = (NodeList)xPath.evaluate("/*[local-name()='document']/*[local-name()='body']/*[local-name()='p'][3]/*[local-name()='r']/*[local-name()='t']", doc, javax.xml.xpath.XPathConstants.NODESET);
            nodes = (NodeList)xPath.evaluate("/*[local-name()='document']/*[local-name()='body']/*[local-name()='p']/*[local-name()='r']/*[local-name()='t' and text() = '" + key + "']", doc, javax.xml.xpath.XPathConstants.NODESET);
            System.out.println(nodes.getLength() + "-> " + xPath.evaluate("/*[local-name()='document']/*[local-name()='body']/*[local-name()='p'][3]/*[local-name()='r']/*[local-name()='t']", doc));

            for (int i = 0; i < nodes.getLength(); i++) {
                Node currentItem = nodes.item(i);
                currentItem.setNodeValue("");
            }

            return nodes != null ? nodes.item(0) : null;
        } catch (javax.xml.xpath.XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }



        /**
         * Recursive function returning all Docx4J-objects of a specific type.
         *
         * @param obj Docx4J-parsed document
         * @param clazz Class we want to look for
         * @param <T> Class we want to look for
         * @return List with all objects of the desired type in correct order
         */
    @SuppressWarnings("checkstyle:ParameterAssignment")
    public static <T> List<T> getAllElementsOfType(Object obj, Class<T> clazz) {
        // Code inspired by https://stackoverflow.com/questions/19676282
        // Thanks!
        List<T> result = new ArrayList<>();
        if (obj instanceof JAXBElement) {
            obj = ((JAXBElement<?>) obj).getValue();
        }
        if (obj.getClass().equals(clazz)) {
            result.add((T) obj);
        } else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementsOfType(child, clazz));
            }
        }
        return result;
    }

    /**
     * Returns the full string back. This string can be understood as the full information that is inside a docx-document.
     *
     * @param texts all Text-objects
     * @return Complete string, never null
     */
    public static String getCompleteString(List<Text> texts) {
        return texts.stream()
            .map(Text::getValue)
            .filter(Objects::nonNull) // can be null if we set it to null during replace
            .reduce(String::concat)
            .orElse("");
    }

    /**
     * Builds {@link TextMetaItem}-list for all Text-objects.
     *
     * @param texts all Text-objects
     * @return {@link TextMetaItem}-list for all Text-objects
     */
    public static List<TextMetaItem> buildMetaItemList(List<Text> texts) {
        int index = 0;
        int iteration = 0;
        List<TextMetaItem> list = new ArrayList<>();
        for (Text text : texts) {
            int length = text.getValue().length();
            list.add(new TextMetaItem(index, index + length - 1, text, iteration));
            index += length;
            iteration++;
        }
        return list;
    }

    /**
     * Builds an array with the length of the complete string of TextMetaItem. This way we can lookup for every
     * index in the complete string what's the responsible Text-objects is.
     *
     * @param metaItemList list with all {@link TextMetaItem}s
     * @return array with the length of the complete string of TextMetaItem
     */
    public static TextMetaItem[] buildIndexToTextMetaItemArray(List<TextMetaItem> metaItemList) {
        int currentStringIndicesToTextIndex = 0;
        // + 1, because inside the loop we use "<" instead of "<="; max is inclusive
        int max = metaItemList.get(metaItemList.size() - 1).getEnd() + 1;

        TextMetaItem[] arr = new TextMetaItem[max];

        for (int i = 0; i < max; i++) {
            TextMetaItem currentTextMetaItem = metaItemList.get(currentStringIndicesToTextIndex);
            arr[i] = currentTextMetaItem;
            if (i >= currentTextMetaItem.getEnd()) {
                currentStringIndicesToTextIndex++;
            }
        }
        return arr;
    }

    /**
     * Builds a list of {@link ReplaceCommand} for a single search and replace entry.
     * It's a list because a search-value can have multiple occurrences.
     *
     * @param completeString complete string
     * @param searchAndReplaceEntry Entry with search value and replace value
     */
    private static List<ReplaceCommand> buildReplaceCommandsForOnePlaceholder(String completeString, Map.Entry<String, String> searchAndReplaceEntry) {
        return StringFindUtil.findAllOccurrencesInString(completeString, searchAndReplaceEntry.getKey()).stream()
            .map(fmi -> new ReplaceCommand(searchAndReplaceEntry.getValue(), fmi))
            .collect(toList());
    }

    /**
     * Builds the list of all {@link ReplaceCommand} for all placeholders and the new values.
     *
     * @param completeString complete string
     * @param replaceMap Map with search and replace values
     * @return all {@link ReplaceCommand} for all placeholders and the new values
     */
    public static List<ReplaceCommand> buildAllReplaceCommands(String completeString, Map<String, String> replaceMap) {
        return replaceMap.entrySet().stream()
            .map(e -> buildReplaceCommandsForOnePlaceholder(completeString, e))
            .flatMap(Collection::stream)
            // important to sort!!!!!
            // we sort from the end to the beginning so that replacements won't invalidate indices of other
            // commands
            .sorted()
            .collect(toList());
    }

    /**
     * Executes a single {@link ReplaceCommand}. It's important that all replace commands are executed in the right order.
     * The right order means from the last to the first one so that indices of other commands won't get invalidated
     * by replacements.
     *
     * @param texts all Text-objects
     * @param replaceCommand {@link ReplaceCommand} to execute
     * @param arr Lookup-Array from index in complete string to TextMetaItem
     */
    public static void executeReplaceCommand(List<Text> texts, ReplaceCommand replaceCommand, TextMetaItem[] arr) {
        TextMetaItem tmi1 = arr[replaceCommand.getFoundResult().getStart()];
        TextMetaItem tmi2 = arr[replaceCommand.getFoundResult().getEnd()];

        if (tmi1.getPosition() == tmi2.getPosition()) {
            // do replacement inside a single Text-object

            String t1 = tmi1.getText().getValue();
            int beginIndex = tmi1.getPositionInsideTextObject(replaceCommand.getFoundResult().getStart());
            int endIndex = tmi2.getPositionInsideTextObject(replaceCommand.getFoundResult().getEnd());

            String keepBefore = t1.substring(0, beginIndex);
            String keepAfter = t1.substring(endIndex + 1);

            // Update Text-object
            tmi1.getText().setValue(keepBefore + replaceCommand.getNewValue() + keepAfter);
        } else {
            // null all Text-objects that may be in between

            if (tmi2.getPosition() - tmi1.getPosition() > 1) {
                int upperBorder = tmi2.getPosition();
                int lowerBorder = tmi1.getPosition() + 1;
                for (int i = lowerBorder; i < upperBorder; i++) {
                    texts.get(i).setValue(null);
                }
            }

            // do replacement across two Text-objects

            String t1 = tmi1.getText().getValue();
            String t2 = tmi2.getText().getValue();

            // indices inside Text-objects (relative to their start)
            int beginIndex = tmi1.getPositionInsideTextObject(replaceCommand.getFoundResult().getStart());
            int endIndex = tmi2.getPositionInsideTextObject(replaceCommand.getFoundResult().getEnd());

            String newValue;
            if (replaceCommand.getNewValue() == null) {
                LOGGER.warn("replaceCommand.getNewValue() is null! Using '' instead!");
                newValue = "";
            } else {
                newValue = replaceCommand.getNewValue();
            }

            t1 = t1.substring(0, beginIndex); // keep this, throw away part of place-holder
            t1 = t1.concat(newValue); // concat new value
            t2 = t2.substring(endIndex + 1); // keep this, throw away part of place-holder

            // Update Text-objects
            tmi1.getText().setValue(t1);
            tmi2.getText().setValue(t2);
        }
    }

    /**
     * Describes information about a single {@link Text}-object inside the List of Text-objects.
     */
    public static class TextMetaItem {

        /**
         * Position/index of the Text-object inside the list of all Text-objects.
         */
        private int position;

        /**
         * Index in complete string where the Text-object starts.
         */
        private int start;

        /**
         * Index in complete string where the Text-object ends.
         */
        private int end;

        /**
         * The Text-object.
         */
        private Text text;

        /**
         * Constructor.
         *
         * @param start Index in complete string where the Text-object starts.
         * @param end Index in complete string where the Text-object ends.
         * @param text Text-object
         * @param position Position/index of the Text-object inside the list of all Text-objects.
         */
        public TextMetaItem(int start, int end, Text text, int position) {
            this.start = start;
            this.end = end;
            this.text = text;
            this.position = position;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public Text getText() {
            return text;
        }

        public int getPosition() {
            return position;
        }

        /**
         * Utility-method to find out what's the relative index inside the Text-object of an
         * index in the complete string.
         *
         * @param completeStringIndex Index inside complete string where this Text-object is a part of
         * @return relative index inside the Text-object of an index in the complete string.
         */
        public int getPositionInsideTextObject(int completeStringIndex) {
            return completeStringIndex - this.start;
        }
    }

    /**
     * Holds all information necessary to do a single replacement.
     */
    public static class ReplaceCommand implements Comparable<ReplaceCommand> {

        /**
         * The new value for the placeholder.
         */
        private String newValue;

        /**
         * Information where the replacement takes place.
         */
        private StringFindUtil.FoundResult foundMetaItem;

        /**
         * Constructor.
         *
         * @param newValue The new value for the placeholder
         * @param foundMetaItem Information where the replacement takes place.
         */
        public ReplaceCommand(String newValue, StringFindUtil.FoundResult foundMetaItem) {
            this.newValue = newValue;
            this.foundMetaItem = foundMetaItem;
        }

        public String getNewValue() {
            return newValue;
        }

        public StringFindUtil.FoundResult getFoundResult() {
            return foundMetaItem;
        }

        /**
         * Compares two replacement commands in a way that they are ordered from the end
         * of the complete string to the beginning. This way replacements can happen without
         * invalidating indices of other commands.
         */
        @Override
        public int compareTo(ReplaceCommand other) {
            // Sortieren von hinten nach vorne
            return other.getFoundResult().getStart() - this.getFoundResult().getStart();
        }
    }
}
