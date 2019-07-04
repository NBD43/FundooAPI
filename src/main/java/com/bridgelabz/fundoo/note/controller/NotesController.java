package com.bridgelabz.fundoo.note.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.NotesDto;
import com.bridgelabz.fundoo.note.service.NoteServiceImpl;
import com.bridgelabz.fundoo.note.service.NotesService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/user/note")
@PropertySource("classpath:message.properties")
public class NotesController {
	
	
	Logger logger = LoggerFactory.getLogger(NotesController.class);
	private NotesService noteService;
	@PostMapping("/create")
	public ResponseEntity<Response> createNote(@RequestBody NotesDto notesdto,@RequestHeader String token){
		logger.info(notesdto.toString());
		Response responseStatus=noteService.createNote(notesdto, token);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
		
	}
	
	
	

}
