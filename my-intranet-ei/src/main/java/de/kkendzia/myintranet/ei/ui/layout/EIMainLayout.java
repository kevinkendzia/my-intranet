package de.kkendzia.myintranet.ei.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu;
import de.kkendzia.myintranet.ei.ui.components.menu.provider.AnnotationItemProvider;
import de.kkendzia.myintranet.ei.ui.layout.appbar.EIAppBar;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

        DrawerMenu menu =
                new DrawerMenu(
                        new DrawerMenu.MenuHeader(),
                        new DrawerMenu.MenuFooter());
        menu.setItemProvider(
                new AnnotationItemProvider(
                        List.of(
                                new DrawerMenu.DrawerMenuItem(
                                        "nav",
                                        "menu.target.nav"),
                                new DrawerMenu.DrawerMenuItem(
                                        "ah",
                                        "menu.target.ah"),
                                new DrawerMenu.DrawerMenuItem(
                                        "other",
                                        "menu.target.other"))));

        setPrimarySection(Section.DRAWER);
        addToDrawer(menu);
        addToNavbar(
                new EIAppBar(
                        presenter.createSearchPreviewDataProvider(),
                        e -> presenter.search(e.getValue()),
                        item -> true, //item.type() == DEFAULT,
                        item -> switch (item.type())
                        {
                            case DEFAULT -> item.name() + " - " + item.searchText();
                            case HEADER -> getTranslation("search.group.header." + item.target() + " - " + item.searchText());
                            case FOOTER -> getTranslation("search.group.footer." + item.target() + " - " + item.searchText());
                        },
                        text -> presenter.createSearchPreviewItem(text)));
    }
}
