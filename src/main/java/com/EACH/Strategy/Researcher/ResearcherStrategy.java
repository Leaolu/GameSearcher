package com.EACH.Strategy.Researcher;

import org.springframework.stereotype.Service;

import com.EACH.DTO.GameDTO;

@Service
public interface ResearcherStrategy {
	
	public GameDTO research(String name);

}
