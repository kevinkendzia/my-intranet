package de.kkendzia.myintranet.ei.ui.components.menu.provider.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface MenuRoute
{
    String key() default "";

    String label();

    int position() default Integer.MAX_VALUE;

    String parent() default "";
}
