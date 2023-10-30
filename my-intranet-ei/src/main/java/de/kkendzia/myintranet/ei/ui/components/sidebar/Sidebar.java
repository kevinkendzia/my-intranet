package de.kkendzia.myintranet.ei.ui.components.sidebar;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasOrderedComponents;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.HasThemeVariant;
import com.vaadin.flow.component.shared.ThemeVariant;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;

import static java.util.Objects.requireNonNull;

public class Sidebar extends Composite<VerticalLayout>
        implements HasThemeVariant<Sidebar.SidebarVariant>, HasOrderedComponents
{
    public static final String ATTRIBUTE_SIDE = "side";

    public Sidebar()
    {
        VerticalLayout root = getContent();
        root.addClassNames("sidebar");
        root.setSizeUndefined();
        root.setAlignItems(Alignment.STRETCH);
        root.addClassName(Overflow.AUTO);
        setSide(Side.LEFT);
    }

    private void setSide(Side side)
    {
        requireNonNull(side, "side can't be null!");
        getContent().getElement().setAttribute(ATTRIBUTE_SIDE, side.getValue());
    }

    //region TYPES
    public enum SidebarVariant implements ThemeVariant
    {
        BOX("box"),
        CONTRAST("contrast");

        private final String variantName;

        SidebarVariant(String variantName)
        {
            this.variantName = variantName;
        }

        @Override
        public String getVariantName()
        {
            return variantName;
        }
    }

    public enum Side
    {
        LEFT("left"),
        RIGHT("right");

        private final String value;

        Side(String value)
        {
            this.value = requireNonNull(value, "value can't be null!");
        }

        public String getValue()
        {
            return value;
        }
    }
    //endregion
}
