package io.summarizer.beans;

import lombok.Data;

@Data
public class SummarizationResponse {
	private String summarizedText;
	private Integer length;
	private Long processingTime;
}
