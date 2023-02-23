package com.vsm.business.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ObjectUtils {

    private final Logger log = LoggerFactory.getLogger(ObjectUtils.class);

    /**
     * Hàm thực hiện coppy thuộc tính từ object nguồn sang object đích
     * @param source: object nguồn
     * @param target: object đích
     * @param tclass: class trả về
     * @param <T>
     * @return      : trả về đối tượng sau khi copy thuộc tính
     */
    public <T> T copyproperties(Object source, Object target, Class<T> tclass) throws IllegalAccessException {
        if(source == null) return null;
        Field[] fields = source.getClass().getDeclaredFields();
        List<Field> listFieldObject = new ArrayList<>();
        int n = fields.length;
        for(int i=0; i<n; i++){
            Field field = fields[i];
            field.setAccessible(true);
            if(field.get(source) != null){
//                Object fieldData = field.get(source);
                if(!checkSimpleType(field.getType())){ // TH mà không phải là kiểu cơ bản
                    listFieldObject.add(field);
                }
            }
        }
        BeanUtils.copyProperties(source, target, String.valueOf(listFieldObject.stream().map(ele -> ele.getName())));
        listFieldObject.forEach(ele -> {
//        for(Field ele : listFieldObject) {
            Field field = null;
            Object sourceFieldValue = null;
            try {
                sourceFieldValue = ele.get(source);
            } catch (IllegalAccessException e) {
                sourceFieldValue = null;
            };
            if (sourceFieldValue != null) {
                try {
                    field = target.getClass().getDeclaredField(ele.getName());
                } catch (NoSuchFieldException e) {
                }
                if (field != null) {
                    try {
                        field.setAccessible(true);
                        Object fieldObject = field.getType().newInstance();
                        BeanUtils.copyProperties(sourceFieldValue, fieldObject);
                        field.set(target, fieldObject);
                    } catch (InstantiationException | IllegalAccessException e) {
                    };
                }
            }
//        }
        });
        return (T) target;
    }

    public <T> T copyproperties_v2(Object source, Object target, Class<T> tclass) throws IllegalAccessException {
        if(source == null) return null;
        Field[] fields = source.getClass().getDeclaredFields();
        List<Field> listFieldObject = new ArrayList<>();
        int n = fields.length;
        for(int i=0; i<n; i++){
            Field field = fields[i];
            field.setAccessible(true);
            if(field.get(source) != null){
//                Object fieldData = field.get(source);
                if(!checkSimpleType(field.getType())){ // TH mà không phải là kiểu cơ bản
                    listFieldObject.add(field);
                }
            }
        }
        BeanUtils.copyProperties(source, target, String.valueOf(listFieldObject.stream().map(ele -> ele.getName())));
//        listFieldObject.forEach(ele -> {
        for(Field ele : listFieldObject) {
            Field field = null;
            Object sourceFieldValue = null;
            try {
                sourceFieldValue = ele.get(source);
            } catch (IllegalAccessException e) {
                sourceFieldValue = null;
            };
            if (sourceFieldValue != null) {
                try {
                    field = target.getClass().getDeclaredField(ele.getName());
                } catch (NoSuchFieldException e) {
                }
                if (field != null) {
                    try {
                        field.setAccessible(true);
                        if(field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(ArrayList.class)){
                            List<Object> list = new ArrayList<>();
                            for(Object sourceElement : (List)sourceFieldValue){
                                Object targetElement = sourceElement.getClass().newInstance();
                                //BeanUtils.copyProperties(sourceElement, targetElement);
                                targetElement = this.coppySimpleTypeOfObject(sourceElement, targetElement);
                                list.add(targetElement);
                            }
                            field.set(target, list);
                        }else if(field.getType().isAssignableFrom(Set.class) || field.getType().isAssignableFrom(HashSet.class)){
                            Set<Object> set = new HashSet<>();
                            for(Object sourceElement : (Set)sourceFieldValue){
                                Object targetElement = sourceElement.getClass().newInstance();
                                //BeanUtils.copyProperties(sourceElement, targetElement);
                                targetElement = this.coppySimpleTypeOfObject(sourceElement, targetElement);
                                set.add(targetElement);
                            }
                            field.set(target, set);
                        }else{
                            Object fieldObject = field.getType().newInstance();
                            BeanUtils.copyProperties(sourceFieldValue, fieldObject);
                            field.set(target, fieldObject);
                        }
                    } catch (InstantiationException | IllegalAccessException e) {
                    };
                }
            }
        }
//        });
        return (T) target;
    }

    // list đánh dấu những kiểu là kiểu cơ bản
    private List<Class> simpleType = Arrays.asList(Integer.class, Long.class, String.class, Instant.class, Date.class, Timestamp.class, Double.class, Float.class, Boolean.class, long.class, int.class, double.class, boolean.class);

    /**
     * Hàm hiềm tra xem class có phải là các class cơ bản không vd (string, long, integer, instant, ...)
     * @param tclass: kiểu dữ liệu cần kiểm tra
     * @return      : trả về flase nếu tclass truyền vào không phải là kiểu cơ bản
     */
    private boolean checkSimpleType(Class<?> tclass){
        if(tclass == null) return true;
        return this.simpleType.stream().anyMatch(ele ->
            ele.equals(tclass)
        );
    }

    private Object coppySimpleTypeOfObject(Object source, Object  target) throws IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        List<Field> listFieldObject = new ArrayList<>();
        int n = fields.length;
        for(int i=0; i<n; i++){
            Field field = fields[i];
            field.setAccessible(true);
            if(field.get(source) != null){
                if(!checkSimpleType(field.getType())){ // TH mà không phải là kiểu cơ bản
                    listFieldObject.add(field);
                }
            }
        }
        BeanUtils.copyProperties(source, target, listFieldObject.stream().map(ele -> ele.getName()).toArray(String[]::new));
//        BeanUtils.copyProperties(source, target, String.valueOf(listFieldObject.stream().map(ele -> ele.getName())));
        return target;
    }


    /**
     * Hàm thực hiện coppy các trường dữ liệu đơn giản gửi Object source sang cho Object target -> trả về kiểu dữ liệu T
     * @param source    : Object nguồn
     * @param target    : Object đích
     * @param tclass    : kiểu dữ liệu cần trả ra
     * @return
     * @param <T>
     * @throws IllegalAccessException
     */
    public <T> T coppySimpleType(Object source, Object target, Class<T> tclass) throws IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        List<Field> listFieldObject = new ArrayList<>();
        int n = fields.length;
        for(int i=0; i<n; i++){
            Field field = fields[i];
            field.setAccessible(true);
            if(field.get(source) != null){
                if(!checkSimpleType(field.getType())){ // TH mà không phải là kiểu cơ bản
                    listFieldObject.add(field);
                }
            }
        }
        BeanUtils.copyProperties(source, target, listFieldObject.stream().map(ele -> ele.getName()).toArray(String[]::new));
