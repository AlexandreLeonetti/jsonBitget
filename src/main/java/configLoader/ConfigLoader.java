//ConfigLoader.java
package configLoader;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    public static Config loadConfig(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(new File(filePath), Config.class);
    }

    public static Trades loadTrades(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(new File(filePath), Trades.class);
    }
    
    public static FullDetails getAllDetails(String pair) {
        try {
            Config config = loadConfig("config.json");
            Config.PrecisionDetails details = config.getPrecisionDetailsByName(pair);
            Trades trades = loadTrades("trades.json");
            Trades.TradeDetails tradeDetails = trades.getTradeDetailsByName(pair);
            if (tradeDetails != null & details !=null) {
            	FullDetails currentPair = new FullDetails();
            	currentPair.setName(tradeDetails.getName());
            	currentPair.setSize(tradeDetails.getSize());
            	currentPair.setStop(tradeDetails.getStop());
            	currentPair.setLimit(tradeDetails.getLimit());
            	currentPair.setMinPrice(tradeDetails.getMinPrice());
            	currentPair.setSizePrecision(details.getSizePrecision());
            	currentPair.setPricePrecision(details.getPricePrecision());
            	return currentPair;
            } else {
                System.out.println("problem fetching details");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
	public static class FullDetails{
		private String name;
		private String size;
		private String stop;
		private String limit;
		private String minPrice;
		private int sizePrecision;
		private int pricePrecision;

        // Default constructor
        public FullDetails() {
        }
        
		public FullDetails(String name, String size, String stop, String limit, String minPrice, int sizePrecision, int pricePrecision) {
			this.name = name;
			this.size = size;
			this.stop = stop;
			this.limit= limit;
			this.minPrice= minPrice;
			this.sizePrecision = sizePrecision;
			this.pricePrecision=pricePrecision;
		}
		public String getName() {
			return name;
		}
		
		public String getSize() {
			return size;
		}
		
		public String getStop() {
			return stop;
		}
		
		public String getLimit() {
			return limit;
		}
		
		public String getMinPrice() {
			return minPrice;
		}
		public int getSizePrecision() {
			return sizePrecision;
		}
		public int getPricePrecision() {
			return pricePrecision;
		}
		
		public void setName(String n) {
			this.name=n;
		}
		public void setSize(String n) {
			this.size = n;
		}
		public void setStop(String s) {
			this.stop=s;
		}
		
		public void setLimit(String s) {
			this.limit = s;
		}
		public void setMinPrice(String s) {
			this.minPrice=s;
		}
		public void setSizePrecision(int n) {
			this.sizePrecision = n;
		}
		public void setPricePrecision(int n) {
			this.pricePrecision = n;
		}
	}
    
}

