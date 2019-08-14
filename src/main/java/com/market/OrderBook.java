package com.market;

import com.parser.AddOrder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderBook {
    Map<BigDecimal,AddOrder> sellOrders = new HashMap<>();
    Map<BigDecimal,AddOrder> buyOrders = new HashMap<>();

    public OrderBook setOrder(AddOrder addOrder) {
        if(addOrder.getOperation().equals("SELL")){
            //ищем в buy самую высокую цену, выше текущей и продаем текущее количество ордеров
            //если продали всё, то ордер не добавляем. Существующую обновляем
            //если продали не всё, то рекурсией смотрим на другие заявки
            sellOrders.put(addOrder.getPrice(), addOrder);
        }
        if(addOrder.getOperation().equals("BUY")){
            //аналогично, только смотрим самую дешевую заявку на продажу, меньше текущей
            buyOrders.put(addOrder.getPrice(), addOrder);
        }
        return this;
    }
}