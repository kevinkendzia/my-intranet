package de.kkendzia.myintranet.ei.utils;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;

import static java.util.Objects.requireNonNull;

public final class GridColumnFactory<T>
{
    private final Grid<T> grid;

    public GridColumnFactory(Grid<T> grid)
    {
        this.grid = requireNonNull(grid, "grid can't be null!");
    }

    public Column<T> addCollapsedColumn(String header, ValueProvider<T, ?> valueProvider)
    {
        return grid.addColumn(valueProvider).setHeader(header).setAutoWidth(true).setFlexGrow(0);
    }

    public Column<T> addCollapsedColumn(String header, Renderer<T> renderer)
    {
        return grid.addColumn(renderer).setHeader(header).setAutoWidth(true);
    }

    public Column<T> addExpandedColumn(String header, ValueProvider<T, ?> valueProvider)
    {
        return grid.addColumn(valueProvider).setHeader(header).setAutoWidth(false).setFlexGrow(1);
    }

    public Column<T> addExpandedColumn(String header, Renderer<T> renderer)
    {
        return grid.addColumn(renderer).setHeader(header).setAutoWidth(false).setFlexGrow(1);
    }

    public Column<T> addSpacerColumn()
    {
        return addExpandedColumn(grid, "", itm -> "");
    }

    //region STATIC
    public static <T> Column<T> addCollapsedColumn(Grid<T> grid, String header, ValueProvider<T, ?> valueProvider)
    {
        return new GridColumnFactory<>(grid).addCollapsedColumn(header, valueProvider);
    }

    public static <T> Column<T> addCollapsedColumn(Grid<T> grid, String header, Renderer<T> renderer)
    {
        return new GridColumnFactory<>(grid).addCollapsedColumn(header, renderer);
    }

    public static <T> Column<T> addExpandedColumn(Grid<T> grid, String header, ValueProvider<T, ?> valueProvider)
    {
        return new GridColumnFactory<>(grid).addExpandedColumn(header, valueProvider);
    }

    public static <T> Column<T> addExpandedColumn(Grid<T> grid, String header, Renderer<T> renderer)
    {
        return new GridColumnFactory<>(grid).addExpandedColumn(header, renderer);
    }

    public static <T> Column<T> addSpacerColumn(Grid<T> grid)
    {
        return new GridColumnFactory<>(grid).addSpacerColumn();
    }
    //endregion
}
