package com.bridgelabz.fundoo.note.service;

import java.util.List;
import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.response.Response;

import org.springframework.stereotype.Service;



@Service
public interface LabelService {
   public Response createLabel(LabelDto labelDto , String token);
	
	

	public Response deleteLabel(Long labelId ,String token);
	
	

	public Response updateLabel(Long labelId , String token , LabelDto labelDto);
	

	public List<Label> getAllLabel(String token);
	
	
	public Response addLabelToNote(Long labelId ,String token , Long noteId);
	
	public Response removeLabelFromNote(Long labelId ,String token , Long noteId);

}
