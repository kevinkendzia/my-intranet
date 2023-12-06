package de.kkendzia.myintranet.ei.ui.components.files.images;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.data.selection.MultiSelectionEvent;
import com.vaadin.flow.data.selection.MultiSelectionListener;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap.WRAP;
import static com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap.WRAP_REVERSE;
import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;

public class ImagePreview<T>
        extends AbstractCompositeField<FlexLayout, ImagePreview<T>, Set<T>>
        implements MultiSelect<ImagePreview<T>, T>, HasSize
{
    private final Image image = new Image();
    private final FlexLayout imageLine = new FlexLayout();
    private final VerticalLayout imageAdd = new VerticalLayout();

    private final ResourceFactory<T> resourceFactory;

    public ImagePreview(ResourceFactory<T> resourceFactory)
    {
        super(new LinkedHashSet<>());
        this.resourceFactory = requireNonNull(resourceFactory, "resourceProvider can't be null!");

        image.getStyle().set("flex", "1 1 10em");

        imageAdd.setWidth("5em");
        imageAdd.setHeight("5em");
        imageAdd.setAlignItems(Alignment.CENTER);
        imageAdd.setJustifyContentMode(JustifyContentMode.CENTER);
        imageAdd.add(VaadinIcon.PLUS_CIRCLE.create());
        imageAdd.addClickListener(e -> fireEvent(new AddImageEvent<>(this, e.isFromClient())));

        imageLine.getStyle().set("flex", "1 1 10em");
        imageLine.addClassName(Overflow.AUTO);
        imageLine.setFlexWrap(WRAP);
        imageLine.setAlignItems(Alignment.STRETCH);
        imageLine.add(imageAdd);

        final var root = getContent();
        root.setFlexWrap(WRAP_REVERSE);
        root.setAlignItems(Alignment.STRETCH);
        root.add(imageLine);
        root.add(image);
    }

    @Override
    protected void setPresentationValue(final Set<T> newPresentationValue)
    {
        imageLine.removeAll();
        imageLine.add(imageAdd);

        if (newPresentationValue != null)
        {
            newPresentationValue.forEach(item ->
            {
                final var img = new SelectionImage<>(item, resourceFactory);
                img.addClickListener(e ->
                {
                    final var preview = e.getSource();
                    final var factory = preview.getResourceFactory();
                    image.setSrc(factory.create(preview.getItem()));
                });
                imageLine.add(img);
            });
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
