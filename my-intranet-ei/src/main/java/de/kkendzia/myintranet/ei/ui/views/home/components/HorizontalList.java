package de.kkendzia.myintranet.ei.ui.views.home.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;

import java.util.Comparator;
import java.util.List;

import static de.kkendzia.myintranet.ei.ui.components.ComponentFactory.xLargeLabel;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

public class HorizontalList<T> extends Composite<VerticalLayout>
{
    private final Component label = xLargeLabel("");
    private final HorizontalLayout hl = new HorizontalLayout();

    private ComponentRenderer<?, T> renderer;
    private int pageSize = 5;
    private SerializableComparator<T> comparator;
    private List<QuerySortOrder> sortOrders = emptyList();

    public HorizontalList()
    {
        hl.addClassName(Overflow.AUTO);

        final var root = getContent();
        root.setPadding(false);
        root.setAlignItems(Alignment.STRETCH);
        root.add(label);
        root.add(hl);
    }

    public void setLabel(String label)
    {
        this.label.getElement().setText(label);
    }

    public void setRenderer(final ComponentRenderer<?, T> renderer)
    {
        this.renderer = renderer;
    }

    public void setPageSize(final int pageSize)
    {
        this.pageSize = pageSize;
    }

    public void setItems(DataProvider<T, Void> items)
    {
        hl.removeAll();

        final var size = items.size(new Query<>());
        if (size > 0)
        {
            items.fetch(createQuery())
                    .map(i ->
                    {
                        if (renderer != null)
                        {
                            return renderer.createComponent(i);
                        }
                        return new Span(String.valueOf(i));
                    })
                    .forEach(hl::add);
        }
        else
        {
            // TODO
            hl.add(new Span("NO DATA!"));
        }
    }

    private Query<T, Void> createQuery()
    {
        return new Query<>(0, pageSize, sortOrders, this.comparator, null);
    }

    public void setComparator(final Comparator<T> comparator)
    {
        this.comparator = comparator::compare;
    }

    public void setSortOrders(final List<QuerySortOrder> sortOrders)
    {
        this.sortOrders = requireNonNull(sortOrders, "sortOrders can't be null!");
    }
}
