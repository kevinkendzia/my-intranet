package de.kkendzia.myintranet.ei.ui.components.navigation;

import com.vaadin.flow.router.QueryParameters;

import java.io.Serializable;

@FunctionalInterface
public interface QueryParametersNavigationAction extends Serializable
{
    void execute(QueryParameters queryParameters);
}
