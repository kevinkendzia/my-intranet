package de.kkendzia.myintranet.ei.core;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import de.kkendzia.myintranet.ei.ui.errors.EIErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class EIInitListener implements VaadinServiceInitListener
{
    @Override
    public void serviceInit(ServiceInitEvent event)
    {
        VaadinService service = event.getSource();
        service.addSessionInitListener(e -> e.getSession().setErrorHandler(new EIErrorHandler()));
    }
}
