package sharebuy.common.exception;

import lombok.Getter;

@Getter
public class ShareBuyException extends RuntimeException {
    private final ErrorCode errorCode;

    public ShareBuyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
