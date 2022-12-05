package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import services.image.MimeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

@Singleton
public class ImagesServlet extends HttpServlet {
    @Inject
    MimeService mimeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestedFile = req.getPathInfo();
        String path = req.getServletContext().getRealPath("/");
        File file = new File(path + "/../Uploads" + requestedFile);
        if (mimeService.isNotImage(requestedFile)) {
            resp.setContentType(mimeService.getMimeType(requestedFile));
            resp.setContentLengthLong(file.length());
            return;
        }
        if (file.isFile() && file.canRead()) {
            try (InputStream reader = Files.newInputStream(file.toPath())) {
                OutputStream writer = resp.getOutputStream();
                byte[] buf = new byte[2048];
                int bytesRead;
                while ((bytesRead = reader.read(buf)) > 0) {
                    writer.write(buf, 0, bytesRead);
                }
            } catch (IOException ex) {
                resp.setStatus(500);
                resp.getWriter().print("Server error");
                System.out.println("ImagesServlet::doGet " + requestedFile +
                        "\n" + ex.getMessage());
            }

        } else {
            resp.setStatus(404);
            resp.getWriter().print("File not found " + requestedFile);
        }
        //req.getRequestDispatcher("/WEB-INF/_layout.jsp").forward(req, resp);
    }
}