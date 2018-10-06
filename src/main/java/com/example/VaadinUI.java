package com.example;
import com.example.dao.ClientRepository;
import com.example.dao.UserRepository;
import com.example.model.User;
import com.example.page.ClientGrid;
import com.example.page.MainGrid;
import com.example.page.Sample;
import com.example.ui.ClientEditor;
import com.example.ui.UserEditor;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by MikhalevPV on 25.04.2018.
 */
@SpringUI
public class VaadinUI extends UI {

    private final UserRepository repo;
    private final ClientRepository repo1;

    final Grid<User> grid;

    private final UserEditor editor;
    private final ClientEditor editor1;

    final TextField filterByaddress;
    final TextField filterByCleanAddress;
    final TextField filterByRegion;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final Button addNewBtn;

    public VaadinUI(UserRepository repo, ClientRepository repo1, UserEditor editor, ClientEditor editor1) {
        this.repo = repo;
        this.repo1 = repo1;
        this.editor = editor;
        this.editor1 = editor1;
        this.grid = new Grid<>( User.class );
        this.filterByaddress = new TextField();
        this.filterByCleanAddress = new TextField();
        this.filterByRegion = new TextField();
        this.addNewBtn = new Button( "New user", VaadinIcons.PLUS );
    }

    @Override
    protected void init(VaadinRequest request) {
        filterByaddress.setWidth( "200px" );
        filterByCleanAddress.setWidth( "200px" );
        filterByRegion.setWidth( "200px" );

        HorizontalLayout actions = new HorizontalLayout( filterByaddress, filterByCleanAddress, filterByRegion );
        VerticalLayout mainLayout = new VerticalLayout( actions, grid, editor );

        HorizontalSplitPanel hPanel = new HorizontalSplitPanel();
        hPanel.setSizeFull();
        hPanel.setSplitPosition( 200, Unit.PIXELS );

        Button toggleButton = new Button( VaadinIcons.ABACUS );
        toggleButton.addClickListener( event -> hPanel.setSecondComponent( new Sample() ) );
        Button backButton = new Button( "PharmacyNet" );
        backButton.addClickListener( event -> {
            hPanel.setSecondComponent( mainLayout );
            System.out.println("i am here!");
        } );
        Button thirdButton = new Button( VaadinIcons.ACADEMY_CAP );
        thirdButton.addClickListener( event -> hPanel.setSecondComponent( new MainGrid( repo, editor, jdbcTemplate ) ) );
        VerticalLayout buttonLayout = new VerticalLayout( toggleButton, backButton, thirdButton);
        hPanel.setFirstComponent( buttonLayout );
        hPanel.setSecondComponent( mainLayout );
        setContent(hPanel);


        Button thirtyButton = new Button( "Client" );
        thirdButton.addClickListener( event -> hPanel.setSecondComponent(  new ClientGrid( repo1, editor1, jdbcTemplate ) ) );
        VerticalLayout buttonLayou = new VerticalLayout(  toggleButton, backButton, thirdButton, thirtyButton);
        hPanel.setFirstComponent( buttonLayou );
        hPanel.setSecondComponent( mainLayout );
        setContent(hPanel);





        grid.setHeight( 400, Unit.PIXELS );
        grid.setWidth( "100%" );

        grid.setColumns( "addressPharmacy", "cleanAddress", "region", "pharmacyNet" );
        filterByaddress.setPlaceholder( "Filter by address" );
        filterByCleanAddress.setPlaceholder( "Filter by clean address" );
        filterByRegion.setPlaceholder( "Filter by Region" );

        // Replace listing with filtered content when user changes filter
        filterByaddress.setValueChangeMode( ValueChangeMode.LAZY );
        filterByaddress.addValueChangeListener( e -> listUsers( filterByaddress.getValue(), filterByCleanAddress.getValue(), filterByRegion.getValue() ) );

        filterByCleanAddress.setValueChangeMode( ValueChangeMode.LAZY );
        filterByCleanAddress.addValueChangeListener( e -> listUsers( filterByaddress.getValue(), filterByCleanAddress.getValue(), filterByRegion.getValue() ) );

        filterByRegion.setValueChangeMode( ValueChangeMode.LAZY );
        filterByRegion.addValueChangeListener( e -> listUsers( filterByaddress.getValue(), filterByCleanAddress.getValue(), filterByRegion.getValue() ) );


        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener( e -> {
            editor.editCustomer( e.getValue() );
        } );

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener( e -> editor.editCustomer( new User( null, "", "", "", "" ) ) );

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler( () -> {
            editor.setVisible( false );
            listUsers( filterByaddress.getValue(), filterByCleanAddress.getValue(), filterByRegion.getValue() );
            //listUsers(null, null, null );
        } );
        listUsers( null, null, null );
        //listUsers(filterByaddress.getValue(),filterByCleanAddress.getValue() ,filterByRegion.getValue() );


    }

