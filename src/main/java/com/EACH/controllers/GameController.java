package com.EACH.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EACH.DTO.GameDTO;
import com.EACH.Strategy.Researcher.ResearcherPlaystation;
import com.EACH.Strategy.Researcher.ResearcherStrategy;

@RestController
@RequestMapping("/api/games/v1")
public class GameController {
	
	ResearcherStrategy researcher;
	
	@GetMapping(value = "/playstation/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameDTO> findOnPlaystation(@PathVariable String name) {
		researcher = new ResearcherPlaystation();
		GameDTO game =  researcher.research(name);
		return ResponseEntity.ok(game);
	}

}
