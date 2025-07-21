package com.EACH.Strategy.Researcher;

import java.util.List;

import org.springframework.stereotype.Service;

import com.EACH.DTO.GameDTO;

@Service
public interface ResearcherStrategy {
	
	public List<GameDTO> research(String name);

}
