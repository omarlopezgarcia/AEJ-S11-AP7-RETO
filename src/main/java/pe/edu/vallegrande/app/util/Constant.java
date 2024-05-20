package pe.edu.vallegrande.app.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {
    public static String COMPUTER_VISION_KEY;
    public static String COMPUTER_VISION_URL = "https://trilary-computer-vision.cognitiveservices.azure.com/vision/v3.2/analyze?visualFeatures=Categories%2CDescription%2CColor%2CAdult%2CFaces%2CBrands%2CImageType";
    public static String GEMINI_API_KEY;
    public static String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";
    public static String IMGBB_API_KEY;
    public static String IMGBB_API_URL = "https://api.imgbb.com/1/upload";

    @Value("${api.computerVisionKey}")
    public void setComputerVisionKey(String key) {
        COMPUTER_VISION_KEY = key;
    }

    @Value("${api.geminiApiKey}")
    public void setGeminiApiKey(String key) {
        GEMINI_API_KEY = key;
        GEMINI_API_URL += "?key=" + key;
    }

    @Value("${api.imgbbApiKey}")
    public void setImgbbApiKey(String key) {
        IMGBB_API_KEY = key;
        IMGBB_API_URL += "?key=" + key;
    }
}