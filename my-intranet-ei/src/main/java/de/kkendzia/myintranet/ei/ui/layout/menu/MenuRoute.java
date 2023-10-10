package de.kkendzia.myintranet.ei.ui.layout.menu;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface MenuRoute
{
    String label();
    String group() default "";
}
