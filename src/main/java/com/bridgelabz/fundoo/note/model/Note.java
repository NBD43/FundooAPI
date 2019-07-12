package com.bridgelabz.fundoo.note.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.bridgelabz.fundoo.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Data
@Entity
@Table
public class Note implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "noteId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long noteId;
	
	@Column(name = "userId")
	private Long userId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name="isTrash")
	private boolean isTrash;
	
	@Column(name = "isPined")
	private boolean isPined;
	
	@Column(name = "isArchived")
	private boolean isArchived;
	
	@Column(name="colour")
	private String colour;
	
	@Column(name = "reminder")
	private String reminder;
	
	@Column(name = "image")
	private String image;
	
	@Column(name="created")
	private LocalDateTime created ;
	
	@Column(name="modified")
	private LocalDateTime modified;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Label> listLabel;
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private List<User> collaboratedUser;
	
	

	

	
	
	
	
	


}