    void listUsers(String correctAddress, String cleanAddress, String region) {
        /*
    }
        if( StringUtils.isEmpty(region) & StringUtils.isEmpty(cleanAddress) & !StringUtils.isEmpty(correctAddress)){
            grid.setItems(repo.findByAddressPharmacyLikeIgnoreCase('%' + correctAddress + '%'));
            return;
        }
        if( StringUtils.isEmpty(cleanAddress) & StringUtils.isEmpty(correctAddress) & !StringUtils.isEmpty(cleanAddress)){
            grid.setItems(repo.findByCleanAddressLikeIgnoreCase('%' + cleanAddress + '%'));
            return;
        }
        if( StringUtils.isEmpty(correctAddress) & StringUtils.isEmpty(cleanAddress) & !StringUtils.isEmpty(region)){
            grid.setItems(repo.findByRegionLikeIgnoreCase('%' + region + '%'));
            return;
        }*/

        String query = "select top 300 " +
                "t.id as id1," +
                " t.correct_address as correct, " +
                " t.clean_address as clean_ad, " +
                " t.pharmacy_net_name as pharmacy_net, " +
                "t.region as region4 from pl.net2 as t where 1=1";
        if (!StringUtils.isEmpty( correctAddress )) query += " and upper(t.correct_address) like upper(?) ";
        if (!StringUtils.isEmpty( cleanAddress )) query += " and upper(t.clean_address) like upper(?) ";
        if (!StringUtils.isEmpty( region )) query += " and upper(t.region) like upper(?) ";
        grid.setItems( jdbcTemplate.query(
                query,
                ps -> {
                    int pCount = 1;
                    if (!StringUtils.isEmpty( correctAddress )) {
                        ps.setString( pCount, "%" + correctAddress + "%" );
                        pCount++;
                    }
                    if (!StringUtils.isEmpty( cleanAddress )) {
                        ps.setString( pCount, "%" + cleanAddress + "%" );
                        pCount++;
                    }
                    if (!StringUtils.isEmpty( region )) {
                        ps.setString( pCount, "%" + region + "%" );
                    }
                },
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setId( rs.getLong( "id1" ) );
                        user.setaddressPharmacy( rs.getString( "correct" ) );
                        user.setCleanAddress( rs.getString( "clean_ad" ) );
                        user.setPharmacyNet( rs.getString( "pharmacy_net" ) );
                        user.setRegion( rs.getString( "region4" ) );
                        return user;
                    }
                } )
        );

/*

        if(region == null & cleanAddress == null & correctAddress == null) {
            grid.setItems( repo.findAll( new PageRequest(1, 20) ).getContent() );
            return;
        }
        grid.setItems(repo.findByRegionLikeIgnoreCaseAndCleanAddressLikeIgnoreCaseAndAddressPharmacyLikeIgnoreCase(
                "%" + region + "%",
                "%" + cleanAddress + "%",
                 "%" + correctAddress  + "%"));
                 */
    }
}
