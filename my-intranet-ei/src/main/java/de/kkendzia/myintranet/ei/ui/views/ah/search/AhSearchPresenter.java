package de.kkendzia.myintranet.ei.ui.views.ah.search;

import com.vaadin.flow.data.provider.DataProvider;
import de.kkendzia.myintranet.domain.ah.AhDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Stream;

@Component
public class AhSearchPresenter
{
    @Autowired
    private AhDAO ahDAO;

    public DataProvider<SearchItem, Void> createSearchDataProvider(String searchtext)
    {

        return DataProvider
                .fromFilteringCallbacks(
                        query -> fetch(searchtext, query.getOffset(), query.getLimit()),
                        query -> count(searchtext));
    }

    private int count(String searchtext)
    {
        // TODO
        return Math.toIntExact(ahDAO
                .findAll()
                .filter(x -> searchtext == null || x.getMatchcode().contains(searchtext))
                .count());
    }

    private Stream<SearchItem> fetch(
            String searchtext,
            int offset,
            int limit)
    {
        // TODO
        return ahDAO
                .findAll()
                .filter(x -> searchtext == null || x.getMatchcode().contains(searchtext))
                .skip(offset)
                .limit(limit)
                .map(x -> new SearchItem(
                        x.getId(),
                        x.getAhnr().value(),
                        x.getMatchcode(),
                        x.getEnterDate(),
                        x.getExitDate()));
    }

    //region TYPES
    public record SearchItem(
            long id,
            int ahnr,
            String matchcode,
            LocalDate enterDate,
            LocalDate exitDate)
    {
        // just a record
    }
    //endregion
}
