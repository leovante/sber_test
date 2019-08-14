package com.parser;

import com.market.ParseToBooks;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

public class Parse {
    String fileName = "D:\\Design program\\GitHub\\test\\src\\main\\resources\\orders.xml";
    AddOrder addOrder;
    DeleteOrder deleteOrder;
    ParseToBooks parseToBooks = new ParseToBooks();

    public Parse() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String row = scanner.next();
            if (row.contains("AddOrder")) {
                addOrder(row);
                parseToBooks.setAddOrder(addOrder);
            }
            if (row.contains("DeleteOrder")) {
                deleteOrder(row);
                parseToBooks.setDeleteOrder(deleteOrder);
            }
        }
    }

    public void addOrder(String a) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(AddOrder.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            this.addOrder = (AddOrder) jaxbUnmarshaller.unmarshal(new StringReader(a));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(String a) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(DeleteOrder.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            this.deleteOrder = (DeleteOrder) jaxbUnmarshaller.unmarshal(new StringReader(a));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}