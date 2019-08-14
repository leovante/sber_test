package com.market;

import com.parser.AddOrder;
import com.parser.DeleteOrder;
import com.parser.Orders;

import java.util.Map;

public class ParseToBooks {
    Orders orders;
    AddOrder addOrder;
    DeleteOrder deleteOrder;
    Map<String, OrderBook> orderBookMap;

    public ParseToBooks() {
    }

    public void setAddOrder(AddOrder addOrder) {
        matching(addOrder);
    }

    public void setDeleteOrder(DeleteOrder deleteOrder) {

    }

    public void matching(AddOrder newOrder) {
        String bookName = newOrder.getBook();
        if (!orderBookMap.containsKey(bookName)) {
            orderBookMap.put(bookName, new OrderBook().setOrder(newOrder));
        } else {
            orderBookMap.get(orderBookMap.containsKey(bookName)).setOrder(newOrder);
        }


    }


}