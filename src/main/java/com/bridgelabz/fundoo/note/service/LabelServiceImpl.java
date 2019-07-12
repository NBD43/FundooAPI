package com.bridgelabz.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.note.dto.LabelDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NotesRepository;
import com.bridgelabz.fundoo.note.repository.LabelRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepo;
import com.bridgelabz.fundoo.utility.ResponseHelper;
import com.bridgelabz.fundoo.utility.TokenGenerator;;

@PropertySource("classpath:message.properties")
@Service("labelService")
public class LabelServiceImpl implements LabelService {
	

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private NotesRepository notesRepository;
	
	@Autowired
	private TokenGenerator userToken;
	
	@Autowired
	private Environment environment;
	

	@Override
	public Response createLabel(LabelDto labelDto, String token) {
		long userId = userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserException(-6, "Invalid input");
		}
		if(labelDto.getLabelName().isEmpty()) {
			throw new UserException(-6, "Label has no name");
		}
		Optional<Label> labelAvailability = labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if(labelAvailability.isPresent()) {
			throw new UserException(-6, "Label already exist");
		}
		
		Label label = modelMapper.map(labelDto,Label.class);
		
		label.setLabelName(labelDto.getLabelName());
		label.setUserId(userId);
		label.setCreatedDate(LocalDateTime.now());
		label.setModifiedDate(LocalDateTime.now());
		labelRepository.save(label);
		user.get().getLabel().add(label);
		userRepository.save(user.get());
		Response response=ResponseHelper.statusResponse(100, environment.getProperty("status.label.created"));
	return response;
	}

	@Override
	public Response deleteLabel(Long labelId, String token) {
		long userId = userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserException(-6, "Invalid input");
		}
		
		Label label=labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label==null) {
			throw new UserException(-6,"Invalid Input");
		}
		user.get().getLabel().remove(label);
		userRepository.save(user.get());
		labelRepository.delete(label);
		
		
		Response response=ResponseHelper.statusResponse(100, environment.getProperty("status.label.deleted"));
		return response;
	}

	@Override
	public Response updateLabel(Long labelId, String token, LabelDto labelDto) {
		long userId = userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserException(-6, "Invalid input");
		}

		Label label=labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label==null) {
			throw new UserException(-6,"No label exists");
		}
		if(labelDto.getLabelName().isEmpty()) {
			throw new UserException(-6,"label has noName");
		}
		
		Optional<Label> labelAvailablity=labelRepository.findByUserIdAndLabelName(userId, labelDto.getLabelName());
		if(labelAvailablity.isPresent()) {
			throw new UserException(-6,"Label already exit");
		}
		
		label.setLabelName(labelDto.getLabelName());
		label.setModifiedDate(LocalDateTime.now());
		labelRepository.save(label);
		
		Response response=ResponseHelper.statusResponse(100, environment.getProperty("status.label.updated"));
		return response;
	}

	@Override
	public List<Label> getAllLabel(String token) {
		long id =userToken.decodeToken(token);
		User user =userRepository.findById(id).get();
		return user.getLabel();
	}

	@Override
	public Response addLabelToNote(Long labelId, String token, Long noteId) {
		long userId = userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserException(-6, "Invalid input");
		}

		Label label=labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label==null) {
			throw new UserException(-6,"No label exists");
		}
		Note note=notesRepository.findBynoteIdAndUserId(noteId, userId);
		if(note==null) {
			throw new UserException(-6,"No Note  exists");
		}
		
		label.setModifiedDate(LocalDateTime.now());
		label.getNotes().add(note);
		note.getListLabel().add(label);
		note.setModified(LocalDateTime.now());
		labelRepository.save(label);
		notesRepository.save(note);
		
		
		Response response=ResponseHelper.statusResponse(100, environment.getProperty("status.label.addedtonote"));
		return response;
	}

	@Override
	public Response removeLabelFromNote(Long labelId, String token, Long noteId) {
		long userId = userToken.decodeToken(token);
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()) {
			throw new UserException(-6, "Invalid input");
		}

		Label label=labelRepository.findByLabelIdAndUserId(labelId, userId);
		if(label==null) {
			throw new UserException(-6,"No label exists");
		}
		
		Note note=notesRepository.findBynoteIdAndUserId(noteId, userId);
		if(note==null) {
			throw new UserException(-6,"No Note  exists");
		}
		
		label.setModifiedDate(LocalDateTime.now());
		note.getListLabel().remove(label);
		note.setModified(LocalDateTime.now());
		labelRepository.save(label);
		notesRepository.save(note);
		
		
		Response response=ResponseHelper.statusResponse(100, environment.getProperty("status.label.removedfromnote"));
		return response;
	}

	

}
