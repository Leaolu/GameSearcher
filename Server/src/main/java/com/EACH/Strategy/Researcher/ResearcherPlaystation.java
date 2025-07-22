package com.EACH.Strategy.Researcher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.EACH.DTO.GameDTO;
import com.EACH.Exceptions.GameNotFoundException;

@Service
public class ResearcherPlaystation implements ResearcherStrategy {
	
	private static final String original = "originalPriceValue";
	private static final String discount = "discountPriceValue";
	private static final String offers = "offerBranding";
	private static final String curr = "priceCurrencyCode";
	
	
	private static String readAttribute(int index, String original){
		String value = "";
		char c = original.charAt(index);
		while(c != ',' && c != '\"'){
			value += c;
			index++;
			c = original.charAt(index);
		}
		return value;
	}

	@Override
	public List<GameDTO> research(String name) {
		
		List<GameDTO> games = new ArrayList<>();
		
		String formattedName = name.replaceAll("_", "-").toLowerCase();
		int count = 0;
		
		try{
			URL url = new URI("https://www.playstation.com/pt-br/games/"+formattedName+"/").toURL();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line;
			while((line = reader.readLine()) != null){
			
					Matcher m = Pattern.compile("(?=("+original+"))").matcher(line);
					
			while(m.find()){
				int fim = m.end() + 2 + original.length();
				String priceValue = "";
				char c = line.charAt(fim);
				while(c != ',' && c != 'u'){
					priceValue += c;
					fim++;
					c = line.charAt(fim);
				}
				
				if(c == 'u'){
					reader.close();
					return games;
				}
				
				fim = line.indexOf(discount, fim);
				String discountValue = readAttribute(fim + 2 + discount.length(), line);
				String currency = readAttribute(line.indexOf(curr, fim)+3+curr.length(), line);
				
				Double originalPrice = Double.parseDouble(priceValue)/100;
				Double discountPrice = Double.parseDouble(discountValue)/100;
				Double[] newPrices = {originalPrice, discountPrice};
				
				fim = line.indexOf(offers, fim);
			 	String offer = readAttribute(fim + 2 + offers.length(), line);
			 	
				String type = readAttribute(line.indexOf("type", fim+110) + 6, line);
				games.add(new GameDTO(newPrices, offer, type.contains("TRIAL"), currency));
				if(!games.get(count).getOfferType().isEmpty()) count++;
			}
			}
			reader.close();
		
		}catch(FileNotFoundException e){
			throw new GameNotFoundException();
		}catch(Exception e){
			e.printStackTrace();
		}
		throw new GameNotFoundException();
	}

}
