package com.example;

import com.example.dao.CityRepository;
import com.example.dao.PharmacyRepository;
import com.example.dao.RegionRepository;
import com.example.page.CityGrid;
import com.example.page.PharmacyGrid;
import com.example.page.RegionGrid;
import com.example.ui.CityEditor;
import com.example.ui.PharmacyEditor;
import com.example.ui.RegionEditor;
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

    @Autowired
    JdbcTemplate jdbcTemplate;

    public VaadinUI(PharmacyRepository repo, PharmacyEditor editor, RegionRepository repo1, RegionEditor editor1, CityRepository repocity, CityEditor editorcity ) {
        this.repoph = repo;
        this.editorph = editor;
        this.repo1 = repo1;
        this.editor1 = editor1;
        this.repocity = repocity;
        this.editorcity = editorcity;

    }

    @Override
    protected void init(VaadinRequest request) {

        HorizontalSplitPanel hPanel = new HorizontalSplitPanel();
        hPanel.setSizeFull();
        hPanel.setSplitPosition( 200, Unit.PIXELS );

        Button parsingButton = new Button( "Pharmacy" );
        parsingButton.addClickListener( event -> hPanel.setSecondComponent( new PharmacyGrid(repoph, editorph, jdbcTemplate ) ) );
        VerticalLayout buttonLayout = new VerticalLayout( parsingButton);
        hPanel.setFirstComponent( buttonLayout );
        setContent(hPanel);


        Button cityButton = new Button( "City" );
        cityButton.addClickListener( event -> hPanel.setSecondComponent( new CityGrid( repocity, editorcity, jdbcTemplate ) ) );
        VerticalLayout buttonLayout1 = new VerticalLayout( cityButton, parsingButton);
        hPanel.setFirstComponent( buttonLayout1 );
        setContent(hPanel);


        Button regionButton = new Button( "Region" );
        regionButton.addClickListener( event -> hPanel.setSecondComponent( new RegionGrid( repo1, editor1, jdbcTemplate ) ) );
        VerticalLayout buttonLayout2 = new VerticalLayout( parsingButton, cityButton,  regionButton);
        hPanel.setFirstComponent( buttonLayout2 );
        setContent(hPanel);


    }

}
