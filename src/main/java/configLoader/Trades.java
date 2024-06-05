//Trades.java
package configLoader;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trades{
	private List<Trade> allTrades; 
	
	public List<Trade> getAllTrades(){// adding the getter apparently helped the mapping.
		return allTrades;
	}
	
	public static class Trade{
		private String name;
		private String size;
		private String stop;
		private String limit;
		private String minPrice;
	
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
	}

    // Method to get precision details by name
    public TradeDetails getTradeDetailsByName(String name) {
    	//System.out.println("getting details"+allTrades);
        if (allTrades!= null) {
            for (Trade pair : allTrades) {
                if (pair.getName().equals(name)) {
                    return new TradeDetails(pair.getName(), pair.getSize(), pair.getStop(),pair.getLimit(),pair.getMinPrice());
                }
            }
        }
        return null; // or throw an exception, or return a default value
    }	
	
	public static class TradeDetails{
		private String name;
		private String size;
		private String stop;
		private String limit;
		private String minPrice;

		public TradeDetails(String name, String size, String stop, String limit, String minPrice) {
			this.name = name;
			this.size = size;
			this.stop = stop;
			this.limit= limit;
			this.minPrice= minPrice;
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
	}	
	
}
