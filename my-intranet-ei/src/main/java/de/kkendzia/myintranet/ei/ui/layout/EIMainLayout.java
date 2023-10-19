package de.kkendzia.myintranet.ei.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import de.kkendzia.myintranet.ei.core.view.AbstractEIView;
import de.kkendzia.myintranet.ei.core.view.sidebar.ViewSidebar;
import de.kkendzia.myintranet.ei.core.view.toolbar.ViewToolbar;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The main view is a top-level placeholder for other views.
 */
public class EIMainLayout
        extends AppLayout
{
    private EIMainLayoutPresenter presenter;

    @Autowired
    public EIMainLayout(EIMainLayoutPresenter presenter)
    {
        this.presenter = presenter;

        addClassName("ei-main-layout");
        setPrimarySection(Section.DRAWER);
        addToDrawer(new EIMenu());
        addToNavbar(new EIAppBar(presenter));
    }

    @Override
    public void showRouterLayoutContent(HasElement content)
    {
        AbstractEIView<?> eiView = requireEIView(content);
        setContent(new ViewWrapper(eiView));
        afterNavigation();
    }

    private static Component requireComponent(HasElement content)
    {
        if (content != null)
        {
            return content
                    .getElement()
                    .getComponent()
                    .orElseThrow(() -> new IllegalArgumentException(
                            "MainLayout content must be a Component"));
        }
        return null;
    }

    private static AbstractEIView<?> requireEIView(HasElement content)
    {
        if (content instanceof AbstractEIView<?> eiView)
        {
            return eiView;
        }

        throw new IllegalArgumentException("MainLayout content must be a EIView! " + content.getClass());
    }

    public static class ViewWrapper extends Composite<HorizontalLayout>
    {
        public ViewWrapper(AbstractEIView<?> view)
        {
//            view.addClassName(LumoUtility.Overflow.AUTO);
            view.getElement().getStyle().setHeight("20em");

            VerticalLayout vlContent = new VerticalLayout();
            vlContent.addClassNames("ei-main-toolbar-layout");
            vlContent.setSizeFull();
            vlContent.setPadding(false);
            vlContent.setAlignItems(FlexComponent.Alignment.STRETCH);
            view.getOptionalToolbarConfig().ifPresent(tb -> vlContent.add(new ViewToolbar(tb)));
            vlContent.addAndExpand(view);

            HorizontalLayout root = getContent();
            root.addClassNames("ei-main-sidebar-layout");
            root.addClassNames(Overflow.AUTO);
            root.setSizeFull();
            root.setPadding(true);
            root.setAlignItems(FlexComponent.Alignment.STRETCH);
            view.getOptionalSidebarConfig().ifPresent(sb -> root.add(new ViewSidebar(sb)));
            root.addAndExpand(vlContent);
        }
    }
}
