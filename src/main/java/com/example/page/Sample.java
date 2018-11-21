package com.example.page;

import com.example.view.PharmacyGrid;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;


public class Sample extends UI {


    public Button button;



    @Override
    protected void init(VaadinRequest request) {
        HorizontalSplitPanel hPanel = new HorizontalSplitPanel();
        hPanel.setSizeFull();
        hPanel.setSplitPosition( 225, Unit.PIXELS );
        HorizontalSplitPanel hPanel2 = new HorizontalSplitPanel();
        hPanel2.setSizeFull();
        hPanel2.setSplitPosition( 300, Unit.PIXELS );



        Button pharmacyButton = new Button( "Pharmacy" );
        pharmacyButton.setWidth( "100" );
        //pharmacyButton.addClickListener( event -> hPanel.setSecondComponent( new PharmacyGrid(repoph, editorph, jdbcTemplate ) ) );
        VerticalLayout buttonLayout = new VerticalLayout( pharmacyButton);
        hPanel.setFirstComponent( buttonLayout );
        setContent(hPanel);



        Button pharmacyButton1 = new Button( "Pharmacy1" );
        pharmacyButton1.setWidth( "100" );
        //pharmacyButton.addClickListener( event -> hPanel.setSecondComponent( new PharmacyGrid(repoph, editorph, jdbcTemplate ) ) );
        VerticalLayout buttonLayout1 = new VerticalLayout( pharmacyButton);
        hPanel.setFirstComponent( buttonLayout1 );
        setContent(hPanel2);


        }


    }






