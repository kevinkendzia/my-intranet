package de.kkendzia.myintranet.domain.ah;

import de.kkendzia.myintranet.domain._core.ValueObject;
import de.kkendzia.myintranet.domain.country.Country;

public record AhAdress(
        String line1,
        String line2,
        String street,
        String zip,
        String city,
        Country country) implements ValueObject
{
}
