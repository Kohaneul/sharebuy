package sharebuy.domain.page.provider.datasource;

import org.springframework.stereotype.Component;
import sharebuy.domain.page.domain.DataSourceType;
import sharebuy.domain.post.service.PostService;

import java.util.Map;
@Component
public class PostProvider implements DataProvider{
    private final PostService postService;

    public PostProvider(PostService postService) {
        this.postService = postService;
    }

    @Override
    public Object get(DataSourceType dataSourceType, Map<String, Object> params) {
//        return switch(dataSourceType){
//            case POST_ALL -> postService.findAllData()
//        };
        return null;
    }
}
