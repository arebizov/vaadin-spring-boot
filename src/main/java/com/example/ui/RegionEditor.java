package com.example.ui;

import com.example.dao.RegionRepository;
import com.example.model.Region;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by MikhalevPV on 25.04.2018.
 */
@SpringComponent
@UIScope
public class RegionEditor extends VerticalLayout {


    @Autowired
    JdbcTemplate jdbcTemplate;
    //JdbcTemplate jdbcTemplate1;

    private final RegionRepository repository;

    /**
     * The currently edited user
     */
    private Region region;

    /* Fields to edit properties in Customer entity */
    //TextField city = new TextField("write fields forces");
    //TextField originalAdress = new TextField("System.out.println(user.getId())");
    TextField mp = new TextField("write fields forces");

    /* Action buttons */
    Button save = new Button("Save", VaadinIcons.CHECK);
    Button cancel = new Button("Cancel" );
    Button delete = new Button("Delete", VaadinIcons.TRASH);
    CssLayout actions = new CssLayout(save, cancel);
    //CssLayout actions = new CssLayout(save, cancel, delete);

    Binder<Region> binder = new Binder<>(Region.class);

    @Autowired
    public RegionEditor(RegionRepository repository) {
        this.repository = repository;
        mp.setWidth( "500px" );
        mp.setId( "mp" );


        //addComponents(correctAddress, cleanAddress, actions);
        addComponents(mp, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener( e -> {
            repository.save( region );


        } );
        delete.addClickListener(e -> repository.delete(region));
        cancel.addClickListener(e -> editCustomer(region));
        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editCustomer(Region c) {
        if (c == null) {
            setVisible(true);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            region = repository.findById(c.getId()).get();
        }
        else {
            region = c;
        }
        cancel.setVisible(persisted);

        // Bind actStatusType properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(region);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in actStatusName field automatically
        mp.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}