package de.kkendzia.myintranet.ei.ui.views.ah.detail.model;

import de.kkendzia.myintranet.app.ah._shared.AhSheet.AdressSection;
import de.kkendzia.myintranet.app.ah._shared.AhSheet.CoreSection;
import de.kkendzia.myintranet.app.ah._shared.AhSheet.MembershipSection;

import java.io.Serializable;

public record AhDetailModel(
        CoreSection coreData,
        AdressSection adressData,
        MembershipSection membershipData) implements Serializable
{

}
