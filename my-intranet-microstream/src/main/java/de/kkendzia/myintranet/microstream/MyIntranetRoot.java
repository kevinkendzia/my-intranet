package de.kkendzia.myintranet.microstream;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.shared.Adress;
import one.microstream.integrations.spring.boot.types.Storage;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Storage
public class MyIntranetRoot
{
    private List<Ah> ahs = new ArrayList<>();
    private List<Adress> adresses = new ArrayList<>();

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
}
