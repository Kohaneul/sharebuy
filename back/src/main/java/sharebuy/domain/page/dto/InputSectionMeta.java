package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sharebuy.domain.post.type.InputType;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.post.type.PageType.INPUT;

public final class InputSectionMeta implements PageSectionMeta<InputSectionMeta.InputItem> {
    private final List<InputItem> inputItemList;

    public InputSectionMeta(List<InputItem> inputItemList) {
        this.inputItemList = inputItemList;
    }

    @Override
    public PageType type() {
        return INPUT;
    }

    @Override
    public List items() {
        return inputItemList;
    }

    @AllArgsConstructor
    @Getter
    static class InputItem {
        private String key;
        private InputType inputType;
        private boolean readonly;


    }

}
