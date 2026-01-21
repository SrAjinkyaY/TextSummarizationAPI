package io.summarizer.beans;

import jakarta.validation.constraints.NotBlank;

public class SummarizationRequest {
	@NotBlank(message = "Text must not be blank")
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
