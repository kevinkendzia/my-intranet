package de.kkendzia.myintranet.ei.ui.layouts.main;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.spring.annotation.RouteScope;
import de.kkendzia.myintranet.app._framework.cqrs.command.CommandMediator;
import de.kkendzia.myintranet.app._framework.cqrs.query.QueryMediator;
import de.kkendzia.myintranet.app.search.queries.SearchAhs;
import de.kkendzia.myintranet.app.search.queries.SearchMandanten;
import de.kkendzia.myintranet.app.useractions._shared.ActionItem;
import de.kkendzia.myintranet.app.useractions.commands.AddFavoriteAction;
import de.kkendzia.myintranet.app.useractions.queries.FindFavoriteActions;
import de.kkendzia.myintranet.app.useractions.queries.FindRecentActions;
import de.kkendzia.myintranet.ei._framework.presenter.EIPresenter;
import de.kkendzia.myintranet.ei._framework.presenter.Presenter;
import de.kkendzia.myintranet.ei.core.navigation.NavigateWithId;
import de.kkendzia.myintranet.ei.core.navigation.NavigateWithQueryParameters;
import de.kkendzia.myintranet.ei.core.session.EISession;
import de.kkendzia.myintranet.ei.ui.tools.data.MultiQueryDataProvider;
import de.kkendzia.myintranet.ei.ui.tools.data.MultiQueryDataProvider.DefaultQueryFactory;
import de.kkendzia.myintranet.ei.ui.views.ah.detail.AhDetailView;
import de.kkendzia.myintranet.ei.ui.views.ah.search.AhSearchView;
import de.kkendzia.myintranet.ei.ui.views.mandant.detail.MandantDetailView;
import de.kkendzia.myintranet.ei.ui.views.mandant.search.MandantSearchView;
import de.kkendzia.myintranet.ei.ui.views.search.SearchView;

import java.util.List;
import java.util.Optional;

import static de.kkendzia.myintranet.app._framework.cqrs.query.paged.Paging.firstPage;
import static de.kkendzia.myintranet.ei._framework.view.search.SearchParameters.SEARCH_TEXT;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.AhKeys.AHS;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.AktionKeys.AKTIONEN;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.MandantKeys.MANDANTEN;
import static de.kkendzia.myintranet.ei.core.i18n.TranslationKeys.VlKeys.VLS;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchItemType.*;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchPreviewItem.*;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget.AH;
import static de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget.MANDANT;
import static java.util.Objects.requireNonNull;

@Presenter
@RouteScope
public class EIMainLayoutPresenter implements EIPresenter
{
    private final transient QueryMediator quMediator;
    private final transient CommandMediator cmdMediator;
    private final EISession session;

    public EIMainLayoutPresenter(final QueryMediator quMediator, CommandMediator cmdMediator, EISession session)
    {
        this.quMediator = quMediator;
        this.cmdMediator = cmdMediator;
        this.session = session;
    }

    public DataProvider<SearchPreviewItem, String> createSearchPreviewDataProvider()
    {
        return new MultiQueryDataProvider<>(
                quMediator,
                new DefaultQueryFactory<>(
                        SearchMandanten::new,
                        EIMainLayoutPresenter::mapMandantPreviewItem,
                        searchText -> headerItem(searchText, MANDANT),
                        searchText -> footerItem(searchText, MANDANT)),
                new DefaultQueryFactory<>(
                        SearchAhs::new,
                        EIMainLayoutPresenter::mapAhPreviewItem,
                        searchText -> headerItem(searchText, AH),
                        searchText -> footerItem(searchText, AH)));
    }

    public void search(SearchPreviewItem value)
    {
        requireNonNull(value, "value can't be null!");
        final var id = value.optionalId();
        final var target = value.optionalTarget();
        final var searchNavigation = target.flatMap(SearchTarget::getOptionalSearchNavigation);
        final var detailNavigation = target.flatMap(SearchTarget::getOptionalDetailNavigation);

        if (id.isPresent() && detailNavigation.isPresent())
        {
            detailNavigation.ifPresent(n -> n.execute(value.id()));
        }
        else if (searchNavigation.isPresent())
        {
            searchNavigation.ifPresent(n -> n.execute(SEARCH_TEXT.createQueryParameters(value.searchText())));
        }
        else
        {
            UI.getCurrent().navigate(SearchView.class, SEARCH_TEXT.createQueryParameters(value.searchText()));
        }
    }

    public List<ActionItem> fetchRecentActions(final int limit)
    {
        return quMediator.fetchAll(new FindRecentActions(session.userId()), firstPage(limit)).toList();
    }

    public List<ActionItem> fetchFavoriteActions(final int limit)
    {
        return quMediator.fetchAll(new FindFavoriteActions(session.userId()), firstPage(limit)).toList();
    }

    public void addFavoriteAction(ActionItem action)
    {
        cmdMediator.run(new AddFavoriteAction(session.userId(), action));
    }


    //region STATIC
    private static SearchPreviewItem mapMandantPreviewItem(
            final SearchMandanten.ResultItem source,
            final String searchText)
    {
        return defaultType(searchText, MANDANT, source.idString(), source.name());
    }

    private static SearchPreviewItem mapAhPreviewItem(
            final SearchAhs.ResultItem source,
            final String searchText)
    {
        return defaultType(searchText, AH, source.idString(), source.ahnr() + " " + source.matchcode());
    }
    //endregion

    //region TYPES
    public enum SearchTarget
    {
        AH(
                AHS,
                NavigateWithId.to(AhDetailView.class),
                NavigateWithQueryParameters.to(AhSearchView.class)),
        VL(
                VLS,
                null,
                null),
        AKTION(
                AKTIONEN,
                null,
                null),
        MANDANT(
                MANDANTEN,
                NavigateWithId.to(MandantDetailView.class),
                NavigateWithQueryParameters.to(MandantSearchView.class));

        private final String key;
        private final NavigateWithId<String> detailNavigation;
        private final NavigateWithQueryParameters searchNavigation;

        SearchTarget(
                String key,
                NavigateWithId<String> detailNavigation,
                NavigateWithQueryParameters searchNavigation)
        {
            this.key = key;
            this.detailNavigation = detailNavigation;
            this.searchNavigation = searchNavigation;
        }

        public String getKey()
        {
            return key;
        }

        public Optional<NavigateWithId<String>> getOptionalDetailNavigation()
        {
            return Optional.ofNullable(detailNavigation);
        }

        public Optional<NavigateWithQueryParameters> getOptionalSearchNavigation()
        {
            return Optional.ofNullable(searchNavigation);
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
            String id,
            String name)
    {
        public Optional<SearchTarget> optionalTarget()
        {
            return Optional.ofNullable(target());
        }

        public Optional<String> optionalId()
        {
            return Optional.ofNullable(id());
        }

        //region STATIC
        public static SearchPreviewItem custom(
                String searchText,
                SearchTarget target)
        {
            return new SearchPreviewItem(searchText, target, DEFAULT, null, searchText);
        }

        public static SearchPreviewItem headerItem(
                String searchText,
                SearchTarget target)
        {
            return new SearchPreviewItem(searchText, target, HEADER, null, searchText);
        }

        public static SearchPreviewItem footerItem(
                String searchText,
                SearchTarget target)
        {
            return new SearchPreviewItem(searchText, target, FOOTER, null, searchText);
        }

        public static SearchPreviewItem defaultType(
                String searchText,
                SearchTarget target,
                String id,
                String name)
        {
            return new SearchPreviewItem(searchText, target, DEFAULT, id, name);
        }
        //endregion
    }
    //endregion
}
