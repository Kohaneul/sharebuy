package sharebuy.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SOLD_OUT(HttpStatus.CONFLICT,"모집이 마감되었습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"게시글이 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"유저가 존재하지 않습니다."),
    USER_NOT_ACTIVE(HttpStatus.FORBIDDEN,"현재 활동중인 유저가 아닙니다."),
    NOT_RECRUITABLE(HttpStatus.FORBIDDEN,"현재 모집중인 게시글이 아닙니다."),
    ALREADY_PARTICIPATED(HttpStatus.CONFLICT, "이미 참여한 공동구매입니다."),
    SELF_PARTICIPATION_NOT_ALLOWED(HttpStatus.BAD_REQUEST,"본인 게시글에는 참여할 수 없습니다."),
    POST_OWNER_NOT_ACTIVE(HttpStatus.FORBIDDEN,"해당 게시자는 현재 활동중인 유저가 아닙니다."),
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND,"페이지가 존재하지 않습니다."),
    PAGE_ACCESS_DENIED(HttpStatus.FORBIDDEN,"페이지를 접근할 수 없습니다."),
    INVALID_COMPONENT_PROVIDER(HttpStatus.INTERNAL_SERVER_ERROR, "존재하지 않는 provider 입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
