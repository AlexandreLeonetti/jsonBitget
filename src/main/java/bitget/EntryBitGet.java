package bitget;

import io.github.cdimascio.dotenv.Dotenv;


import com.bitget.openapi.common.client.BitgetRestClient;
import com.bitget.openapi.common.domain.ClientParameter;
import com.bitget.openapi.common.enums.SupportedLocaleEnum;
import configLoader.ConfigLoader;
import configLoader.ConfigLoader.FullDetails;
import utils.Utils;
public class EntryBitGet{

    private RealFuturesService real;

    public int run(String symbol) throws Exception {
    	String sTriggerMargin;
    	String sLimitMargin;
    	String size;
    	String treshold;
    	int pricePrecision;

        Dotenv dotenv = Dotenv.configure().directory(System.getProperty("user.dir")).load();
        BitgetRestClient bitgetRestClient = createBitgetRestClient(dotenv);
        
        FullDetails details = ConfigLoader.getAllDetails(symbol); 
        
        if(details!=null) {
        	sTriggerMargin = details.getStop();
        	sLimitMargin   = details.getLimit();
        	size		   = details.getSize();
        	treshold	   = details.getMinPrice();
        	pricePrecision = details.getPricePrecision();
        }else {
            System.out.println("Failed to get details of : "+symbol);
        	return -1;
        }
        //String size	          = dotenv.get("SIZE");
        //String sTriggerMargin = dotenv.get("TRIGGER");
        //String sLimitMargin   = dotenv.get("LIMIT");
        
        //String size	  = "0.001";
    	//String sTriggerMargin = "0.004";
    	//String sLimitMargin	  = "0.005";
    	float triggerMargin =  Float.parseFloat(sTriggerMargin);
    	float limitMargin   =  Float.parseFloat(sLimitMargin); 
    	float sizef			=  Float.parseFloat(size);

    	System.out.println("testing Entry Bitget");
    	System.out.println("trigger is : "+sTriggerMargin);
    	System.out.println("Limit is : "+ sLimitMargin);

        // Initialize RealFuturesService with the manually created BitgetRestClient
        real = new RealFuturesService(bitgetRestClient);
        System.out.println("real starting futures ...");

        //real.stopBuyDemoFutures();
        // 1 get price
         String realPrice = real.getTicker(symbol, pricePrecision);
        // add conditional buy treshold <<<< HERE 
         if(Utils.isLess(realPrice,treshold)) {// shouldNotbuy is set to true id price is lower than treshold
        	 System.out.printf("Price %s is lower than treshold %s, so we don't buy.",realPrice,treshold);
        	 return 0;
         }
        // 2 limit buy
         size = Utils.numberFormatter(details.getSizePrecision(), sizef);
        real.limitBuy(symbol, size , realPrice);// symbol, size, price
        
        // 3 sleep 500ms
        try {
            Thread.sleep(2000); // Delay of 2000 milliseconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.err.println("Interrupted during sleep");
        }
        // 4 stop loss with limit
        float price = Float.parseFloat(realPrice);
        float triggerf = price*(1-triggerMargin);
        float limitf   = price*(1-limitMargin);

        //String trigger = new DecimalFormat("#.#").format(triggerf); // get a function that automatically format
        //String limit   = new DecimalFormat("#.#").format(limitf);
        String trigger = Utils.numberFormatter(details.getPricePrecision(), triggerf);
        String limit   = Utils.numberFormatter(details.getPricePrecision(), limitf);

        real.stopSell(symbol, size, trigger, limit);//symbol, size, trigger, price 
    	return 0;
    }

    private BitgetRestClient createBitgetRestClient(Dotenv dotenv) throws Exception {
        String apiKey = dotenv.get("APIKEY");
        String secretKey = dotenv.get("SECRETKEY");
        String passphrase = dotenv.get("PASSPHRASE");

        ClientParameter parameter = ClientParameter.builder()
            .apiKey(apiKey)
            .secretKey(secretKey)
            .passphrase(passphrase)
            .baseUrl("https://api.bitget.com")
            .locale(SupportedLocaleEnum.EN_US.getName()).build();
        return BitgetRestClient.builder().configuration(parameter).build();
    }
}