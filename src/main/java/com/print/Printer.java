package com.print;

import com.market.OrderBook;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Printer {
    Map<String, OrderBook> orderBookMap;

    public Printer(Map<String, OrderBook> orderBook) {
        this.orderBookMap = orderBook;
    }

    public void manager(){
        SortedMap<Double, Integer> offerMapPrint = new TreeMap<>();
        SortedMap<Double, Integer> bidMapPrint = new TreeMap<>();



        OrderBook orderBook = null;
        for (String entity : orderBookMap.keySet()) {
            orderBook = orderBookMap.get(entity);
            System.out.println(
                    "Стакан: " + entity + "\n" +
                            "Покупка - Продажа\n" +
                            "-----------------\n" +
                            "Колво@Цена - Колво@Цена");

        }
    }
}