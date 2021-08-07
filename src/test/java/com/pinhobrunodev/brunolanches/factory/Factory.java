package com.pinhobrunodev.brunolanches.factory;

import com.pinhobrunodev.brunolanches.entities.Category;

public class Factory {

    public static  Category createCategory(){
        return new Category(null,"Bebidas");
    }
}
