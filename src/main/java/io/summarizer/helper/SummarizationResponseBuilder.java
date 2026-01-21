package io.summarizer.helper;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.summarizer.beans.SummarizationResponse;

public class SummarizationResponseBuilder {
	private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public static SummarizationResponse build(String text, long processingTime) {
		SummarizationResponse response = new SummarizationResponse();
		response.setSummarizedText(text);
		response.setLength(text.length());
		response.setProcessingTime(processingTime);
		LOGGER.info("Generated Text Length: " + text.length() + " Processing Time : " + processingTime);
		return response;
	}
}
