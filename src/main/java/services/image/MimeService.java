package services.image;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class MimeService {
    private final Map<String, String> imageTypes =
            new HashMap<>() {{
                put(".jpg", "image/jpeg");
                put(".jpeg", "image/jpeg");
                put(".png", "image/png");
                put(".bmp", "image/bmp");
                put(".gif", "image/bmp");
                put(".webp", "image/webp");
            }};

    public String getExtension(String filename) {
        int dotPos = filename.lastIndexOf('.');
        if (dotPos == -1) {
            return null;
        }
        return filename.substring(dotPos);
    }

    public String getMimeType(String filename) {
        return imageTypes.get(getExtension(filename));
    }

    public boolean isImage(String filename) {
        return imageTypes.containsKey(getExtension(filename));
    }

    public boolean isNotImage(String filename) {
        return !isImage(filename);
    }
}
