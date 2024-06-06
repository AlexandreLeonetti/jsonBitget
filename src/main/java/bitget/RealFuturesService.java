/* RealFuturesService.java */
package bitget; 

import com.bitget.openapi.common.client.BitgetRestClient;
import com.bitget.openapi.dto.response.ResponseResult;
import com.alibaba.fastjson.JSON;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class RealFuturesService {

    private final BitgetRestClient bitgetRestClient;

    public RealFuturesService(BitgetRestClient bitgetRestClient) {
        this.bitgetRestClient = bitgetRestClient;
    }

    public void limitBuy (String symbol,
    					  String size,
    					  String price) throws Exception {
        try {
        	//String symbol = "BTCUSDT";
            String productType = "USDT-FUTURES";
            String side = "buy";
            String orderType = "limit";
            String marginMode = "isolated";
            String marginCoin = "USDT";
            //String size = "0.001";
            //String price = "44444";

            String force = "GTC";

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("symbol", symbol);
            paramMap.put("productType", productType);
            paramMap.put("side", side);
            paramMap.put("orderType", orderType);
            paramMap.put("marginMode", marginMode);
            paramMap.put("marginCoin", marginCoin);
            paramMap.put("size", size);
            paramMap.put("price", price);
            paramMap.put("force", force);

            ResponseResult result = bitgetRestClient.bitget().v2().mixOrder().placeOrder(paramMap);
            System.out.println("From place real limit buy FUTURES ORDER");
            System.out.println(JSON.toJSONString(result));
        } catch (Exception e) {
            System.err.println("Error placing order: " + e.getMessage());
            throw e;
        }
    }
  
    
    public void stopSell(String symbol, String size, String triggerPrice, String price) throws Exception {
        try {
            String planType = "normal_plan";
            //String symbol = "BTCUSDT";
            String productType = "USDT-FUTURES";
            String side = "sell";
            String orderType = "limit";
            String marginMode = "isolated";
            String marginCoin = "USDT";
            //String size = "0.001";
            //String price = "78000";
            String force = "GTC";
            //String triggerPrice = "77000";
            String triggerType = "fill_price";

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("planType", planType);
            paramMap.put("symbol", symbol);
            paramMap.put("productType", productType);
            paramMap.put("side", side);
            paramMap.put("orderType", orderType);
            paramMap.put("marginMode", marginMode);
            paramMap.put("marginCoin", marginCoin);
            paramMap.put("size", size);
            paramMap.put("price", price);
            paramMap.put("force", force);
            paramMap.put("triggerPrice", triggerPrice);
            paramMap.put("triggerType", triggerType);

            ResponseResult result = bitgetRestClient.bitget().v2().mixOrder().placePlanOrder(paramMap);
            System.out.println("From place real stopSell FUTURES ORDER");
            System.out.println(JSON.toJSONString(result));
        } catch (Exception e) {
            System.err.println("Error placing order: " + e.getMessage());
            throw e;
        }
    }
    
    /*     @GET("/api/spot/v1/market/ticker")
    Call<ResponseResult> ticker(@QueryMap Map<String, String> paramMap);
    */
    
    public String getTicker(String symbol) throws Exception {
    	try {
    		Map<String, String> paramMap = new HashMap<>();
    		paramMap.put("symbol", symbol);
             paramMap.put("productType","USDT-FUTURES");
   		
    		//String urlTicker = "/api/v2/spot/market/tickers";
    		String futureTicker = "/api/v2/mix/market/ticker";
    		ResponseResult result = bitgetRestClient.bitget().v2().request().get(futureTicker,paramMap);
            System.out.println("From place REAL getTicker");
            String res = JSON.toJSONString(result);
            JSONObject jsonObject = new JSONObject(res);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            JSONObject firstDataObject = dataArray.getJSONObject(0); // Assuming you want the first object in the array
            String lastPr = firstDataObject.getString("lastPr");
            System.out.println("Last Price: " + lastPr);
            
            float price = Float.parseFloat(lastPr);
            price = price*0.9998f;
            String formattedPrice= new DecimalFormat("#.#").format(price);
            return formattedPrice;
    	}catch(Exception e){
    		System.err.println("Error getting ticker: " + e.getMessage());
    		throw e;
    	}
    }
}