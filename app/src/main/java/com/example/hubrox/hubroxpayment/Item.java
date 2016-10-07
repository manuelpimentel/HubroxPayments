package com.example.hubrox.hubroxpayment;

/**
 * Created by manuel on 31/08/16.
 */
public class Item {
    private String Desc, Code;
    private float Price;

    public Item() {
        super();
    }

    public Item(String desc, String code, float price) {
        Desc = desc;
        Code = code;
        Price = price;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }
}
