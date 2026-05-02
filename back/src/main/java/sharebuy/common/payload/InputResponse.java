package sharebuy.common.payload;

import sharebuy.domain.post.type.InputType;

public interface InputResponse {
    String getField();
    InputType getInputType();
    boolean getReadOnly();
}
