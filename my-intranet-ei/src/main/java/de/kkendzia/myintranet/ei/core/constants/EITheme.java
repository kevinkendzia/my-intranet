package de.kkendzia.myintranet.ei.core.constants;

import static java.util.Objects.requireNonNull;

public class EITheme
{
    private static final String DIR = "themes";
    //    public static final String NAME = "my-intranet-ei";
    public static final String NAME = "ei-red";

    private EITheme()
    {
        // No Instance!
    }

    /*
     * TYPES
     */

    public static class Image
    {
        private static final String DIR = "img";
        public static final Image IMG_HOME = new Image("home.svg");

        private final String name;

        private Image(String name)
        {
            this.name = requireNonNull(name, "name can't be null!");
        }

        public String getName()
        {
            return name;
        }

        public String getPath()
        {
            return String.join("/", EITheme.DIR, EITheme.NAME, Image.DIR, name);
        }
    }
}
