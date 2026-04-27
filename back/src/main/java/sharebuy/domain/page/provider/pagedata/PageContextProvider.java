package sharebuy.domain.page.provider.pagedata;

import sharebuy.domain.page.domain.DataSourceType;

import java.util.Map;

public interface PageContextProvider {
    DataSourceType getType();
    Object get(Map<String,String> params);

}
