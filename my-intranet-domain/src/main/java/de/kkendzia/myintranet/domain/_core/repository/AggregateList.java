package de.kkendzia.myintranet.domain._core.repository;

import de.kkendzia.myintranet.domain._core.AggregateRoot;

import java.util.List;
import java.util.stream.Stream;

public interface AggregateList<A extends AggregateRoot<A, ?>>
{
    long countAll();

    List<A> listAll();

    Stream<A> list(int offset, int limit);
}
