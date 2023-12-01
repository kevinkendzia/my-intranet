package de.kkendzia.myintranet.ei.ui.views.home.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;

import static de.kkendzia.myintranet.ei.ui.components.ComponentFactory.xLargeLabel;
import static java.util.Collections.emptyList;

public class HorizontalList<T> extends Composite<VerticalLayout>
{
    private final Component label = xLargeLabel("");
    private final HorizontalLayout hl = new HorizontalLayout();

    private ComponentRenderer<?, T> renderer;
    private int pageSize = 5;

    public HorizontalList()
    {
        hl.addClassName(LumoUtility.Overflow.AUTO);

        final var root = getContent();
        root.setPadding(false);
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.setHeight("10em");
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
            items.fetch(new Query<>(0, pageSize, emptyList(), null, null))
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
}
