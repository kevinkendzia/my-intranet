package de.kkendzia.myintranet.ei.core.mixins;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.dom.ClassList;
import de.kkendzia.myintranet.ei.core.constants.EIStyles;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static de.kkendzia.myintranet.domain.utils.Reduce.toOnlyElement;

public interface HasScrollSnapAlign extends HasElement
{
    default Optional<ScrollSnapAlign> getOptionalScrollSnapAlign()
    {
        return getElement().getClassList()
                .stream()
                .filter(ScrollSnapAlign::exists)
                .reduce(toOnlyElement())
                .flatMap(ScrollSnapAlign::of);
    }
    default ScrollSnapAlign getScrollSnapAlign()
    {
        return getOptionalScrollSnapAlign().orElse(null);
    }

    default void setScrollSnapAlign(ScrollSnapAlign newScrollSnapAlign)
    {
        ScrollSnapAlign currentScrollSnapAlign = getScrollSnapAlign();
        if(Objects.equals(newScrollSnapAlign, currentScrollSnapAlign))
        {
            return;
        }

        ClassList classes = getElement().getClassList();

        if(currentScrollSnapAlign != null)
        {
            classes.remove(currentScrollSnapAlign.getValue());
        }

        if (newScrollSnapAlign != null)
        {
            classes.add(newScrollSnapAlign.getValue());
        }
    }

    enum ScrollSnapAlign
    {
        NONE(null),
        START(EIStyles.SCROLL_SNAP_ALIGN_START);

        private final String value;

        ScrollSnapAlign(String value)
        {
            this.value = value;
        }

        public static boolean exists(String value)
        {
            return Stream.of(values()).anyMatch(x -> Objects.equals(x.getValue(), value));
        }

        public static Optional<ScrollSnapAlign> of(String value)
        {
            return Stream.of(values()).filter(x -> Objects.equals(x.getValue(), value)).reduce(toOnlyElement());
        }

        public String getValue()
        {
            return value;
        }
    }
}
