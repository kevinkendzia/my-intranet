package de.kkendzia.myintranet.ei.ui.components.fields;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.shared.Registration;

import java.util.*;

import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;

@SuppressWarnings({"rawtypes", "unchecked"})
public class HasObjectValue implements HasValue<HasValue.ValueChangeEvent<Object>, Object>
{
    private TypedObjectField delegate;
    private Map<ValueChangeListener<?>, Set<Registration>> listeners = new HashMap<>();

    private TypedObjectField requireDelegate()
    {
        return requireNonNull(delegate, "delegate can't be null!");
    }

    public <T> TypedObjectField<T> as(Class<T> valueType)
    {
        delegate = new TypedObjectField<>(valueType);
        listeners.forEach((listener, registrations) ->
        {
            Registration r2 = delegate.addValueChangeListener(listener);
            registrations.add(r2);
        });
        return delegate;
    }

    @Override
    public void setValue(Object value)
    {
        requireDelegate().setValue(value);
    }

    @Override
    public Object getValue()
    {
        return requireDelegate().getValue();
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super ValueChangeEvent<Object>> listener)
    {
        listeners.put(listener, new HashSet<>(Set.of(() -> listeners.remove(listener))));
        return () -> listeners.getOrDefault(listener, emptySet()).forEach(Registration::remove);
    }

    @Override
    public void setReadOnly(boolean readOnly)
    {
        requireDelegate().setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly()
    {
        return requireDelegate().isReadOnly();
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible)
    {
        requireDelegate().setRequiredIndicatorVisible(requiredIndicatorVisible);
    }

    @Override
    public boolean isRequiredIndicatorVisible()
    {
        return requireDelegate().isRequiredIndicatorVisible();
    }
}
