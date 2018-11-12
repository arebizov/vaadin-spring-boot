package com.example.ui;

import com.example.dao.UserRepository;
import com.example.model.User;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.Registration;
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
public class UserEditor extends VerticalLayout {


    @Autowired
    JdbcTemplate jdbcTemplate;
    //JdbcTemplate jdbcTemplate1;
    private final UserRepository repository;

    /**
     * The currently edited user
     */
    private com.example.model.User user;

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

    Binder<User> binder = new Binder<>(User.class);

    @Autowired
    public UserEditor(UserRepository repository) {
        this.repository = repository;
        addressPharmacy.setWidth( "500px" );
        addressPharmacy.setId( "addressPharmacy" );
        cleanAddress.setWidth( "500px" );

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
        save.addClickListener( e -> {
            repository.save( user );
            jdbcTemplate.update( "update pl.net2 set status_validation=? where id = ?", 5, user.getId() );
            jdbcTemplate.update( "update pl.net2 set id_kladr=? where id = ?", "00000000000000000", user.getId() );
            jdbcTemplate.update( "update pl.net2 set correct_address=? where name in (select name from pl.net2 where id=?)"
                    ,user.getaddressPharmacy()
                   ,user.getId()
            );
                                } );
        delete.addClickListener(e -> repository.delete(user));
        cancel.addClickListener(e -> editCustomer(user));
        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editCustomer(com.example.model.User c) {
        if (c == null) {
            setVisible(true);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            user = repository.findById(c.getId()).get();
        }
        else {
            user = c;
        }
        cancel.setVisible(persisted);

        // Bind actStatusType properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(user);

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