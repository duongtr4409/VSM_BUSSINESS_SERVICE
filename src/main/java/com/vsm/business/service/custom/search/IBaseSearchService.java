package com.vsm.business.service.custom.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import com.vsm.business.service.custom.search.service.bo.ISearchResponseDTO;
import com.vsm.business.service.dto.ProcessInfoDTO;
import com.vsm.business.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public interface IBaseSearchService<T, E> {

    String SUFFIX_START = "_START";
    String SUFFIX_END = "_END";

    Logger log = LoggerFactory.getLogger(IBaseSearchService.class);
    ObjectUtils objectUtils = new ObjectUtils();
    Map<Class, Map<String, String>> mapingField = new HashMap<>();
    ISearchResponseDTO simpleQuerySearch(T t, Pageable pageable) throws IllegalAccessException;
    ISearchResponseDTO simpleQuerySearchWithParam(T t, Map<String, Object> paramsMap, Pageable pageable) throws IllegalAccessException;
    ISearchResponseDTO simpleQuerySearchIgnoreField(T t, Map<String, Object> paramsMap, Pageable pageable, List<String> ignoreField) throws IllegalAccessException;

    ObjectMapper objectMapper = new ObjectMapper();

//    ISearchResponseDTO simpleQuerySearchWithParam(UserInfoDTO t, Map<String, Object> paramsMap,Pageable pageable);

    default List<ConditionSimpleQuery> buildCondition(T t) throws IllegalAccessException {
        List<ConditionSimpleQuery> conditionSimpleQueryList = new ArrayList<>();
        this.initMapingField();
        if(t != null){
            Field[] fields = t.getClass().getDeclaredFields();
            int n = fields.length;
            for(int i=0; i<n; i++){
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(t);
                if(value != null){
                    ConditionSimpleQuery conditionSimpleQuery = new ConditionSimpleQuery();
                    if(checkSimpleType(value.getClass())){
                        try {
                            conditionSimpleQuery.setFieldName(field.getName());
                            if(checkSameClass(value.getClass(), STRING_CLASS)){             // nếu trường cần tìm kiếm là String
                                conditionSimpleQuery.setValue(String.valueOf(value));
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.LIKE);
                            }else if(checkSameClass(value.getClass(), NUMBER_CLASS)){       // nếu trường cần tìm kiếm là Number
                                conditionSimpleQuery.setValue(Double.valueOf(String.valueOf(value)));
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.EQUALS);
                            }else if(checkSameClass(value.getClass(), BOOLEAN_CLASS)){      // nếu trường cần tìm kiếm là Boolean
                                conditionSimpleQuery.setValue(Boolean.valueOf(String.valueOf(value)));
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.EQUALS);
                            }
                            conditionSimpleQueryList.add(conditionSimpleQuery);
                        }catch (Exception e){log.error("{}", e);}
                    }else{
                        try {
                            conditionSimpleQuery.setFieldName(this.getEntityField(t.getClass(),field.getName()));
                            if(value.getClass().isAssignableFrom(HashSet.class)){      // nếu trường cần tìm kiếm là List, Set
                                if(((Set)value).isEmpty()) continue;
                                Set<Object> setValue = new HashSet<>();
                                for(Object temp : (Set)value){
                                    Field fieldIdTemp = temp.getClass().getDeclaredField("id");
                                    fieldIdTemp.setAccessible(true);
                                    Object newValue = fieldIdTemp.get(temp);
                                    setValue.add(newValue);
                                }
                                conditionSimpleQuery.setValue(setValue);
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.OBJECT_IN);
                            }else if(value.getClass().isAssignableFrom(ArrayList.class)){
                                if(((List)value).isEmpty()) continue;
                                List<Object> listValue = new ArrayList<>();
                                for(Object temp : (List)value){
                                    Field fieldIdTemp = temp.getClass().getDeclaredField("id");
                                    fieldIdTemp.setAccessible(true);
                                    Object newValue = fieldIdTemp.get(temp);
                                    listValue.add(newValue);
                                }
                                conditionSimpleQuery.setValue(listValue);
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.OBJECT_IN);
                            }else if(!checkSameClass(value.getClass(), SIMPLE_TYPE)){
                                Field fieldIdValue = value.getClass().getDeclaredField("id");
                                fieldIdValue.setAccessible(true);
                                Object newValue = fieldIdValue.get(value);
                                conditionSimpleQuery.setValue(newValue);
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.OBJECT_IN);
                            }
                            conditionSimpleQueryList.add(conditionSimpleQuery);
                        }catch (Exception e){log.error("{}", e);}
                    }
                }
            }
        }
        return conditionSimpleQueryList;
    }

    default List<ConditionSimpleQuery> buildConditionWithParam(T t, Map<String, Object> paramsMap) throws IllegalAccessException {
        this.initMapingField();
        List<ConditionSimpleQuery> conditionSimpleQueryList = new ArrayList<>();
        Set<String> keySets = paramsMap.keySet();
        List<Field> listFieldNameOfObject = new ArrayList<>();
        if(t != null){
            Field[] fields = t.getClass().getDeclaredFields();

            if(fields != null) listFieldNameOfObject = Arrays.stream(fields).collect(Collectors.toList());

            int n = fields.length;
            for(int i=0; i<n; i++){
                Field field = fields[i];
                field.setAccessible(true);
                if(paramsMap.containsKey(field.getName())) continue;        // nếu có trong param -> bỏ qua để thêm vào between
                Object value = field.get(t);
                if(value != null){
                    ConditionSimpleQuery conditionSimpleQuery = new ConditionSimpleQuery();
                    if(checkSimpleType(value.getClass())){
                        try {
                            conditionSimpleQuery.setFieldName(field.getName());
                            if(checkSameClass(value.getClass(), STRING_CLASS)){             // nếu trường cần tìm kiếm là String
                                conditionSimpleQuery.setValue(String.valueOf(value));
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.LIKE);
                            }else if(checkSameClass(value.getClass(), NUMBER_CLASS)){       // nếu trường cần tìm kiếm là Number
                                conditionSimpleQuery.setValue(Double.valueOf(String.valueOf(value)));
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.EQUALS);
                            }else if(checkSameClass(value.getClass(), BOOLEAN_CLASS)){      // nếu trường cần tìm kiếm là Boolean
                                conditionSimpleQuery.setValue(Boolean.valueOf(String.valueOf(value)));
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.EQUALS);
                            }
                            conditionSimpleQueryList.add(conditionSimpleQuery);
                        }catch (Exception e){log.error("{}", e);}
                    }else{
                        try {
                            conditionSimpleQuery.setFieldName(this.getEntityField(t.getClass(),field.getName()));
                            if(value.getClass().isAssignableFrom(HashSet.class)){      // nếu trường cần tìm kiếm là List, Set
                                if(((Set)value).isEmpty()) continue;
                                Set<Object> setValue = new HashSet<>();
                                for(Object temp : (Set)value){
                                    Field fieldIdTemp = temp.getClass().getDeclaredField("id");
                                    fieldIdTemp.setAccessible(true);
                                    Object newValue = fieldIdTemp.get(temp);
                                    setValue.add(newValue);
                                }
                                conditionSimpleQuery.setValue(setValue);
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.OBJECT_IN);
                            }else if(value.getClass().isAssignableFrom(ArrayList.class)){
                                if(((List)value).isEmpty()) continue;
                                List<Object> listValue = new ArrayList<>();
                                for(Object temp : (List)value){
                                    Field fieldIdTemp = temp.getClass().getDeclaredField("id");
                                    fieldIdTemp.setAccessible(true);
                                    Object newValue = fieldIdTemp.get(temp);
                                    listValue.add(newValue);
                                }
                                conditionSimpleQuery.setValue(listValue);
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.OBJECT_IN);
                            }else if(!checkSameClass(value.getClass(), SIMPLE_TYPE)){
                                Field fieldIdValue = value.getClass().getDeclaredField("id");
                                fieldIdValue.setAccessible(true);
                                Object newValue = fieldIdValue.get(value);
                                conditionSimpleQuery.setValue(newValue);
                                conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.OBJECT_IN);
                            }
                            conditionSimpleQueryList.add(conditionSimpleQuery);
                        }catch (Exception e){log.error("{}", e);}
                    }
                }
            }
        }
        if(paramsMap != null && !paramsMap.isEmpty()){
            for(String key : keySets){
                Field field = listFieldNameOfObject.stream().filter(ele -> key.equals(ele.getName())).findFirst().orElse(null);
                if(field != null){      // nếu tồn tại trường trong tương ứng trong object -> thêm vào điều kiện tìm kiếm
                    ConditionSimpleQuery conditionSimpleQuery = new ConditionSimpleQuery();
                    conditionSimpleQuery.setFieldName(key);
                    conditionSimpleQuery.setSimpleOperator(ConditionSimpleQuery.QuerySimpleOperator.BETWEEN);
                    conditionSimpleQuery.setValueStart(field.get(t));
                    try {
                        byte[] jsonEnd = objectMapper.writeValueAsBytes(paramsMap.get(key));
                        if(field.getType().isAssignableFrom(Instant.class)){
                            conditionSimpleQuery.setValueEnd(objectMapper.readValue(jsonEnd, Instant.class));
                        }else{
                            conditionSimpleQuery.setValueEnd(objectMapper.readValue(jsonEnd, String.class));
                        }
                    } catch (IOException e) {log.error("{}",e);};
                    conditionSimpleQueryList.add(conditionSimpleQuery);
                }
            }
        }
        return conditionSimpleQueryList;
    }

    /**
     * Hàm hiềm tra xem class có phải là các class cơ bản không vd (string, long, integer, instant, ...)
     * @param tclass: kiểu dữ liệu cần kiểm tra
     * @return      : trả về flase nếu tclass truyền vào không phải là kiểu cơ bản
     */
    List<Class<?>> SIMPLE_TYPE = Arrays.asList(Integer.class, Long.class, String.class, Instant.class, Date.class, Timestamp.class, Double.class, Float.class, Boolean.class, long.class, int.class, double.class, float.class, Float.class, Boolean.class, boolean.class);
    default boolean checkSimpleType(Class<?> tclass){
        if(tclass == null) return true;
        return this.SIMPLE_TYPE.stream().anyMatch(ele ->
            ele.equals(tclass)
        );
    }

    List<Class<?>> STRING_CLASS = Arrays.asList(String.class);
    List<Class<?>> NUMBER_CLASS = Arrays.asList(Long.class, long.class, Integer.class, int.class, Double.class, double.class, Float.class, float.class);
    List<Class<?>> BOOLEAN_CLASS = Arrays.asList(Boolean.class, boolean.class);
    default boolean checkSameClass(Class<?> tclass, List<Class<?>> classList){
        boolean result = classList.stream().anyMatch(ele -> ele.equals(tclass));
        return result;
    }

    // Chưa dùng \\
    default T convertToDTO(Class<T> tclass, E e, List<String> ignoreFiledList) {
        T t = null;
        try {
            t = tclass.newInstance();
            t = objectUtils.coppyPropertiesIgnoreField(e, t, ignoreFiledList, tclass);
            return t;
        }
        catch (InstantiationException ex) { log.error("{}", ex);}
        catch (IllegalAccessException ex) { log.error("{}", ex);}
        catch (Exception ex) { log.error("{}", ex);}
        return t;
    }


        // utils \\
    default String removeSuffix(String text, String suffix){
        if(text.endsWith(suffix)){
            int textLength = text.length();
            int suffixLength = suffix.length();
            String temp = text.substring(textLength - suffixLength);
            if(suffix.equals(temp)){
                return text.substring(0, textLength - suffixLength);
            }
        }
        return text;
    }


    default void initMapingField(){
        if(mapingField == null || mapingField.isEmpty()){

            // cấu hình cho objectmapper
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.findAndRegisterModules();

            // maping lại filed của PROCESS
            Map<String, String> fieldMappingProcess = new HashMap<>();
            fieldMappingProcess.put("requestDTOS", "requests");
            mapingField.put(ProcessInfoDTO.class, fieldMappingProcess);

            //

        }
    }

    /**
     * Hàm thực hiện lấy tên của trường trong Entity
     * @param tclass    : class
     * @param fieldName : tên trường trong DTO
     * @return          : tên của trường trong Entity
     */
    default String getEntityField(Class tclass, String fieldName){
        Map<String, String> mapingFieldName = mapingField.get(tclass);
        if(mapingFieldName == null) return fieldName;
        String entityFieldName = mapingFieldName.get(fieldName);
        return entityFieldName != null ? entityFieldName : fieldName;
    }
}
