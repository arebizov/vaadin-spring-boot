package com.example.ui;

import com.example.dao.ParsingRepository;

import com.example.model.Parsing;

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
public class ParsingEditor extends VerticalLayout {


    @Autowired
    JdbcTemplate jdbcTemplate;
    //JdbcTemplate jdbcTemplate1;
    private final ParsingRepository repository;

    /**
     * The currently edited user
     */
    private Parsing parsing;

    /* Fields to edit properties in Customer entity */
    TextField addressPharmacy = new TextField("write correct address");
    //TextField originalAdress = new TextField("System.out.println(user.getId())");
    TextField cleanAddress = new TextField("address name");

    /* Action buttons */
    Button save = new Button("Save", VaadinIcons.CHECK);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcons.TRASH);
    CssLayout actions = new CssLayout(save, cancel);
    //CssLayout actions = new CssLayout(save, cancel, delete);

    Binder<Parsing> binder = new Binder<>(Parsing.class);

    @Autowired
    public ParsingEditor(ParsingRepository repository) {
        this.repository = repository;
        //addressPharmacy.setWidth( "500px" );
        //addressPharmacy.setId( "addressPharmacy" );
        //cleanAddress.setWidth( "500px" );

        //addComponents(correctAddress, cleanAddress, actions);
        addComponents(addressPharmacy, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        //save.addClickListener( e -> {
         //   repository.save( pharmacy );
         //   jdbcTemplate.update( "update pl.net2 set status_validation=? where id = ?", 5, pharmacy.getId() );
         //   jdbcTemplate.update( "update pl.net2 set id_kladr=? where id = ?", "00000000000000000", pharmacy.getId() );
         //   jdbcTemplate.update( "update pl.net2 set correct_address=? where name in (select name from pl.net2 where id=?)"
         //           ,pharmacy.getaddressPharmacy()
          //         ,pharmacy.getId()
          //  );
          //                      } );
        delete.addClickListener(e -> repository.delete(parsing));
        cancel.addClickListener(e -> editCustomer(parsing));
        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editCustomer(Parsing c) {
        if (c == null) {
            setVisible(true);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            parsing = repository.findById(c.getId()).get();
        }
        else {
            parsing = c;
        }
        cancel.setVisible(persisted);

        // Bind actStatusType properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(parsing);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in actStatusName field automatically
        addressPharmacy.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}