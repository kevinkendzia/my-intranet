package de.kkendzia.myintranet.ei.ui._framework.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import de.kkendzia.myintranet.ei.ui._framework.EIComponent;
import de.kkendzia.myintranet.ei.ui._framework.parameters.ParameterDefinition;

import java.util.*;

import static java.util.Collections.emptyList;

public abstract class AbstractEIView<C extends Component> extends EIComponent<C> implements HasDynamicTitle, BeforeEnterObserver
{
    private String pageTitle;
    private Set<ParameterDefinition<?>> qpDefinitions = new HashSet<>();
    private Map<ParameterDefinition<?>, List<?>> qpValueMap = new HashMap<>();

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
