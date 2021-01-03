package com.company;


import util.Orders.Item;
import util.Orders.ItemDatabase;
import util.Orders.Order;

public class OrderForm {


    public static void main(String[] args) {
        //ids are:
        //0 milk, 1 sugar, 2 candy, 3 soda.
        Order order1 = new Order("Dovid", "Elkana 15", "Jerusalem", "Israel", "9531020", 0,2,1,3);
        order1.print();
        Order order2 = new Order("Chaim", "Elkana 15", "Jerusalem", "Israel", "9531020", 0,2,1,3);
        order2.print();
        Order order3 = new Order();
        order3.setName("Moishe");
        order3.setAddress("Elkana 15", "Jerusalem", "Israel", "9531020");
        System.out.println(order3.getAddress());
        order3.setItemInArray(0, ItemDatabase.getInstance().getItemById(1));
        order3.insertItemInArray(0, "shoes", "ecco size 43", 120.09, true);
        order3.print();
        Item[] items = order3.getInstanceItems();
        System.out.println(items[0].getName() + order3.getName());
    }
}
