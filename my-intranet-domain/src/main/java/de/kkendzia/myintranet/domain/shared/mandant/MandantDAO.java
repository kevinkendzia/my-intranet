package de.kkendzia.myintranet.domain.shared.mandant;

import de.kkendzia.myintranet.domain._framework.dao.CRUDDAO;
import de.kkendzia.myintranet.domain._framework.dao.Paging;

import java.util.stream.Stream;

public interface MandantDAO extends CRUDDAO<Mandant, Long>
{
    long countByNameLike(String searchText);

    Stream<Mandant> findAllByNameLike(String text, Paging paging);
}
