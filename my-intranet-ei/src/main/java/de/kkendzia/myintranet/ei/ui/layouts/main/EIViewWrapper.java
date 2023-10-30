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
import de.kkendzia.myintranet.ei.ui.components.sidebar.Sidebar;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ConfigurableToolbar;

import java.util.Optional;

import static de.kkendzia.myintranet.ei.core.constants.EIStyles.MEDIA.*;
import static de.kkendzia.myintranet.ei.ui.components.sidebar.Sidebar.SidebarVariant.BOX;

public class EIViewWrapper extends Composite<HorizontalLayout>
{
    public EIViewWrapper(EIView view)
    {
        view.addClassName(Overflow.AUTO);

        HorizontalLayout hlRightSidebar = new HorizontalLayout();
        hlRightSidebar.addClassNames("right-sidebar-layout");
        hlRightSidebar.addClassNames(Overflow.AUTO);
        hlRightSidebar.setSizeFull();
        hlRightSidebar.setPadding(true);
        hlRightSidebar.setAlignItems(Alignment.STRETCH);

        view.getStyle().set("flex", "3 3 70%");
        hlRightSidebar.addAndExpand((Component) view);

        createRightSidebar(view)
                .ifPresent(hlRightSidebar::add);

        VerticalLayout vlToolbar = new VerticalLayout();
        vlToolbar.addClassNames("toolbar-layout");
        vlToolbar.setSizeFull();
        vlToolbar.setPadding(true);
        vlToolbar.setAlignItems(Alignment.STRETCH);

        createToolbar(view)
                .ifPresent(vlToolbar::add);

        vlToolbar.addAndExpand(hlRightSidebar);

        HorizontalLayout hlLeftSidebar = getContent();
        hlLeftSidebar.addClassNames("left-sidebar-layout");
        hlLeftSidebar.addClassNames(Overflow.AUTO);
        hlLeftSidebar.setSizeFull();
        hlLeftSidebar.setPadding(false);
        hlLeftSidebar.setAlignItems(Alignment.STRETCH);

        createLeftSidebar(view)
                .ifPresent(hlLeftSidebar::add);


        vlToolbar.getStyle().set("flex", "3 3 80%");
        hlLeftSidebar.addAndExpand(vlToolbar);
    }

    private static Optional<ConfigurableSidebar> createRightSidebar(final EIView view)
    {
        if (view instanceof HasRightSidebar viewWithRightSidebar)
        {
            return
                    viewWithRightSidebar
                            .getOptionalRightSidebarConfigSupplier()
                            .map(sb ->
                            {
                                ConfigurableSidebar sidebar = new ConfigurableSidebar(sb);
                                sidebar.getStyle().set("flex", "1 1 30%");
                                sidebar.addThemeVariants(BOX);
                                sidebar.addClassName(HIDE_XS);
                                sidebar.addClassName(HIDE_S);

                                if (view instanceof HasLeftSidebar)
                                {
                                    sidebar.addClassName(HIDE_M);
                                    sidebar.addClassName(HIDE_L);
                                }

                                if (view instanceof RightSidebarNotifier n)
                                {
                                    n.addRightSidebarChangeListener(e -> sidebar.rebuild());
                                }

                                return sidebar;
                            });
        }
        return Optional.empty();
    }

    private static Optional<ConfigurableToolbar> createToolbar(EIView view)
    {
        if (view instanceof HasToolbarConfig viewWithToolbar)
        {
            return
                    viewWithToolbar.getOptionalToolbarConfigSupplier().map(tb ->
                    {
                        ConfigurableToolbar toolbar = new ConfigurableToolbar(tb);

                        if (view instanceof ToolbarNotifier notifier)
                        {
                            notifier.addToolbarChangeListener(e -> toolbar.rebuild());
                        }

                        return toolbar;
                    });
        }
        return Optional.empty();
    }

    private static Optional<ConfigurableSidebar> createLeftSidebar(final EIView view)
    {
        if (view instanceof HasLeftSidebar viewWithLeftSidebar)
        {
            return
                    viewWithLeftSidebar
                            .getOptionalLeftSidebarConfigSupplier()
                            .map(sb ->
                            {
                                ConfigurableSidebar sidebar = new ConfigurableSidebar(sb);
                                sidebar.getStyle().set("flex", "1 1 20%");
                                sidebar.addThemeVariants(Sidebar.SidebarVariant.CONTRAST);

                                if (view instanceof LeftSidebarNotifier notifier)
                                {
                                    notifier.addLeftSidebarChangeListener(e -> sidebar.rebuild());
                                }

                                return sidebar;
                            });
        }
        return Optional.empty();
    }
}
