package de.kkendzia.myintranet.ei.core.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import de.kkendzia.myintranet.ei.core.EIComponent;
import de.kkendzia.myintranet.ei.core.parameters.ParameterDefinition;
import de.kkendzia.myintranet.ei.core.view.sidebar.HasSidebarConfig;
import de.kkendzia.myintranet.ei.core.view.sidebar.SidebarConfig;
import de.kkendzia.myintranet.ei.core.view.sidebar.SidebarNotifier;
import de.kkendzia.myintranet.ei.core.view.toolbar.HasToolbarConfig;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarNotifier;

import java.util.*;

import static java.util.Collections.emptyList;

public abstract class AbstractEIView<C extends Component> extends EIComponent<C>
        implements EIView, HasDynamicTitle, BeforeEnterObserver, HasToolbarConfig, ToolbarNotifier,
        HasSidebarConfig, SidebarNotifier
{
    private String pageTitle;
    private final Set<ParameterDefinition<?>> qpDefinitions = new HashSet<>();
    private final Map<ParameterDefinition<?>, List<?>> qpValueMap = new HashMap<>();

    protected AbstractEIView()
    {
        addClassName("ei-view");
    }

    protected void registerQueryParameter(ParameterDefinition<?> definition)
    {
        qpDefinitions.add(definition);
    }

    protected <T> T getFirstQueryParameterValue(ParameterDefinition<T> definition)
    {
        //noinspection unchecked
        List<T> values = (List<T>) qpValueMap.getOrDefault(definition, emptyList());
        return values.isEmpty() ? null : values.get(0);
    }

    protected <T> List<T> getQueryParameterValues(ParameterDefinition<T> definition)
    {
        //noinspection unchecked
        return (List<T>) qpValueMap.getOrDefault(definition, emptyList());
    }

    protected void setToolbarConfig(ToolbarConfig toolbarConfig)
    {
        HasToolbarConfig.setToolbarConfig(this, toolbarConfig);
    }
    protected void setToolbarConfig(ToolbarConfig.ToolbarConfigSupplier toolbarConfigSupplier)
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
    public void beforeEnter(BeforeEnterEvent event)
    {
        Map<String, List<String>> queryParameters = event.getLocation().getQueryParameters().getParameters();
        for (ParameterDefinition<?> def : qpDefinitions)
        {
            List<String> values = queryParameters.getOrDefault(def.getName(), emptyList());
            if (!values.isEmpty())
            {
                qpValueMap.put(def, values.stream().map(x -> def.getParser().apply(x)).toList());
            }
        }
    }
}
