package de.kkendzia.myintranet.microstream.shared.mandant;

import de.kkendzia.myintranet.domain._framework.dao.Paging;
import de.kkendzia.myintranet.domain.mandant.Mandant;
import de.kkendzia.myintranet.domain.mandant.MandantRepository;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import de.kkendzia.myintranet.microstream._framework.AbstractMSDAO;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class MSMandantDAO extends AbstractMSDAO<Mandant, Long> implements MandantRepository
{
    public MSMandantDAO()
    {
        super(MyIntranetRoot::getMandanten);
    }

    @Override
    public long countByNameLike(String searchText)
    {
        return getRoot().getMandanten()
                .stream()
                .filter(m -> isContains(searchText, m))
                .count();
    }

    @Override
    public Stream<Mandant> findAllByNameLike(String searchText, Paging paging)
    {
        return getRoot().getMandanten()
                .stream()
                .filter(m -> isContains(searchText, m))
                .skip(paging.offset())
                .limit(paging.limit());
    }

    private static boolean isContains(String searchText, Mandant m)
    {
        return m.getName().toLowerCase().contains(searchText.toLowerCase());
    }
}
