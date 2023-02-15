package com.vsm.business.repository.specification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vsm.business.domain.RequestData;
import com.vsm.business.repository.specification.bo.ConditionQuery;
import com.vsm.business.repository.specification.bo.ConditionSimpleQuery;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;

@Component
public class AbstractSpecification <T>{

    public static final Logger log = LoggerFactory.getLogger(AbstractSpecification.class);

    public static Map<Class, Map<String, Class>> mapClass = new HashMap<>();

    public static Map<Class, Map<String, Class>> mapClassSimple = new HashMap<>();

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static Specification addCondition(ConditionQuery conditionQuery, Class tclass) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            switch (conditionQuery.getOperator()){
                case EQUALS:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionQuery.getFieldName()).getType();
                        byte[] json = objectMapper.writeValueAsBytes(conditionQuery.getValue());
                        return criteriaBuilder.equal(root.get(conditionQuery.getFieldName()), objectMapper.readValue(json, type));
                    } catch (Exception e) {log.error("{}", e); return null;}
                case IN:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionQuery.getFieldName()).getType();
                        List params = new ArrayList();
                        for(Object temp : conditionQuery.getListValue()){
                            byte[] json = objectMapper.writeValueAsBytes(temp);
                            params.add(objectMapper.readValue(json, type));
                        }
                        return criteriaBuilder.in(root.get(conditionQuery.getFieldName())).value(params);
                    } catch (Exception e) {log.error("{}", e); return null;}
                case LIKE:
                    //return criteriaBuilder.like(root.get(conditionQuery.getFieldName()), "%" + conditionQuery.getValue() + "%");
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionQuery.getFieldName()).getType();
                        byte[] json = objectMapper.writeValueAsBytes(conditionQuery.getValue());
                        return criteriaBuilder.like(root.<String>get(conditionQuery.getFieldName()), "%" + objectMapper.readValue(json, type) + "%");
                    } catch (Exception e) {log.error("{}", e); return null;}
                case LESS_THAN:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionQuery.getFieldName()).getType();
                        if(type.isAssignableFrom(Double.class)){
                            return criteriaBuilder.lessThan(root.get(conditionQuery.getFieldName()).as(Double.class), Double.valueOf(conditionQuery.getValue().toString()));
                        }else if(type.isAssignableFrom(Long.class)){
                            return criteriaBuilder.lessThan(root.get(conditionQuery.getFieldName()).as(Long.class), Long.valueOf(conditionQuery.getValue().toString()));
                        }else{
                            return criteriaBuilder.lessThan(root.get(conditionQuery.getFieldName()).as(Instant.class), Instant.parse(conditionQuery.getValue().toString()));
                        }
                    } catch (NoSuchFieldException e) {log.error("{}", e.getStackTrace());}
                    return criteriaBuilder.lessThan(root.get(conditionQuery.getFieldName()).as(Instant.class), Instant.parse(conditionQuery.getValue().toString()));
                case LESS_THAN_OR_EQUALS:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionQuery.getFieldName()).getType();
                        if(type.isAssignableFrom(Double.class)){
                            return criteriaBuilder.lessThanOrEqualTo(root.get(conditionQuery.getFieldName()).as(Double.class), Double.valueOf(conditionQuery.getValue().toString()));
                        }else if(type.isAssignableFrom(Long.class)){
                            return criteriaBuilder.lessThanOrEqualTo(root.get(conditionQuery.getFieldName()).as(Long.class), Long.valueOf(conditionQuery.getValue().toString()));
                        }else{
                            return criteriaBuilder.lessThanOrEqualTo(root.get(conditionQuery.getFieldName()).as(Instant.class), Instant.parse(conditionQuery.getValue().toString()));
                        }
                    } catch (NoSuchFieldException e) {log.error("{}", e.getStackTrace());}
                    return criteriaBuilder.lessThanOrEqualTo(root.get(conditionQuery.getFieldName()).as(Instant.class), Instant.parse(conditionQuery.getValue().toString()));
                case GREATER_THAN:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionQuery.getFieldName()).getType();
                        if(type.isAssignableFrom(Double.class)){
                            return criteriaBuilder.greaterThan(root.get(conditionQuery.getFieldName()).as(Double.class), Double.valueOf(conditionQuery.getValue().toString()));
                        }else if(type.isAssignableFrom(Long.class)){
                            return criteriaBuilder.greaterThan(root.get(conditionQuery.getFieldName()).as(Long.class), Long.valueOf(conditionQuery.getValue().toString()));
                        }else{
                            return criteriaBuilder.greaterThan(root.get(conditionQuery.getFieldName()).as(Instant.class), Instant.parse(conditionQuery.getValue().toString()));
                        }
                    } catch (NoSuchFieldException e) {log.error("{}", e.getStackTrace());}
                    return criteriaBuilder.greaterThan(root.get(conditionQuery.getFieldName()).as(Instant.class), Instant.parse(conditionQuery.getValue().toString()));
                case GREATER_THAN_OR_EQUALS:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionQuery.getFieldName()).getType();
                        if(type.isAssignableFrom(Double.class)){
                            return criteriaBuilder.greaterThanOrEqualTo(root.get(conditionQuery.getFieldName()).as(Double.class), Double.valueOf(conditionQuery.getValue().toString()));
                        }else if(type.isAssignableFrom(Long.class)){
                            return criteriaBuilder.greaterThanOrEqualTo(root.get(conditionQuery.getFieldName()).as(Long.class), Long.valueOf(conditionQuery.getValue().toString()));
                        }else{
                            return criteriaBuilder.greaterThanOrEqualTo(root.get(conditionQuery.getFieldName()).as(Instant.class), Instant.parse(conditionQuery.getValue().toString()));
                        }
                    } catch (NoSuchFieldException e) {log.error("{}", e.getStackTrace());}
                    return criteriaBuilder.greaterThanOrEqualTo(root.get(conditionQuery.getFieldName()).as(Instant.class), Instant.parse(conditionQuery.getValue().toString()));
                default:
                    throw new RuntimeException("Not suport Operator: " + conditionQuery.getOperator());
            }
        };
    }


    public static Specification buildQuery(List<ConditionQuery> conditionQueryList, Class tclass){
        if(!mapClass.containsKey(tclass)){
            Field[] declaredFields = tclass.getDeclaredFields();
            Map<String, Class> mapField = new HashMap<>();
            for(Field field : declaredFields){
                mapField.put(field.getName(), field.getType());
            }
            mapClass.put(tclass, mapField);
        }
        Specification result = Specification.where(addCondition(conditionQueryList.get(0), tclass));
        int n = conditionQueryList.size();
        for(int i=1; i<n; i++){
            result = result.and(addCondition(conditionQueryList.get(i), tclass));
        }
        return result;
    }

    public static List<Object> groupQuery(List<String> groupFieldName, Class tclass, EntityManager entityManager) throws JsonProcessingException {
        if(!mapClass.containsKey(tclass)){
            Field[] declaredFiedls = tclass.getDeclaredFields();
            Map<String, Class> mapFiled = new HashMap<>();
            for(Field filed : declaredFiedls){
                mapFiled.put(filed.getName(), filed.getType());
            }
            mapClass.put(tclass, mapFiled);
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<RequestData> root = criteriaQuery.from(RequestData.class);
        List<Expression<?>> listGroupBy = new ArrayList<>();
        List<Selection<?>> listSelect = new ArrayList<>();
        for(String fieldName : groupFieldName){
            listSelect.add(root.get(fieldName));
            listGroupBy.add(root.get(fieldName));
        }
        listSelect.add(criteriaBuilder.count(root));
        criteriaQuery.multiselect(listSelect).groupBy(listGroupBy);
        List<Object[]> resultTemp = entityManager.createQuery(criteriaQuery).getResultList();
        List<Object> result = new ArrayList<>();
        int n = groupFieldName.size();
        for(Object[] objects : resultTemp){
            JSONObject jsonObject = new JSONObject();
            for(int i=0; i<n; i++){
                jsonObject.put(groupFieldName.get(i), objects[i] != null ? objects[i] : JSONObject.NULL);
            }
            jsonObject.put("count", objects[objects.length - 1]);
            result.add(objectMapper.readValue(jsonObject.toString(), Object.class));
        }
        return result;
    }







    public static Specification addSimpleCondition(ConditionSimpleQuery conditionSimpleQuery, Class tclass) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            switch (conditionSimpleQuery.getSimpleOperator()){
                case EQUALS:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionSimpleQuery.getFieldName()).getType();
                        byte[] json = objectMapper.writeValueAsBytes(conditionSimpleQuery.getValue());
                        return criteriaBuilder.equal(root.get(conditionSimpleQuery.getFieldName()), objectMapper.readValue(json, type));
                    } catch (Exception e) {log.error("{}", e); return null;}

                case LIKE:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionSimpleQuery.getFieldName()).getType();
                        if(conditionSimpleQuery.getValue().getClass().equals(String.class)){
                            byte[] json = objectMapper.writeValueAsBytes(conditionSimpleQuery.getValue().toString().toUpperCase());
                            return criteriaBuilder.like(criteriaBuilder.upper(root.<String>get(conditionSimpleQuery.getFieldName())), "%" + objectMapper.readValue(json, type) + "%");
                        }else{
                            byte[] json = objectMapper.writeValueAsBytes(conditionSimpleQuery.getValue());
                            return criteriaBuilder.like(root.<String>get(conditionSimpleQuery.getFieldName()), "%" + objectMapper.readValue(json, type) + "%");
                        }
                    } catch (Exception e) {log.error("{}", e); return null;}

                case OBJECT_IN:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionSimpleQuery.getFieldName()).getType();
                        if(conditionSimpleQuery.getValue() != null && (type.isAssignableFrom(ArrayList.class) || type.isAssignableFrom(HashSet.class))){
                            Join join = root.join(conditionSimpleQuery.getFieldName());
                            List params = new ArrayList();
                            if(type.isAssignableFrom(List.class)){
                                for(Object temp : (conditionSimpleQuery.getValue().getClass().isAssignableFrom(List.class) || conditionSimpleQuery.getValue().getClass().isAssignableFrom(ArrayList.class)
                                    ? (List)conditionSimpleQuery.getValue() : (Set)conditionSimpleQuery.getValue())){
                                    byte[] json = objectMapper.writeValueAsBytes(temp);
                                    params.add(objectMapper.readValue(json, Long.class));
                                }
                            }else if(type.isAssignableFrom(Set.class)){
                                for(Object temp : (conditionSimpleQuery.getValue().getClass().isAssignableFrom(Set.class) || conditionSimpleQuery.getValue().getClass().isAssignableFrom(HashSet.class)
                                    ? (Set)conditionSimpleQuery.getValue() : (List)conditionSimpleQuery.getValue())){
                                    byte[] json = objectMapper.writeValueAsBytes(temp);
                                    params.add(objectMapper.readValue(json, Long.class));
                                }
                            }
                            return criteriaBuilder.in(join.get("id")).value(params);
                        }else if(conditionSimpleQuery.getValue() != null){
                            byte[] json = objectMapper.writeValueAsBytes(conditionSimpleQuery.getValue());
                            Join join = root.join(conditionSimpleQuery.getFieldName());
                            return criteriaBuilder.equal(join.get("id"), objectMapper.readValue(json, Long.class));
                        }
                        return criteriaBuilder.isEmpty(root);
                    }catch (Exception e) {log.error("{}", e); return null;}

                case BETWEEN:
                    try {
                        Class<?> type = tclass.getDeclaredField(conditionSimpleQuery.getFieldName()).getType();
                        if(conditionSimpleQuery.getValueStart().getClass().equals(Instant.class)){
                            byte[] jsonStart = objectMapper.writeValueAsBytes(conditionSimpleQuery.getValueStart());
                            byte[] jsonEnd = objectMapper.writeValueAsBytes(conditionSimpleQuery.getValueEnd());
                            return criteriaBuilder.between(root.get(conditionSimpleQuery.getFieldName()), objectMapper.readValue(jsonStart, Instant.class), objectMapper.readValue(jsonEnd, Instant.class));
                        }else{
                            byte[] jsonStart = objectMapper.writeValueAsBytes(conditionSimpleQuery.getValueStart());
                            byte[] jsonEnd = objectMapper.writeValueAsBytes(conditionSimpleQuery.getValueEnd());
                            return criteriaBuilder.between(root.get(conditionSimpleQuery.getFieldName()),
                                objectMapper.readValue(jsonStart, String.class),
                                objectMapper.readValue(jsonEnd, String.class));
                        }
                    }catch (Exception e) {log.error("{}", e); return null;}

                default:
                    throw new RuntimeException("Not suport Simple Operator: " + conditionSimpleQuery.getSimpleOperator());
            }
        };
    }

    /**
     *  Hàm thực hiện build những câu query đơn giản (like, = )
     * @param conditionSimpleQueryList: list điều kiện
     * @param tclass
     * @return
     */
    public static Specification buildQuerySimpleQuery(List<ConditionSimpleQuery> conditionSimpleQueryList, Class tclass){
        // cấu hình cho objectmapper
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.findAndRegisterModules();
        if(!mapClassSimple.containsKey(tclass)){
            Field[] declaredFields = tclass.getDeclaredFields();
            Map<String, Class> mapField = new HashMap<>();
            for(Field field : declaredFields){
                mapField.put(field.getName(), field.getType());
            }
            mapClassSimple.put(tclass, mapField);
        }
        if(conditionSimpleQueryList == null || conditionSimpleQueryList.isEmpty()) return null;
        Specification result = Specification.where(addSimpleCondition(conditionSimpleQueryList.get(0), tclass));
        int n = conditionSimpleQueryList.size();
        for(int i=1; i<n; i++){
            result = result.and(addSimpleCondition(conditionSimpleQueryList.get(i), tclass));
        }
        return result;
    }
}
