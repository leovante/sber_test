package com.market;

import com.parser.AddOrder;
import com.parser.DeleteOrder;
import com.parser.Orders;

import java.util.Map;
import java.util.TreeMap;

public class ParseToBooks {
    Orders orders;
    AddOrder addOrder;
    DeleteOrder deleteOrder;
    Map<String, OrderBook> orderBookMap;//коллекция стаканов

    public ParseToBooks() {
        orderBookMap = new TreeMap<String, OrderBook>();
    }

    public void setAddOrder(AddOrder order) {
        String bookName = order.getBook();
        if(orderBookMap.containsKey(bookName)) {
            orderBookMap.get(bookName).addOrder(order);
        }else{
            orderBookMap.put(bookName, new OrderBook().addOrder(order));
        }
    }

    public void setDeleteOrder(DeleteOrder deleteOrder) {

    }
}