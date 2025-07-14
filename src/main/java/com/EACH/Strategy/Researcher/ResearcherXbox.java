package com.EACH.Strategy.Researcher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.EACH.DTO.GameDTO;
import com.EACH.Exceptions.GameNotFoundException;
import com.EACH.Exceptions.GameUnavailableException;

@Service
public class ResearcherXbox implements ResearcherStrategy{
	private static final String search = "SEARCH_GAMES_SEARCHQUERY";
	private static final String noDiscount = "Price-module__moreText___sNMVr";
	private static final String wDiscountOriginal = "Price-module__originalPrice___XNCxs";
	private static final String wDiscount = "Price-module__listedDiscountPrice___A-+ds";
	
	private static String readUntilPlus(String line, int index) {
		
		char c = line.charAt(index);
		String value = "";
		while(c != '+' && c != '<') {
			value += c;
			index++;
			c = line.charAt(index);
		}
		return value.replace(",", ".");
		
	}
	
	private static String getId(String fName) {
		String name = fName.replaceAll("-", "+");
		try{
			URL url = new URI("https://www.xbox.com/pt-br/Search/Results?q="+name).toURL();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line;
			while((line = reader.readLine()) != null){
				if(line.contains(search)) {
					int index = line.indexOf(search);
					index = line.indexOf("productId", index)+12;
					String id = "";
					char c = line.charAt(index);
					while(c != '\"') {
						id += c;
						index++;
						c = line.charAt(index);
					}
					return id;
				}
			}
		}catch(FileNotFoundException e){
			throw new GameNotFoundException();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GameDTO> research(String name) {
		// TODO Auto-generated method stub
		String formattedName = name.replaceAll("_", "-").toLowerCase();
		String id = getId(formattedName);
		List<GameDTO> games = new ArrayList<>();
		
		try{
			URL url = new URI("https://www.xbox.com/pt-br/games/store/"+formattedName+"/"+id).toURL();
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line;
			while((line = reader.readLine()) != null){
				if(line.contains(noDiscount)) {
					int index = line.indexOf(noDiscount, 0)+noDiscount.length();
					Double originalPrice = 0.0;
					if(' ' == line.charAt(index + noDiscount.length())) {
						index = line.indexOf(wDiscountOriginal) +4 + wDiscountOriginal.length();
						
						originalPrice = Double.valueOf(readUntilPlus(line, index).replace(",", ""))/100;
						
						index = line.indexOf(wDiscount, index) + 4 + wDiscount.length();
						
						Double discountPrice = Double.valueOf(readUntilPlus(line, index));
						
						Double[] prices = {originalPrice, discountPrice};
						games.add(new GameDTO(prices, "\"NONE\"", false));
						
						}else {
							index += 4;
							originalPrice = Double.parseDouble(readUntilPlus(line, index));
							Double[] prices = {originalPrice, originalPrice};
							games.add(new GameDTO(prices, "\"NONE\"", false));
						}
					
					if(line.contains("<title>Game Pass</title>")) {
						Double[] prices = {originalPrice, 0.0};
						games.add(new GameDTO(prices, "GAMEPASS", false));
					}
					return games;
					}
				}
		}catch(FileNotFoundException e){
			throw new GameNotFoundException();
		}catch(NumberFormatException e) {
			throw new GameUnavailableException();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
