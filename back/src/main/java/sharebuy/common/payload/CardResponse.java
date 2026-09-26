package sharebuy.common.payload;

import java.util.UUID;
public interface CardResponse {
    UUID getId();
    String getTitle();
    String getNickName();
    String getLoginId();
    String getAvatar();
    String getContent();
    String getImagePath();
    String getStatus();
    Integer getCurrentParticipants();
    Integer getMaxParticipants();
}