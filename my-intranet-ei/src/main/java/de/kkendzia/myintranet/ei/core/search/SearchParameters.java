package de.kkendzia.myintranet.ei.core.search;

import de.kkendzia.myintranet.ei.core.parameters.ParameterDefinition;

public final class SearchParameters
{
    public static final ParameterDefinition<String> SEARCH_TEXT = ParameterDefinition.stringParam("searchtext");

    private SearchParameters()
    {
        // No Instance!
    }
}
