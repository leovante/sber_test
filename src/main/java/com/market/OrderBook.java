package com.market;

import com.parser.AddOrder;
import com.parser.DeleteOrder;

import java.util.*;

public class OrderBook {
    /*коллекции покупки и продажи в стакане*/
    private Map<Double, List<AddOrder>> bidMap;
    private Map<Double, List<AddOrder>> offerMap;
    private Queue<Double> bidMaxPriceList = null;
    private Queue<Double> offerMinPriceList = null;
    private List<Integer> bidOrderIdList;
    private List<Integer> offerOrderIdList;
    private Map<Integer, Double> orderId_priceMap;

    /*Инициализация обработчика заявок*/
    public OrderBook() {
        bidMap = new HashMap<Double, List<AddOrder>>();
        offerMap = new HashMap<Double, List<AddOrder>>();
        bidMaxPriceList = new PriorityQueue<Double>(Collections.reverseOrder()); // top is maximum bid price
        offerMinPriceList = new PriorityQueue<Double>();  // top is minimum offer price
        bidOrderIdList = new LinkedList<>();
        offerOrderIdList = new LinkedList<>();
        orderId_priceMap = new HashMap<>();
    }

    /*удаление заявки из стакана*/
    public OrderBook deleteOrder(DeleteOrder deleteOrderId) {
        if (bidOrderIdList.contains(deleteOrderId.getOrderId()) && bidMap.get(orderId_priceMap.get(deleteOrderId.getOrderId())) != null) {
            List<AddOrder> orderList1 = bidMap.get(orderId_priceMap.get(deleteOrderId.getOrderId()));
            if (orderList1 == null) {
                System.out.println();
            }
            int size1 = orderList1.size();
            for (int i = 0; i < size1; i++) {
                Double price = orderList1.get(i).getPrice();
                orderList1.remove(i);
                new DeleteZeroBucket(price, bidMap, bidMaxPriceList);
                return this;
            }
        } else if (offerOrderIdList.contains(deleteOrderId.getOrderId()) && offerMap.get(orderId_priceMap.get(deleteOrderId.getOrderId())) != null) {
            List<AddOrder> orderList2 = offerMap.get(orderId_priceMap.get(deleteOrderId.getOrderId()));
            int size2 = orderList2.size();
            for (int i = 0; i < size2; i++) {
                Double price = orderList2.get(i).getPrice();
                orderList2.remove(i);
                new DeleteZeroBucket(price, offerMap, offerMinPriceList);
                return this;
            }
        } else {
            System.err.println("deleteOrderId not deleted");
        }
        return this;
    }

    /*обработка заявки в стакане*/
    public OrderBook addOrder(AddOrder order) {
        Double price = order.getPrice();
        Integer quantity = order.getVolume();
        Integer orderId = order.getOrderId();
        if (orderId == 452) {
            System.out.println();
        }
        orderId_priceMap.put(orderId, price);

        /*  Adds offer to map by hashing the price, then
         *  adding offer to list located in that hash bucket
         */
        if (order.getOperation().equals("SELL")) {
            List<AddOrder> bucket = getBucket(offerMap, price);
            AddOrder newOffer = new AddOrder(price, quantity, orderId);
            bucket.add(newOffer);
            offerMap.put(newOffer.getPrice(), bucket);
            offerMinPriceList.add(price);
            offerOrderIdList.add(newOffer.getOrderId());
            matchOrders();
        }

        /*  Adds bid to map by hashing the price, then
         *  adding bid to list located in that hash bucket
         */
        if (order.getOperation().equals("BUY")) {
            List<AddOrder> bucket = getBucket(bidMap, price);
            AddOrder newBid = new AddOrder(price, quantity, orderId);
            bucket.add(newBid);
            bidMap.put(newBid.getPrice(), bucket);
            bidMaxPriceList.add(price);
            bidOrderIdList.add(newBid.getOrderId());
            matchOrders();
        }
        return this;
    }

    // Returns bucket list if price match, otherwise returns new list
    public List<AddOrder> getBucket(Map<Double, List<AddOrder>> hashmap, Double price) {
        List<AddOrder> bucket;
        if (hashmap.containsKey(price)) {
            bucket = hashmap.get(price);
        } else {
            bucket = new LinkedList<AddOrder>();
        }
        return bucket;
    }

