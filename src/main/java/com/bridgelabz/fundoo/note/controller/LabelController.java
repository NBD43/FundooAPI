package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.service.LabelService;
import com.bridgelabz.fundoo.response.Response;

import io.swagger.annotations.ResponseHeader;



@RestController
@RequestMapping("/user/label")
@PropertySource("classpath:message.properties")
public class LabelController {
	
	Logger logger = LoggerFactory.getLogger(LabelController.class);
	@Autowired
	private LabelService labelService;
	
	@PostMapping("/create")
	ResponseEntity<Response> createLabel(@RequestBody LabelDto labelDto , @RequestHeader String token) {
		Response statusResponse = labelService.createLabel(labelDto, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete")
	ResponseEntity<Response> delete(@RequestParam long labelId, @RequestHeader String token) {
		Response statusResponse = labelService.deleteLabel(labelId, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/update")
	ResponseEntity<Response> updateLabel(@RequestBody LabelDto labelDto , @RequestHeader String token,@RequestParam long labelId) {
		Response statusResponse = labelService.updateLabel(labelId, token, labelDto);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getlabel")
	List<LabelDto> getLabel(@RequestHeader String token){
		List<LabelDto> listLabel = labelService.getAllLabel(token);
		return listLabel;
	}
	
	@PutMapping("/addlabeltonote")
	ResponseEntity<Response> addNoteToLabels(@RequestParam long labelId , @RequestHeader String token , @RequestParam long noteId){
		Response statusResponse = labelService.addLabelToNote(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@PutMapping("/removelabelfromnote")
	ResponseEntity<Response> removeNoteToLabels(@RequestParam long labelId , @RequestHeader String token , @RequestParam long noteId){
		Response statusResponse = labelService.removeLabelFromNote(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}

}
