package sharebuy.domain.post.dto;

import sharebuy.domain.order.domain.Category;
import sharebuy.domain.post.domain.Appointment;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.domain.post.domain.PurchaseType;

import java.time.LocalDateTime;

public record PostSaveDto(
        String title,
        String content,
        Appointment appointment,
        PostStatus status,
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
