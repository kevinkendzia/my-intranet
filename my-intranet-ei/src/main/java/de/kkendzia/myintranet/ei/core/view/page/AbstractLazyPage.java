package de.kkendzia.myintranet.ei.core.view.page;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;

public abstract class AbstractLazyPage<C extends Component> extends AbstractPage<C>
{
    private boolean loaded = false;

    protected AbstractLazyPage()
    {
        getContent().addClassName("ei-lazy-page");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent)
    {
        super.onAttach(attachEvent);
        if (!loaded)
        {
            load();
        }
    }

    private void load()
    {
        loaded = true;
        onLoad();
    }

    protected abstract void onLoad();

    @Override
    public final void refresh()
    {
        loaded = false;
        if (isAttached())
        {
            load();
        }
    }
}
