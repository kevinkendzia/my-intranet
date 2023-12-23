package de.kkendzia.myintranet.ei.ui.components.files.upload;

import com.vaadin.flow.component.AbstractCompositeField;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ImageUpload extends AbstractCompositeField<VerticalLayout, ImageUpload, byte[]>
{
    private final Image image = new Image();
    private final MemoryBuffer receiver = new MemoryBuffer();

    public ImageUpload()
    {
        super(null);

        image.setHeight("15em");
        image.addClassName(LumoUtility.Border.ALL);
        image.addClassName(LumoUtility.BorderColor.CONTRAST_10);
        image.getStyle().set("object-fit", "contain");

        Upload upload = new Upload(receiver);
        upload.addSucceededListener(e ->
        {
            try
            {
                byte[] bytes = receiver.getInputStream().readAllBytes();
                setModelValue(bytes, e.isFromClient());
                image.setSrc(new StreamResource("uploaded", () -> new ByteArrayInputStream(bytes)));
                Notification.show("Successfully loaded file bytes!");
            }
            catch (IOException ex)
            {
                // TODO
//                throw new RuntimeException(ex);
//                image.setSrc((String) null);
                Notification.show("Error loading file bytes!");
            }
        });

        upload.addFailedListener(e -> Notification.show("Error uploading file!"));

        VerticalLayout root = getContent();
        root.setAlignItems(FlexComponent.Alignment.STRETCH);
        root.setPadding(false);
        root.add(image);
        root.add(upload);
    }

    @Override
    protected void setPresentationValue(byte[] newPresentationValue)
    {
        image.setSrc(new StreamResource("loaded", () -> new ByteArrayInputStream(newPresentationValue)));
    }
}
