package com.EACH.DTO;

import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"prices", "offerType", "demo"})
public class GameDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Double[] prices;
	private String offerType;
	private Boolean demo;
	
	public GameDTO(Double[] prices, String offerType, Boolean demo) {
		this.prices = prices;
		this.offerType = offerType;
		this.demo = demo;
		
	}
	public Double[] getPrices() {
		return prices;
	}

	public String getOfferType() {
		return offerType;
	}


	public Boolean getDemo() {
		return demo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(demo, offerType, prices);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameDTO other = (GameDTO) obj;
		return Objects.equals(demo, other.demo) && Objects.equals(offerType, other.offerType)
				&& Objects.equals(prices, other.prices);
	}

}
