package de.kkendzia.myintranet.ei.ui.components.form;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FormBinder<B>
{
    private B bean;
    private final List<AbstractForm<?>> forms = new ArrayList<>();
    private final Map<AbstractForm<?>, ValueProvider<B, ?>> getters = new HashMap<>();

    public <T> void bind(
            AbstractForm<T> form,
            ValueProvider<B, T> getter)
    {
        forms.add(form);
        getters.put(form, getter);
    }

    public void setBean(B bean)
    {
        this.bean = bean;
        readBean(bean);
    }

    private void readBean(B bean)
    {
        forms.forEach(f -> setBeanIntoForm(bean, f));
    }

    private <T> void setBeanIntoForm(B bean, AbstractForm<T> f)
    {
        //noinspection unchecked
        ValueProvider<B, T> getter = (ValueProvider<B, T>) getters.get(f);
        T sub = getter.apply(bean);
        f.setBean(sub);
    }

    public B getBean()
    {
        writeBean();
        return bean;
    }

    public void writeBean()
    {
        forms.forEach(AbstractForm::writeBean);
    }
    public boolean writeBeanIfValid()
    {
        if(validate())
        {
            writeBean();
            return true;
        }
        return false;
    }

    public void clear()
    {
        readBean(bean);
    }

    public boolean validate()
    {
        return forms.stream().allMatch(f -> f.validate().isOk());
    }

    public boolean hasChanges()
    {
        return forms.stream().anyMatch(AbstractForm::hasChanges);
    }

    public Registration addValueChangeListener(HasValue.ValueChangeListener<? super HasValue.ValueChangeEvent<?>> listener)
    {
        List<Registration> registrations = forms.stream().map(f -> f.addValueChangeListener(listener)).toList();
        return Registration.combine(registrations.toArray(Registration[]::new));
    }
}
