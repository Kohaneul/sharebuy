package sharebuy.domain.page.dto;

import java.util.Map;
import java.util.UUID;

import static sharebuy.domain.page.provider.pagedata.ContextConstants.LAT;
import static sharebuy.domain.page.provider.pagedata.ContextConstants.LNG;

public class ContextParam {
    private final Map<String, String> params;

    public ContextParam(Map<String, String> params) {
        this.params = params;
    }

    public Double getLat() {
        String value = params.get(LAT);
        return value !=null ? Double.parseDouble(value) : null;
    }

    public Double getLng() {
        String value = params.get(LNG);
        return value != null ? Double.parseDouble(value) : null;
    }

    public UUID getUUID(String key) {
        return UUID.fromString( params.get(key));
    }
}
