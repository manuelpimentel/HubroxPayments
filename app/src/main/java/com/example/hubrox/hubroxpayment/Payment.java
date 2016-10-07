package com.example.hubrox.hubroxpayment;

import java.util.ArrayList;

/**
 * Created by manuel on 05/10/16.
 */
public class Payment {

    private ArrayList<String> itemCodes;
    private Float Total;

    public Payment(ArrayList<String> itemCodes, Float total) {
        this.itemCodes = itemCodes;
        Total = total;
    }

    public ArrayList<String> getItemCodes() {
        return itemCodes;
    }

    public void setItemCodes(ArrayList<String> itemCodes) {
        this.itemCodes = itemCodes;
    }

    public Float getTotal() {
        return Total;
    }

    public void setTotal(Float total) {
        Total = total;
    }


}
