package de.kkendzia.myintranet.app.ah._shared;

import de.kkendzia.myintranet.app.mandant.queries.ListMandanten;
import de.kkendzia.myintranet.domain.ah.Ah;
import de.kkendzia.myintranet.domain.country.Country;
import de.kkendzia.myintranet.domain.mitgliedsform.MitgliedsForm;
import de.kkendzia.myintranet.domain.regulierer.Regulierer;
import de.kkendzia.myintranet.domain.verband.Verband;

import java.io.Serializable;
import java.time.LocalDate;

public record AhSheet(
        CoreSection coreSection,
        AdressSection adressSection,
        MembershipSection membershipSection) implements Serializable
{
    public static class CoreSection
    {
        private Ah.Ahnr ahnr;
        private String matchcode;
        private ListMandanten.MandantItem mandant;
        private LocalDate enterDate;
        private LocalDate exitDate;

        public CoreSection(
                Ah.Ahnr ahnr,
                String matchcode,
                ListMandanten.MandantItem mandant,
                LocalDate enterDate,
                LocalDate exitDate)
        {
            this.ahnr = ahnr;
            this.matchcode = matchcode;
            this.mandant = mandant;
            this.enterDate = enterDate;
            this.exitDate = exitDate;
        }

        public Ah.Ahnr getAhnr()
        {
            return ahnr;
        }

        public void setAhnr(Ah.Ahnr ahnr)
        {
            this.ahnr = ahnr;
        }

        public String getMatchcode()
        {
            return matchcode;
        }

        public void setMatchcode(String matchcode)
        {
            this.matchcode = matchcode;
        }

        public ListMandanten.MandantItem getMandant()
        {
            return mandant;
        }

        public void setMandant(ListMandanten.MandantItem mandant)
        {
            this.mandant = mandant;
        }

        public LocalDate getEnterDate()
        {
            return enterDate;
        }

        public void setEnterDate(LocalDate enterDate)
        {
            this.enterDate = enterDate;
        }

        public LocalDate getExitDate()
        {
            return exitDate;
        }

        public void setExitDate(final LocalDate exitDate)
        {
            this.exitDate = exitDate;
        }
    }

    public static class AdressSection
    {
        private String line1;
        private String line2;
        private String street;
        private String zip;
        private String city;
        private Country country;

        public AdressSection(String line1, String line2, String street, String zip, String city, Country country)
        {
            this.line1 = line1;
            this.line2 = line2;
            this.street = street;
            this.zip = zip;
            this.city = city;
            this.country = country;
        }

        public String getLine1()
        {
            return line1;
        }

        public void setLine1(String line1)
        {
            this.line1 = line1;
        }

        public String getLine2()
        {
            return line2;
        }

        public void setLine2(String line2)
        {
            this.line2 = line2;
        }

        public String getStreet()
        {
            return street;
        }

        public void setStreet(String street)
        {
            this.street = street;
        }

        public String getZip()
        {
            return zip;
        }

        public void setZip(String zip)
        {
            this.zip = zip;
        }

        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public Country getCountry()
        {
            return country;
        }

        public void setCountry(Country country)
        {
            this.country = country;
        }
    }

    public static class MembershipSection
    {
        private Regulierer regulator;
        private Verband association;
        private MitgliedsForm membershipForm;

        public MembershipSection(Regulierer regulator, Verband association, MitgliedsForm membershipForm)
        {
            this.regulator = regulator;
            this.association = association;
            this.membershipForm = membershipForm;
        }

        public Regulierer getRegulator()
        {
            return regulator;
        }

        public void setRegulator(Regulierer regulator)
        {
            this.regulator = regulator;
        }

        public Verband getAssociation()
        {
            return association;
        }

        public void setAssociation(Verband association)
        {
            this.association = association;
        }

        public MitgliedsForm getMembershipForm()
        {
            return membershipForm;
        }

        public void setMembershipForm(MitgliedsForm membershipForm)
        {
            this.membershipForm = membershipForm;
        }
    }
}
