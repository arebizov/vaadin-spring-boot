package com.example.page;

import com.example.dao.ClientRepository;
import com.example.dao.UserRepository;
import com.example.model.Client;
import com.example.ui.BudgetCombobox;
import com.example.ui.ClientEditor;
import com.example.ui.UserEditor;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientGrid extends VerticalLayout {
    private final ClientRepository repo;

    final Grid<Client> grid;

    private final ClientEditor editor;

    final TextField filterByaddress;
    final TextField filterByCleanAddress;
    final TextField filterByRegion;

    JdbcTemplate jdbcTemplate;

    private final Button addNewBtn;


    public ClientGrid(ClientRepository repo1, ClientEditor editor, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repo = repo1 ;
        this.editor = editor;
        this.grid = new Grid<>( Client.class );
        this.filterByaddress = new TextField();
        this.filterByCleanAddress = new TextField();
        this.filterByRegion = new TextField();
        this.addNewBtn = new Button( "New user", VaadinIcons.PLUS );
        init();
    }

    public void init(){
        filterByaddress.setWidth( "200px" );
        filterByCleanAddress.setWidth( "200px" );
        filterByRegion.setWidth( "200px" );
        BudgetCombobox combo = new BudgetCombobox();
        HorizontalLayout actions = new HorizontalLayout( filterByaddress, filterByCleanAddress, filterByRegion, combo );

        addComponents( actions, grid, editor );
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
        addNewBtn.addClickListener( e -> editor.editCustomer( new Client( null, "", "", "", "" ) ) );

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler( () -> {
            editor.setVisible( false );
            listUsers( filterByaddress.getValue(), filterByCleanAddress.getValue(), filterByRegion.getValue() );
            //listUsers(null, null, null );
        } );
        listUsers( null, null, null );

    }


    void listUsers(String correctAddress, String cleanAddress, String region) {

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
                new RowMapper<Client>() {
                    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Client user = new Client();
                        user.setId( rs.getLong( "id1" ) );
                        user.setaddressPharmacy( rs.getString( "correct" ) );
                        user.setCleanAddress( rs.getString( "clean_ad" ) );
                        user.setPharmacyNet( rs.getString( "pharmacy_net" ) );
                        user.setRegion( rs.getString( "region4" ) );
                        return user;
                    }
                } )
        );
    }




}
