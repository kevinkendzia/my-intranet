package de.kkendzia.myintranet.ei.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility;

public final class ComponentFactory
{
    private ComponentFactory()
    {
        // No Instance!
    }

    public static Component xLargeLabel(String text)
    {
        final var div = new Div();
        div.addClassName(LumoUtility.FontSize.XLARGE);
        div.addClassName(LumoUtility.FontWeight.BOLD);
        div.setText(text);
        return div;
    }

}
