package com.example.textanalyzer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TextAnalyzerBackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAnalyzeTextOKRequest() throws Exception {
        String requestBody = "{\"input\": \"test input\", " +
                "\"letterType\": \"consonants\"}";

        mockMvc.perform(post("/api/analyze")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void testAnalyzeTextMissingLetterType() throws Exception {
        String requestBody = "{\"input\": \"test input\"}";

        mockMvc.perform(post("/api/analyze")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAnalyzeTextNonExistingLetterType() throws Exception {
        String requestBody = "{\"input\": \"test input\" " +
                "\"letterType\": \"non-existing\"}";

        mockMvc.perform(post("/api/analyze")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAnalyzeTextMissingInput() throws Exception {
        String requestBody = "{\"letterType\": \"consonants\"}";

        mockMvc.perform(post("/api/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAnalyzeTextNullRequest() throws Exception {
        String requestBody = "null"; // Explicitly sending a null JSON value

        mockMvc.perform(post("/api/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAnalyzeTextRandomJson() throws Exception {
        String requestBody = "{\"randomField\": \"randomValue\"}";

        mockMvc.perform(post("/api/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAnalyzeTextRandomXML() throws Exception {
        String requestBody = "<note><body>Test XML</body></note>";

        mockMvc.perform(post("/api/analyze")
                        .contentType(MediaType.APPLICATION_XML)
                        .content(requestBody))
                .andExpect(status().isUnsupportedMediaType());
    }

}
