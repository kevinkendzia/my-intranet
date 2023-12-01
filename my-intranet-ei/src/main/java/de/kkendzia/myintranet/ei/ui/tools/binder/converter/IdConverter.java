package de.kkendzia.myintranet.ei.ui.tools.binder.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.function.ValueProvider;

import java.util.Collection;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class IdConverter<T, I> implements Converter<T, I>
{
    private final ValueProvider<T, I> idGetter;
    private final SerializableSupplier<Collection<T>> itemSupplier;

    public IdConverter(ValueProvider<T, I> idGetter, SerializableSupplier<Collection<T>> itemSupplier)
    {
        this.idGetter = requireNonNull(idGetter, "idGetter can't be null!");
        this.itemSupplier = requireNonNull(itemSupplier, "itemSupplier can't be null!");
    }

    @Override
    public Result<I> convertToModel(T value, ValueContext context)
    {
        if (value == null)
        {
            return Result.ok(null);
        }
        return Result.ok(idGetter.apply(value));
    }

    @Override
    public T convertToPresentation(I value, ValueContext context)
    {
        if (value == null)
        {
            return null;
        }
        return itemSupplier.get()
                .stream()
                .filter(z -> Objects.equals(idGetter.apply(z), value))
                .findFirst()
                .orElse(null);
    }
}
