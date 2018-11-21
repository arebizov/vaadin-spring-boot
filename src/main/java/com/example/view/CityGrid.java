package com.example.view;
import com.example.dao.CityRepository;
import com.example.model.City;

import com.example.ui.BudgetCombobox;
import com.example.ui.CityEditor;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CityGrid extends VerticalLayout {

    private final CityRepository repo;

    final Grid<City> grid;

    private final CityEditor editor;

    final TextField filterByaddress;
    final TextField filterByCleanAddress;
    final TextField filterByRegion;

    JdbcTemplate jdbcTemplate;

    private final Button addNewBtn;

    public CityGrid(CityRepository repo, CityEditor editor, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repo = repo ;
        this.editor = editor;
        this.grid = new Grid<>( City.class );
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
        HorizontalLayout actions = new HorizontalLayout( filterByaddress, filterByCleanAddress, filterByRegion );

        addComponents( actions, grid, editor );
        grid.setHeight( 400, Unit.PIXELS );
        grid.setWidth( "100%" );

        grid.setColumns( "city", "mp", "code_region" );
        filterByaddress.setPlaceholder( "Filter by city" );
        filterByCleanAddress.setPlaceholder( "Filter by FF" );
        filterByRegion.setPlaceholder( "Filter by code Region" );

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
        //addNewBtn.addClickListener( e -> editor.editCustomer( new User( null ) ) );

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler( () -> {
          editor.setVisible( false );
            listUsers( filterByaddress.getValue(), filterByCleanAddress.getValue(), filterByRegion.getValue() );
         listUsers(null, null, null );
       } );
        listUsers( null, null, null );

    }


    void listUsers(String city, String mp, String code_region) {
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

        String query = "select top 50 " +
                "t.id as id1," +
                " t.city as city1, " +
                " t.mp as mp1, " +
                " t.code_region as code_region1 from ETL.D_city as t where 1=1";
        if (!StringUtils.isEmpty( city )) query += " and upper(t.city) like upper(?) ";
        if (!StringUtils.isEmpty( mp )) query += " and upper(t.mp) like upper(?) ";
        if (!StringUtils.isEmpty( code_region )) query += " and upper(t.code_region1) like upper(?) ";
        grid.setItems( jdbcTemplate.query(
                query,
                ps -> {
                    int pCount = 1;
                    if (!StringUtils.isEmpty( city )) {
                        ps.setString( pCount, "%" + city + "%" );
                        pCount++;
                    }
                    if (!StringUtils.isEmpty( mp )) {
                        ps.setString( pCount, "%" + mp + "%" );
                        pCount++;
                    }
                    if (!StringUtils.isEmpty( code_region )) {
                        ps.setString( pCount, "%" + code_region + "%" );
                    }
                },
                new RowMapper<City>() {
                    public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                        City city = new City();
                        city.setId( rs.getLong( "id1" ) );
                        city.setCity (rs.getString( "city1" ) );
                        city.setMp( rs.getString( "mp1" ) );
                        city.setcode_region( rs.getString( "code_region1" ) );
                        return city;
                    }
                } )
        );
    }

}
