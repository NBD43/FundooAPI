package com.bridgelabz.fundoo.note.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LabelDto {
	@NotEmpty(message = "labelName should not be Empty")
	@NotNull(message = "label name should not be null")
	private String labelName;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
}
