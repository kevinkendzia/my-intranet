package de.kkendzia.myintranet.microstream.ah;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

@Component
public class MSAhDAO extends AbstractMSDAO<Ah, Long> implements AhDAO
{
    public MSAhDAO()
    {
        super(MyIntranetRoot::getAhs);
    }
}
