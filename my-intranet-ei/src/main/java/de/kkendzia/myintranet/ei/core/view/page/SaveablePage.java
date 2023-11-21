package de.kkendzia.myintranet.ei.core.view.page;

import com.vaadin.flow.data.binder.BinderValidationStatus;
import de.kkendzia.myintranet.domain.mandant.Mandant;

public interface SaveablePage extends EIPage
{
    BinderValidationStatus<Mandant> validate();

    boolean hasChanges();

    void onSave();
}
