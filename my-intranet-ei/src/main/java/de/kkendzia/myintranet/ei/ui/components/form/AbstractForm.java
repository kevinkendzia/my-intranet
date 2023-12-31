package de.kkendzia.myintranet.ei.ui.components.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

public abstract class AbstractForm<B> extends Composite<VerticalLayout> implements ThemableLayout
{
    private final Binder<B> binder;
    private final FormLayout form;

    protected AbstractForm()
    {
        this(null, new Binder<>());
    }

    protected AbstractForm(Binder<B> binder)
    {
        this(null, binder);
    }

    protected AbstractForm(String label)
    {
        this(label, new Binder<>());
    }

    protected AbstractForm(String label, Binder<B> binder)
    {
        this.binder = binder;

        form = new FormLayout();
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("30em", 2));

        VerticalLayout root = getContent();
        root.addClassName("my-intranet-form");
        root.setPadding(false);

        if (label != null)
        {
            NativeLabel lbl = new NativeLabel(label);
            root.add(lbl);
        }
        root.add(form);
    }

    protected <C extends HasValueAndElement<?, T>, T, T2> Binder.Binding<B, T2> add(
            C field,
            int colSpan,
            BindFunction<B, T, T2> bindFunction)
    {
        form.add((Component) field, colSpan);
        return bindFunction.bind(field, binder);
    }

    protected <C extends HasValueAndElement<?, T>, T, T2> Binder.Binding<B, T2> add(
            C field,
            BindFunction<B, T, T2> bindFunction)
    {
        return add(field, -1, bindFunction);
    }

    @FunctionalInterface
    public interface BindFunction<B, T, T2>
    {
        Binder.Binding<B, T2> bind(HasValue<?, T> field, Binder<B> binder);
    }
}
