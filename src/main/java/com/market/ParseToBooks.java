package com.market;

import com.parser.AddOrder;
import com.parser.DeleteOrder;
import com.parser.Orders;

import java.util.HashMap;
import java.util.Map;

public class ParseToBooks {
    Orders orders;
    AddOrder addOrder;
    DeleteOrder deleteOrder;
    Map<String, OrderBook> orderBookMap = new HashMap<>();//коллекция стаканов

    public ParseToBooks() {
    }

    public void setAddOrder(AddOrder addOrder) {
        orderProcess(addOrder);
    }

    public void setDeleteOrder(DeleteOrder deleteOrder) {

    }

    public void orderProcess(AddOrder newOrder) {
        String bookName = newOrder.getBook();
        if (!orderBookMap.containsKey(bookName)) {//создаю новый стакан, если такого нет
            orderBookMap.put(bookName, new OrderBook().addOrder(newOrder));
        } else {//отправляю новую заявку в стакан
            orderBookMap.get(orderBookMap.containsKey(bookName)).addOrder(newOrder);
        }
    }
}