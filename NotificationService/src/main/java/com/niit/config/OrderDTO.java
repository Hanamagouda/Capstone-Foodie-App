/*
 * Author : Mushib Khan
 * Date : 04-04-2023
 * Created with : IntelliJ IDEA Community Edition
 */

package com.niit.config;

import org.bson.json.JsonObject;

public class OrderDTO {
    private JsonObject jsonObject;

    public OrderDTO() {
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
