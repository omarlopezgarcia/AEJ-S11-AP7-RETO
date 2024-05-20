package pe.edu.vallegrande.app.service.impl;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.vallegrande.app.model.GeminiImageAnalysis;
import pe.edu.vallegrande.app.repository.GeminiImageAnalisis;
import pe.edu.vallegrande.app.service.GeminiImageAnalysisService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;

import static pe.edu.vallegrande.app.util.Constant.GEMINI_API_URL;
import static pe.edu.vallegrande.app.util.Constant.IMGBB_API_URL;

@Service
public class GeminiAnalysisServiceImpl implements GeminiImageAnalysisService {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final GeminiImageAnalisis geminiImageAnalisis;

    public GeminiAnalysisServiceImpl(GeminiImageAnalisis geminiImageAnalisis) {
        this.geminiImageAnalisis = geminiImageAnalisis;
    }

    public Mono<GeminiImageAnalysis> processImage(MultipartFile file, String language) {
        try {

            byte[] fileBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(fileBytes);

            String imgbbUrl = uploadToImgbb(base64Image);

            String jsonInputString = "{ \"contents\": [ { \"parts\": [ { \"text\": \"What is depicted in this image? Please include the response in the corresponding language. Here's the language code: " + language + "\" }, { \"inline_data\": { \"mime_type\":\"image/jpeg\", \"data\": \"" + base64Image + "\" } } ] } ] }";
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

            String text = firstCandidate.optString("text");
            if (text == null) {
                return Mono.error(new Exception("No se encontró el campo 'text' en la parte del contenido."));
            }

            JSONArray safetyRatings = firstCandidate.optJSONArray("safetyRatings");

            String message = "No se encontraron clasificaciones de seguridad para la imagen.";
            if (safetyRatings != null) {
                for (int i = 0; i < safetyRatings.length(); i++) {
                    JSONObject rating = safetyRatings.optJSONObject(i);
                    String probability = rating.optString("probability");
                    if ("LIKELY".equals(probability) || "VERY_LIKELY".equals(probability)) {
                        String category = rating.optString("category");
                        switch (category) {
                            case "HARM_CATEGORY_SEXUALLY_EXPLICIT":
                                message = "La imagen es sexualmente explícita.";
                                break;
                            case "HARM_CATEGORY_HATE_SPEECH":
                                message = "La imagen contiene discurso de odio.";
                                break;
                            case "HARM_CATEGORY_HARASSMENT":
                                message = "La imagen contiene acoso.";
                                break;
                            case "HARM_CATEGORY_DANGEROUS_CONTENT":
                                message = "La imagen contiene contenido peligroso.";
                                break;
                            default:
                                message = "La imagen es segura.";
                                break;
                        }
                        break;
                    }
                }
            }

            GeminiImageAnalysis analysis = new GeminiImageAnalysis();
            analysis.setImgbbUrl(imgbbUrl);
            analysis.setGeminiText(text);
            analysis.setMessage(message);
            analysis.setNombreLanguage(language);
            return geminiImageAnalisis.save(analysis);
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

    @Override
    public Flux<GeminiImageAnalysis> getAll() {
        return geminiImageAnalisis.getAll();
    }

   private String uploadToImgbb(String base64Image) throws IOException {
    RequestBody requestBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("image", base64Image)
            .build();

    Request request = new Request.Builder()
            .url(IMGBB_API_URL)
            .post(requestBody)
            .build();

    Response response = httpClient.newCall(request).execute();
    if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);

    String responseBody = response.body().string();
    JSONObject jsonObject = new JSONObject(responseBody);
    return jsonObject.getJSONObject("data").getString("url");
}
}
