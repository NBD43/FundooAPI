package com.bridgelabz.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.note.dto.NotesDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NotesRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepo;
import com.bridgelabz.fundoo.utility.ResponseHelper;
import com.bridgelabz.fundoo.utility.TokenGenerator;


public class NoteServiceImpl implements NotesService {
	Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@Autowired
	private TokenGenerator userToken;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private NotesRepository notesRepository;

	@Autowired
	private Environment environment;

	@Override
	public Response createNote(NotesDto notesDto, String token) {

		long id = userToken.decodeToken(token);
		logger.info(notesDto.toString());
		if (notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {

			throw new UserException(-5, "Title and description are empty");

		}
		Note notes = modelMapper.map(notesDto, Note.class);
		Optional<User> user = userRepository.findById(id);
		notes.setUserId(id);
		notes.setCreated(LocalDateTime.now());
		notes.setModified(LocalDateTime.now());
		user.get().getNotes().add(notes);
		notesRepository.save(notes);
		userRepository.save(user.get());
		
		
		Response response=ResponseHelper.statusResponse(100, environment.getProperty("status.notes.createdSuccessfull"));
		return response;
		
		
		
	}


	@Override
	public Response updateNote(NotesDto notesDto, String token, long noteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(String token, long noteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotesDto> getAllNotes(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response pinAndUnPin(String token, long noteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response archiveAndUnArchive(String token, long noteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response trashAndUnTrash(String token, long noteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deletePermanently(String token, long noteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotesDto> getArchiveNotes(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NotesDto> getTrashNotes(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
