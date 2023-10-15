package de.kkendzia.myintranet.ei.ui._framework.search;

import de.kkendzia.myintranet.ei.ui._framework.parameters.ParameterDefinition;

import static de.kkendzia.myintranet.ei.ui._framework.parameters.ParameterDefinition.stringParam;

public final class SearchParameters
{
    public static final ParameterDefinition<String> SEARCH_TEXT = stringParam("searchtext");

    private SearchParameters()
    {
        // No Instance!
    }
}
