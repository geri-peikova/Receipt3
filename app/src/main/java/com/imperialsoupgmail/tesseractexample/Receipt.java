package com.imperialsoupgmail.tesseractexample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gery on 2/1/2018.
 */

public class Receipt {
    List<Order> receipt;

    public Receipt(){
        receipt = new ArrayList<>();
    }

    public Order getOrder(int i){
        return receipt.get(i);
    }

    public void addOrder(Order a){
        receipt.add(a);
    }

    public int countOrders(){
        int count = 0;
        for (Order i: receipt) {
            count++;
        }
        return count;
    }

    public void removeOrder(Order a){
        receipt.remove(a);
    }

}
