package com.example.textanalyzer;

import com.example.model.AnalyzeText200Response;
import com.example.model.AnalyzeTextRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TextAnalyzerController {

    @PostMapping("/analyze")
    public AnalyzeText200Response analyzeText(@RequestBody AnalyzeTextRequest request) {
        return TextAnalyzer.analyze(request);
    }
}