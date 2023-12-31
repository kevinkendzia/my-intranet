package de.kkendzia.myintranet.ei.ui.layouts.main.drawer.menu.provider.annotation;

import com.vaadin.flow.component.icon.VaadinIcon;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface MenuVaadinIcon
{
    VaadinIcon value();
}
