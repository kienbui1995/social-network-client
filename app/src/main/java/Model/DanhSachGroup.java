package Model;

import com.zaihuishou.expandablerecycleradapter.model.ExpandableListItem;

import java.util.List;

/**
 * Created by joker on 4/4/17.
 */

public class DanhSachGroup implements ExpandableListItem {
    @Override
    public List<?> getChildItemList() {
        return null;
    }

    @Override
    public boolean isExpanded() {
        return false;
    }

    @Override
    public void setExpanded(boolean isExpanded) {

    }
}
