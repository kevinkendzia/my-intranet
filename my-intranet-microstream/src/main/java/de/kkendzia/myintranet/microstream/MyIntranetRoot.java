package de.kkendzia.myintranet.microstream;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.shared.adress.Adress;
import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.domain.shared.mandant.MandantSetting;
import one.microstream.integrations.spring.boot.types.Storage;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Storage
public class MyIntranetRoot
{
    private boolean init;
    private long lastId;
    private List<Ah> ahs = new ArrayList<>();
    private List<Adress> adresses = new ArrayList<>();
    private List<Mandant> mandanten = new ArrayList<>();
    private List<MandantSetting> mandantSettings = new ArrayList<>();

    public boolean isInit()
    {
        return init;
    }

    public void setInit(boolean init)
    {
        this.init = init;
    }

    public List<Ah> getAhs()
    {
        return ahs;
    }

    public void setAhs(List<Ah> ahs)
    {
        this.ahs = requireNonNull(ahs, "ahs can't be null!");
    }

    public List<Adress> getAdresses()
    {
        return this.adresses;
    }

    public void setAdresses(List<Adress> adresses)
    {
        this.adresses = requireNonNull(adresses, "adresses can't be null!");
    }

    public List<Mandant> getMandanten()
    {
        return mandanten;
    }

    public void setMandanten(List<Mandant> mandanten)
    {
        this.mandanten = requireNonNull(mandanten, "mandanten can't be null!");
    }

    public long getLastId()
    {
        return this.lastId;
    }

    public void setLastId(long lastId)
    {
        this.lastId = lastId;
    }

    public List<MandantSetting> getMandantSettings()
    {
        return mandantSettings;
    }

    public void setMandantSettings(List<MandantSetting> mandantSettings)
    {
        this.mandantSettings = mandantSettings;
    }
}
