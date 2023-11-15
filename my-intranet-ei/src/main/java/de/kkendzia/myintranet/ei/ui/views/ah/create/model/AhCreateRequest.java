package de.kkendzia.myintranet.ei.ui.views.ah.create.model;

import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhAdressData;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhCoreData;
import de.kkendzia.myintranet.ei.ui.views.ah._shared.model.AhMemberData;

import java.io.Serializable;

public record AhCreateRequest(
        AhCoreData coreData,
        AhAdressData adressData,
        AhMemberData memberData) implements Serializable
{

}
