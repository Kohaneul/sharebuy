package sharebuy.common.storage;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import sharebuy.SharebuyApplication;
import sharebuy.common.exception.ShareBuyException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 파일 저장/조회/삭제 테스트
 */
@ActiveProfiles("dev")
@SpringBootTest(
        classes = SharebuyApplication.class
)
@Slf4j
class LocalFileStorageServiceTest {
    @Autowired
    private LocalFileStorageService fileStorageService;


    @Test
    void fileSaveTest(){
        MockMultipartFile file1 =
                new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image".getBytes());
        MockMultipartFile file2 =
                new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image".getBytes());
        List<MultipartFile> savedFile = List.of(file1, file2);
        List<String> savedPaths = fileStorageService.save(savedFile, "test");

        //저장된게 선언한 path의 수와 같은지
        assertThat(savedPaths).hasSize(savedFile.size());

        assertThat(savedPaths.get(0)).startsWith("test/").endsWith(".jpg");
        log.info("저장된 경로 = {}", savedPaths.get(0));
    }

    /**
     * 파일 읽기
     */
    @Test
    void fileReadTest(){
        String path = "test/2026/1aa76a48-698c-4146-9ce6-403012ba3c85.jpg";
        List<Resource> load = fileStorageService.load(List.of(path));
        assertThat(load).hasSize(1);
    }

    /**
     * 파일 제거
     */
    @Test
    void fileDeleteTest(){
        String path = "test/2026/1aa76a48-698c-4146-9ce6-403012ba3c85.jpg";
        fileStorageService.delete(path);
        Assertions.assertThatThrownBy(()-> fileStorageService.load(List.of(path))).isInstanceOf(ShareBuyException.class);
    }
}