package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharebuy.domain.post.type.InputType;
import sharebuy.domain.post.type.PageSectionType;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.page.dto.InputSectionMeta.*;
import static sharebuy.domain.post.type.PageSectionType.INPUT;
import static sharebuy.domain.post.type.PageType.PAGE;

@RequiredArgsConstructor
public final class InputSectionMeta implements TypeSectionMeta<InputItem> {
    private final List<InputItem> inputItemList;

    @Override
    public PageType getPageType() {
        return PAGE;
    }

    @Override
    public PageSectionType pageSectionType() {
        return INPUT;
    }

    @Override
    public List items() {
        return inputItemList;
    }

    @AllArgsConstructor
    @Getter
    public static class InputItem {
        private String key;
        private InputType inputType;
        private boolean readonly;


    }

}
