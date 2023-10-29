package de.kkendzia.myintranet.microstream.shared;

import de.kkendzia.myintranet.domain.shared.adress.Adress;
import de.kkendzia.myintranet.domain.shared.adress.AdressDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MSAdressDAO extends AbstractMSDAO<Adress, Long> implements AdressDAO
{
    public MSAdressDAO()
    {
        super(MyIntranetRoot::getAdresses);
    }

    @Override
    public boolean exists(Adress adress)
    {
        return getRoot().getAdresses().stream().anyMatch(a ->
                Objects.equals(a.getLine1(), adress.getLine1())
                        && Objects.equals(a.getLine2(), adress.getLine2())
                        && Objects.equals(a.getStreet(), adress.getStreet())
                        && Objects.equals(a.getZip(), adress.getZip())
                        && Objects.equals(a.getCity(), adress.getCity())
                        && Objects.equals(a.getCountry(), adress.getCountry()));
    }
}
