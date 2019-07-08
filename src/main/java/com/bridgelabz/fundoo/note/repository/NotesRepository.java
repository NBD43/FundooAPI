package com.bridgelabz.fundoo.note.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundoo.note.model.Note;

public interface NotesRepository extends  JpaRepository<Note,Long>{
	
	public Note findBynoteIdAndUserId(long Id,long userId);
	public Note findByUserIdAndNoteId(long userId, long noteId);
	public List<Note> findByUserId(long userId);

}
