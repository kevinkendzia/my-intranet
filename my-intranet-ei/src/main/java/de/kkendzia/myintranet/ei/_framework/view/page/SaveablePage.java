package de.kkendzia.myintranet.ei._framework.view.page;

import com.vaadin.flow.data.binder.BinderValidationStatus;

public interface SaveablePage<T> extends EIPage
{
    BinderValidationStatus<T> validate();

    boolean hasChanges();

    void onSave();
}
