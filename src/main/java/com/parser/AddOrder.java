package com.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.math.BigInteger;

@XmlRootElement(name = "AddOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddOrder{

    @XmlAttribute(name="book")
    String book;

    @XmlAttribute(name="operation")
    String operation;

    @XmlAttribute(name="price")
    Double price;

    @XmlAttribute(name="volume")
    Integer volume;

    @XmlAttribute(name="orderId")
    Integer orderId;

    public AddOrder() {
    }

    public AddOrder(Double price, int quantity, Integer orderId){
        this.price = price;
        this.volume = quantity;
        this.orderId = orderId;
    }

    public String getBook() {
        return book;
    }

    public String getOperation() {
        return operation;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getVolume() {
        return volume;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}