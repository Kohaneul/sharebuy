package sharebuy.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse {
    private boolean result;
    private String message;

    public BaseResponse(boolean result) {
        this.result = result;
    }
}
