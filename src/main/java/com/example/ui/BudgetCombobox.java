package com.example.ui;

import com.example.dictionary.Dict;

import java.util.ArrayList;
import java.util.List;
import com.vaadin.ui.ComboBox;

public class BudgetCombobox extends ComboBox<Dict> {

    public BudgetCombobox() {
        setPlaceholder( "Введите значение" );
        List<Dict> list = new ArrayList<>(  );
        list.add( new Dict( "1", "one" ) );
        list.add( new Dict( "2", "two" ) );
        list.add( new Dict( "3", "three" ) );
        list.add( new Dict( "4", "four" ) );
        list.add( new Dict( "5", "five" ) );
        setItems( list );
        setItemCaptionGenerator( Dict::getValue );
    }
}
