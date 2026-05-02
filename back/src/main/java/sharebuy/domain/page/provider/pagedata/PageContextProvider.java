package sharebuy.domain.page.provider.pagedata;

import sharebuy.domain.page.domain.DataSourceType;
import sharebuy.domain.page.dto.UserContextParam;

import java.util.Map;

public interface PageContextProvider {
    DataSourceType getType();
    Object get(UserContextParam userContextParam, String actionParam);

}
