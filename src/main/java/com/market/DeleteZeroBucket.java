package com.market;

import com.parser.AddOrder;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DeleteZeroBucket {

    DeleteZeroBucket(Double price, Map<Double, List<AddOrder>> map, Queue<Double> queuePriceList){
        if(map.get(price).size()==0){
            map.remove(price);
            queuePriceList.remove(price);
        }
    }

    DeleteZeroBucket(Double price, Map<Double, List<AddOrder>> map){
        if(map.get(price).size()==0){
            map.remove(price);
        }
    }
}