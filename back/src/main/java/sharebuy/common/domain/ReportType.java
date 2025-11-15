package sharebuy.common.domain;

public enum ReportType {
    // 사용자 간의 비매너 행위
    INAPPROPRIATE_LANGUAGE("욕설 및 비방"),
    // 거래 및 사기 관련
    FRAUD("사기 또는 불법 거래 시도"),
    NO_SHOW("약속 불이행 (노쇼)"),
    // 게시글 내용 관련
    SPAM("홍보성/도배 게시글"),
    INAPPROPRIATE_CONTENT("음란물 또는 부적절한 콘텐츠"),
    ETC("기타 사유");
    private final String description; // 관리자 페이지에 표시할 설명

    ReportType(String description) {
        this.description = description;
    }
}
