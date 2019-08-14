package com.parser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DeleteOrder")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteOrder {

    @XmlAttribute
    String book;

    @XmlAttribute
    Integer orderId;
}
