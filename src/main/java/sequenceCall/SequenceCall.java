//SequenceCall.java
package sequenceCall;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import bitget.EntryBitGet;
import configLoader.Sequence;
import utils.Utils;

public class SequenceCall{
    public static Sequence loadSequence(String filePath) throws IOException {
        ObjectMapper seqMapper= new ObjectMapper();
        seqMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return seqMapper.readValue(new File(filePath), Sequence.class);
    }
    
    public static List<String> getSeq() {
        try {
            Sequence seq= loadSequence("sequence.json");
            //System.out.println(seq.getAllPairs());
            return seq.getAllPairs();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void sequentialCall() {
        List<String> sequence = getSeq();
        if (sequence != null) {
                EntryBitGet bitget = new EntryBitGet();
            for (String s : sequence) {
            	try {
                int res = bitget.run(s);
                System.out.println("bitget entry returned : "+res);
            	}catch(Exception e) {
            		System.out.println(e);
            	}
                Utils.sleepMs(3); // Sleep for 3 milliseconds
                System.out.println(s);
            }
            return ;
        } else {
            System.err.println("Sequence is null");
        }
    }
    
    
}
