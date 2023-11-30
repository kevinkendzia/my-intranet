package de.kkendzia.myintranet.ei.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouteConfiguration;

import static java.util.Objects.requireNonNull;

public final class HistoryUtils
{
    private HistoryUtils()
    {
        // No Instance!
    }

    public static <C extends Component> void updateUrl(C target, QueryParameters queryParameters)
    {
        requireNonNull(target, "target can't be null!");
        requireNonNull(queryParameters, "queryParameters can't be null!");

        String url = RouteConfiguration.forSessionScope().getUrl(target.getClass());
        url += queryParameters.getQueryString();
        updateUrl(target, url);
    }

    public static <T, C extends Component & HasUrlParameter<T>> void updateUrl(HasUrlParameter<T> target, T value)
    {
        requireNonNull(target, "source can't be null!");
        requireNonNull(value, "value can't be null!");

        //noinspection unchecked
        String url = RouteConfiguration.forSessionScope().getUrl((Class<C>) target.getClass(), value);
        updateUrl((Component) target, url);
    }

    private static void updateUrl(Component target, String url)
    {
        requireNonNull(target, "target can't be null!");
        requireNonNull(url, "url can't be null!");

        target
                .getUI()
                .orElseThrow(() -> new IllegalArgumentException("UI is NOT accessible!"))
                .getPage()
                .getHistory()
                .replaceState(null, url);
    }

}
