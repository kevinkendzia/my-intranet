package de.kkendzia.myintranet.ei.ui.views.other.mandant.detail;

import de.kkendzia.myintranet.domain.shared.Mandant;
import de.kkendzia.myintranet.domain.shared.MandantDAO;
import de.kkendzia.myintranet.ei.core.annotations.Presenter;
import de.kkendzia.myintranet.ei.ui.errors.UnknownIDError.UnknownIDException;
import org.springframework.beans.factory.annotation.Autowired;

@Presenter
public class MandantDetailPresenter
{
    @Autowired
    private MandantDAO mandantDAO;

    public Mandant loadMandantById(long id)
    {
        return mandantDAO.finaOneById(id).orElseThrow(UnknownIDException::new);
    }

    public void save(Mandant mandant)
    {
        if (mandant.getId() <= 0)
        {
            mandantDAO.create(mandant);
        }
        else
        {
            mandantDAO.update(mandant);
        }
    }
}
