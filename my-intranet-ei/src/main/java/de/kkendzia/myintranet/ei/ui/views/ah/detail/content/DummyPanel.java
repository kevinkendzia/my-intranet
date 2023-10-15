package de.kkendzia.myintranet.ei.ui.views.ah.detail.content;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class DummyPanel extends VerticalLayout
{
    public DummyPanel()
    {
        setWidthFull();
        setHeight("200px");
        addClassName(LumoUtility.Background.CONTRAST_20);
        add(new H2("TEST"));
    }

    public void setLoaded()
    {
        add(new Span("LOADED!"));
    }
}
