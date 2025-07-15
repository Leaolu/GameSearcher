package com.EACH.Strategy.Researcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.EACH.DTO.GameDTO;
import com.EACH.Exceptions.GameNotFoundException;

public class ResearcherSteam implements ResearcherStrategy {
	
	private static final String noDiscount = "game_purchase_price price";
	private static final String wDiscount = "discount_block game_purchase_discount";
	private static final String curr = "priceCurrency";
	
	private static final String discount = "data-discount=";
	
	
	private static String readUntilQuotes(String line, int index) {
		String response = "";
		char c = line.charAt(index);
		while(c != '\"') {
			response += c;
			index++;
			c = line.charAt(index);
		}
		return response;
	}
	
	private static String getGameURL(String name){
		try {
			URL url = new URI("https://store.steampowered.com/search/?term="+name.replaceAll("_", "+")).toURL();
			String gameUrl = "https://store.steampowered.com/app/";
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			while((line = reader.readLine()) != null) {
				if(line.contains(gameUrl)) {
					gameUrl += readUntilQuotes(line, line.indexOf(gameUrl)+gameUrl.length());
					return gameUrl;
				}
			}
			throw new GameNotFoundException();
			
		} catch(Exception e) {
			throw new GameNotFoundException();
			
		}
		
	}

	@Override
	public List<GameDTO> research(String name) {
		List<GameDTO> games = new ArrayList<>();
		String currency = "";
		
		try {
			URL gameUrl =new URI(getGameURL(name)).toURL();
			BufferedReader reader = new BufferedReader(new InputStreamReader(gameUrl.openStream()));
			
			String line;
			while((line = reader.readLine()) != null) {
				
				if(line.contains(curr)) currency = readUntilQuotes(line, line.indexOf(curr)+curr.length()+11);
				
				if(line.contains(noDiscount)) {
					Double price = Double.valueOf(readUntilQuotes(line, line.indexOf(noDiscount)+noDiscount.length()+20))/100;
					Double[] prices = {price, price};
					games.add(new GameDTO(prices, "NONE", false, currency));
					return games;
					
				}else if(line.contains(wDiscount)) {
					int index = line.indexOf(wDiscount)+wDiscount.length()+20;
					Double discountPrice = Double.valueOf(readUntilQuotes(line, index))/100;
					Double dis = Double.parseDouble(readUntilQuotes(line, line.indexOf(discount)+discount.length()+1))/100;
					Double originalPrice = discountPrice/dis;
					Double[] prices = {originalPrice, discountPrice };
					games.add(new GameDTO(prices, "NONE", false, currency));
					return games;
				}
				
			}
			throw new GameNotFoundException();
		} catch (Exception e) {
			throw new GameNotFoundException();
		}
	}

}
