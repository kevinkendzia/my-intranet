package de.kkendzia.myintranet.ei.ui.components.text;

import com.vaadin.flow.component.html.H3;

public class StickyH3 extends H3
{
//    private ScrollSnapAlign scrollSnapAlign;

//    public StickyH3(
//            String text,
//            ScrollSnapAlign scrollSnapAlign)
//    {
//        super(text);
//        addClassName("sticky-h3");
//        setScrollSnapAlign(scrollSnapAlign);
//    }

    public StickyH3(String text)
    {
        super(text);
        addClassName("sticky-h3");
    }

//    public void setScrollSnapAlign(ScrollSnapAlign scrollSnapAlign)
//    {
//        if (scrollSnapAlign != null)
//        {
//            addClassName(scrollSnapAlign.getValue());
//        }
//        else if (this.scrollSnapAlign != null)
//        {
//            removeClassName(this.scrollSnapAlign.getValue());
//        }
//
//        this.scrollSnapAlign = scrollSnapAlign;
//    }
//
//    public enum ScrollSnapAlign
//    {
//        START(EIStyles.SCROLL_SNAP_ALIGN_START);
//
//        private final String value;
//
//        ScrollSnapAlign(String value)
//        {
//            this.value = value;
//        }
//
//        public String getValue()
//        {
//            return value;
//        }
//    }
}
