package sharebuy.common.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {

    List<String> save(List<MultipartFile> file, String directory);
    List<String> getPath(List<String> path);
    void delete(String path);
}
