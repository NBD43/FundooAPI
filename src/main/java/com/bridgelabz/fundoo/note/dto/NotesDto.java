package com.bridgelabz.fundoo.note.dto;

import javax.validation.constraints.NotEmpty;

public class NotesDto {
	
	@NotEmpty(message = "pls provide title")
	private String title;
	@NotEmpty(message = "pls provide description")
	private String description;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "NotesDto [title=" + title + ", description=" + description + "]";
	}
	
	

}
