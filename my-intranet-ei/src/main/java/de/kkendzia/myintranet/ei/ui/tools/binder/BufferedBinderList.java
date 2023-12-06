package de.kkendzia.myintranet.ei.ui.tools.binder;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;
import de.kkendzia.myintranet.app._framework.result.VoidResult;

import java.io.Serializable;
import java.util.*;

import static com.vaadin.flow.function.ValueProvider.identity;
import static de.kkendzia.myintranet.app._framework.result.VoidResult.failure;
import static de.kkendzia.myintranet.app._framework.result.VoidResult.success;

public final class BufferedBinderList<B> implements Serializable
{
    private final Map<BufferedBinder<?>, ValueProvider<B, ?>> binderGettersMap = new HashMap<>();

    public <T> BufferedBinder<T> createBinder(
            BufferedBinder<T> binder,
            ValueProvider<B, T> getter)
    {
        binderGettersMap.put(binder, getter);
        return binder;
    }

    public <T> BufferedBinder<T> createBinder(ValueProvider<B, T> getter)
    {
        return createBinder(new BufferedBinder<>(), getter);
    }

    public BufferedBinder<B> createBinder()
    {
        return createBinder(new BufferedBinder<>(), identity());
    }

    public void readBeanAndCache(B bean)
    {
        getBinders().forEach((binder) -> readBeanAndCache(bean, binder));
    }

    @SuppressWarnings("unchecked")
    private <T> void readBeanAndCache(B bean, BufferedBinder<T> binder)
    {
        ValueProvider<B, T> getter = (ValueProvider<B, T>) binderGettersMap.get(binder);
        T sub = getter.apply(bean);
        binder.readBeanAndCache(sub);
    }

    public void writeBeanToCache() throws ValidationException
    {
        for (BufferedBinder<?> bufferedBinder : getBinders())
        {
            bufferedBinder.writeBeanToCache();
        }
    }

    public boolean writeBeanToCacheIfValid()
    {
        if (validateAll().isSuccess())
        {
            try
            {
                writeBeanToCache();
            }
            catch (ValidationException e)
            {
                throw new IllegalStateException("Validation succeeded but write NOT!", e);
            }
            return true;
        }
        return false;
    }

    public void resetBeans()
    {
        getBinders().forEach(BufferedBinder::resetBean);
    }

    public VoidResult<List<String>> validateAll()
    {
        final var errorMessages =
                getBinders()
                        .stream()
                        .map(Binder::validate)
                        .filter(BinderValidationStatus::hasErrors)
                        .map(BinderValidationStatus::getValidationErrors)
                        .flatMap(Collection::stream)
                        .map(ValidationResult::getErrorMessage)
                        .toList();

        return errorMessages.isEmpty() ? success() : failure(errorMessages);
    }

    public boolean hasChanges()
    {
        return getBinders().stream().anyMatch(Binder::hasChanges);
    }

    public Registration addValueChangeListener(HasValue.ValueChangeListener<? super HasValue.ValueChangeEvent<?>> listener)
    {
        List<Registration> registrations = getBinders()
                .stream()
                .map(f -> f.addValueChangeListener(listener))
                .toList();
        return Registration.combine(registrations.toArray(Registration[]::new));
    }

    private Set<BufferedBinder<?>> getBinders()
    {
        return binderGettersMap.keySet();
    }
}
