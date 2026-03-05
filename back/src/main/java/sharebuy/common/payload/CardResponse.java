package sharebuy.common.payload;

import java.util.UUID;
public record CardResponse(
        UUID id,
        String title,
        AuthorInfo author,
        String content,
        String imgUrl,
        String status
){
    public record AuthorInfo(String nickname, String avatar){}
}