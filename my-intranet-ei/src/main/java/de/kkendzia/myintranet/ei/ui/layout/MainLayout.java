package de.kkendzia.myintranet.ei.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import de.kkendzia.myintranet.ei.ui.layout.appbar.EIAppBar;
import de.kkendzia.myintranet.ei.ui.layout.menu.EIMainMenu;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout
{
    public MainLayout()
    {
        setPrimarySection(Section.DRAWER);
        addToDrawer(new EIMainMenu());
        addToNavbar(new EIAppBar());
    }
}
