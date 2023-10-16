package de.kkendzia.myintranet.microstream.shared;

import de.kkendzia.myintranet.domain.shared.Mandant;
import de.kkendzia.myintranet.domain.shared.MandantDAO;
import de.kkendzia.myintranet.microstream.AbstractMicrostreamDAO;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.domain.utils.Reduce.toOnlyElement;

@Component
public class MSMandantDAO extends AbstractMicrostreamDAO<Mandant, Long> implements MandantDAO
{
    @Override
    public Stream<Mandant> findAll()
    {
        return getRoot().getMandanten().stream();
    }

    @Override
    public Optional<Mandant> finaOneById(Long id)
    {
        return getRoot().getMandanten().stream().filter(a -> a.getId() == id).reduce(toOnlyElement());
    }

    @Override
    public void update(Mandant entity)
    {
        getRoot().getMandanten().replaceAll((x) -> Objects.equals(x, entity) ? entity : x);
        getManager().store(getRoot().getAdresses());
    }

    @Override
    public void create(Mandant entity)
    {
        getRoot().getMandanten().add(entity);
        getManager().store(getRoot().getMandanten());
        getManager().store(entity);
    }
}
