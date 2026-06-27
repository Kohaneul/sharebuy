package sharebuy.domain.post.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sharebuy.domain.post.entity.Participation;

import java.util.List;
import java.util.UUID;
@Getter
@AllArgsConstructor
public class PostEndEvent {
    private final UUID postId;
    private final List<Participation> participationList;


}
