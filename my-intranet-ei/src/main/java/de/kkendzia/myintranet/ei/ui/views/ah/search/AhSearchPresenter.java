package de.kkendzia.myintranet.ei.ui.views.ah.search;

import com.vaadin.flow.data.provider.DataProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class AhSearchPresenter
{
    public DataProvider<SearchItem, Void> createSearchDataProvider(String searchtext)
    {

        return DataProvider
                .fromFilteringCallbacks(
                        query -> fetch(searchtext, query.getOffset(), query.getLimit()),
                        query -> count(searchtext));
    }

    private int count(String searchtext)
    {
        return Math.toIntExact(createStream()
                .filter(x -> x.matchcode().contains(searchtext))
                .count());
    }

    private Stream<SearchItem> fetch(
            String searchtext,
            int offset,
            int limit)
    {
        return createStream()
                .filter(x -> x.matchcode().contains(searchtext))
                .skip(offset)
                .limit(limit);
    }

    private static Stream<SearchItem> createStream()
    {
        Random random = new Random();
        LocalDate now = LocalDate.now();

        return Stream.of(
                new SearchItem(
                        1,
                        11111,
                        "TEST_AH1",
                        now.minusMonths(random.nextInt(12)),
                        now.plusMonths(random.nextInt(12))),
                new SearchItem(
                        2,
                        22222,
                        "TEST_AH2",
                        now.minusMonths(random.nextInt(12)),
                        now.plusMonths(random.nextInt(12))),
                new SearchItem(
                        3,
                        33333,
                        "TEST_AH3",
                        now.minusMonths(random.nextInt(12)),
                        now.plusMonths(random.nextInt(12))),
                new SearchItem(
                        4,
                        44444,
                        "TEST_AH4",
                        now.minusMonths(random.nextInt(12)),
                        now.plusMonths(random.nextInt(12))),
                new SearchItem(
                        5,
                        55555,
                        "TEST_AH5",
                        now.minusMonths(random.nextInt(12)),
                        now.plusMonths(random.nextInt(12))));
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
