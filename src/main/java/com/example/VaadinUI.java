package com.example;

import com.example.dao.UserRepository;

import com.example.model.User;

import com.example.page.MainGrid;
import com.example.page.Sample;

import com.example.ui.UserEditor;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by MikhalevPV on 25.04.2018.
 */
@SpringUI
public class VaadinUI extends UI {
    private final UserRepository repo;
    final Grid<User> grid;
    private final UserEditor editor;
    final TextField filterByaddress;
    final TextField filterByCleanAddress;
    final TextField filterByRegion;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final Button addNewBtn;

    public VaadinUI(UserRepository repo,  UserEditor editor) {
        this.repo = repo;
        this.editor = editor;
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

       Button toggleButton = new Button( "TEST" );
       toggleButton.addClickListener( event -> hPanel.setSecondComponent( new Sample() ) );


        /* Button backButton = new Button( "PharmacyNet" );
        backButton.addClickListener( event -> {
            hPanel.setSecondComponent( mainLayout );
            System.out.println("i am here!");
        } );
        */
        Button thirdButton = new Button( "Parsing" );
        thirdButton.addClickListener( event -> hPanel.setSecondComponent( new MainGrid( repo, editor, jdbcTemplate ) ) );
        VerticalLayout buttonLayout = new VerticalLayout( toggleButton, thirdButton);
        hPanel.setFirstComponent( buttonLayout );
        hPanel.setSecondComponent( mainLayout );
        setContent(hPanel);


        //Button thirtyButton = new Button( "ClientGrid" );
        //thirdButton.addClickListener( event -> hPanel.setSecondComponent(  new ClientGrid( repo1, editor1, jdbcTemplate ) ) );
        //VerticalLayout buttonLayou = new VerticalLayout(  toggleButton, thirdButton, thirtyButton);
        //hPanel.setFirstComponent( buttonLayou );
        //hPanel.setSecondComponent( mainLayout );
        //setContent(hPanel);


    }

}
