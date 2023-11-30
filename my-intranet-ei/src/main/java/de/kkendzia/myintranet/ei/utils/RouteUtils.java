package de.kkendzia.myintranet.ei.utils;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import de.kkendzia.myintranet.ei._framework.view.search.SearchRoute;
import de.kkendzia.myintranet.ei.ui.layouts.main.EIMainLayoutPresenter.SearchTarget;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.List;

public final class RouteUtils
{
    private RouteUtils()
    {
        // No Instance!
    }

    private static List<HasElement> getCurrentTargetChain()
    {
        return UI.getCurrent().getInternals().getActiveRouterTargetsChain();
    }

    public static String getPageTitle()
    {
        // Get list of current targetChain, the first view is the top view.
        List<HasElement> targetChain = getCurrentTargetChain();
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

    public static boolean isCurrentSearchRoute()
    {
        List<HasElement> targetChain = getCurrentTargetChain();
        return isSearchRoute(targetChain);
    }

    public static boolean isSearchRoute(List<HasElement> targetChain)
    {
        if (!targetChain.isEmpty())
        {
            HasElement view = targetChain.get(0);

//            // If the view has a dynamic title we'll use that
//            if (view instanceof HasDynamicTitle dt)
//            {
//                pageTitle = dt.getPageTitle();
//            }
//            else
//            {
            // It does not have a dynamic title. Try to read title from
            // annotations
            SearchRoute a = AnnotationUtils.findAnnotation(view.getClass(), SearchRoute.class);
            return a != null;
//            }
        }

        return false;
    }

    public static SearchTarget getCurrentSearchTarget()
    {
        List<HasElement> targetChain = getCurrentTargetChain();
        return getSearchTarget(targetChain);
    }

    public static SearchTarget getSearchTarget(List<HasElement> targetChain)
    {
        if (!targetChain.isEmpty())
        {
            HasElement view = targetChain.get(0);

//            // If the view has a dynamic title we'll use that
//            if (view instanceof HasDynamicTitle dt)
//            {
//                pageTitle = dt.getPageTitle();
//            }
//            else
//            {
            // It does not have a dynamic title. Try to read title from
            // annotations
            SearchRoute a = AnnotationUtils.findAnnotation(view.getClass(), SearchRoute.class);
            return a != null ? a.target() : null;
//            }
        }

        return null;

    }
}
