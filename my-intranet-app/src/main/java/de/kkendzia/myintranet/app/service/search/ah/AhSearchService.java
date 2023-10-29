package de.kkendzia.myintranet.app.service.search.ah;

import de.kkendzia.myintranet.domain.ah.Ah;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class AhSearchService
{
    public Stream<Ah> findAhs()
    {
        // TODO
        return Stream.empty();
    }
}
