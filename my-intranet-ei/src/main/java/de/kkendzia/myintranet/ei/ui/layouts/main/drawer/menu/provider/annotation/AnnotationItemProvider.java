package de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.DrawerMenu;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.DrawerMenu.TreeItem;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.MenuDefinition;
import de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation.AnnotationItemProvider.AnnotationAnalyzer.AnnotationData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;


public class AnnotationItemProvider implements DrawerMenu.ItemProvider
{
    private final SerializableSupplier<RouteConfiguration> routConfigurationSupplier = RouteConfiguration::forSessionScope;
    private transient List<MenuDefinition> menuDefinitions = emptyList();
    private SerializablePredicate<RouteData> routeFilter;
    private String fallbackKey;

    @Override
    public List<TreeItem> collectItems()
    {
        Map<String, TreeItem> keyItemMap = new HashMap<>();
        List<TreeItem> rootItems = new ArrayList<>();

        rootItems.addAll(mapDefinitionsRecursive(menuDefinitions, item -> extendItemMap(keyItemMap, item)));
        Map<AnnotationData, TreeItem> routes = collectAnnotationData(item -> extendItemMap(keyItemMap, item));

        for (Map.Entry<AnnotationData, TreeItem> entry : routes.entrySet())
        {
            AnnotationData annotation = entry.getKey();
            TreeItem item = entry.getValue();

            if (keyItemMap.containsKey(annotation.parent()))
            {
                TreeItem parent = keyItemMap.get(annotation.parent());
                parent.children().add(item);
            }
            else if (fallbackKey != null && keyItemMap.containsKey(fallbackKey))
            {
                TreeItem parent = keyItemMap.get(fallbackKey);
                parent.children().add(item);
            }
            else
            {
                rootItems.add(item);
            }
        }

        return postProcessRecursive(rootItems);
    }

    private List<TreeItem> postProcessRecursive(List<TreeItem> items)
    {
        List<TreeItem> filtered = new ArrayList<>();
        for (TreeItem item : items)
        {
            List<TreeItem> children = postProcessRecursive(item.children());
            item.children().clear();
            item.children().addAll(children);

            if (!item.children().isEmpty() || item.navigationTarget() != null)
            {
                filtered.add(item);
            }
        }
        filtered.sort(comparingInt(TreeItem::position));
        return filtered;
    }

    private Map<AnnotationData, TreeItem> collectAnnotationData(SerializableConsumer<TreeItem> itemConsumer)
    {
        RouteConfiguration config = routConfigurationSupplier.get();
        AnnotationAnalyzer analyzer = new AnnotationAnalyzer();
        Map<AnnotationData, TreeItem> result = new HashMap<>();
        for (RouteData r : config.getAvailableRoutes())
        {
            if (analyzer.isMenuRoute(r) && (routeFilter == null || routeFilter.test(r)))
            {
                AnnotationData analyze = analyzer.analyze(r);
                TreeItem item = analyze.createItem();
                result.put(analyze, item);
                itemConsumer.accept(item);

            }
        }
        return result;
    }

    private static List<TreeItem> mapDefinitionsRecursive(
            List<MenuDefinition> menuDefinitions,
            SerializableConsumer<TreeItem> itemConsumer)
    {
        return menuDefinitions
                .stream()
                .map(def ->
                {
                    TreeItem item = def.createItem();
                    itemConsumer.accept(item);
                    item.children().addAll(mapDefinitionsRecursive(def.children(), itemConsumer));
                    return item;
                })
                .toList();
    }

    private static void extendItemMap(Map<String, TreeItem> itemMap, TreeItem item)
    {
        if (item.key().isEmpty())
        {
            return;
        }
        if (itemMap.containsKey(item.key()))
        {
            throw new IllegalStateException("ItemMap already contains key \"" + item.key() + "\"");
        }
        itemMap.put(item.key(), item);
    }

    public void setMenuDefinitions(List<MenuDefinition> menuDefinitions)
    {
        this.menuDefinitions = requireNonNull(menuDefinitions, "menuStructure can't be null!");
    }

    public void setMenuStructure(MenuDefinition... menuStructure)
    {
        setMenuDefinitions(List.of(menuStructure));
    }

    public void setRouteFilter(SerializablePredicate<RouteData> routeFilter)
    {
        this.routeFilter = routeFilter;
    }

    public void setFallbackKey(String fallbackKey)
    {
        this.fallbackKey = fallbackKey;
    }

    /*
     * TYPES
     */

    public static class AnnotationAnalyzer
    {
        public boolean isMenuRoute(RouteData route)
        {
            Class<? extends Component> target = route.getNavigationTarget();
            return target.isAnnotationPresent(Route.class) &&
                    target.isAnnotationPresent(MenuRoute.class);
        }

        public AnnotationData analyze(RouteData routeData)
        {
            Class<? extends Component> target = routeData.getNavigationTarget();
            MenuRoute annotation = getMenuRoute(target);
            SerializableSupplier<Component> iconSupplier = getIconSupplier(target);

            return new AnnotationData(
                    routeData,
                    annotation.key().isEmpty() ? "" : annotation.key(),
                    annotation.label(),
                    annotation.position(),
                    annotation.parent(),
                    iconSupplier);
        }

        public String extractLabel(Class<? extends Component> target)
        {
            MenuRoute annotation = getMenuRoute(target);
            return annotation.label();
        }

        private static MenuRoute getMenuRoute(Class<? extends Component> target)
        {
            MenuRoute annotation = target.getAnnotation(MenuRoute.class);
            requireNonNull(annotation, "annotation can't be null!");
            return annotation;
        }

        private static SerializableSupplier<Component> getIconSupplier(Class<? extends Component> target)
        {
            MenuVaadinIcon iconAnnotation = target.getAnnotation(MenuVaadinIcon.class);
            if (iconAnnotation != null)
            {
                return iconAnnotation.value()::create;
            }
            return null;
        }

        public record AnnotationData(
                RouteData routeData,
                String key,
                String label,
                int position,
                String parent,
                SerializableSupplier<Component> iconSupplier)
        {
            public TreeItem createItem()
            {
                return new TreeItem(
                        key(),
                        label(),
                        true,
                        iconSupplier(),
                        routeData().getNavigationTarget(),
                        position());
            }
        }

    }
}
