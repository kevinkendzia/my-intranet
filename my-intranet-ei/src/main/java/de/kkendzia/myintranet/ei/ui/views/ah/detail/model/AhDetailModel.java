package de.kkendzia.myintranet.ei.ui.views.ah.detail.model;

import de.kkendzia.myintranet.app.ah._shared.AhSheet;

import java.io.Serializable;

public record AhDetailModel(
        AhSheet.CoreSection coreData,
        AhSheet.AdressSection adressData,
        AhSheet.MembershipSection memberData) implements Serializable
{

}
