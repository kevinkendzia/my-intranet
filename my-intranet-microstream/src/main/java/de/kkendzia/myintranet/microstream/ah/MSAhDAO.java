package de.kkendzia.myintranet.microstream.ah;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhRepository;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

@Component
public class MSAhDAO extends AbstractMSDAO<Ah, Long> implements AhRepository
{
    public MSAhDAO()
    {
        super(MyIntranetRoot::getAhs);
    }
}
