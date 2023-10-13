package de.kkendzia.myintranet.ei.ui.components.menu.provider;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu;
import de.kkendzia.myintranet.ei.ui.components.menu.DrawerMenu.DrawerMenuItem;

import java.lang.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;


public class AnnotationItemProvider implements DrawerMenu.ItemProvider
{
    private SerializableSupplier<RouteConfiguration> routConfigurationSupplier = RouteConfiguration::forSessionScope;
    private final List<DrawerMenuItem> preDefinedGroups;

    public AnnotationItemProvider(List<DrawerMenuItem> preDefinedGroups)
    {
        this.preDefinedGroups = preDefinedGroups;
    }

    public AnnotationItemProvider()
    {
        this(new ArrayList<>());
    }

    @Override
    public List<DrawerMenuItem> getItems()
    {
        RouteConfiguration config = routConfigurationSupplier.get();
        List<RouteData> routes = config.getAvailableRoutes();
        Map<String, List<MenuRouteData>> routeMap = group(routes);

        List<DrawerMenuItem> rootItems = new ArrayList<>(preDefinedGroups);

        for (Map.Entry<String, List<MenuRouteData>> entry : routeMap.entrySet())
        {
            for (MenuRouteData data : entry.getValue())
            {
                String parent = entry.getKey();

                if (parent.isEmpty())
                {
                    rootItems.add(createItemFromRoute(data));
                }
                else
                {
                    String[] segments = parent.split("/");

                    DrawerMenuItem lastItem = null;

                    for (String segment : segments)
                    {
                        List<DrawerMenuItem> curLayer = lastItem == null ? rootItems : lastItem.children();
                        DrawerMenuItem curItem = findItemByKey(segment, curLayer);
                        if (curItem == null)
                        {
                            curItem = createItemFromSegment(segment);
                            curLayer.add(curItem);
                        }
                        lastItem = curItem;
                    }

                    requireNonNull(lastItem, "lastItem can't be null!");
                    lastItem.children().add(createItemFromRoute(data));
                }
            }
        }

        rootItems.sort(comparing(DrawerMenuItem::position));

        return rootItems;
    }

    private static DrawerMenuItem createItemFromSegment(String segment)
    {
        // TODO: Icon!
        return DrawerMenuItem.group(segment, segment, () -> VaadinIcon.HARDDRIVE.create(), Integer.MAX_VALUE);
    }

    private static DrawerMenuItem createItemFromRoute(MenuRouteData data)
    {
        // TODO: Icon!
        return new DrawerMenuItem(
                data.key(),
                data.label(),
                () -> VaadinIcon.ACCORDION_MENU.create(),
                data.routeData().getNavigationTarget(),
                data.position(),
                new ArrayList<>());
    }

    private Map<String, List<MenuRouteData>> group(List<RouteData> routes)
    {
        return routes
                .stream()
                .filter(this::isMenuRoute)
                .map(this::convertSingleRoute)
                .collect(
                        groupingBy(
                                MenuRouteData::parent,
                                collectingAndThen(
                                        toList(),
                                        AnnotationItemProvider::sort)));
    }

    private static List<MenuRouteData> sort(List<MenuRouteData> lst)
    {
        lst.sort(
                comparing(MenuRouteData::position)
                        .thenComparing(MenuRouteData::key));
        return lst;
    }


    private static DrawerMenuItem findItemByKey(String key, List<DrawerMenuItem> items)
    {
        return items
                .stream()
                .filter(x -> Objects.equals(x.key(), key))
                .reduce((x1, x2) ->
                        {
                            throw new IllegalStateException("Found multiple root items for key " + key);
                        })
                .orElse(null);
    }


    private boolean isMenuRoute(RouteData route)
    {
        Class<? extends Component> target = route.getNavigationTarget();
        return target.isAnnotationPresent(Route.class) &&
                target.isAnnotationPresent(MenuRoute.class);
    }

    private MenuRouteData convertSingleRoute(RouteData route)
    {
        Class<? extends Component> target = route.getNavigationTarget();
        MenuRoute annotation = target.getAnnotation(MenuRoute.class);
        requireNonNull(annotation, "annotation can't be null!");
        return new MenuRouteData(
                route,
                annotation.key(),
                annotation.label(),
                annotation.position(),
                annotation.parent());
    }

    /*
     * TYPES
     */

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    @Documented
    public @interface MenuRoute
    {
        String key() default "";

        String label();

        int position() default Integer.MAX_VALUE;

        String parent() default "";
    }

    public record MenuRouteData(
            RouteData routeData,
            String key,
            String label,
            int position,
            String parent)
    {
        // just a record
    }
}
