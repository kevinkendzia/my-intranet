package de.kkendzia.myintranet.ei.core.utils;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;

public final class GridColumnFactory
{
    private GridColumnFactory()
    {
        // No Instance!
    }

    public static <T> Grid.Column<T> addCollapsedColumn(Grid<T> grid, String header, ValueProvider<T, ?> valueProvider)
    {
        return grid.addColumn(valueProvider).setHeader(header).setAutoWidth(true).setFlexGrow(0);
    }

    public static <T> Grid.Column<T> addCollapsedColumn(Grid<T> grid, String header, Renderer<T> renderer)
    {
        return grid.addColumn(renderer).setHeader(header).setAutoWidth(true);
    }

    public static <T> Grid.Column<T> addExpandedColumn(Grid<T> grid, String header, ValueProvider<T, ?> valueProvider)
    {
        return grid.addColumn(valueProvider).setHeader(header).setAutoWidth(false).setFlexGrow(1);
    }

    public static <T> Grid.Column<T> addSpacerColumn(Grid<T> grid)
    {
        return addExpandedColumn(grid, "", itm -> "");
    }
}
