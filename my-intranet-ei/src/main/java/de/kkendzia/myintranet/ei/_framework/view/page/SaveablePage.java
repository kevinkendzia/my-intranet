package de.kkendzia.myintranet.ei._framework.view.page;

import de.kkendzia.myintranet.app._framework.result.VoidResult;

import java.util.List;

public interface SaveablePage extends EIPage
{
    VoidResult<List<String>> validate();

    boolean hasChanges();

    void onSave();
}
