package de.kkendzia.myintranet.ei.ui.layouts.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import de.kkendzia.myintranet.ei.core.view.EIView;
import de.kkendzia.myintranet.ei.core.view.mixins.*;
import de.kkendzia.myintranet.ei.ui.components.sidebar.ConfigurableSidebar;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ConfigurableToolbar;

import static de.kkendzia.myintranet.ei.ui.components.sidebar.ConfigurableSidebar.ConfigurableSidebarVariant.BOX;
import static de.kkendzia.myintranet.ei.ui.components.sidebar.ConfigurableSidebar.ConfigurableSidebarVariant.CONTRAST;

public class EIViewWrapper extends Composite<HorizontalLayout>
{
    public EIViewWrapper(EIView view)
    {
        view.addClassName(Overflow.AUTO);

        HorizontalLayout hlRightSidebar = new HorizontalLayout();
        hlRightSidebar.addClassNames("right-sidebar-layout");
        hlRightSidebar.addClassNames(Overflow.AUTO);
        hlRightSidebar.setSizeFull();
        hlRightSidebar.setPadding(false);
        hlRightSidebar.setAlignItems(Alignment.STRETCH);

        view.getStyle().set("flex", "3 3 70%");
        hlRightSidebar.addAndExpand((Component) view);

        if (view instanceof HasRightSidebar s)
        {
            s.getOptionalRightSidebarConfigSupplier()
                    .ifPresent(sb ->
                    {
                        ConfigurableSidebar sidebar = new ConfigurableSidebar(sb);
                        sidebar.getStyle().set("flex", "1 1 30%");
                        sidebar.addThemeVariants(BOX);
                        hlRightSidebar.add(sidebar);

                        if (view instanceof RightSidebarNotifier n)
                        {
                            n.addRightSidebarChangeListener(e -> sidebar.rebuild());
                        }
                    });
        }

        VerticalLayout vlToolbar = new VerticalLayout();
        vlToolbar.addClassNames("toolbar-layout");
        vlToolbar.setSizeFull();
        vlToolbar.setPadding(true);
        vlToolbar.setAlignItems(Alignment.STRETCH);

        if (view instanceof HasToolbarConfig t)
        {
            t.getOptionalToolbarConfigSupplier().ifPresent(tb ->
            {
                ConfigurableToolbar toolbar = new ConfigurableToolbar(tb);
                vlToolbar.add(toolbar);

                if (view instanceof ToolbarNotifier n)
                {
                    n.addToolbarChangeListener(e -> toolbar.rebuild());
                }
            });
        }
        vlToolbar.addAndExpand(hlRightSidebar);

        HorizontalLayout hlLeftSidebar = getContent();
        hlLeftSidebar.addClassNames("left-sidebar-layout");
        hlLeftSidebar.addClassNames(Overflow.AUTO);
        hlLeftSidebar.setSizeFull();
        hlLeftSidebar.setPadding(false);
        hlLeftSidebar.setAlignItems(Alignment.STRETCH);

        if (view instanceof HasLeftSidebar s)
        {
            s.getOptionalLeftSidebarConfigSupplier().ifPresent(sb ->
            {
                ConfigurableSidebar sidebar = new ConfigurableSidebar(sb);
                sidebar.getStyle().set("flex", "1 1 20%");
                sidebar.addThemeVariants(CONTRAST);
                hlLeftSidebar.add(sidebar);

                if (view instanceof LeftSidebarNotifier n)
                {
                    n.addLeftSidebarChangeListener(e -> sidebar.rebuild());
                }
            });
        }

        vlToolbar.getStyle().set("flex", "3 3 80%");
        hlLeftSidebar.addAndExpand(vlToolbar);
    }
}
