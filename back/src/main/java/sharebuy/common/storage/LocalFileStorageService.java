package sharebuy.common.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sharebuy.common.exception.ShareBuyException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static sharebuy.common.exception.ErrorCode.FILE_NOT_EXIST;
import static sharebuy.common.exception.ErrorCode.FILE_UPLOAD_FAILED;

@Component
@Profile("dev")
public class LocalFileStorageService implements FileStorageService {

    @Value("${file.upload-path}")
    private String uploadPath;

    private final ImageFileValidator imageFileValidator;

    public LocalFileStorageService(ImageFileValidator imageFileValidator) {
        this.imageFileValidator = imageFileValidator;
    }


    @Override
    public List<String> save(List<MultipartFile> file, String directory) {
        String year = String.valueOf(LocalDate.now().getYear());
        return file.stream().map(f->saveFile(f,directory,year)).toList();
    }

    @Override
    public List<String> getPath(List<String> paths) {
        final String imagePath="/images/";
        return paths.stream().map(path->imagePath+path).toList();
    }

    /**
     * 파일 저장
     * @param file
     * @param directory
     * @param year
     * @return
     */
    private String saveFile(
            MultipartFile file,
            String directory,
            String year)
    {
        //해당 파일의 확장자 추출
        String extension = imageFileValidator.validateAndGetExtension(file);

        //파일이름 -> uuid 로
        String filename = UUID.randomUUID() +extension;

        Path directoryPath = Paths.get(uploadPath,directory, year);

        try {
            //없으면 새로 생성
            Files.createDirectories(directoryPath);

            Path filePath = directoryPath.resolve(filename);
            file.transferTo(filePath);

            return directory + "/" + year + "/" + filename;

        } catch (IOException e) {
            throw new ShareBuyException(FILE_UPLOAD_FAILED);
        }
    }


    @Override
    public void delete(String imagePath) {
        try {
            Files.deleteIfExists(resolveSafe(imagePath));
        } catch (IOException e) {
            throw new ShareBuyException(FILE_NOT_EXIST);
        }
    }

    private Path resolveSafe(String relativePath) {
        Path base = Paths.get(uploadPath).toAbsolutePath().normalize();
        Path resolved = base.resolve(relativePath).normalize();
        if (!resolved.startsWith(base)) {
            throw new ShareBuyException(FILE_UPLOAD_FAILED);
        }
        return resolved;
    }

}
