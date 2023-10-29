package de.kkendzia.myintranet.ei.core.i18n;

public final class TranslationKeys
{
    //region AH
    public static final String AHNR = "label.ahnr";
    public static final String AHS = "label.ahs";
    public static final String AH = "label.ah";
    public static final String MITGLIEDSFORM = "label.mitgliedsdaten";
    public static final String VERBAND = "label.verband";
    //endregion

    //region VL
    public static final String VLNR = "label.vlnr";
    public static final String VLS = "label.vls";
    public static final String VL = "label.vl";
    //endregion

    //region AKTION
    public static final String AKTIONSNUMMER = "label.aktionsnummer";
    public static final String AKTIONEN = "label.aktionen";
    public static final String AKTION = "label.aktion";
    //endregion

    //region ADRESS
    public static final String ADRESS = "label.adress";
    public static final String ADRESS_LINE1 = "label.adressLine1";
    public static final String ADRESS_LINE2 = "label.adressLine2";
    public static final String STREET = "label.street";
    public static final String ZIP = "label.zip";
    public static final String CITY = "label.city";
    public static final String COUNTRY = "label.country";
    //endregion


    //region OTHER
    public static final String OTHER = "label.other";
    public static final String MANDANT = "label.mandant";
    public static final String MANDANTEN = "label.mandanten";
    public static final String MATCHCODE = "label.matchcode";
    public static final String ENTER_DATE = "label.enterDate";
    public static final String EXIT_DATE = "label.exitDate";
    public static final String REGULIERER = "label.regulierer";
    public static final String COMMON = "label.common";
    public static final String CREATE = "label.create";
    public static final String ID = "label.id";
    public static final String NAME = "label.name";
    public static final String IMAGE = "label.image";
    public static final String SAVE = "label.save";
    public static final String KEY = "label.key";
    public static final String DETAILS = "label.details";
    public static final String SETTINGS = "label.settings";
    public static final String ADD = "label.add";
    public static final String TYPE = "label.type";
    public static final String VALUE = "label.value";
    public static final String SUCCESS = "label.success";
    public static final String FILTER = "label.filter";
    public static final String CANCEL = "label.cancel";
    public static final String EDIT = "label.edit";
    public static final String SUBMIT = "label.submit";
    public static final String ACTIONS = "label.actions";
    public static final String DELETE = "label.delete";
    public static final String LOGOUT = "label.logout";
    public static final String APP_TITLE = "label.appTitle";
    public static final String START = "label.start";
    public static final String SEARCH = "label.search";
    public static final String USERS = "label.users";
    public static final String ROLES = "label.roles";
    public static final String PERMISSIONS = "label.permissions";
    public static final String ADMIN = "label.admin";
    public static final String MITGLIEDSDATEN = "label.mitgliedsdaten";
    //endregion

    private TranslationKeys()
    {
        // No Instance!
    }

    public static class Notification
    {
        public static class Error
        {
            public static final String ERROR = "label.error";

            public static class Message
            {
                public static final String NO_CHANGES = "error.msg.noChanges";
                public static final String VALIDATION_ERROR = "error.msg.validationError";
            }
        }
    }

    public static final class Validation
    {
        public static final String REQUIRED = "validation.required";

        private Validation()
        {
            // No Instance!
        }
    }
}
