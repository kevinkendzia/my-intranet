package de.kkendzia.myintranet.microstream.shared;

import de.kkendzia.myintranet.domain.shared.adress.Adress;
import de.kkendzia.myintranet.domain.shared.adress.AdressDAO;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import org.springframework.stereotype.Component;

@Component
public class MSAdressDAO extends AbstractMSDAO<Adress, Long> implements AdressDAO
{
    public MSAdressDAO()
    {
        super(MyIntranetRoot::getAdresses);
    }
}
