package de.kkendzia.components.imagepreview;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
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
import java.util.*;

import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;

@CssImport("./image-preview.css")
public class ImagePreview<T>
        extends AbstractCompositeField<Div, ImagePreview<T>, Set<T>>
        implements MultiSelect<ImagePreview<T>, T>, HasSize
{
    private final Div mainImageLayout = new Div();
    private final Image imgMain = new Image();
    private final Button btnPrevious = new Button(VaadinIcon.CHEVRON_CIRCLE_LEFT_O.create());
    private final Button btnNext = new Button(VaadinIcon.CHEVRON_CIRCLE_RIGHT_O.create());

    private final Div itemLayout = new Div();
    private final Button btnAdd = new Button(VaadinIcon.PLUS_CIRCLE.create());

    private final ResourceFactory<T> resourceFactory;

    // STATE
    private SelectionImage<T> selectedImage;
    private final List<SelectionImage<T>> selectionImages = new ArrayList<>();

    public ImagePreview(ResourceFactory<T> resourceFactory)
    {
        super(new LinkedHashSet<>());
        this.resourceFactory = requireNonNull(resourceFactory, "resourceProvider can't be null!");

        imgMain.addClassName("main-image");
        imgMain.add(btnPrevious);
        imgMain.add(btnNext);

        btnPrevious.addClassName("previous-button");
        btnPrevious.addClickListener(e -> previousImage());
        btnNext.addClassName("next-button");
        btnNext.addClickListener(e -> nextImage());

        mainImageLayout.addClassName("main-image-layout");
        mainImageLayout.add(btnPrevious);
        mainImageLayout.add(imgMain);
        mainImageLayout.add(btnNext);

        btnAdd.addClassName("add-button");
        btnAdd.addClickListener(e -> fireEvent(new AddImageEvent<>(this, e.isFromClient())));

        itemLayout.addClassName("item-layout");
        itemLayout.add(btnAdd);

        final Div root = getContent();
        root.addClassName("image-preview");
        root.add(itemLayout);
        root.add(mainImageLayout);
    }

    private void previousImage()
    {
        if (selectionImages.size() <= 1)
        {
            return;
        }

        if (selectedImage != null)
        {
            final var curIndex = selectionImages.indexOf(selectedImage);
            final var previousIndex = curIndex - 1;
            if (previousIndex >= 0)
            {
                final SelectionImage<T> previousComponent = selectionImages.get(previousIndex);
                selectImage(previousComponent);
            }
            else
            {
                final SelectionImage<T> lastComponent = selectionImages.get(selectionImages.size() - 1);
                selectImage(lastComponent);
            }
        }
        else
        {
            final SelectionImage<T> lastComponent = selectionImages.get(selectionImages.size() - 1);
            selectImage(lastComponent);
        }
    }

    private void nextImage()
    {
        if (selectionImages.size() <= 1)
        {
            return;
        }

        if (selectedImage != null)
        {
            final var curIndex = selectionImages.indexOf(selectedImage);
            final var nextIndex = curIndex + 1;
            if (selectionImages.size() > nextIndex)
            {
                final SelectionImage<T> nextComponent = selectionImages.get(nextIndex);
                selectImage(nextComponent);
            }
            else
            {
                final SelectionImage<T> firstComponent = selectionImages.get(0);
                selectImage(firstComponent);
            }
        }
        else
        {
            final SelectionImage<T> firstComponent = selectionImages.get(0);
            selectImage(firstComponent);
        }
    }

    @Override
    protected void setPresentationValue(final Set<T> newPresentationValue)
    {
        itemLayout.removeAll();
        itemLayout.add(btnAdd);

        if (newPresentationValue != null)
        {
            newPresentationValue.forEach(item ->
            {
                SelectionImage<T> img = new SelectionImage<>(item, resourceFactory);
                img.addClickListener(e -> selectImage(e.getSource()));
                selectionImages.add(img);
                itemLayout.add(img);
            });

            selectionImages.stream().findFirst().ifPresent(this::selectImage);
        }
    }

    private void selectImage(final SelectionImage<T> image)
    {
        if (this.selectedImage != null)
        {
            selectedImage.setSelected(false);
        }

        final ResourceFactory<T> factory = image.getResourceFactory();
        imgMain.setSrc(factory.create(image.getItem()));
        this.selectedImage = image;
        selectedImage.scrollIntoView();
        selectedImage.setSelected(true);
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

    public void setValue(Collection<T> items)
    {
        setValue(new LinkedHashSet<>(items));
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

        public void setSelected(final boolean selected)
        {
            getElement().setAttribute("selected", selected);
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
