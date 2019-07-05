package com.bridgelabz.fundoo.note.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@Autowired
	private NotesService noteService;
	
	
	@PostMapping("/create")
	public ResponseEntity<Response> createNote(@RequestBody NotesDto notesDto,@RequestHeader String token){
		logger.info(notesDto.toString());
		Response responseStatus=noteService.createNote(notesDto, token);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
		
	}
	@PutMapping("/update")
	public ResponseEntity<Response> updatingNote(@RequestBody NotesDto notesDto,@RequestHeader String token,@RequestParam long noteId){
		logger.info(notesDto.toString());
		Response responseStatus=noteService.updateNote(notesDto, token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	@PutMapping("/delete")
	public ResponseEntity<Response>deleteNote(@RequestHeader String token,@RequestParam long noteId){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.delete(token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	@DeleteMapping("/delete")
	public ResponseEntity<Response>deleteNoteFromDisk(@RequestHeader String token,@RequestParam long noteId){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.deletePermanently(token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	

}
