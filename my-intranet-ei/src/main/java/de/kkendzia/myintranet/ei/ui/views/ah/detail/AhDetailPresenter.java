package de.kkendzia.myintranet.ei.ui.views.ah.detail;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhDAO;
import de.kkendzia.myintranet.ei.ui.errors.UnknownIDError.UnknownIDException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AhDetailPresenter
{
    @Autowired
    private AhDAO ahDAO;

    public Ah loadAhById(long id)
    {
        return ahDAO.finaOneById(id).orElseThrow(UnknownIDException::new);
    }

    public void save()
    {

    }
}
