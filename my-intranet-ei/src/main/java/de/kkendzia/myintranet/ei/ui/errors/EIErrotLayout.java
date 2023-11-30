package de.kkendzia.myintranet.ei.ui.errors;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class EIErrotLayout extends Composite<VerticalLayout>
{
    private final H1 hTitle = new H1(getTranslation("label.error"));
    private final H2 hDescription = new H2(getTranslation("label.error"));

    public EIErrotLayout()
    {
        VerticalLayout root = getContent();
        root.setAlignItems(FlexComponent.Alignment.CENTER);
        root.setJustifyContentMode(JustifyContentMode.CENTER);
        root.add(hTitle);
        root.add(hDescription);
    }

    public void setTitle(String title)
    {
        hTitle.setText(title);
    }

    public void setDescription(String description)
    {
        hDescription.setText(description);
    }
}
