package com.example.textanalyzer;

import com.example.model.AnalyzeTextRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TextAnalyzer {

    private static final String UPPER_CASE_VOWELS = "AEIOU";
    private static final String UPPER_CASE_CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZ";

    public static Map<String, Integer> analyze(AnalyzeTextRequest request) {
        String upperCaseInput = Optional.ofNullable(request.getInput())
                .map(TextAnalyzer::customToUpperCase)
                .orElse("");
        Map<String, Integer> letterCount = new HashMap<>();
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
        return letterCount;
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
