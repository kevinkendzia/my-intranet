package de.kkendzia.myintranet.ei._framework.view.search;

import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface SearchRoute
{
    SearchTarget target();
}
