package de.kkendzia.myintranet.microstream.shared.mandant;

import de.kkendzia.myintranet.domain.shared.mandant.MandantSetting;
import de.kkendzia.myintranet.domain.shared.mandant.MandantSettingDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMicrostreamDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class MSMandantSettingDAO extends AbstractMicrostreamDAO<MandantSetting, Long> implements MandantSettingDAO
{
    public MSMandantSettingDAO()
    {
        super(MyIntranetRoot::getMandantSettings);
    }

    @Override
    public long countAllBy(long mandantId)
    {
        return findAllBy(mandantId).count();
    }

    @Override
    public Stream<MandantSetting> findAllBy(long mandantId)
    {
        return findAll().filter(x -> Objects.equals(x.getMandantId(), mandantId));
    }
}
