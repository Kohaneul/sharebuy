package sharebuy.common.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sharebuy.common.exception.ShareBuyException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Set;

import static sharebuy.common.exception.ErrorCode.FILE_UPLOAD_FAILED;

@Component
public class ImageFileValidator {
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png", ".gif");

    public String validateAndGetExtension(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ShareBuyException(FILE_UPLOAD_FAILED);
        }

        String extension = extractExtension(file.getOriginalFilename());

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new ShareBuyException(FILE_UPLOAD_FAILED);
        }

        validateImageContent(file);

        return extension;
    }

    /**
     * 확장자 추출 (마지막 '.' 이후, 소문자로 변환)
     */
    private String extractExtension(String fileName) {
        if (fileName == null) {
            throw new ShareBuyException(FILE_UPLOAD_FAILED);
        }

        int idx = fileName.lastIndexOf('.');
        if (idx == -1 || idx == fileName.length() - 1) {
            throw new ShareBuyException(FILE_UPLOAD_FAILED);
        }

        return fileName.substring(idx).toLowerCase(Locale.ROOT);
    }

    /**
     * 실제 이미지 파일인지 내용으로 검증 (확장자만 바꾼 위장 파일 차단)
     */
    private void validateImageContent(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            if (ImageIO.read(in) == null) {
                throw new ShareBuyException(FILE_UPLOAD_FAILED);
            }
        } catch (IOException e) {
            throw new ShareBuyException(FILE_UPLOAD_FAILED);
        }
    }

}
