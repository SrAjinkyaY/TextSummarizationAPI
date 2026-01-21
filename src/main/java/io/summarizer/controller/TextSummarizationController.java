package io.summarizer.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.summarizer.beans.SummarizationRequest;
import io.summarizer.beans.SummarizationResponse;
import io.summarizer.service.TextSummarizationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/text")
public class TextSummarizationController {

    private final TextSummarizationService textSummarizationService;

    // Constructor Injection
    public TextSummarizationController(TextSummarizationService textSummarizationService) {
        this.textSummarizationService = textSummarizationService;
    }

    @PostMapping("/summarize")
    public SummarizationResponse summarizeText(@RequestBody @Valid SummarizationRequest request) {
        return textSummarizationService.summarizeText(request);
    }

}
