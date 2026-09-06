package sharebuy.domain.post.dto;

import sharebuy.domain.order.domain.Category;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.domain.post.domain.PurchaseType;

import java.time.LocalDateTime;
import java.util.List;

public record PostSaveDto(
        String title,
        String content,
        String placeName,
        String primaryAddress,
        String detailAddress,
        double latitude,
        double longitude,
        LocalDateTime appointmentTime,
        PostStatus status,
        List<String> imgUrl,
        String purchasePlace,
        String productCode,
        PurchaseType purchaseType,
        String purchaseUrl,
        Integer totalPrice,
        Integer perPrice,
        LocalDateTime purchaseAt,
        Integer currentParticipants,
        Integer maxParticipants,
        Category category
) {
};
