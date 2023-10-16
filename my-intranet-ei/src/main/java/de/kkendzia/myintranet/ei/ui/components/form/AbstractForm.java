package de.kkendzia.myintranet.ei.ui.components.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

public abstract class AbstractForm<T> extends Composite<VerticalLayout> implements ThemableLayout
{
    private Binder<T> binder = new Binder<>();
    private T bean;

    protected AbstractForm(String label)
    {
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("10em", 2));

        initForm(form, binder);

        VerticalLayout root = getContent();
        if(label != null)
        {
            NativeLabel lbl = new NativeLabel(label);
            root.add(lbl);
        }
        root.add(form);
    }

    protected abstract void initForm(FormLayout form, Binder<T> binder);

    public void setBean(T bean)
    {
        this.bean = bean;
        binder.readBean(bean);
    }

    public T getBean()
    {
        binder.writeBeanIfValid(bean);
        return bean;
    }
    public T getChanges()
    {
        if(binder.hasChanges())
        {
            return getBean();
        }
        return null;
    }

    public void clear()
    {
        binder.readBean(bean);
    }
}
