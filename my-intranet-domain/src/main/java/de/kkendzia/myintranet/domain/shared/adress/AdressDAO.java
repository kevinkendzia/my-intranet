package de.kkendzia.myintranet.domain.shared.adress;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;

public interface AdressDAO extends CRUDDAO<Adress, Long>
{
    boolean exists(Adress adress);
}
