package de.kkendzia.myintranet.ei.ui.views.ah.create.model;

import de.kkendzia.myintranet.ei.ui.views.ah.create.content.AhAdressDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah.create.content.AhCoreDataForm;
import de.kkendzia.myintranet.ei.ui.views.ah.create.content.AhMemberDataForm.AhMemberData;

import java.io.Serializable;

public record AhCreateRequest(
        AhCoreDataForm.AhCoreData coreData,
        AhAdressDataForm.AhAdressData adressData,
        AhMemberData memberData) implements Serializable
{

}
