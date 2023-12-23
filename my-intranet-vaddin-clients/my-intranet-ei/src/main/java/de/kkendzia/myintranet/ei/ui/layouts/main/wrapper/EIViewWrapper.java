package de.kkendzia.myintranet.ei.ui.layouts.main.wrapper;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import de.kkendzia.components.expandablesidebar.ExpandableSidebar.Side;
import de.kkendzia.myintranet.ei._framework.view.EIView;
import de.kkendzia.myintranet.ei._framework.view.mixins.*;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ConfigurableToolbar;
import de.kkendzia.myintranet.ei.ui.layouts.main.wrapper.sidebar.SidebarConfigurator;

import java.util.Optional;

import static de.kkendzia.components.expandablesidebar.ExpandableSidebarVariant.*;
import static de.kkendzia.myintranet.ei.core.constants.EIStyles.MEDIA.*;

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
        hlRightSidebar.setSpacing(true);
        hlRightSidebar.setAlignItems(Alignment.STRETCH);
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
        hlLeftSidebar.setSpacing(false);
        hlLeftSidebar.setAlignItems(Alignment.STRETCH);

        createLeftSidebar(view)
                .ifPresent(hlLeftSidebar::add);
        hlLeftSidebar.addAndExpand(vlToolbar);
    }

    private static Optional<SidebarConfigurator> createRightSidebar(final EIView view)
    {
        if (view instanceof HasRightSidebar viewWithRightSidebar)
        {
            return
                    viewWithRightSidebar
                            .getOptionalRightSidebarConfigSupplier()
                            .map(sb ->
                            {
                                SidebarConfigurator sidebar = new SidebarConfigurator(sb);
                                sidebar.getContent().setSide(Side.RIGHT);
                                sidebar.getContent().addThemeVariants(BORDERED, RIGHT, ROUND);
                                sidebar.getContent().setExpanded(true);

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

    private static Optional<SidebarConfigurator> createLeftSidebar(final EIView view)
    {
        if (view instanceof HasLeftSidebar viewWithLeftSidebar)
        {
            return
                    viewWithLeftSidebar
                            .getOptionalLeftSidebarConfigSupplier()
                            .map(sb ->
                            {
                                SidebarConfigurator sidebar = new SidebarConfigurator(sb);
                                sidebar.getContent().setSide(Side.LEFT);
                                sidebar.getContent().addThemeVariants(BORDERED, LEFT, ROUND);
                                sidebar.getContent().setExpanded(false);

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
