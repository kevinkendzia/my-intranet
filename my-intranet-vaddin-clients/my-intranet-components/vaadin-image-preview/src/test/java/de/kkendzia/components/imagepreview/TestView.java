package de.kkendzia.components.imagepreview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.util.Set;

@Route("")
public class TestView extends VerticalLayout
{

    public TestView()
    {
        ImagePreview<Item> preview = new ImagePreview<>(item -> new StreamResource(
                item.name(),
                () -> TestView.class.getClassLoader().getResourceAsStream(item.path())));
        preview.setId("image-preview");
        preview.setWidth("75%");
        preview.setHeight("50%");
        preview.setValue(Set.of(
                new Item("test1", "images/dragonballs_16_9.jpg"),
                new Item("test2", "images/bleach_hollow_mask_FHD.jpg"),
                new Item("test3", "images/kame_house_21_9.jpg"),
                new Item("test3", "images/konoha-sasuke2.png")));

        /*
         * ROOT
         */

        setWidth("100%");
        setHeight("100%");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(preview);
    }

    public record Item(
            String name,
            String path)
    {
        // just a record
    }
}
