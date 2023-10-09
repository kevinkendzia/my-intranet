package de.kkendzia.myintranet.app.data.ah;

import de.kkendzia.myintranet.app.data.AbstractDummyDAO;
import de.kkendzia.myintranet.domain.ah.Ah;
import org.springframework.stereotype.Repository;

@Repository
public class AhDummyDAOImpl
    extends AbstractDummyDAO<Ah>
        implements AhDAO
{
    protected AhDummyDAOImpl()
    {
        super(10);
    }

    @Override
    protected Ah createEntity(int i)
    {
        return new Ah(i, new Ah.Ahnr(i), "AH"+i);
    }
}
