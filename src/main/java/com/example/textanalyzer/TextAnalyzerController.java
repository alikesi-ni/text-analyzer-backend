package com.example.textanalyzer;

import com.example.model.AnalyzeText200Response;
import com.example.model.AnalyzeTextRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
public class TextAnalyzerController {

    @PostMapping("/analyze")
    public AnalyzeText200Response analyzeText(@Valid @RequestBody AnalyzeTextRequest request) {
        // method should not be necessary with proper annotation
        validateRequest(request);
        return TextAnalyzerService.analyze(request);
    }

    private void validateRequest(AnalyzeTextRequest request) {
        if (request == null || request.getLetterType() == null || request.getInput() == null) {
            throw new InvalidRequestException("Both input and letterType are required.");
        }
    }
}