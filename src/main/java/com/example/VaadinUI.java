package com.example;

import com.example.dao.*;
import com.example.page.*;
import com.example.ui.*;
import com.example.view.CityGrid;
import com.example.view.PharmacyGrid;
import com.example.view.RegionGrid;
import com.example.view.UserGrid;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringUI
public class VaadinUI extends UI {
    private final PharmacyRepository repoph;
    private final PharmacyEditor editorph;

    private final RegionRepository repo1;
    private final RegionEditor editor1;

    private final CityRepository repocity;
    private final CityEditor editorcity;

    private final UserRepository repopar;
    private final UserEditor editorpar;


    @Autowired
    JdbcTemplate jdbcTemplate;

    public VaadinUI(PharmacyRepository repo, PharmacyEditor editor, RegionRepository repo1, RegionEditor editor1, CityRepository repocity, CityEditor editorcity,
    UserRepository repopar, UserEditor editorpar
    ) {
        this.repoph = repo;
        this.editorph = editor;
        this.repo1 = repo1;
        this.editor1 = editor1;
        this.repocity = repocity;
        this.editorcity = editorcity;
        this.repopar = repopar;
        this.editorpar = editorpar;

    }

    @Override
    protected void init(VaadinRequest request) {


        HorizontalSplitPanel hPanel = new HorizontalSplitPanel();
        hPanel.setSizeFull();
        hPanel.setSplitPosition( 150, Unit.PIXELS );

        Button pharmacyButton = new Button( "Pharmacy" );
        pharmacyButton.setWidth( "100" );
        pharmacyButton.addClickListener( event -> hPanel.setSecondComponent( new PharmacyGrid(repoph, editorph, jdbcTemplate ) ) );
        VerticalLayout buttonLayout = new VerticalLayout( pharmacyButton);
        hPanel.setFirstComponent( buttonLayout );
        setContent(hPanel);


        Button cityButton = new Button( "City" );
        cityButton.setWidth( "100" );
        cityButton.addClickListener( event -> hPanel.setSecondComponent( new CityGrid( repocity, editorcity, jdbcTemplate ) ) );
        VerticalLayout buttonLayout1 = new VerticalLayout( cityButton, pharmacyButton);
        hPanel.setFirstComponent( buttonLayout1 );
        setContent(hPanel);


        Button regionButton = new Button( "Region" );
        regionButton.setWidth( "100" );
        regionButton.addClickListener( event -> hPanel.setSecondComponent( new RegionGrid( repo1, editor1, jdbcTemplate ) ) );
        VerticalLayout buttonLayout2 = new VerticalLayout( pharmacyButton, cityButton,  regionButton);
        hPanel.setFirstComponent( buttonLayout2 );
        setContent(hPanel);


        Button parsingButton1 = new Button( "TMPPars" );
        parsingButton1.setWidth( "100" );
        parsingButton1.addClickListener( event -> hPanel.setSecondComponent( new UserGrid( repopar, editorpar, jdbcTemplate ) ) );
        VerticalLayout buttonLayout3 = new VerticalLayout( pharmacyButton, cityButton,  regionButton, parsingButton1);
        hPanel.setFirstComponent( buttonLayout3 );
        setContent(hPanel);



        Button budgetButton = new Button( "Budget" );
        budgetButton.setWidth( "100" );
        budgetButton.addClickListener( event -> hPanel.setSecondComponent( new Sample() ) );
        VerticalLayout buttonLayou4 = new VerticalLayout( pharmacyButton, cityButton,  regionButton, parsingButton1, budgetButton);
        hPanel.setFirstComponent( buttonLayou4 );
        setContent(hPanel);

    }

}
