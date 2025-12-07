package sharebuy.domain.board.domain;

/**
 * 게시글 상태 : 모집 중, 마감, 취소
 */
public enum PurchaseStatus {
    RECRUITING("참여중"),
    EDITING("취소"),
    CLOSED("마감");

    private final String description; // 상태에 대한 설명

    PurchaseStatus(String description) {
        this.description = description;
    }
}
