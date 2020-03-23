package com.mmall.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.exception.ParamException;
import org.apache.commons.collections.MapUtils;

import javax.validation.*;
import java.util.*;

public class BeanValidator {

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public  static <T>Map<String,String> validator(T t,Class... groups){

        Validator validator = validatorFactory.getValidator();
        Set validateResult = validator.validate(t, groups);
        if(validateResult.isEmpty()){
            return Collections.emptyMap();
        }else {
            LinkedHashMap  errors = Maps.newLinkedHashMap();
            Iterator iterator = validateResult.iterator();
            while (iterator.hasNext()){
                ConstraintViolation violation =(ConstraintViolation) iterator.next();
                errors.put(violation.getPropertyPath().toString(),violation.getMessage());
            }
            return errors;
        }
    }

    public static  Map<String,String> validatorList(Collection<?> collection){

         Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map errors;
        do{
            if(!iterator.hasNext()){
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            errors = validator(object,new Class[0]);
        }while (errors.isEmpty());

        return errors;
    }

    public static Map<String,String> validatorObject(Object frist , Object... objects){
        if(objects!=null && objects.length>0){

            return validatorList(Lists.asList(frist,objects));
        }else {

            return validator(frist,new Class[0]);
        }

    }


    public  static  void  check(Object param)throws ParamException{
        Map<String,String> map =  BeanValidator.validatorObject(param);
        if(MapUtils.isNotEmpty(map)){

            throw new ParamException(map.toString());
        }


    }


}
