package sharebuy.common.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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


    @Override
    public List<String> save(List<MultipartFile> file, String directory) {
        String year = String.valueOf(LocalDate.now().getYear());
        return file.stream().map(f->saveFile(f,directory,year)).toList();
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
        String extension = getExtension(file.getOriginalFilename());

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

    /**
     * 확장자 추출
     * @param fileName
     * @return
     */
    private String getExtension(String fileName) {
        if(fileName ==null){
            return "";
        }
        int idx = fileName.lastIndexOf(".");

        if (idx == -1) {
            return "";
        }
        return fileName.substring(idx);
    }

    /**
     * path를 통해서 파일 조회
     * @param paths
     * @return
     */
    @Override
    public List<Resource> load(List<String> paths) {
        return paths.stream()
                .map(this::loadFile).toList();
    }

    private Resource loadFile(String path) {
        Path filePath = Paths.get(uploadPath,path);
        try{
            if(!Files.exists(filePath)){
              throw new ShareBuyException(FILE_NOT_EXIST);
            }
        }
        catch(Exception e){
            throw new ShareBuyException(FILE_NOT_EXIST);
        }
        return new FileSystemResource(filePath);
    }

    @Override
    public void delete(String imagePath) {
        Path filePath = Paths.get(uploadPath, imagePath);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new ShareBuyException(FILE_NOT_EXIST);
        }

    }
}
