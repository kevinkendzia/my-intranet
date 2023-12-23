package de.kkendzia.components.imagepreview;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.data.selection.MultiSelectionEvent;
import com.vaadin.flow.data.selection.MultiSelectionListener;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;

@CssImport("./image-preview.css")
public class ImagePreview<T>
        extends AbstractCompositeField<Div, ImagePreview<T>, Set<T>>
        implements MultiSelect<ImagePreview<T>, T>, HasSize
{
    private final Image imgPreview = new Image();
    private final Div imageLayout = new Div();
    private final Div imageAdd = new Div();

    private final ResourceFactory<T> resourceFactory;

    public ImagePreview(ResourceFactory<T> resourceFactory)
    {
        super(new LinkedHashSet<>());
        this.resourceFactory = requireNonNull(resourceFactory, "resourceProvider can't be null!");

        imgPreview.addClassName("image-preview-image");

        imageAdd.addClassName("image-preview-add");
        imageAdd.add(VaadinIcon.PLUS_CIRCLE.create());
        imageAdd.addClickListener(e -> fireEvent(new AddImageEvent<>(this, e.isFromClient())));

        imageLayout.addClassName("image-preview-item-container");
        imageLayout.add(imageAdd);

        final var root = getContent();
        root.addClassName("image-preview");
        root.add(imageLayout);
        root.add(imgPreview);
    }

    @Override
    protected void setPresentationValue(final Set<T> newPresentationValue)
    {
        imageLayout.removeAll();
        imageLayout.add(imageAdd);

        if (newPresentationValue != null)
        {
            newPresentationValue.forEach(item ->
            {
                final var img = new SelectionImage<>(item, resourceFactory);
                img.addClickListener(e ->
                {
                    final var preview = e.getSource();
                    final var factory = preview.getResourceFactory();
                    imgPreview.setSrc(factory.create(preview.getItem()));
                });
                imageLayout.add(img);
            });

            newPresentationValue.stream().findFirst().ifPresent(i -> imgPreview.setSrc(resourceFactory.create(i)));
        }
    }

    @Override
    public Set<T> getValue()
    {
        return unmodifiableSet(super.getValue());
    }

    @Override
    public void setValue(Set<T> value)
    {
        super.setValue(value != null ? value : emptySet());
    }

//    @SafeVarargs
//    public final void setValue(T... items)
//    {
//        Set<T> value = new LinkedHashSet<>(List.of(items));
//        setValue(value);
//    }

    public void setValue(Collection<T> items)
    {
        Set<T> value = new LinkedHashSet<>(items);
        setValue(value);
    }

    @Override
    public void updateSelection(final Set<T> addedItems, final Set<T> removedItems)
    {
        final Set<T> selection = new LinkedHashSet<>(getValue());
        if (selection.containsAll(addedItems) && disjoint(selection, removedItems))
        {
            // no change
            return;
        }

        // Remove items
        removedItems.forEach(selection::remove);
        // Add items
        selection.addAll(addedItems);
        super.setValue(selection);
    }

    @Override
    public Set<T> getSelectedItems()
    {
        return getValue();
    }

    @Override
    public Registration addSelectionListener(final MultiSelectionListener<ImagePreview<T>, T> listener)
    {
        return addValueChangeListener(event -> listener
                .selectionChange(new MultiSelectionEvent<>(this, this,
                        event.getOldValue(), event.isFromClient())));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Registration addAddImageListener(final ComponentEventListener<AddImageEvent<T>> listener)
    {
        return addListener(AddImageEvent.class, (ComponentEventListener) listener);
    }

    public static class SelectionImage<T> extends Composite<Image> implements ClickNotifier<SelectionImage<T>>
    {
        private final transient T item;
        private final ResourceFactory<T> resourceFactory;

        public SelectionImage(final T item, ResourceFactory<T> resourceFactory)
        {
            this.item = requireNonNull(item, "value can't be null!");
            this.resourceFactory = requireNonNull(resourceFactory, "resourceProvider can't be null!");

            final var root = getContent();
            root.setSrc(resourceFactory.create(item));
        }

        public T getItem()
        {
            return item;
        }

        public ResourceFactory<T> getResourceFactory()
        {
            return resourceFactory;
        }
    }

    @FunctionalInterface
    public interface ResourceFactory<T> extends Serializable
    {
        StreamResource create(T item);
    }

    public static class AddImageEvent<T> extends ComponentEvent<ImagePreview<T>>
    {
        public AddImageEvent(final ImagePreview<T> source, final boolean fromClient)
        {
            super(source, fromClient);
        }
    }
}
