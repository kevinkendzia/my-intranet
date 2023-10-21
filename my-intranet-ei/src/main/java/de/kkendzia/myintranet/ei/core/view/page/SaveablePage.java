package de.kkendzia.myintranet.ei.core.view.page;

public interface SaveablePage extends EIPage
{
    boolean validate();

    boolean hasChanges();
}
