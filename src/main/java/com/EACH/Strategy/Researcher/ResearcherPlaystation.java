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
	
	private static String readAttribute(int index, String original){
		String value = "";
		char c = original.charAt(index);
		while(c != ','){
			value += c;
			index++;
			c = original.charAt(index);
		}
		return value;
	}

	@Override
	public GameDTO research(String name) {
		
		List<Double[]> prices = new ArrayList<>();
		List<String> offerBranding = new ArrayList<>();
		List<Boolean> demo = new ArrayList<>();
		
		String formattedName = name.replaceAll("_", "-").toLowerCase();
		
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
					return new GameDTO(prices, offerBranding, demo, url);
				}
				
				fim = line.indexOf(discount, fim);
				String discountValue = readAttribute(fim + 2 + discount.length(), line);
				
				Double originalPrice = Double.parseDouble(priceValue)/100;
				Double discountPrice = Double.parseDouble(discountValue)/100;
				Double[] newPrices = {originalPrice, discountPrice};
				prices.add(newPrices);
				fim = line.indexOf(offers, fim);
			 	String offer = readAttribute(fim + 2 + offers.length(), line);
			 	offerBranding.add(offer);
			 	
				String type = readAttribute(line.indexOf("type", fim+110) + 6, line);
				demo.add(type.contains("TRIAL"));
			}
			}
			reader.close();
		
		}catch(FileNotFoundException e){
			throw new GameNotFoundException();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
