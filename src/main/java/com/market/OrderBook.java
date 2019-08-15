package com.market;

import com.parser.AddOrder;

import java.math.BigDecimal;
import java.util.*;

public class OrderBook {
    /*коллекции покупки и продажи в стакане*/
    private Map<BigDecimal, List<AddOrder>> bidMap;
    private Map<BigDecimal, List<AddOrder>> offerMap;
    private Queue<BigDecimal> bidMaxPriceList = null;
    private Queue<BigDecimal> offerMinPriceList = null;

    /*Инициализация обработчика заявок*/
    public OrderBook(){
        bidMap = new HashMap<BigDecimal, List<AddOrder>>();
        offerMap = new HashMap<BigDecimal, List<AddOrder>>();
        bidMaxPriceList = new PriorityQueue<BigDecimal>(Collections.reverseOrder()); // top is maximum bid price
        offerMinPriceList = new PriorityQueue<BigDecimal>();  // top is minimum offer price
    }

    /*обработка заявки в стакане*/
    public OrderBook addOrder(AddOrder order) {
        if(order.getOperation().equals("SELL")){
            //ищем в buy самую высокую цену, выше текущей и продаем текущее количество ордеров
            //если продали всё, то ордер не добавляем. Существующую обновляем
            //если продали не всё, то рекурсией смотрим на другие заявки
            bidMap.put(order.getPrice(), order);
            matchOrders();
        }
        if(order.getOperation().equals("BUY")){
            //аналогично, только смотрим самую дешевую заявку на продажу, меньше текущей
            offerMap.put(order.getPrice(), order);
            matchOrders();
        }
        return this;
    }

/*
    public void addBid(double price, int quantity)
    {
        List<Order> bucket = getBucket(bidMap, price);
        Order newBid = new Order(price, quantity);
        bucket.add(newBid);
        bidMap.put(newBid.getPrice(), bucket);
        bidMaxPriceList.add(price);
        matchOrders();
    }

    */
/*  Adds offer to map by hashing the price, then
     *  adding offer to list located in that hash bucket
     *//*

    public void addOffer(double price, int quantity)
    {
        List<Order> bucket = getBucket(offerMap, price);
        Order newOffer = new Order(price, quantity);
        bucket.add(newOffer);
        offerMap.put(newOffer.getPrice(), bucket);
        offerMinPriceList.add(price);
        matchOrders();
    }
*/

    public void matchOrders(){


        /*
        List<Order> bidBucket = null;
        List<Order> offerBucket = null;
        Double lowestOffer = null;
        Double highestBid = null;
        boolean finished = false;

        while(!finished)
        {
            // Peek because we don't want to remove the top element until the order is closed
            highestBid = bidMaxPriceList.peek();
            lowestOffer = offerMinPriceList.peek();

            // No possible trade if either list is empty or no bid higher than an offer
            if(lowestOffer == null || highestBid == null || lowestOffer > highestBid)
            {
                finished = true;
            	logger.info("OrderBook matchOrders finished = true");
            }
            else
            {
                // Gets buckets for both maps
                bidBucket = bidMap.get(bidMaxPriceList.peek());
                offerBucket = offerMap.get(offerMinPriceList.peek());

                // Gets first element from each bucket since they're the oldest
                int bidQuantity = bidBucket.get(0).getQuantity();
                int offerQuantity = offerBucket.get(0).getQuantity();

                if(bidQuantity > offerQuantity)
                {
                	logger.info("bidQuantity > offerQuantity");
                    System.out.println(successfulTrade(offerQuantity, lowestOffer));

                    // Decrements quantity in bid
                    bidQuantity -= offerQuantity;
                    bidBucket.get(0).setQuantity(bidQuantity);
                	logger.info("bidQuantity remaining qty : {}", bidQuantity);

                    // Closes previous offer
                    offerBucket.remove(0);
                    offerMinPriceList.remove();
                }
                else if(offerQuantity > bidQuantity)
                {
                	logger.info("bidQuantity < offerQuantity");
                    System.out.println(successfulTrade(bidQuantity, lowestOffer));

                    // Decrements quantity in offer
                    offerQuantity -= bidQuantity;
                    offerBucket.get(0).setQuantity(offerQuantity);
                	logger.info("offerQuantity remaining qty : {}", offerQuantity);

                    //  Closes previous bid
                    bidBucket.remove(0);
                    bidMaxPriceList.remove();
                }
                else
                {
                    // bidQuantity is an arbitrary choice because both quantities are equal.
                    // lowestOffer is chosen because it's the price at which the trade is made.
                    System.out.println(successfulTrade(bidQuantity, lowestOffer));

                    // Removes bid and offer because they're both closed
                    bidBucket.remove(0);
                    bidMaxPriceList.remove();
                    offerBucket.remove(0);
                    offerMinPriceList.remove();
                }
            }
        }*/
    }
}