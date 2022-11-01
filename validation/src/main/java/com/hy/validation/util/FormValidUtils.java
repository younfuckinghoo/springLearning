package com.hy.validation.util;


import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 开发者：edward
 * 特点：
 * 开发时间：2021/5/29 14:14
 * 文件说明： 手动表单校验工具类
 */
public class FormValidUtils {
    private static Validator validator;

    //初始化校验器
    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 验证表单
     */
    public synchronized static <T> Map<String, ?> valid(T t, Class<?>... groupClazz) {
        Set<ConstraintViolation<T>> validate = validator.validate(t, groupClazz);
        if (!CollectionUtils.isEmpty(validate)) {
            Map<String, String> errorMap = new ConcurrentHashMap<>();
            validate.forEach(tConstraintViolation -> {
                errorMap.put(tConstraintViolation.getPropertyPath().toString(), tConstraintViolation.getMessage());
            });
            return errorMap;
        }
        return null;
    }

    /**
     * 指定属性校验
     */
    public synchronized static <T> Map<String, ?> valid(T t, String fieldName, Class<?>... groupClazz) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validateProperty(t, fieldName, groupClazz);
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            Map<String, String> errorMap = new ConcurrentHashMap<>();
            constraintViolations.forEach(tConstraintViolation -> {
                errorMap.put(tConstraintViolation.getPropertyPath().toString(), tConstraintViolation.getMessage());
            });
            return errorMap;


        }
        return null;
    }


}



