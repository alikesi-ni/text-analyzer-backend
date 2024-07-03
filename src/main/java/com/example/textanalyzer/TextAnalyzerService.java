package com.example.textanalyzer;

import com.example.model.AnalyzeText200Response;
import com.example.model.AnalyzeTextRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TextAnalyzerService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UPPER_CASE_VOWELS = "AEIOU";
    private static final String UPPER_CASE_CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZ";
    private static final String ATTRIBUTABLE_CHARACTERS =
            UPPER_CASE_CONSONANTS + UPPER_CASE_CONSONANTS.toLowerCase()
            + UPPER_CASE_VOWELS + UPPER_CASE_VOWELS.toLowerCase();

    public static AnalyzeText200Response analyze(AnalyzeTextRequest request) {
        LOGGER.log(Level.INFO, request);
        Map<String, Integer> letterCount = new HashMap<>();
        Set<String> nonAttributableCharacters = new HashSet<>();

        String lettersToCheckAgainst = request.getLetterType() == AnalyzeTextRequest.LetterTypeEnum.CONSONANTS
                ? UPPER_CASE_CONSONANTS : UPPER_CASE_VOWELS;

        String input = request.getInput();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            char lowerCaseCharacter = Character.toLowerCase(c);
            if (ATTRIBUTABLE_CHARACTERS.indexOf(c) != -1) {
                int index = lettersToCheckAgainst.toLowerCase().indexOf(lowerCaseCharacter);
                if (index != -1) {
                    char upperCaseCharacter = lettersToCheckAgainst.charAt(index);
                    letterCount.put(String.valueOf(upperCaseCharacter),
                            letterCount.getOrDefault(String.valueOf(upperCaseCharacter), 0) + 1);
                }
            }
            else {
                nonAttributableCharacters.add(String.valueOf(c));
            }
        }

        AnalyzeText200Response response = new AnalyzeText200Response();
        response.setCharacterCount(letterCount);
        response.setNonAttributableCharacters(nonAttributableCharacters.stream().toList());
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
