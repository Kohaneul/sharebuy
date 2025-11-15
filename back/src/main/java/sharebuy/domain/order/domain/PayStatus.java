package sharebuy.domain.order.domain;

public enum PayStatus {
    // 상품 종류별 카테고리
    FOOD("식품/음료"),
    BEAUTY("뷰티/화장품"),
    DIGITAL("디지털/가전"),
    LIVING("생활/잡화"),
    ETC("기타");

    private final String description;

    PayStatus(String description) {
        this.description = description;
    }
}
