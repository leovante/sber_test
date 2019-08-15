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
    String fileName = "C:\\Users\\Dmitry\\Desktop\\orders.xml";
    AddOrder addOrder;
    DeleteOrder deleteOrder;
    ParseToBooks parseToBooks = new ParseToBooks();

    /*направляю на исполнение заявку, в зависимости от типа*/
    public Parse() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        scanner.useDelimiter("\n");
        while (scanner.hasNext()) {
            String row = scanner.next();
            if (row.contains("AddOrder")) {
                addOrder = new AddOrder();
                addOrder = addOrder(row);
                parseToBooks.setAddOrder(addOrder);
            }
            if (row.contains("DeleteOrder")) {
                deleteOrder = new DeleteOrder();
                deleteOrder = deleteOrder(row);
                parseToBooks.setDeleteOrder(deleteOrder);
            }
        }
    }
    /*оборачиваю заявки xml в объекты*/
    public AddOrder addOrder(String a) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(AddOrder.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            this.addOrder = (AddOrder) jaxbUnmarshaller.unmarshal(new StringReader(a));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return addOrder;
    }

    public DeleteOrder deleteOrder(String a) {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(DeleteOrder.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            this.deleteOrder = (DeleteOrder) jaxbUnmarshaller.unmarshal(new StringReader(a));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return deleteOrder;
    }
}