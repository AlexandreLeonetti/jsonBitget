/* App.java */
package com.example.jsonBitGet;

import configLoader.ConfigLoader;
import configLoader.ConfigLoader.FullDetails;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting... !");
        String pair = "BTCUSDT";

        FullDetails details = ConfigLoader.getAllDetails(pair);
        if (details != null) {
            System.out.println("Name of the pair: " + details.getName());
            System.out.println("Size of the order: " + details.getSize());
            System.out.println("Stop: " + details.getStop());
            System.out.println("Limit: " + details.getLimit());
            System.out.println("Minimum price: " + details.getMinPrice());
            System.out.println("Size Precision: " + details.getSizePrecision());
            System.out.println("Price Precision: " + details.getPricePrecision());
        } else {
            System.out.println("Failed to get details of : "+pair);
        }
    }
}
