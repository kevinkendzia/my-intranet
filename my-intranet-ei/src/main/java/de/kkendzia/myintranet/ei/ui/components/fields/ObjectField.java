package de.kkendzia.myintranet.ei.ui.components.fields;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.customfield.CustomField;

// 21.10.2023 KK TODO: replace with converter?
public class ObjectField extends CustomField<Object>
{
    private HasValue<?, ?> delegate;

    public ObjectField()
    {
        super(null);
    }

    @Override
    protected Object generateModelValue()
    {
        return null;
    }

    @Override
    protected void setPresentationValue(Object newPresentationValue)
    {
    }

    public <T> TypedObjectField<T> as(Class<T> valueClass)
    {
        delegate = new TypedObjectField<>(valueClass);
        return (TypedObjectField<T>) delegate;
    }

}
