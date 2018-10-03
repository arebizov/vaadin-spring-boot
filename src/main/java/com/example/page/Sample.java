package com.example.page;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

public class Sample extends VerticalLayout {

    private Button button;

    public Sample() {
        this.button = new Button( "Нажми меня" );
        addComponent( button );
    }
}



