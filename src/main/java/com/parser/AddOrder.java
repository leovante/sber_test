package com.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "AddOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddOrder{
    @XmlAttribute(name="book")
    String book;

    @XmlAttribute(name="operation")
    String operation;

    @XmlAttribute(name="price")
    BigDecimal price;

    @XmlAttribute(name="volume")
    Integer volume;

    @XmlAttribute(name="orderId")
    Integer orderId;

    public String getBook() {
        return book;
    }

    public String getOperation() {
        return operation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getVolume() {
        return volume;
    }

    public Integer getOrderId() {
        return orderId;
    }
}