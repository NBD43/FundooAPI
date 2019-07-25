package com.bridgelabz.fundoo.note.controller;


import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.NotesDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.service.NoteServiceImpl;
import com.bridgelabz.fundoo.note.service.NotesService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/user/note")
@CrossOrigin(allowedHeaders = "*", origins = "*")
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
	public ResponseEntity<Response> updatingNote(@RequestBody NotesDto notesDto,@RequestHeader String token,@RequestParam Long noteId){
		logger.info(notesDto.toString());
		Response responseStatus=noteService.updateNote(notesDto, token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	@PutMapping("/delete")
	public ResponseEntity<Response>deleteNote(@RequestHeader String token,@RequestParam Long noteId){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.delete(token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	@DeleteMapping("/delete")
	public ResponseEntity<Response>deleteNoteFromDisk(@RequestHeader String token,@RequestParam Long noteId){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.deletePermanently(token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	@GetMapping("/getAllNotes")
	public List<Note>getAllNotes(@RequestHeader String token){
		List<Note> listNotes=noteService.getAllNotes(token);
		return listNotes;
	}
	
	@PutMapping("/pin")
	public ResponseEntity<Response>pinAndUnpin(@RequestHeader String token,@RequestParam Long noteId){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.pinAndUnPin(token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	@PutMapping("/archive")
	public ResponseEntity<Response>archiveAndUnarchive(@RequestHeader String token,@RequestParam Long noteId){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.archiveAndUnArchive(token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	
	@PutMapping("/trash")
	public ResponseEntity<Response>trashAndUntrash(@RequestHeader String token,@RequestParam Long noteId){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.trashAndUnTrash(token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	
	@PutMapping("/colour")
	public ResponseEntity<Response>noteColour(@RequestHeader String token,@RequestParam Long noteId,@RequestHeader String colour){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.colourNote(colour, token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	

	@PutMapping("/reminder")
	public ResponseEntity<Response>noteReminder(@RequestHeader String token,@RequestParam Long noteId,@RequestHeader String reminderDate){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.reminderNote(reminderDate, token, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	
	@PostMapping("/addcolaborator")
	public ResponseEntity<Response>CollaboratorAdd(@RequestHeader String token,@RequestParam Long noteId,@RequestParam String email){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.addCollabrator(token, email, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	
	@DeleteMapping("/removecollaborator")
	public ResponseEntity<Response>CollaboratorRemove(@RequestHeader String token,@RequestParam Long noteId,@RequestHeader String email){
		//logger.info(notesDto.toString());
		Response responseStatus=noteService.removeCollabrator(token, email, noteId);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	
	

}
