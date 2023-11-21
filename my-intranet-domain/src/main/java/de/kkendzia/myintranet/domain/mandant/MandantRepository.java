package de.kkendzia.myintranet.domain.mandant;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;
import de.kkendzia.myintranet.domain._framework.dao.Paging;

import java.util.stream.Stream;

public interface MandantRepository
        extends AggregateLookup<Mandant, Mandant.MandantID>, AssociationResolver<Mandant, Mandant.MandantID>
{
    long countByNameLike(String searchText);

    Stream<Mandant> findAllByNameLike(String text, Paging paging);
}