    public void matchOrders() {
        List<AddOrder> bidBucket = null;
        List<AddOrder> offerBucket = null;
        Double lowestOffer = null;
        Double highestBid = null;
        boolean finished = false;

        while (!finished) {
            // Peek because we don't want to remove the top element until the order is closed
            highestBid = bidMaxPriceList.peek();
            lowestOffer = offerMinPriceList.peek();

            // No possible trade if either list is empty or no bid higher than an offer
            if (lowestOffer == null || highestBid == null || lowestOffer > highestBid) {
                finished = true;
                System.out.println("OrderBook matchOrders finished = true");
            } else if (bidMap.get(bidMaxPriceList.peek()) != null
                    && bidMap.get(bidMaxPriceList.peek()).size() != 0
                    && offerMap.get(offerMinPriceList.peek()) != null
                    && offerMap.get(offerMinPriceList.peek()).size() != 0) {
                // Gets buckets for both maps
                bidBucket = bidMap.get(bidMaxPriceList.peek());
                offerBucket = offerMap.get(offerMinPriceList.peek());

                // Gets first element from each bucket since they're the oldest
//                System.out.println("Ордер ID " + offerBucket.get(0).getOrderId());
                int bidQuantity = bidBucket.get(0).getVolume();
                int offerQuantity = offerBucket.get(0).getVolume();

                if (bidQuantity > offerQuantity) {
                    System.out.println("bidQuantity > offerQuantity");
                    System.out.println(successfulTrade(offerQuantity, lowestOffer));

                    // Decrements quantity in bid
                    bidQuantity -= offerQuantity;
                    bidBucket.get(0).setVolume(bidQuantity);
                    System.out.println("bidQuantity remaining qty : {" + bidQuantity + "}");

                    // Closes previous offer
                    Double price = offerMinPriceList.peek();
                    offerBucket.remove(0);
                    offerMinPriceList.remove();
                    new DeleteZeroBucket(price, offerMap);
                } else if (offerQuantity > bidQuantity) {
                    System.out.println("bidQuantity < offerQuantity");
                    System.out.println(successfulTrade(bidQuantity, lowestOffer));

                    // Decrements quantity in offer
                    offerQuantity -= bidQuantity;
                    offerBucket.get(0).setVolume(offerQuantity);
                    System.out.println("offerQuantity remaining qty : {" + offerQuantity + "}");

                    //  Closes previous bid
                    Double price = bidMaxPriceList.peek();
                    bidBucket.remove(0);
                    bidMaxPriceList.remove();
                    new DeleteZeroBucket(price, bidMap);
                } else {
                    // bidQuantity is an arbitrary choice because both quantities are equal.
                    // lowestOffer is chosen because it's the price at which the trade is made.
                    System.out.println(successfulTrade(bidQuantity, lowestOffer));

                    // Removes bid and offer because they're both closed
                    bidBucket.remove(0);
                    bidMaxPriceList.remove();
                    offerBucket.remove(0);
                    offerMinPriceList.remove();
                }
            } else {
                if (bidMap.get(bidMaxPriceList.peek()) == null) {
                    new DeleteZeroBucket(bidMaxPriceList);
                }
                if (offerMap.get(offerMinPriceList.peek()) == null) {
                    new DeleteZeroBucket(offerMinPriceList);
                }
                if (bidMap.get(bidMaxPriceList.peek()) != null && bidMap.get(bidMaxPriceList.peek()).size() == 0) {
                    bidMap.remove(bidMaxPriceList.peek());
                }
                if (offerMap.get(offerMinPriceList.peek()) != null && offerMap.get(offerMinPriceList.peek()).size() == 0) {
                    offerMap.remove(offerMinPriceList.peek());
                }
            }
        }
    }

    // Returns the string printed for a successful trade.
    public String successfulTrade(int quantity, double price) {
        System.out.println("successfulTrade bidQuantity : {" + quantity + "}");
        System.out.println("successfulTrade lowestOffer : {" + price + "}");
        return quantity + " shares traded for $" + price + " per share.";
    }

    public Map<Double, List<AddOrder>> getBidMap() {
        return bidMap;
    }

    public Map<Double, List<AddOrder>> getOfferMap() {
        return offerMap;
    }
}