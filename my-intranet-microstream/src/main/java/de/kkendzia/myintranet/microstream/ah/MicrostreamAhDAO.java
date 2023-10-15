package de.kkendzia.myintranet.microstream.ah;

import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.ah.AhDAO;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static de.kkendzia.myintranet.domain.utils.Reduce.toOnlyElement;

@Component
public class MicrostreamAhDAO implements AhDAO
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
}
