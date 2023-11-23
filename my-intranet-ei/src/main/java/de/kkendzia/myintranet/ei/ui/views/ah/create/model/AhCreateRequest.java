package de.kkendzia.myintranet.ei.ui.views.ah.create.model;

import de.kkendzia.myintranet.app.ah.commands.AhAdressData;
import de.kkendzia.myintranet.app.ah.commands.AhCoreData;
import de.kkendzia.myintranet.app.ah.commands.AhMemberData;

import java.io.Serializable;

public record AhCreateRequest(
        AhCoreData coreData,
        AhAdressData adressData,
        AhMemberData memberData) implements Serializable
{

}
