package com.example.page;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;

public class Sample1 extends VerticalLayout {


    private final Button addNewBtn;

        public Sample1(Button addNewBtn) {
        this.addNewBtn = addNewBtn;
    }

    public void init() {

        HorizontalLayout actions = new HorizontalLayout( addNewBtn );


    }
}



