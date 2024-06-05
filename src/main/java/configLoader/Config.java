//Config.java
package configLoader;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {
    private BinanceConfig binance;

    // Getter for binance
    public BinanceConfig getBinance() {
        return binance;
    }

    public static class BinanceConfig {
        private List<PairDetails> allDetails;

        // Getter for allDetails
        public List<PairDetails> getAllDetails() {
            return allDetails;
        }
    }

    public static class PairDetails {
        private String name;
        private int pricePrecision;
        private int sizePrecision;
       /* 
        public PairDetails(String name, int pricePrecision, int sizePrecision) {
        	this.name = name;
        	this.pricePrecision = pricePrecision;
        	this.sizePrecision  = sizePrecision;
        }*/
        // Getters for PairDetails fields
        public String getName() {
            return name;
        }

        public int getPricePrecision() {
            return pricePrecision;
        }

        public int getSizePrecision() {
            return sizePrecision;
        }
    }
    
    // Method to get precision details by name
    public PrecisionDetails getPrecisionDetailsByName(String name) {
        if (binance != null && binance.getAllDetails() != null) {
            for (PairDetails pair : binance.getAllDetails()) {
                if (pair.getName().equals(name)) {
                    return new PrecisionDetails(pair.getName(), pair.getPricePrecision(), pair.getSizePrecision());
                }
            }
        }
        return null; // or throw an exception, or return a default value
    }
   
    // Class to hold precision details
    public static class PrecisionDetails {
    	private String name;
        private int pricePrecision;
        private int sizePrecision;


        public PrecisionDetails(String name, int pricePrecision, int sizePrecision) {
        	this.name = name;
            this.pricePrecision = pricePrecision;
            this.sizePrecision = sizePrecision;
        }

        // Getters
        
        public String getName() {
        	return name;
        }

        public int getPricePrecision() {
            return pricePrecision;
        }

        public int getSizePrecision() {
            return sizePrecision;
        }

        @Override
        public String toString() {
            return "PrecisionDetails{" +
                    "pricePrecision=" + pricePrecision +
                    ", sizePrecision=" + sizePrecision +
                    '}';
        }
    } 
}

 
