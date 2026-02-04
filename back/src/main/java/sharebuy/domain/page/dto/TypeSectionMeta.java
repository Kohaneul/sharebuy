package sharebuy.domain.page.dto;


import java.util.List;

public interface TypeSectionMeta<T> extends PageSectionMeta {

    List<T> items();
}
