package de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider;

import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.DrawerMenu;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ListItemProvider implements DrawerMenu.ItemProvider
{
    private List<DrawerMenu.TreeItem> items;

    public ListItemProvider(List<DrawerMenu.TreeItem> items)
    {
        this.items = requireNonNull(items);
    }

    @Override
    public List<DrawerMenu.TreeItem> collectItems()
    {
        return items;
    }
}
