package de.kkendzia.myintranet.ei.ui.components.toolbar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.HasThemeVariant;
import com.vaadin.flow.component.shared.ThemeVariant;

import static java.util.Objects.requireNonNull;

// TODO: sticky? shrinking toolbar?
public class Toolbar extends Composite<HorizontalLayout>
        implements HasThemeVariant<Toolbar.ToolbarVariant>, ThemableLayout
{
    private final VerticalLayout vlTitle = new VerticalLayout();
    private final HorizontalLayout hlBadges = new HorizontalLayout();
    private final HorizontalLayout hlActions = new HorizontalLayout();

    public Toolbar()
    {
        vlTitle.setSpacing(false);
        vlTitle.setPadding(false);

        VerticalLayout vlLeft = new VerticalLayout();
        vlLeft.setPadding(false);
        vlLeft.add(vlTitle);
        vlLeft.add(hlBadges);

        hlActions.setJustifyContentMode(JustifyContentMode.END);
        hlActions.setAlignItems(Alignment.END);
        hlActions.setPadding(false);

        HorizontalLayout root = getContent();
        root.addClassNames("toolbar");
        root.setAlignItems(Alignment.STRETCH);
        root.setJustifyContentMode(JustifyContentMode.BETWEEN);
        root.setPadding(false);

        root.add(vlLeft);
        root.addAndExpand(hlActions);
    }

    public void addTitle(Component titleComponent)
    {
        vlTitle.add(titleComponent);
    }

    public void addBadge(Component badgeComponent)
    {
        hlBadges.add(badgeComponent);
    }

    public void addAction(Component actionComponent)
    {
        hlActions.add(actionComponent);
    }

    public void removeAll()
    {
        vlTitle.removeAll();
        hlBadges.removeAll();
        hlActions.removeAll();
    }

    public enum ToolbarVariant implements ThemeVariant
    {
        CONTRAST("contrast"),
        BORDER_BOTTOM("border-bottom");

        private final String variantName;

        ToolbarVariant(String variantName)
        {
            this.variantName = requireNonNull(variantName, "variantName can't be null!");
        }

        @Override
        public String getVariantName()
        {
            return variantName;
        }
    }
}
