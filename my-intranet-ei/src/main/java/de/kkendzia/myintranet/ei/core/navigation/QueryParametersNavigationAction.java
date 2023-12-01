package de.kkendzia.myintranet.ei.core.navigation;

import com.vaadin.flow.router.QueryParameters;

import java.io.Serializable;

@FunctionalInterface
public interface QueryParametersNavigationAction extends Serializable
{
    void execute(QueryParameters queryParameters);
}
