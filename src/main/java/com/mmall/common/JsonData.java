package com.mmall.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class JsonData {

    private boolean ret;

    private String msg;

    private Object data;

    public JsonData() {
    }

    public JsonData(boolean ret) {
        this.ret = ret;
    }

    public static JsonData success(Object data,String msg){
        JsonData  jsonData = new JsonData(true);
        jsonData.setData(data);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public static JsonData success(Object data){
        JsonData  jsonData = new JsonData(true);
        jsonData.setData(data);
        return jsonData;
    }

    public static JsonData success(){
        JsonData  jsonData = new JsonData(true);
        return jsonData;
    }


    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("ret",ret);
        result.put("msg",msg);
        result.put("data",data);
        return result;
    }



}
