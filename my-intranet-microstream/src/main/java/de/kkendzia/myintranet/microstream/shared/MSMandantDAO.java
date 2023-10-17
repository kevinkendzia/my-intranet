package de.kkendzia.myintranet.microstream.shared;

import de.kkendzia.myintranet.domain.shared.Mandant;
import de.kkendzia.myintranet.domain.shared.MandantDAO;
import de.kkendzia.myintranet.microstream._framework.AbstractMicrostreamDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import org.springframework.stereotype.Component;

@Component
public class MSMandantDAO extends AbstractMicrostreamDAO<Mandant, Long> implements MandantDAO
{
    public MSMandantDAO()
    {
        super(MyIntranetRoot::getMandanten);
    }
}
