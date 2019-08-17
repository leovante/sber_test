package com.print;

import com.market.OrderBook;
import com.parser.AddOrder;

import java.util.*;

public class Printer {
    private Map<String, OrderBook> orderBookMap;
    private SortedMap<Double, Integer> bidMapPrint = null;
    private SortedMap<Double, Integer> offerMapPrint = null;
    private Queue<Double> bidMaxPriceList = null;
    private Queue<Double> offerMinPriceList = null;


    public Printer(Map<String, OrderBook> orderBook) {
        this.orderBookMap = orderBook;
    }

    public void exec() {
        for (String entity : orderBookMap.keySet()) {
            OrderBook orderBook = orderBookMap.get(entity);
            bidMapPrint = new TreeMap<>(Collections.reverseOrder());
            offerMapPrint = new TreeMap<>();
            bidMaxPriceList = new PriorityQueue<Double>(Collections.reverseOrder()); // сначала максимальная цена
            offerMinPriceList = new PriorityQueue<Double>();  // сначала минимальная цена

            bidMapPrint = calculateMap(orderBook.getBidMap(), bidMaxPriceList);
            offerMapPrint = calculateMap(orderBook.getOfferMap(), offerMinPriceList);

            int bidCount = bidMaxPriceList.size();
            int offerCount = offerMinPriceList.size();
            System.out.println(
                    "Стакан: " + entity + "\n" +
                            "Покупка - Продажа\n" +
                            "-----------------\n" +
                            "Колво@Цена - Колво@Цена");
            for (int i = 0; i < bidCount || i < offerCount; i++) {
                System.out.println(bucketController(bidMaxPriceList.poll(), bidMapPrint) + " - " + bucketController(offerMinPriceList.poll(), offerMapPrint));
            }
        }
    }

    private SortedMap<Double, Integer> calculateMap(Map<Double, List<AddOrder>> orderBookMap, Queue<Double> maxPriceList) {
        SortedMap<Double, Integer> orderCalc = new TreeMap<>();
        for (Map.Entry<Double, List<AddOrder>> entry : orderBookMap.entrySet()) {
            Double key = entry.getKey();
            int volume = 0;

            List<AddOrder> addOrders = entry.getValue();
            Iterator<AddOrder> iter = addOrders.iterator();
            while (iter.hasNext()) {
                volume += iter.next().getVolume();
            }
            orderCalc.put(key, volume);
            maxPriceList.add(key);
        }
        return orderCalc;
    }

    private String bucketController(Double price, SortedMap<Double, Integer> mapPrint) {
        String text;
        if (price != null) {
            text = mapPrint.get(price) + "@" + price;
        }else{
            text = "-------";
        }
        return text;
    }
}