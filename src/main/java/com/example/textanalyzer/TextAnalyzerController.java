package com.example.textanalyzer;

import com.example.model.AnalyzeText200Response;
import com.example.model.AnalyzeTextRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TextAnalyzerController {

    @PostMapping("/analyze")
    public AnalyzeText200Response analyzeText(@RequestBody AnalyzeTextRequest request) {
        return TextAnalyzerService.analyze(request);
    }
}