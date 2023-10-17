package de.kkendzia.myintranet.microstream.ah;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMicrostreamDAO;
import org.springframework.stereotype.Component;

@Component
public class MSAhDAO extends AbstractMicrostreamDAO<Ah, Long> implements AhDAO
{
    public MSAhDAO()
    {
        super(MyIntranetRoot::getAhs);
    }
}
