package de.kkendzia.myintranet.domain.ah;

public record Ah(long id, de.kkendzia.myintranet.domain.ah.Ah.Ahnr ahnr, String matchcode)
{
    // record

    /*
     * TYPES
     */

    public record Ahnr(int value)
    {
        // record
    }
}
