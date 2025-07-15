package com.EACH.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EACH.DTO.GameDTO;
import com.EACH.Strategy.Researcher.ResearcherNintendo;
import com.EACH.Strategy.Researcher.ResearcherPlaystation;
import com.EACH.Strategy.Researcher.ResearcherStrategy;
import com.EACH.Strategy.Researcher.ResearcherXbox;

@RestController
@RequestMapping("/api/games/v1")
public class GameController implements GameControllerDocs{
	
	ResearcherStrategy researcher;
	
	@GetMapping(value = "/playstation/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameDTO>> findOnPlaystation(@PathVariable String name) {
		researcher = new ResearcherPlaystation();
		List<GameDTO> game =  researcher.research(name);
		return ResponseEntity.ok(game);
	}
	
	@GetMapping(value = "/xbox/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameDTO>> findOnXbox(@PathVariable String name) {
		researcher = new ResearcherXbox();
		List<GameDTO> game =  researcher.research(name);
		return ResponseEntity.ok(game);
	}
	
	@GetMapping(value = "/nintendo/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GameDTO>> findOnNintendo(@PathVariable String name) {
		researcher = new ResearcherNintendo();
		List<GameDTO> game =  researcher.research(name);
		return ResponseEntity.ok(game);
	}

}
