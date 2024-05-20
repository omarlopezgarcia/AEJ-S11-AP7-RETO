package pe.edu.vallegrande.app.service.impl;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.app.model.GeminiTranslate;
import pe.edu.vallegrande.app.repository.GeminiTranslateRepository;
import pe.edu.vallegrande.app.service.GeminiTranslateService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static pe.edu.vallegrande.app.util.Constant.GEMINI_API_URL;

@Service
public class GeminiTranslateServiceImpl implements GeminiTranslateService {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final GeminiTranslateRepository translateRepository;

    @Autowired
    public GeminiTranslateServiceImpl(GeminiTranslateRepository translateRepository) {
        this.translateRepository = translateRepository;
    }

    @Override
    public Mono<GeminiTranslate> translateText(String text, String language) {
        try {
            String jsonInputString = "{ \"contents\": [ { \"parts\": [ { \"text\": \"Without giving me a phrase or more context, translate just this word to " +
                    language + ": " + text + "\" } ] } ] }";
            RequestBody geminiRequestBody = RequestBody.create(
                    jsonInputString,
                    MediaType.get("application/json; charset=utf-8")
            );

            Request geminiRequest = new Request.Builder()
                    .url(GEMINI_API_URL)
                    .post(geminiRequestBody)
                    .build();

            Response geminiResponse = httpClient.newCall(geminiRequest).execute();
            String geminiResponseBody = geminiResponse.body().string();
            JSONObject geminiJson = new JSONObject(geminiResponseBody);

            JSONObject firstCandidate = geminiJson.optJSONArray("candidates")
                    .optJSONObject(0)
                    .optJSONObject("content")
                    .optJSONArray("parts")
                    .optJSONObject(0);

            if (firstCandidate == null) {
                return Mono.error(new Exception("No se encontraron candidatos en la respuesta."));
            }

            String translatedText = firstCandidate.optString("text");
            if (translatedText == null) {
                return Mono.error(new Exception("No se encontró el texto traducido en la respuesta."));
            }

            GeminiTranslate translation = new GeminiTranslate();
            translation.setLanguages(language);
            translation.setEntered_text(text);
            translation.setTranslated_text(translatedText);

            return translateRepository.save(translation);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public Flux<GeminiTranslate> getAll() {
        return translateRepository.getAll();
    }
}