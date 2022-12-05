package services.image;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Singleton
public class ImagesService {
    @Inject
    MimeService mimeService;

    public String saveImage(String path, Part image) {
        try {
            String imageFilename = image.getSubmittedFileName();
            String extension = mimeService.getExtension(imageFilename);
            if (mimeService.isNotImage(imageFilename)) {
                throw new Exception("File type not supported: " + extension);
            }
            String imageName = UUID.randomUUID() + extension;
            File uploads = new File(path + File.separator + imageName);
            Files.copy(image.getInputStream(), uploads.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return imageName;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
