package sharebuy.domain.post.dto;

import sharebuy.common.domain.Location;
import sharebuy.domain.order.domain.Category;
import sharebuy.domain.post.domain.Appointment;
import sharebuy.domain.post.domain.Place;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
public record PostDetailResponse(
        UUID id,
        String title,
        String content,
        String placeName,
        double latitude,
        double longitude,
        LocalDateTime appointmentTime,
        PostStatus status,
        List<String> imgUrl,
        String purchasePlace,
        String productCode,
        String purchaseUrl,
        Integer totalPrice,
        Integer perPrice,
        LocalDateTime purchaseAt,
        Integer currentParticipants,
        Integer maxParticipants,
        Category category,
        boolean canClose
) {
    public static PostDetailResponse from(Post post,boolean canClose){
        Appointment appointment = post.getAppointment();
        LocalDateTime appointmentTime = appointment.getAppointmentTime();
        Place place = appointment.getPlace();
        Location location = place.getLocation();
        return new PostDetailResponse(post.getId(),post.getTitle(),post.getContent(),place.getPlaceName()
                   ,location.getLatitude(), location.getLongitude(), appointmentTime,post.getStatus(),post.getImgUrl()
                    ,post.getPurchasePlace(),post.getProductCode(),post.getPurchaseUrl(),post.getTotalPrice(),post.getPerPrice(),post.getPurchaseAt()
                    ,post.getCurrentParticipants(),post.getMaxParticipants(),post.getCategory(),canClose);
    }
};
