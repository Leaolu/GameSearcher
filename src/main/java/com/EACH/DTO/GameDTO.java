package com.EACH.DTO;

import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"prices", "offerType", "demo"})
public class GameDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Double[]> prices;
	private List<String> offerType;
	private List<Boolean> demo;
	private URL link;
	
	public GameDTO(List<Double[]> prices, List<String> offerType, List<Boolean> demo, URL link) {
		this.prices = prices;
		this.offerType = offerType;
		this.demo = demo;
		this.link = link;
		
	}
	public List<Double[]> getPrices() {
		return prices;
	}

	public List<String> getOfferType() {
		return offerType;
	}


	public List<Boolean> getDemo() {
		return demo;
	}

	public URL getLink() {
		return link;
	}

	public void setLink(URL link) {
		this.link = link;
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
