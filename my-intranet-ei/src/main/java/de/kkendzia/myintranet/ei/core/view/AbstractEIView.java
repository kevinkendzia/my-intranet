package de.kkendzia.myintranet.ei.core.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.*;
import de.kkendzia.myintranet.ei.core.EIComponent;
import de.kkendzia.myintranet.ei.core.parameters.ParameterDefinition;
import de.kkendzia.myintranet.ei.core.view.mixins.HasSidebarConfig;
import de.kkendzia.myintranet.ei.ui.components.sidebar.SidebarConfig;
import de.kkendzia.myintranet.ei.ui.components.sidebar.ConfigurableSidebarNotifier;
import de.kkendzia.myintranet.ei.core.view.mixins.HasToolbarConfig;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ToolbarConfiguration;
import de.kkendzia.myintranet.ei.ui.components.toolbar.ConfigurableToolbarNotifier;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public abstract class AbstractEIView<C extends Component> extends EIComponent<C>
        implements EIView, HasDynamicTitle, BeforeEnterObserver, HasToolbarConfig, ConfigurableToolbarNotifier,
        HasSidebarConfig, ConfigurableSidebarNotifier
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

    protected void setSidebarConfig(SidebarConfig sidebarConfig)
    {
        HasSidebarConfig.setSidebarConfig(this, sidebarConfig);
    }

    protected void setSidebarConfig(SidebarConfig.SidebarConfigSupplier sidebarConfigSupplier)
    {
        HasSidebarConfig.setSidebarConfig(this, sidebarConfigSupplier);
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
            List<String> values = queryParameters.getOrDefault(def.getName(), emptyList());
            if (!values.isEmpty())
            {
                qpValueMap.put(def, values.stream().map(x -> def.getParser().apply(x)).toList());
            }
        }
    }

    private void collectRouteParameters(BeforeEnterEvent event)
    {
        RouteParameters routeParameters = event.getRouteParameters();
        for (ParameterDefinition<?> def : rpValueMap.keySet())
        {
            routeParameters
                    .get(def.getName())
                    .ifPresent(v -> rpValueMap.put(def, def.getParser().apply(v)));
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
