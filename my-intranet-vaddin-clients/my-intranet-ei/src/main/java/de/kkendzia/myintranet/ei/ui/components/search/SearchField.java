package de.kkendzia.myintranet.ei.ui.components.search;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.SerializablePredicate;

import java.util.List;

public class SearchField<T> extends AbstractCompositeField<ComboBox<T>, SearchField<T>, T>
{
    private SerializablePredicate<T> enabledPredicate = itm -> true;
    private ItemLabelGenerator<T> itemLabelGenerator = String::valueOf;
    private SerializableFunction<String, T> itemFactory = searchText -> null;

    public SearchField()
    {
        super(null);

        ComboBox<T> cbo = getContent();
        cbo.setAutoOpen(false);
        cbo.setRenderer(new ComponentRenderer<>(this::createItemComponent));
        cbo.setPrefixComponent(VaadinIcon.SEARCH.create());
        cbo.addValueChangeListener(e ->
        {
            if (e.isFromClient())
            {
                T item = e.getValue();
                if (getEnabledPredicate().test(item))
                {
                    setModelValue(item, true);
                }
                else
                {
                    resetComboBox(e.getOldValue());
                }
            }
        });
        cbo.setAllowCustomValue(true);
        cbo.addCustomValueSetListener(e ->
        {
            T item = getItemFactory().apply(e.getDetail());
            if (getEnabledPredicate().test(item))
            {
                setModelValue(item, true);
            }
            else
            {
                resetComboBox(null);
            }
        });

        // 13.10.2023 KK: Workaround can be replaced when feature was implemented:
        // https://github.com/vaadin/flow-components/issues/5314
        // https://github.com/vaadin/web-components/pull/2750
        cbo.getElement().setAttribute("autoselect", true);
    }

    private void resetComboBox(T oldValue)
    {
        ComboBox<T> cb = getContent();
        if (oldValue != null)
        {
            cb.setValue(oldValue);
        }
        else
        {
            cb.clear();
        }
    }

    private Span createItemComponent(T itm)
    {
        Span span = new Span(itemLabelGenerator.apply(itm));

        if (enabledPredicate.test(itm))
        {
            span.getStyle().set("padding-left", "10px");
            span.getStyle().set("pointer-events", "auto");
        }
        else
        {
            span.getStyle().set("opacity", "0.5");
        }

        return span;
    }

    @Override
    protected void setPresentationValue(T newPresentationValue)
    {
        getContent().setValue(newPresentationValue);
    }

    public void setItems(List<T> items)
    {
        getContent().setItems(items);
    }

    public void setItems(DataProvider<T, String> dataProvider)
    {
        getContent().setItems(dataProvider);
    }

    protected SerializablePredicate<T> getEnabledPredicate()
    {
        return enabledPredicate;
    }

    public void setEnabledPredicate(SerializablePredicate<T> enabledPredicate)
    {
        this.enabledPredicate = enabledPredicate;
    }

    public void setItemLabelGenerator(ItemLabelGenerator<T> itemLabelGenerator)
    {
        this.itemLabelGenerator = itemLabelGenerator;
        getContent().setItemLabelGenerator(itemLabelGenerator);
    }

    public void setItemFactory(SerializableFunction<String, T> itemFactory)
    {
        this.itemFactory = itemFactory;
    }

    protected SerializableFunction<String, T> getItemFactory()
    {
        return itemFactory;
    }

    public void setPlaceholder(String placeholder)
    {
        getContent().setPlaceholder(placeholder);
    }
}
