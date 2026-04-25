package sharebuy.domain.page.provider.datasource;

import sharebuy.domain.page.domain.DataSourceType;

import java.util.Map;

public interface DataProvider {
    Object get(DataSourceType dataSourceType, Map<String,Object> params);

}
