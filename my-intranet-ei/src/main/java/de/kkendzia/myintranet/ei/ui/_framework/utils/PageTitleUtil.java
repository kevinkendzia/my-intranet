package de.kkendzia.myintranet.ei.ui._framework.utils;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.List;

public final class PageTitleUtil
{
    private PageTitleUtil()
    {
        // No Instance!
    }

    public static String getPageTitle()
    {
        // Get list of current targetChain, the first view is the top view.
        List<HasElement> targetChain = UI.getCurrent().getInternals().getActiveRouterTargetsChain();
        return getPageTitle(targetChain);
    }

    public static String getPageTitle(List<HasElement> targetChain)
    {
        String pageTitle = null;
        if (!targetChain.isEmpty())
        {
            HasElement view = targetChain.get(0);

            // If the view has a dynamic title we'll use that
            if (view instanceof HasDynamicTitle dt)
            {
                pageTitle = dt.getPageTitle();
            }
            else
            {
                // It does not have a dynamic title. Try to read title from
                // annotations
                PageTitle pt = AnnotationUtils.findAnnotation(view.getClass(), PageTitle.class);
                pageTitle = pt != null ? pt.value() : null;
            }
        }

        return pageTitle;
    }
}
