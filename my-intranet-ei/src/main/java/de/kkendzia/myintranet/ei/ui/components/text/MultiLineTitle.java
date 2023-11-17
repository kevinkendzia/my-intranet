package de.kkendzia.myintranet.ei.ui.components.text;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

public class MultiLineTitle extends Composite<VerticalLayout>
{
    private final Div cPreTitle = new Div();
    private final H1 cTitle = new H1();

    public MultiLineTitle(final String preTitle, final String title)
    {
        cPreTitle.addClassName(TextColor.SECONDARY);
        cPreTitle.addClassName(FontSize.LARGE);

        cTitle.addClassName(FontWeight.BOLD);

        VerticalLayout vlText = getContent();
        vlText.setPadding(false);
        vlText.setSpacing(false);
        vlText.add(cPreTitle);
        vlText.add(cTitle);

        cPreTitle.setText(preTitle);
        cTitle.setText(title);
    }

    public MultiLineTitle()
    {
        this("", "");
    }

    public void setPreTitle(String preTitle)
    {
        cPreTitle.setText(preTitle);
    }

    public void setTitle(String title)
    {
        cTitle.setText(title);
    }
}
