package com.bridgelabz.fundoo.note.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dto.NotesDto;
import com.bridgelabz.fundoo.response.Response;

@Service
public interface NotesService {

	public Response createNote(NotesDto notesDto, String token);
	
	public Response updateNote(NotesDto notesDto, String token, long noteId);

	public Response delete(String token, long noteId);

	public List<NotesDto> getAllNotes(String token);

	public Response pinAndUnPin(String token, long noteId);

	public Response archiveAndUnArchive(String token, long noteId);

	public Response trashAndUnTrash(String token, long noteId);

	public Response deletePermanently(String token, long noteId);

	public List<NotesDto> getArchiveNotes(String token);

	public List<NotesDto> getTrashNotes(String token);
}
