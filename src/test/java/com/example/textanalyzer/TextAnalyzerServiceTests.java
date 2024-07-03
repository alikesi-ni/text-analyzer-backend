package com.example.textanalyzer;

import com.example.model.AnalyzeText200Response;
import com.example.model.AnalyzeTextRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TextAnalyzerServiceTests {

    @Test
    void testSameUpperCaseAndLowerCaseConsonant() {
        String input = "Dedalus";
        AnalyzeTextRequest.LetterTypeEnum letterType = AnalyzeTextRequest.LetterTypeEnum.CONSONANTS;
        AnalyzeTextRequest request = new AnalyzeTextRequest();
        request.setInput(input);
        request.setLetterType(letterType);

        AnalyzeText200Response response = TextAnalyzerService.analyze(request);

        assertNotNull(response);
        assertEquals(2, response.getCharacterCount().get("D"));
    }

    @Test
    void testSameUpperCaseAndLowerCaseVowel() {
        String input = "Information";
        AnalyzeTextRequest.LetterTypeEnum letterType = AnalyzeTextRequest.LetterTypeEnum.VOWELS;
        AnalyzeTextRequest request = new AnalyzeTextRequest();
        request.setInput(input);
        request.setLetterType(letterType);

        AnalyzeText200Response response = TextAnalyzerService.analyze(request);

        assertNotNull(response);
        assertEquals(2, response.getCharacterCount().get("I"));
    }

    @Test
    void testEszett() {
        String input = "Straße STRAẞE";
        AnalyzeTextRequest.LetterTypeEnum letterType = AnalyzeTextRequest.LetterTypeEnum.CONSONANTS;
        AnalyzeTextRequest request = new AnalyzeTextRequest();
        request.setInput(input);
        request.setLetterType(letterType);

        AnalyzeText200Response response = TextAnalyzerService.analyze(request);

        assertNotNull(response);
        assertTrue(response.getNonAttributableCharacters().contains("ß")
                && response.getNonAttributableCharacters().contains("ẞ"));
        assertEquals(2, response.getCharacterCount().get("S"));
    }

    @Test
    void testUmlauts() {
        String input = "ÄÖÜäöü";
        AnalyzeTextRequest.LetterTypeEnum letterType = AnalyzeTextRequest.LetterTypeEnum.VOWELS;
        AnalyzeTextRequest request = new AnalyzeTextRequest();
        request.setInput(input);
        request.setLetterType(letterType);

        AnalyzeText200Response response = TextAnalyzerService.analyze(request);

        assertNotNull(response);
        assertTrue(response.getNonAttributableCharacters().contains("Ä")
                && response.getNonAttributableCharacters().contains("Ö")
                && response.getNonAttributableCharacters().contains("Ü"));
        assertNull(response.getCharacterCount().get("Ä"));
        assertNull(response.getCharacterCount().get("Ö"));
        assertNull(response.getCharacterCount().get("Ü"));
    }

    @Test
    void testNonLatinCharacters() {
        String input = "我Ђ\u200Eओض";
        AnalyzeTextRequest.LetterTypeEnum letterType = AnalyzeTextRequest.LetterTypeEnum.CONSONANTS;
        AnalyzeTextRequest request = new AnalyzeTextRequest();
        request.setInput(input);
        request.setLetterType(letterType);

        AnalyzeText200Response response = TextAnalyzerService.analyze(request);

        assertNotNull(response);
        assertTrue(response.getNonAttributableCharacters().contains("我")
                && response.getNonAttributableCharacters().contains("Ђ")
                && response.getNonAttributableCharacters().contains("\u200E")
                && response.getNonAttributableCharacters().contains("ओ")
                && response.getNonAttributableCharacters().contains("ض"));

        for (String character : response.getNonAttributableCharacters()) {
            assertNull(response.getCharacterCount().get(character));
        }
    }

}
