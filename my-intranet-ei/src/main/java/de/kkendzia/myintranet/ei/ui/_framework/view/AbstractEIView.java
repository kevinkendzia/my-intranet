package de.kkendzia.myintranet.ei.ui._framework.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.router.HasDynamicTitle;
import de.kkendzia.myintranet.ei.ui._framework.EIComponent;
import de.kkendzia.myintranet.ei.ui._framework.parameters.ParameterDefinition;

public abstract class AbstractEIView<C extends Component> extends EIComponent<C> implements HasDynamicTitle
{
    private String pageTitle;

    protected AbstractEIView()
    {

    }

    protected <T> void setParameter(ParameterDefinition<T> definition, T value)
    {
        ComponentUtil.setData(this, definition.getName(), value);
    }
    protected <T> T getParameter(ParameterDefinition<T> definition)
    {
        //noinspection unchecked
        return (T) ComponentUtil.getData(this, definition.getName());
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
}
