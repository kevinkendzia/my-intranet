package de.kkendzia.myintranet.ei.ui.views.mandant.search;

import com.vaadin.flow.data.provider.DataProvider;
import de.kkendzia.myintranet.domain.shared.mandant.MandantDAO;
import de.kkendzia.myintranet.ei.core.presenter.EIPresenter;
import de.kkendzia.myintranet.ei.core.presenter.Presenter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

@Presenter
public class MandantSearchPresenter implements EIPresenter
{
    @Autowired
    private MandantDAO mandantDAO;

    public DataProvider<SearchItem, String> createSearchDataProvider()
    {
        return DataProvider
                .fromFilteringCallbacks(
                        query -> fetch(query.getFilter().orElse(null), query.getOffset(), query.getLimit()),
                        query -> count(query.getFilter().orElse(null)));
    }

    private int count(String searchtext)
    {
        // TODO
        return searchtext == null
               ? 0
               : Math.toIntExact(mandantDAO
                       .findAll()
                       .filter(x -> x.getName().contains(searchtext))
                       .count());
    }

    private Stream<SearchItem> fetch(
            String searchtext,
            int offset,
            int limit)
    {
        // TODO
        return searchtext == null
               ? Stream.empty()
               : mandantDAO
                       .findAll()
                       .filter(x -> x.getName().contains(searchtext))
                       .skip(offset)
                       .limit(limit)
                       .map(x -> new SearchItem(x.getId(), x.getName()));
    }

    //region TYPES
    public record SearchItem(
            long id,
            String name)
    {
        // just a record
    }
    //endregion
}
