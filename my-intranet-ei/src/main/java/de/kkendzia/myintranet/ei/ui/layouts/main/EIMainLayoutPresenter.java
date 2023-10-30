package de.kkendzia.myintranet.ei.ui.layouts.main;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;
import de.kkendzia.myintranet.ei.core.presenter.EIPresenter;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import de.kkendzia.myintranet.ei.ui.views.ah.search.AhSearchView;
import de.kkendzia.myintranet.ei.ui.views.search.SearchView;

import java.util.stream.Stream;

import static de.kkendzia.myintranet.ei.core.view.search.SearchParameters.SEARCH_TEXT;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchItemType.*;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget.*;

@Presenter
public class EIMainLayoutPresenter implements EIPresenter
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
        else if (value.target() != null && value.target() != OTHER)
        {
            UI.getCurrent().navigate(
                    switch (value.target)
                    {
                        case AH -> AhSearchView.class;
                        default -> throw new IllegalStateException("Not implemented!");
                    },
                    SEARCH_TEXT.createQueryParameters(value.searchText()));
        }
        else
        {
            UI.getCurrent().navigate(SearchView.class, SEARCH_TEXT.createQueryParameters(value.searchText()));
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
        return 8;
    }

    //region TYPES
    public enum SearchTarget
    {
        // TODO
        OTHER(TranslationKeys.OTHER),
        AH(TranslationKeys.AHS),
        VL(TranslationKeys.VLS),
        AKTION(TranslationKeys.AKTIONEN);

        private final String key;

        SearchTarget(String key)
        {
            this.key = key;
        }

        public String getKey()
        {
            return key;
        }
    }

    public enum SearchItemType
    {
        DEFAULT,
        HEADER,
        FOOTER
    }

    public record SearchPreviewItem(
            String searchText,
            SearchTarget target,
            SearchItemType type,
            long id,
            String name)
    {
        public static SearchPreviewItem custom(
                String searchText,
                SearchTarget target)
        {
            return new SearchPreviewItem(searchText, target, DEFAULT, -1, searchText);
        }
    }
    //endregion
}
