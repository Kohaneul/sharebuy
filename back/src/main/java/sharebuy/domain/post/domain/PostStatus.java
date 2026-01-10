package sharebuy.domain.post.domain;

/**
 * 게시글 상태 : 모집 중, 마감, 취소
 */
public enum PostStatus {
    RECRUITING("모집중"),
    EDITING("수정중"),
    CLOSED("마감"),
    CANCELED("취소");

    private final String description; // 상태에 대한 설명

    PostStatus(String description) {
        this.description = description;
    }
}
