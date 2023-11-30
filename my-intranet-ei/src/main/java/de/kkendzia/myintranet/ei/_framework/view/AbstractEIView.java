package de.kkendzia.myintranet.ei._framework.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.RouteParameters;
import de.kkendzia.myintranet.ei._framework.parameters.ParameterDefinition;
import de.kkendzia.myintranet.ei._framework.view.mixins.*;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.layouts.main.sidebar.SidebarConfiguration;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public abstract class AbstractEIView<C extends Component>
        extends Composite<C>
        implements
        EIView,
        HasDynamicTitle,
        BeforeEnterObserver,
        HasToolbarConfig,
        ToolbarNotifier,
        HasLeftSidebar,
        LeftSidebarNotifier,
        HasRightSidebar,
        RightSidebarNotifier
{
    private String pageTitle;
    private final Map<ParameterDefinition<?>, List<?>> qpValueMap = new HashMap<>();
    private final Map<ParameterDefinition<?>, Object> rpValueMap = new HashMap<>();

    protected AbstractEIView()
    {
        addClassName("ei-view");
    }

    protected void registerQueryParameter(ParameterDefinition<?> definition)
    {
        qpValueMap.put(definition, null);
    }

    protected <T> Stream<T> qpValues(ParameterDefinition<T> definition)
    {
        //noinspection unchecked
        List<T> values = (List<T>) qpValueMap.getOrDefault(definition, emptyList());
        return Optional.ofNullable(values).stream().flatMap(Collection::stream);
    }

    protected <T> Optional<T> qpValue(ParameterDefinition<T> definition)
    {
        return qpValues(definition).findFirst();
    }

    protected <T> T qpValue(ParameterDefinition<T> definition, T defaultValue)
    {
        return qpValue(definition).orElse(defaultValue);
    }

    protected void registerRouteParameter(ParameterDefinition<?> definition)
    {
        rpValueMap.put(definition, null);
    }

    protected <T> Optional<T> rpValues(ParameterDefinition<T> definition)
    {
        //noinspection unchecked
        return (Optional<T>) Optional.ofNullable(rpValueMap.get(definition));
    }

    protected void setToolbarConfig(ToolbarConfiguration toolbarConfiguration)
    {
        HasToolbarConfig.setToolbarConfig(this, toolbarConfiguration);
    }

    protected void setToolbarConfig(ToolbarConfiguration.ToolbarConfigSupplier toolbarConfigSupplier)
    {
        HasToolbarConfig.setToolbarConfig(this, toolbarConfigSupplier);
    }

    protected void setLeftSidebarConfig(SidebarConfiguration sidebarConfig)
    {
        HasLeftSidebar.setLeftSidebarConfig(this, sidebarConfig);
    }

    protected void setLeftSidebarConfig(LeftSidebarConfigSupplier sidebarConfigSupplier)
    {
        HasLeftSidebar.setLeftSidebarConfig(this, sidebarConfigSupplier);
    }

    protected void setRightSidebarConfig(SidebarConfiguration sidebarConfig)
    {
        HasRightSidebar.setRightSidebarConfig(this, sidebarConfig);
    }

    protected void setRightSidebarConfig(RightSidebarConfigSupplier sidebarConfigSupplier)
    {
        HasRightSidebar.setRightSidebarConfig(this, sidebarConfigSupplier);
    }

    @Override
    public String getPageTitle()
    {
        return pageTitle;
    }

    protected void setPageTitle(String pageTitle)
    {
        this.pageTitle = pageTitle;
    }

    @Override
    public final void beforeEnter(BeforeEnterEvent event)
    {
        collectQueryParameters(event);
        collectRouteParameters(event);
        beforeEnterView(event);
    }

    private void collectQueryParameters(BeforeEnterEvent event)
    {
        Map<String, List<String>> queryParameters = event.getLocation().getQueryParameters().getParameters();
        for (ParameterDefinition<?> def : qpValueMap.keySet())
        {
            List<String> values = queryParameters.getOrDefault(def.name(), emptyList());
            if (!values.isEmpty())
            {
                qpValueMap.put(def, values.stream().map(x -> def.parser().apply(x)).toList());
            }
        }
    }

    private void collectRouteParameters(BeforeEnterEvent event)
    {
        RouteParameters routeParameters = event.getRouteParameters();
        for (ParameterDefinition<?> def : rpValueMap.keySet())
        {
            routeParameters
                    .get(def.name())
                    .ifPresent(v -> rpValueMap.put(def, def.parser().apply(v)));
        }
    }

    protected void beforeEnterView(BeforeEnterEvent event)
    {
        // optional
    }

//    protected void updateURL()
//    {
//        String deepLinkingUrl = RouteConfiguration.forSessionScope().getUrl(getClass());
//        getUI().orElseThrow(() -> new IllegalArgumentException("UI is NOT accessible!"))
//                .getPage()
//                .getHistory()
//                .replaceState(null, deepLinkingUrl);
//    }
}
