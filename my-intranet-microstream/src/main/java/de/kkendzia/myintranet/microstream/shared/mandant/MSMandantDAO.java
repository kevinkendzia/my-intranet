package de.kkendzia.myintranet.microstream.shared.mandant;

import de.kkendzia.myintranet.domain.shared.mandant.Mandant;
import de.kkendzia.myintranet.domain.shared.mandant.MandantDAO;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import org.springframework.stereotype.Component;

@Component
public class MSMandantDAO extends AbstractMSDAO<Mandant, Long> implements MandantDAO
{
    public MSMandantDAO()
    {
        super(MyIntranetRoot::getMandanten);
    }
}
