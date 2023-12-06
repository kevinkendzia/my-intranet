package de.kkendzia.myintranet.ei.ui.tools.binder;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertySet;
import com.vaadin.flow.data.binder.ValidationException;

import static java.util.Objects.requireNonNull;

public class BufferedBinder<T> extends Binder<T>
{
    private transient T bean;

    public BufferedBinder(final PropertySet<T> propertySet)
    {
        super(propertySet);
    }

    public BufferedBinder(final Class<T> beanType)
    {
        super(beanType);
    }

    public BufferedBinder()
    {
    }

    public BufferedBinder(final Class<T> beanType, final boolean scanNestedDefinitions)
    {
        super(beanType, scanNestedDefinitions);
    }

    public void readBeanAndCache(T bean)
    {
        this.bean = requireNonNull(bean, "bean can't be null!");
        readBean(bean);
    }

    public void resetBean()
    {
        if (bean == null)
        {
            throw new IllegalStateException("No bean cached! Call readBeanAndCache() before!");
        }
        readBean(bean);
    }

    public boolean writeBeanToCacheIfValid()
    {
        if (bean == null)
        {
            throw new IllegalStateException("No bean cached! Call readBeanAndCache() before!");
        }

        return writeBeanIfValid(bean);
    }

    public void writeBeanToCache() throws ValidationException
    {
        if (bean == null)
        {
            throw new IllegalStateException("No bean cached! Call readBeanAndCache() before!");
        }

        writeBean(bean);
    }
}
