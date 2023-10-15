package de.kkendzia.myintranet.microstream.shared;

import de.kkendzia.myintranet.domain.shared.Adress;
import de.kkendzia.myintranet.domain.shared.AdressDAO;
import de.kkendzia.myintranet.microstream.AbstractMicrostreamDAO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static de.kkendzia.myintranet.domain.utils.Reduce.toOnlyElement;

@Component
public class MSAdressDAO extends AbstractMicrostreamDAO<Adress, Long> implements AdressDAO
{
    @Override
    public List<Adress> findAll()
    {
        return getRoot().getAdresses();
    }

    @Override
    public Optional<Adress> finaOneById(Long id)
    {
        return getRoot().getAdresses().stream().filter(a -> a.getId() == id).reduce(toOnlyElement());
    }

    @Override
    public void update(Adress entity)
    {
        getRoot().getAdresses().replaceAll((x) -> Objects.equals(x, entity) ? entity : x);
        getManager().store(getRoot().getAdresses());
    }

    @Override
    public void create(Adress entity)
    {
        getRoot().getAdresses().add(entity);
        getManager().store(getRoot().getAdresses());
        getManager().store(entity);
    }
}
