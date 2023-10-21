package de.kkendzia.myintranet.ei.core.view.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import de.kkendzia.myintranet.ei.core.constants.EIStyles;

import static de.kkendzia.myintranet.ei.core.constants.EIStyles.SCROLL_SNAP_Y;
import static java.util.Objects.requireNonNull;

public class SectionLayout extends Composite<VerticalLayout>
{
    public SectionLayout()
    {
        VerticalLayout root = getContent();
        root.addClassName("section-layout");
        root.addClassName(SCROLL_SNAP_Y);
        root.addClassName(Overflow.AUTO);
        root.setPadding(false);
        root.setSpacing(false);
        root.setAlignItems(Alignment.STRETCH);
    }

    public void add(String title, Component content)
    {
        requireNonNull(content, "content can't be null!");

        Section section = new Section(content);
        section.addClassName("section-layout-section");
        section.addClassName(EIStyles.SCROLL_SNAP_ALIGN_START);

        if(title != null)
        {
            H3 h3 = new H3(title);
            h3.addClassName("section-layout-title");
            h3.addClassName("sticky-top");
            h3.addClassName(LumoUtility.Background.BASE);
            h3.addClassName(LumoUtility.Padding.Bottom.MEDIUM);

            getContent().add(h3);
            //   TODO: Implement!
            getContent().getStyle().set("scroll-padding", "calc(calc(23.5px + var(--lumo-space-m)) + var(--lumo-space-m))");
        }

        getContent().add(section);
    }
}
