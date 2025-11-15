package sharebuy.domain.order.domain;

public enum PaymentMethod {
    CARD("신용카드/체크카드"),
    BANK_TRANSFER("계좌 이체"),
    VIRTUAL_ACCOUNT("가상 계좌"),
    KAKAO_PAY("카카오페이"),
    NAVER_PAY("네이버페이");
    // 필요한 다른 수단을 추가할 수 있습니다.

    private final String description; // 사용자에게 보여줄 설명

    PaymentMethod(String description) {
        this.description = description;
    }
}
