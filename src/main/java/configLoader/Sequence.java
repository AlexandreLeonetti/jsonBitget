//Sequence.java

package configLoader;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sequence{
	private List<String> allPairs;
	
	public List<String> getAllPairs(){
		return allPairs;
	}

}