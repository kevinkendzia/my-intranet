package de.kkendzia.myintranet.ei.ui.components.fields;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ReadOnlyHasValue;
import com.vaadin.flow.function.SerializableSupplier;
import de.kkendzia.myintranet.ei.core.i18n.TranslationKeys;

import java.util.Map;

import static java.util.Map.entry;

public class TypedObjectField<T> extends CustomField<T>
{
    private Map<Class<?>, SerializableSupplier<HasValue<?, ?>>> fieldFactories =
            Map.ofEntries(
                    entry(Integer.class, this::createIntegerField),
                    entry(String.class, this::createTextField));
    private HasValue<?, T> activeField;

    public TypedObjectField(Class<T> valueClass)
    {
        super(null);

        if (fieldFactories.containsKey(valueClass))
        {
            //noinspection unchecked
            activeField = (HasValue<?, T>) fieldFactories.get(valueClass).get();
            add((Component) activeField);
        }
        else
        {
            Span span = new Span();
            activeField = new ReadOnlyHasValue<>(t -> span.setText(String.valueOf(t)));
            add(span);
        }
    }

    private TextField createTextField()
    {
        TextField txt = new TextField();
        txt.setPlaceholder(getTranslation(TranslationKeys.VALUE));
        return txt;
    }

    private IntegerField createIntegerField()
    {
        IntegerField txt = new IntegerField();
        txt.setPlaceholder(getTranslation(TranslationKeys.VALUE));
        return txt;
    }

    @Override
    protected T generateModelValue()
    {
        return activeField.getValue();
    }

    @Override
    protected void setPresentationValue(T newPresentationValue)
    {
        activeField.setValue(newPresentationValue);
    }
}
