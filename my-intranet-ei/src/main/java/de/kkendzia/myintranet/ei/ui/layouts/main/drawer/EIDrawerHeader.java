package de.kkendzia.myintranet.ei.ui.layouts.main.drawer;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public class EIDrawerHeader extends Composite<Header>
{
    public EIDrawerHeader(String prefixText, String titleText)
    {
        Span prefix = new Span(prefixText);
        prefix.addClassNames(FontSize.MEDIUM, TextColor.SECONDARY);

        Span title = new Span(titleText);
        title.addClassNames(FontWeight.BOLD, FontSize.XLARGE, TextColor.HEADER);

        final var vlContent = new VerticalLayout();
        vlContent.addClassName(Border.ALL);
        vlContent.addClassName(BorderColor.CONTRAST_5);
        vlContent.addClassName(BorderRadius.MEDIUM);
        vlContent.setPadding(true);
        vlContent.setSpacing(false);
        vlContent.add(prefix);
        vlContent.add(title);

        Header header = getContent();
//        header.setHeight("8em");
//        header.addClassName(Padding.MEDIUM);
        header.add(vlContent);
    }
}
