package de.kkendzia.myintranet.ei._framework.presenter;

import java.util.Optional;

public abstract class AbstractStatefullPresenter<T> implements EIPresenter
{
    private transient T state;

    public T getState()
    {
        return state;
    }

    public Optional<T> getOptionalState()
    {
        return Optional.ofNullable(state);
    }

    public void setState(final T state)
    {
        this.state = state;
    }
}
