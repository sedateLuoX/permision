package com.mmall.util;

import org.apache.commons.lang3.StringUtils;

public class LeveUtil {

    public final static String SPILT =".";

    public final static String ROOT ="0";

    public static String calculateLeve(String parentLeave ,int parentId ){

        if(StringUtils.isBlank(parentLeave)){

            return ROOT;
        }else {

            return StringUtils.join(parentLeave,SPILT,parentId);
        }
    }
}
