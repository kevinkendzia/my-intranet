package de.kkendzia.myintranet.ei.ui.layout.menu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.RouteData;

import java.util.Optional;

import static org.vaadin.lineawesome.LineAwesomeIcon.PLANE_SOLID;


public class MenuRouteAnalyzer
{
    private static final MenuRouteAnalyzer INSTANCE = new MenuRouteAnalyzer();

    public Optional<MenuRouteData> analyze(RouteData route)
    {
        Class<? extends Component> target = route.getNavigationTarget();
        MenuRoute annotation = target.getAnnotation(MenuRoute.class);
        if (annotation != null)
        {
            return Optional.of(new MenuRouteData(annotation.label(), PLANE_SOLID::create));
        }
        return Optional.empty();
    }

    public static MenuRouteAnalyzer getInstance()
    {
        return INSTANCE;
    }

    /*
     * TYPES
     */

    public record MenuRouteData(String label, SerializableSupplier<Component> iconSupplier)
    {
        // just a record
    }
}
