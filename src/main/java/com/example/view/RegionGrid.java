package com.example.view;
import com.example.dao.RegionRepository;
import com.example.dictionary.Dict;
import com.example.model.Region;
import com.example.ui.BudgetCombobox;
import com.example.ui.CityEditor;
import com.example.ui.RegionEditor;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RegionGrid extends VerticalLayout {

    private final RegionRepository repo;

    final Grid<Region> grid;

    private final RegionEditor editor;

    final TextField filterByaddress;
    final TextField filterByCleanAddress;
    final TextField filterByRegion;

    JdbcTemplate jdbcTemplate;

    private final Button addNewBtn;

    public RegionGrid(RegionRepository repo, RegionEditor editor, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.repo = repo ;
        this.editor = editor;
        this.grid = new Grid<>( Region.class );
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

        grid.setColumns( "region", "mp", "id_fo" );
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
        //addNewBtn.addClickListener( e -> editor.editCustomer( new User( null ) ) );

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler( () -> {
            editor.setVisible( false );
            listUsers( filterByaddress.getValue(), filterByCleanAddress.getValue(), filterByRegion.getValue() );
            //listUsers(null, null, null );
        } );
        listUsers( null, null, null );

    }


    void listUsers(String region, String mp, String id_fo) {
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
                " t.region as region1, " +
                " t.mp as mp1, " +
                " t.id_fo as id_fo1 from ETL.D_region as t where 1=1";
        if (!StringUtils.isEmpty( region )) query += " and upper(t.region) like upper(?) ";
        if (!StringUtils.isEmpty( mp )) query += " and upper(t.mp) like upper(?) ";
        if (!StringUtils.isEmpty( id_fo )) query += " and upper(t.id_fo1) like upper(?) ";
        grid.setItems( jdbcTemplate.query(
                query,
                ps -> {
                    int pCount = 1;
                    if (!StringUtils.isEmpty( region )) {
                        ps.setString( pCount, "%" + region + "%" );
                        pCount++;
                    }
                    if (!StringUtils.isEmpty( mp )) {
                        ps.setString( pCount, "%" + mp + "%" );
                        pCount++;
                    }
                    if (!StringUtils.isEmpty( id_fo )) {
                        ps.setString( pCount, "%" + id_fo + "%" );
                    }
                },
                new RowMapper<Region>() {
                    public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Region region = new Region();
                        region.setId( rs.getLong( "id1" ) );
                        region.setRegion (rs.getString( "region1" ) );
                        region.setMp( rs.getString( "mp1" ) );
                        region.setId_fo( rs.getString( "id_fo1" ) );
                        return region;
                    }
                } )
        );
    }

}
