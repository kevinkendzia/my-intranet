package de.kkendzia.myintranet.ei.ui.components.text;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.TextAlignment;

import java.io.Serializable;
import java.util.Map;

import static de.kkendzia.myintranet.ei.ui.components.text.Counter.CounterI18N.State.FOUND_ITEMS;
import static de.kkendzia.myintranet.ei.ui.components.text.Counter.CounterI18N.State.FOUND_NOTHING;
import static java.util.Map.entry;
import static java.util.Objects.requireNonNull;

public class Counter extends Composite<Span>
{
    public static final String DEFAULT_FOUND_ITEMS = "counter.foundItems";
    public static final String DEFAULT_FOUND_NOTHING = "counter.foundNothing";
    private final CounterI18N i18n;

    public Counter(CounterI18N i18n)
    {
        this.i18n = requireNonNull(i18n, "i18n can't be null!");
        getContent().addClassName(TextAlignment.RIGHT);
    }

    public Counter()
    {
        this(new CounterI18N(DEFAULT_FOUND_ITEMS, DEFAULT_FOUND_NOTHING));
    }

    public void setValue(long value)
    {
        Map<CounterI18N.State, String> i18nMap = i18n.getI18nMap();
        getContent().setText(value > 0
                             ? getTranslation(i18nMap.getOrDefault(FOUND_ITEMS, DEFAULT_FOUND_ITEMS), value)
                             : getTranslation(i18nMap.getOrDefault(FOUND_NOTHING, DEFAULT_FOUND_NOTHING)));
    }

    //region TYPES
    public static class CounterI18N implements Serializable
    {
        private final Map<State, String> i18nMap;

        public CounterI18N(Map<State, String> i18nMap)
        {
            this.i18nMap = i18nMap;
        }

        public CounterI18N(String foundItems, String foundNothing)
        {
            this(Map.ofEntries(
                    entry(FOUND_ITEMS, foundItems),
                    entry(FOUND_NOTHING, foundNothing)));
        }

        public void setFoundItems(String key)
        {
            i18nMap.put(FOUND_ITEMS, key);
        }

        public void setFoundNothing(String key)
        {
            i18nMap.put(FOUND_NOTHING, key);
        }

        public Map<State, String> getI18nMap()
        {
            return i18nMap;
        }

        public enum State
        {
            FOUND_ITEMS,
            FOUND_NOTHING
        }
    }
    //endregion
}
