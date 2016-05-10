package com.ez08.im.util;

import com.ez08.im.model.EzAction;
import com.google.gson.Gson;

/**
 * User: lyjq(1752095474)
 * Date: 2016-05-05
 */
public class Json2EzAction {

    public static EzAction convert(String json){
        Gson gson = new Gson();
        String temp = json.substring(1,json.length());
        EzAction ezAction = gson.fromJson(temp,EzAction.class);
        return ezAction;
    }
}
