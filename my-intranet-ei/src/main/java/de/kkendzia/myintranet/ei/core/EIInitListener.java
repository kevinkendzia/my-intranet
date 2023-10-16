package de.kkendzia.myintranet.ei.core;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import de.kkendzia.myintranet.ei.ui.errors.EIErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EIInitListener implements VaadinServiceInitListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EIInitListener.class);

    @Override
    public void serviceInit(ServiceInitEvent event)
    {
        VaadinService service = event.getSource();

        service.addSessionInitListener(e ->
        {
            /*
             * 16.10.2023 KK:
             * Only triggered on new sessions!
             * Clear Browser-Data (Cookies) if event isn't triggering!
             */
            e.getSession().setErrorHandler(new EIErrorHandler());
        });

        LOGGER.info("EI-Service initialized!");
    }
}
