package io.summarizer.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SummarizationClientBuilder {

	@Value("${summarization.system-message:You are a helpful AI assistant. Summarize the text clearly.}")
	private String systemMessage;

	@Bean
	public ChatClient summarizationChatClient(ChatClient.Builder builder) {
		return builder.defaultSystem(systemMessage).build();
	}
}
