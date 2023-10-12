package de.kkendzia.myintranet.ei.ui.components.search;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializablePredicate;

import java.util.List;

public class SearchField<T> extends AbstractCompositeField<ComboBox<T>, SearchField<T>, T>
{
    private SerializablePredicate<T> enabledPredicate = itm -> true;
    private ItemLabelGenerator<T> titleGenerator = String::valueOf;

    public SearchField()
    {
        super(null);
        ComboBox<T> cbo = getContent();
        cbo.setRenderer(new ComponentRenderer<>(this::createItemComponent));
        cbo.addValueChangeListener(e ->
        {
            if (e.isFromClient())
            {
                T item = e.getValue();
                if (getEnabledPredicate().test(item))
                {
                    setModelValue(e.getValue(), true);
                }
                resetComboBox(e);
            }
        });
    }


    private Span createItemComponent(T itm)
    {
        Span span = new Span(titleGenerator.apply(itm));

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

    public void setTitleGenerator(ItemLabelGenerator<T> titleGenerator)
    {
        this.titleGenerator = titleGenerator;
    }

    public void setPlaceholder(String placeholder)
    {
        getContent().setPlaceholder(placeholder);
    }

    //region STATIC
    private static <T> void resetComboBox(AbstractField.ComponentValueChangeEvent<ComboBox<T>, T> e)
    {
        ComboBox<T> cb = e.getSource();
        if (e.getOldValue() != null)
        {
            cb.setValue(e.getOldValue());
        }
        else
        {
            cb.clear();
        }
    }
    //endregion
}
