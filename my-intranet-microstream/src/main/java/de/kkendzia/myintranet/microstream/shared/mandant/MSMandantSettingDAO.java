package de.kkendzia.myintranet.microstream.shared.mandant;

import de.kkendzia.myintranet.domain.mandant.MandantSetting;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

@Component
public class MSMandantSettingDAO extends AbstractMSDAO<MandantSetting, Long> implements MandantSettingDAO
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

    @Override
    public boolean exists(long mandantId, String name)
    {
        return getRoot().getMandantSettings()
                .stream()
                .anyMatch(x -> Objects.equals(x.getMandantId(), mandantId) && Objects.equals(x.getName(), name));
    }
}
