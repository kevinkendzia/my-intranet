package de.kkendzia.myintranet.domain.shared.mandant;

import de.kkendzia.myintranet.domain._framework.CRUDDAO;

import java.util.stream.Stream;

public interface MandantSettingDAO extends CRUDDAO<MandantSetting, Long>
{
    long countAllBy(long mandantId);
    Stream<MandantSetting> findAllBy(long mandantId);

    boolean exists(long mandantId, String name);
}
