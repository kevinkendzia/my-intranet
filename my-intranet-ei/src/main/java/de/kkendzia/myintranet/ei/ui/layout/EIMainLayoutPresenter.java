package de.kkendzia.myintranet.ei.ui.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.QueryParameters;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import de.kkendzia.myintranet.ei.ui.views.ah.search.AhSearchView;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchItemType.*;
import static de.kkendzia.myintranet.ei.ui.layout.EIMainLayoutPresenter.SearchTarget.*;

@Component
public class EIMainLayoutPresenter
{
    public void search(SearchPreviewItem value)
    {
        // TODO
        if (value.id() > 0)
        {
            UI.getCurrent().navigate(
                    switch (value.target)
                    {
                        case AH -> AhDetailView.class;
                        default -> throw new IllegalStateException("Not implemented!");
                    },
                    value.id());
        }
        else
        {
            UI.getCurrent().navigate(
                    switch (value.target)
                    {
                        case AH -> AhSearchView.class;
                        default -> throw new IllegalStateException("Not implemented!");
                    },
                    QueryParameters.of("searchtext", value.searchText()));
        }
    }

    public DataProvider<SearchPreviewItem, String> createSearchPreviewDataProvider()
    {
        return DataProvider.fromFilteringCallbacks(this::searchPreview, this::countPreview);
    }

    private Stream<SearchPreviewItem> searchPreview(Query<SearchPreviewItem, String> query)
    {
        String searchText = query.getFilter().orElse("");

        return Stream.of(
                        new SearchPreviewItem(searchText, AH, HEADER, -1, ""),
                        new SearchPreviewItem(searchText, AH, DEFAULT, 1, "HOMA"),
                        new SearchPreviewItem(searchText, AH, DEFAULT, 2, "SEIP"),
                        new SearchPreviewItem(searchText, AH, FOOTER, -1, ""),
                        new SearchPreviewItem(searchText, VL, HEADER, -1, ""),
                        new SearchPreviewItem(searchText, VL, DEFAULT, 1, "ACTONA"),
                        new SearchPreviewItem(searchText, AKTION, HEADER, -1, ""),
                        new SearchPreviewItem(searchText, AKTION, DEFAULT, 1, "Sommermöbel 2023"))
                .skip(query.getOffset())
                .limit(query.getLimit());
    }

    private int countPreview(Query<SearchPreviewItem, String> query)
    {
        return 5;
    }

    public SearchPreviewItem createSearchPreviewItem(String text)
    {
        return new SearchPreviewItem(text, OTHER, DEFAULT,-1,text);
    }

    //region TYPES
    public enum SearchTarget
    {
        // TODO
        OTHER,
        AH,
        VL,
        AKTION;
    }
    public enum SearchItemType
    {
        DEFAULT,
        HEADER,
        FOOTER;
    }

    public record SearchPreviewItem(
            String searchText,
            SearchTarget target,
            SearchItemType type,
            long id,
            String name)
    {
        // just a record
    }
    //endregion
}
