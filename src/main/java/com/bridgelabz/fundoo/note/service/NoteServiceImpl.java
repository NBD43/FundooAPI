package com.bridgelabz.fundoo.note.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.note.dto.ColorDto;
import com.bridgelabz.fundoo.note.dto.NotesDto;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.NotesRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepo;
import com.bridgelabz.fundoo.utility.ResponseHelper;
import com.bridgelabz.fundoo.utility.TokenGenerator;
import com.bridgelabz.fundoo.utility.Utility;

@PropertySource("classpath:message.properties")
@Service("noteService")
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
		
		
		Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.notes.createdSuccessfull"));
		return response;
		
		
		
	}


	@Override
	public Response updateNote(NotesDto notesDto, String token, Long noteId) {
		if(notesDto.getTitle().isEmpty() && notesDto.getDescription().isEmpty()) {
			throw new UserException(-5,"Title and discriptions are empty");
		}
		long id=userToken.decodeToken(token);
		Note notes=notesRepository.findBynoteIdAndUserId(noteId, id);
		notes.setTitle(notesDto.getTitle());
		notes.setDescription(notesDto.getDescription());
		notes.setModified(LocalDateTime.now());
		notesRepository.save(notes);
		Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.notes.updated"));
		return response;
	}

	@Override
	public Response delete(String token, Long noteId) {
		long id=userToken.decodeToken(token);
		Note notes=notesRepository.findBynoteIdAndUserId(noteId, id);
		if(notes==null) {
			throw new UserException(-5,"Invalid input");
		}
		if(notes.isTrash()==false) {
			notes.setTrash(true);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.trashed"));
		}
		Response response=ResponseHelper.statusResponse(100, environment.getProperty("status.note.trashError"));
		return response;
	}
	
	@Override
	public Response deletePermanently(String token, Long noteId) {
		long id =userToken.decodeToken(token);
		Optional<User> user=userRepository.findById(id);
		Note note=notesRepository.findById(noteId).orElseThrow();
		System.out.println(note);
		if(note.isTrash()==true) {
			user.get().getNotes().remove(note);
			userRepository.save(user.get());
			notesRepository.delete(note);
			Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.deleted"));
			return response;
		}else {
		Response response=ResponseHelper.statusResponse(100, environment.getProperty("status.note.notdeleted"));
		return response;
		}
	}

	@Override
	public List<Note> getAllNotes(String token) {
		long id =userToken.decodeToken(token);
		User user =userRepository.findById(id).get();
		
//		List<NotesDto> listNotes=new ArrayList<NotesDto>();
//		for(Note userNotes:notes) {
//			NotesDto noteDto=modelMapper.map(userNotes,NotesDto.class);
//			if(userNotes.isArchived()==false && userNotes.isTrash()==false) {
//				listNotes.add(noteDto);
//			}

		
		return user.getNotes();
	}

	@Override
	public Response pinAndUnPin(String token, Long noteId) {
		long id =userToken.decodeToken(token);
		Note notes=notesRepository.findBynoteIdAndUserId(noteId, id);
		if(notes==null) {
			throw new UserException(-5,"Invalid input");
		}
		if(notes.isPined()==false) {
			notes.setPined(true);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.pinned"));
			return response;
		}else {
			notes.setPined(false);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.unpinned"));
			return response;
		
		}
	}
           
	@Override
	public Response archiveAndUnArchive(String token, Long noteId) {
		long id =userToken.decodeToken(token);
		Note notes=notesRepository.findBynoteIdAndUserId(noteId, id);
		if(notes==null) {
			throw new UserException(-5,"Invalid input");
		}
		if(notes.isArchived()==false) {
			notes.setArchived(true);;
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.archieved"));
			return response;
		}else {
			notes.setArchived(false);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.unarchieved"));
			return response;
		
		}
	}

	@Override
	public Response trashAndUnTrash(String token, Long noteId) {
		System.out.println(token+"......"+noteId);
		long id =userToken.decodeToken(token);
		Note notes=notesRepository.findBynoteIdAndUserId(noteId, id);
		if(notes==null) {
			throw new UserException(-5,"Invalid input");
		}
		if(notes.isTrash()==false) {
			notes.setTrash(true);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.trashed"));
			return response;
		}else {
			notes.setTrash(false);
			notes.setModified(LocalDateTime.now());
			notesRepository.save(notes);
			Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.untrashed"));
			return response;
		
		}
	}


	@Override
	public Response colourNote(String token,ColorDto colorDto) {
		long id =userToken.decodeToken(token);
		Note notes=notesRepository.findBynoteIdAndUserId(colorDto.getNoteId(), id);
		notes.setColour(colorDto.getColor());
		notesRepository.save(notes);
		Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.notes.colour"));
		return response;
	}


	@Override
	public Response reminderNote(String reminderDate, String token, Long noteId) {
		long id =userToken.decodeToken(token);
		Note notes=notesRepository.findBynoteIdAndUserId(noteId, id);
		LocalDateTime today=LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime remind=LocalDateTime.parse(reminderDate, formatter);
		if(today.isBefore(remind)) {
			throw new UserException(-6,"date is before the orignal time");
		}
		notes.setReminder(reminderDate);
		notesRepository.save(notes);
		Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.notes.setreminder"));
		return response;
	}


	@Override
	public Response addCollabrator(String token, String email, Long noteId) {
		long userId=userToken.decodeToken(token);
		System.out.println("userId"+userId);
		Optional<User> mainUser=userRepository.findById(userId);
		Optional<User> user=userRepository.findByEmailId(email);
		if(!user.isPresent()) {
			throw  new UserException(-4,"No user exit");
		}
		Note note=notesRepository.findBynoteIdAndUserId(noteId, userId);
		//Note note = notesRepository.findByUserIdAndNoteId(userId, noteId);
		System.out.println("note is"+note);
		if(note==null) {
			throw new UserException(-5,"No note exist");
		}
		if(user.get().getCollabaratedNotes().contains(note)) {
			throw new UserException(-5,"Note is already collabrated");
		}
		
		user.get().getCollabaratedNotes().add(note);
		note.getCollaboratedUser().add(user.get());
		userRepository.save(user.get());
		notesRepository.save(note);
		Utility.send(email, "Collaborated Note", note.getTitle()+"colaborated with yoy");
		Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.collaborated"));
		return response;
	}


	@Override
	public Response removeCollabrator(String token, String email, Long noteId) {
		long userId=userToken.decodeToken(token);
		Optional<User> mainUser=userRepository.findById(userId);
		Optional<User> user=userRepository.findByEmailId(email);
		if(!user.isPresent()) {
			throw  new UserException(-4,"No user exit");
		}
		Note note=notesRepository.findBynoteIdAndUserId(userId, noteId);
		if(note==null) {
			throw new UserException(-5,"No note exist");
		}
		if(user.get().getCollabaratedNotes().contains(note)) {
			throw new UserException(-5,"Note is already collabrated");
		}
		
		user.get().getCollabaratedNotes().remove(note);
		note.getCollaboratedUser().remove(user.get());
		userRepository.save(user.get());
		notesRepository.save(note);
		
		Response response=ResponseHelper.statusResponse(200, environment.getProperty("status.note.removecollaborated"));
		return response;
	}


	


	@Override
	public List<Note> getAllArchive(String token) {
		long userId = userToken.decodeToken(token);

		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("No note is available"));
		List<Note> archieveNote = user.getNotes().stream().filter(data -> (data.isArchived() == true && data.isTrash() == false) )
				.collect(Collectors.toList());

		user.getNotes().stream().filter(data1 -> (data1.isArchived() == true)).collect(Collectors.toList())
				.forEach(System.out::println);
		return archieveNote;
	}
	
	
	@Override
	public List<Note> getNotes(String token) {
		long userId = userToken.decodeToken(token);

		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("No note is available"));
		List<Note> archieveNote = user.getNotes().stream().filter(data -> (data.isArchived() == false && data.isTrash() == false && data.isPined()==false ) )
				.collect(Collectors.toList());

		user.getNotes().stream().filter(data1 -> (data1.isArchived() == false)).collect(Collectors.toList())
				.forEach(System.out::println);
		return archieveNote;
	}
	
	
	@Override
	public List<Note> getTrashNotes(String token) {
		long userId = userToken.decodeToken(token);

		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("No note is available"));
		List<Note> archieveNote = user.getNotes().stream().filter(data -> (data.isTrash() == true) )
				.collect(Collectors.toList());

		user.getNotes().stream().filter(data1 -> (data1.isTrash() == true)).collect(Collectors.toList())
				.forEach(System.out::println);
		return archieveNote;
	}
	
	
	@Override
	public List<Note> getPinnedNotes(String token) {
		long userId = userToken.decodeToken(token);

		User user = userRepository.findById(userId).orElseThrow(() -> new UserException("No note is available"));
		List<Note> archieveNote = user.getNotes().stream().filter(data -> (data.isArchived() == false && data.isTrash() == false && data.isPined()==true) )
				.collect(Collectors.toList());

		user.getNotes().stream().filter(data1 -> (data1.isPined()==true)).collect(Collectors.toList())
				.forEach(System.out::println);
		return archieveNote;
	}

	

}
