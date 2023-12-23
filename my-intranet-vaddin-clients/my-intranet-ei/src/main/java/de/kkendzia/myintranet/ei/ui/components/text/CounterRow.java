package de.kkendzia.myintranet.ei.ui.components.text;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.Collection;
import java.util.List;

public class CounterRow extends Composite<HorizontalLayout>
{
    public CounterRow(Counter... counter)
    {
        this(List.of(counter));
    }
    public CounterRow(Collection<Counter> counter)
    {
        HorizontalLayout root = getContent();
        root.setPadding(false);
        root.setJustifyContentMode(JustifyContentMode.END);
        counter.forEach(root::add);
    }
}
