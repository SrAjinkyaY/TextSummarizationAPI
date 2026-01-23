package io.summarizer.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.summarizer.beans.SummarizationRequest;
import io.summarizer.beans.SummarizationResponse;
import io.summarizer.helper.SummarizationResponseBuilder;

@Service
public class TextSummarizationServiceImpl implements TextSummarizationService {

	private final ChatClient chatClient;

	@Value("${summarization.prompt.template}")
	private String promptTemplate;

	// Constructor Injection
	public TextSummarizationServiceImpl(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@Override
	public SummarizationResponse summarizeText(SummarizationRequest request) {
		long startTime = System.currentTimeMillis();
		String summarizedText = chatClient.prompt().user(u -> u.text(promptTemplate).param("text", request.getText()))
				.call().content();
		long endTime = System.currentTimeMillis();
		return SummarizationResponseBuilder.build(summarizedText, endTime - startTime);
	}
}
