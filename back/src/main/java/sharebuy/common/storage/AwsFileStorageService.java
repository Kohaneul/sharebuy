package sharebuy.common.storage;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@Profile("prod")
public class AwsFileStorageService implements FileStorageService {

    @Override
    public List<String> save(List<MultipartFile> file, String directory) {
        return List.of();
    }

    @Override
    public List<Resource> load(List<String> paths) {
        return List.of();
    }

    @Override
    public void delete(String path) {

    }
}
