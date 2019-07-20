package com.bridgelabz.fundoo.user.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bridgelabz.fundoo.note.dto.NotesDto;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String mobileNum;
	private boolean isVerify;
	private LocalDateTime registerDate = LocalDateTime.now();
	private String profile;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Note> notes;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Label> label;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Note> collabaratedNotes;
//	public Long getUserId() {
//		return userId;
//	}
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//	public String getFirstName() {
//		return firstName;
//	}
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//	public String getLastName() {
//		return lastName;
//	}
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//	public String getEmailId() {
//		return emailId;
//	}
//	public void setEmailId(String emailId) {
//		this.emailId = emailId;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getMobileNum() {
//		return mobileNum;
//	}
//	public void setMobileNum(String mobileNum) {
//		this.mobileNum = mobileNum;
//	}
//	public boolean isVerify() {
//		return isVerify;
//	}
//	public void setVerify(boolean isVerify) {
//		this.isVerify = isVerify;
//	}
//	public LocalDateTime getRegisterDate() {
//		return registerDate;
//	}
//	public void setRegisterDate(LocalDateTime registerDate) {
//		this.registerDate = registerDate;
//	}
//	public List<Note> getNotes() {
//		return notes;
//	}
//	public void setNotes(List<Note> notes) {
//		this.notes = notes;
//	}
//	public List<Label> getLabel() {
//		return label;
//	}
//	public void setLabel(List<Label> label) {
//		this.label = label;
//	}
//	public List<Note> getCollabaratedNotes() {
//		return collabaratedNotes;
//	}
//	public void setCollabaratedNotes(List<Note> collabaratedNotes) {
//		this.collabaratedNotes = collabaratedNotes;
//	}
	
	


	

	

}
