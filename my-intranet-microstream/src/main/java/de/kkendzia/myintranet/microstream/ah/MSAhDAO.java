package de.kkendzia.myintranet.microstream.ah;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhDAO;
import de.kkendzia.myintranet.microstream.AbstractMicrostreamDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static de.kkendzia.myintranet.domain.utils.Reduce.toOnlyElement;

@Component
public class MSAhDAO extends AbstractMicrostreamDAO<Ah, Long> implements AhDAO
{
    @Autowired
    private MyIntranetRoot root;

    @Override
    public List<Ah> findAll()
    {
        return root.getAhs();
    }

    @Override
    public Optional<Ah> finaOneById(Long id)
    {
        return root
                .getAhs()
                .stream()
                .filter(a -> a.getId() == id)
                .reduce(toOnlyElement());
    }

    @Override
    public void update(Ah entity)
    {
        getRoot().getAhs().replaceAll(x -> Objects.equals(x, entity) ? entity : x);
        getManager().store(getRoot().getAhs());
    }

    @Override
    public void create(Ah entity)
    {
        getRoot().getAhs().add(entity);
        getManager().store(getRoot().getAhs());
        getManager().store(entity);
    }
}
