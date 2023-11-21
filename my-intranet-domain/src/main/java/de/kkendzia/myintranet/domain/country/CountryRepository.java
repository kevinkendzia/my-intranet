package de.kkendzia.myintranet.domain.country;

import de.kkendzia.myintranet.domain._core.repository.AggregateLookup;
import de.kkendzia.myintranet.domain._core.repository.AssociationResolver;

public interface CountryRepository extends AggregateLookup<Country, Country.CountryID>, AssociationResolver<Country, Country.CountryID>
{
}
