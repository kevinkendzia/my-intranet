package de.kkendzia.myintranet.ei.ui.views.home.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import de.kkendzia.myintranet.app.useractions._shared.ActionItem;

public class HomeActionButton extends Composite<RouterLink>
{
    public HomeActionButton(ActionItem action)
    {
        final var b = new Button(action.getTitle(), VaadinIcon.LIST.create());
        b.setWidth("11em");
        b.setHeight("7em");

        final var link = getContent();
        link.addClassName("action-button");
        link.add(b);

        RouteConfiguration
                .forSessionScope()
                .getRoute(action.getRoute())
                .ifPresent(link::setRoute);
    }

    public void setRoute(Class<Component> route)
    {
        getContent().setRoute(route);
    }
}
