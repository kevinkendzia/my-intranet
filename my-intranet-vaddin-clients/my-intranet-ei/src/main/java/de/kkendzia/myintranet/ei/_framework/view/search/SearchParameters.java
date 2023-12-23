package de.kkendzia.myintranet.ei._framework.view.search;

import de.kkendzia.myintranet.ei._framework.parameters.ParameterDefinition;

import static de.kkendzia.myintranet.ei._framework.parameters.ParameterDefinition.stringParam;

public final class SearchParameters
{
    public static final ParameterDefinition<String> SEARCH_TEXT = stringParam("searchtext");

    private SearchParameters()
    {
        // No Instance!
    }
}
