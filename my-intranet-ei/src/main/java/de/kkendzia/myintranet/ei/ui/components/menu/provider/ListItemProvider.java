package de.kkendzia.myintranet.ei.ui.components.menu.provider;

import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ListItemProvider implements DrawerMenu.ItemProvider
{
    private List<DrawerMenu.DrawerMenuItem> items;

    public ListItemProvider(List<DrawerMenu.DrawerMenuItem> items)
    {
        this.items = requireNonNull(items);
    }

    @Override
    public List<DrawerMenu.DrawerMenuItem> getItems()
    {
        return items;
    }
}