//        BeanUtils.copyProperties(source, target, String.valueOf(listFieldObject.stream().map(ele -> ele.getName())));
        return (T) target;
    }


    public <T> T coppyPropertiesIgnoreField(Object source, Object target, List<String> filedIgnores, Class<T> tclass){
        Field[] fields = source.getClass().getDeclaredFields();
        List<String> listTypeObjectField = new ArrayList<>();       // danh sách các trường là Object
        List<Field> listTypeObjectFieldCopy = new ArrayList<>();   // dánh sách các trường là Object và cần phải coppy để trả ra
        List<Field> listTypeObjectFieldNoCopy = new ArrayList<>(); // danh sách các trường là Object và ko cần coppy (vẫn copy nhưng trả ra thông tin ID thôi)
        int n = fields.length;
        for(int i=0; i<n; i++){
            Field field = fields[i];
            field.setAccessible(true);
            if(field != null){
                if(!this.checkSimpleType(field.getType()) && field != null){
                    if(!filedIgnores.stream().anyMatch(ele -> ele.equals(field.getName()))){
                        listTypeObjectFieldCopy.add(field);
                    }else{
                        listTypeObjectFieldNoCopy.add(field);
                    }
                    listTypeObjectField.add(field.getName());
                }
            }
        }
        listTypeObjectField.addAll(filedIgnores);
        BeanUtils.copyProperties(source, target, listTypeObjectField.stream().toArray(String[]::new));

        // coppy những trường là Object
        List<Field> fieldsOfTarget = Arrays.stream(target.getClass().getDeclaredFields()).collect(Collectors.toList());
        listTypeObjectFieldCopy = listTypeObjectFieldCopy.stream().filter(ele -> {
            return fieldsOfTarget.stream().anyMatch(ele1 -> ele1.getName().equals(ele.getName()));
        }).collect(Collectors.toList());
        if(!listTypeObjectFieldCopy.isEmpty()){
            for(int i=0; i<listTypeObjectFieldCopy.size(); i++){
                try {
                    Field field = listTypeObjectFieldCopy.get(i);
                    Field fieldTarget = fieldsOfTarget.stream().filter(ele -> ele.getName().equals(field.getName())).findFirst().orElse(null);
                    if(field != null && fieldTarget != null){
                        field.setAccessible(true);
                        fieldTarget.setAccessible(true);
                        Object sourceValue = field.get(source);
                        if(sourceValue == null) continue;
                        if(field.getType().isAssignableFrom(ArrayList.class) || field.getType().isAssignableFrom(List.class)){

                            List<Object> list = new ArrayList<>();
                            for(Object sourceElement : (List)sourceValue){
                                Object targetElement = sourceElement.getClass().newInstance();
                                //BeanUtils.copyProperties(sourceElement, targetElement);
                                targetElement = this.coppySimpleTypeOfObject(sourceElement, targetElement);
                                list.add(targetElement);
                            }
                            field.set(target, list);
                        }else if(field.getType().isAssignableFrom(HashSet.class) || field.getType().isAssignableFrom(Set.class)){
                            Set<Object> set = new HashSet<>();
                            for(Object sourceElement : (Set)sourceValue){
                                Object targetElement = sourceElement.getClass().newInstance();
                                targetElement = this.coppySimpleTypeOfObject(sourceElement, targetElement);
                                set.add(targetElement);
                            }
                            //field.set(target, set);
                            Set targetSet = (Set)fieldTarget.get(target);
                            targetSet.addAll(set);
                            fieldTarget.set(target, targetSet);
                        }else{
                            Field fieldOfTarget = target.getClass().getDeclaredField(field.getName());
                            Object targetValue = fieldOfTarget.getType().newInstance();
                            BeanUtils.copyProperties(sourceValue, targetValue);
                            fieldTarget.set(target, targetValue);
                        }
                    }
                }
                catch (IllegalAccessException e) {e.printStackTrace();}
                catch (NoSuchFieldException e) {e.printStackTrace();}
                catch (InstantiationException e) {e.printStackTrace();}
                catch (Exception e) {e.printStackTrace();}
            }
        }

        return (T)target;
    }

    /**
     * Hàm thực hiện coppy các trường dữ liệu map theo tên từ Object source sang cho Object target -> trả về kiểu dữ liệu T
     * @param source    : Object nguồn
     * @param target    : Object đích
     * @param tclass    : kiểu dữ liệu cần trả ra
     * @param mapName   : dánh sách map tên các trường cần copy từ source sang target
     * @return
     * @param <T>
     * @throws IllegalAccessException
     */
    public <T> T copyFieldOfObjectWithName(Object source, Object target, Map<String, String> mapName ,Class<T> tclass) throws IllegalAccessException {
        Class<?> classSource = source.getClass();
        Class<?> classTarget = target.getClass();
        for(Map.Entry<String, String> enttry : mapName.entrySet()){
            try {
                Field fieldOfSource = classSource.getDeclaredField(enttry.getValue());
                fieldOfSource.setAccessible(true);
                Field fieldOfTarget = classTarget.getDeclaredField(enttry.getKey());
                fieldOfTarget.setAccessible(true);
                fieldOfTarget.set(target, fieldOfSource.get(source));
            } catch (Exception e) {
                log.error("{}", e);
            }
        }
        return (T) target;
    }

}
