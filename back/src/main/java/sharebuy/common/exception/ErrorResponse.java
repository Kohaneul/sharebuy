package sharebuy.common.exception;

public record ErrorResponse (
    String message,
    String code
){};
