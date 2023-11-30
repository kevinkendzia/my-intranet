package de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.SerializableSupplier;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.DrawerMenu.TreeItem;

import java.util.List;

import static java.util.Collections.emptyList;

public record MenuDefinition(
        String key,
        String label,
        SerializableSupplier<Component> iconSupplier,
        Class<? extends Component> navigationTarget,
        int position,
        List<MenuDefinition> children)
{
    public MenuDefinition(String key, String label, MenuDefinition... children)
    {
        this(key, label, null, null, Integer.MAX_VALUE, List.of(children));
    }

    public MenuDefinition(String key, String label)
    {
        this(key, label, null, null, Integer.MAX_VALUE, emptyList());
    }

    public TreeItem createItem()
    {
        return new TreeItem(key(), label(), false, iconSupplier(), navigationTarget(), position());
    }
}
