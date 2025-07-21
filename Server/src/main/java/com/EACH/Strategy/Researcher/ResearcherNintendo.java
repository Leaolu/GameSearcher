package com.EACH.Strategy.Researcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.EACH.DTO.GameDTO;
import com.EACH.Exceptions.GameNotFoundException;

public class ResearcherNintendo implements ResearcherStrategy {
	
	private static final String cur = "priceCurrency";
	
	private String readUntilQuotes(String line, int index) {
		String response = "";
		char c = line.charAt(index);
		while(c != '\"') {
			response += c;
			index++;
			c = line.charAt(index);
		}
		return response;
	}
	
	private boolean SearchOnSwitch(String name, List<GameDTO> games, int version) {
		try {
			String ver = (version == 2)? "-2" : "";
			URL url = new URI("https://www.nintendo.com/pt-br/store/products/"+name+"-switch"+ver+"/").toURL();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line;
			while((line = reader.readLine()) != null){
				if(line.contains("price")) {
					int index = line.indexOf("price")+cur.length()+3;
					String currency = readUntilQuotes(line, index);
					index = line.indexOf("price", index)+8;
					Double price = Double.valueOf(readUntilQuotes(line, index));
					Double[] prices = {price, price}; 
					games.add(new GameDTO(prices, "Switch"+ver, false, currency));
					return true;
				}
			}
		}catch(Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public List<GameDTO> research(String name) {
		List<GameDTO> games = new ArrayList<>();
		name = name.replaceAll("_", "-").toLowerCase();
		if(SearchOnSwitch(name, games, 1) || SearchOnSwitch(name, games, 2)) {
			return games;
		}else throw new GameNotFoundException();
	}

}
