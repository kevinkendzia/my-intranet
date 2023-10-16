package de.kkendzia.myintranet.ei.core.search;

import de.kkendzia.myintranet.ei.core.parameters.ParameterDefinition;

import static de.kkendzia.myintranet.ei.core.parameters.ParameterDefinition.stringParam;

public final class SearchParameters
{
    public static final ParameterDefinition<String> SEARCH_TEXT = stringParam("searchtext");

    private SearchParameters()
    {
        // No Instance!
    }
}
