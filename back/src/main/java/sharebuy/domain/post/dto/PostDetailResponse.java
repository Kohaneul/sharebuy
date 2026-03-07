package sharebuy.domain.post.dto;

import sharebuy.common.domain.Location;
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
        List<String> imgUrl
) {
    public static PostDetailResponse from(Post post){
        Appointment appointment = post.getAppointment();
        LocalDateTime appointmentTime = appointment.getAppointmentTime();
        Place place = appointment.getPlace();
        Location location = place.getLocation();
        return new PostDetailResponse(post.getId(),post.getTitle(),post.getContent(),place.getPlaceName()
                   ,location.getLatitude(), location.getLongitude(), appointmentTime,post.getStatus(),post.getImgUrl());
    }
};
