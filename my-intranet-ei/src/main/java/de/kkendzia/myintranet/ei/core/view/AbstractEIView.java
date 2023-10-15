package de.kkendzia.myintranet.ei.core.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import de.kkendzia.myintranet.ei.core.EIComponent;
import de.kkendzia.myintranet.ei.core.parameters.ParameterDefinition;
import de.kkendzia.myintranet.ei.core.view.sidebar.SidebarConfig;
import de.kkendzia.myintranet.ei.core.view.toolbar.ToolbarConfig;

import java.util.*;

import static java.util.Collections.emptyList;

public abstract class AbstractEIView<C extends Component> extends EIComponent<C> implements HasDynamicTitle, BeforeEnterObserver, EIView
{
    private String pageTitle;
    private Set<ParameterDefinition<?>> qpDefinitions = new HashSet<>();
    private Map<ParameterDefinition<?>, List<?>> qpValueMap = new HashMap<>();
    private ToolbarConfig toolbarConfig;
    private SidebarConfig sidebarConfig;

    protected AbstractEIView()
    {

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
        this.toolbarConfig = toolbarConfig;
    }

    @Override
    public Optional<ToolbarConfig> getOptionalToolbarConfig()
    {
        return Optional.ofNullable(toolbarConfig);
    }

    public Optional<SidebarConfig> getOptionalSidebarConfig()
    {
        return Optional.ofNullable(sidebarConfig);
    }

    protected void setSidebarConfig(SidebarConfig sidebarConfig)
    {
        this.sidebarConfig = sidebarConfig;
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
