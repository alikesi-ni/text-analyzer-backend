package com.example.textanalyzer;

import com.example.model.AnalyzeText200Response;
import com.example.model.AnalyzeTextRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.*;


public class TextAnalyzer {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String UPPER_CASE_VOWELS = "AEIOU";
    private static final String UPPER_CASE_CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZ";

    public static AnalyzeText200Response analyze(AnalyzeTextRequest request) {
        LOGGER.log(Level.INFO, request);
        String upperCaseInput = Optional.ofNullable(request.getInput())
                .map(TextAnalyzer::customToUpperCase)
                .orElse("");
        Map<String, Integer> letterCount = new HashMap<>();
        List<String> nonAttributableCharacters = new ArrayList<>();
        String lettersToCheckAgainst = "";

        if (request.getLetterType() == AnalyzeTextRequest.LetterTypeEnum.CONSONANTS) {
            lettersToCheckAgainst = UPPER_CASE_CONSONANTS;
        }
        else if (request.getLetterType() == AnalyzeTextRequest.LetterTypeEnum.VOWELS) {
            lettersToCheckAgainst = UPPER_CASE_VOWELS;
        }

        for (int i = 0; i < upperCaseInput.length(); i++) {
            char c = upperCaseInput.charAt(i);
            if (lettersToCheckAgainst.indexOf(c) != -1) {
                    letterCount.put(String.valueOf(c), letterCount.getOrDefault(String.valueOf(c), 0) + 1);
            }
        }
        AnalyzeText200Response response = new AnalyzeText200Response();
        response.setCharacterCount(letterCount);
        response.setNonAttributableCharacters(nonAttributableCharacters);
        LOGGER.log(Level.INFO, response);
        return response;
    }

    private static String customToUpperCase(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (c == 'ß') {
                result.append('ẞ');
            } else {
                result.append(Character.toUpperCase(c));
            }
        }

        return result.toString();
    }


}
