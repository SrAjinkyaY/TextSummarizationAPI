package io.summarizer.service;

import io.summarizer.beans.SummarizationRequest;
import io.summarizer.beans.SummarizationResponse;

public interface TextSummarizationService {
    public SummarizationResponse summarizeText( SummarizationRequest request);
}
