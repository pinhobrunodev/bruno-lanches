package com.pinhobrunodev.brunolanches.factory;

import com.pinhobrunodev.brunolanches.dto.category.CategoryDTO;
import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.entities.Category;
import com.pinhobrunodev.brunolanches.entities.Product;

public class Factory {

    public static Category createCategory() {
        return new Category(1L, "Eletronico");
    }

    public static CategoryDTO createCategoryDTO() {
        Category category = createCategory();
        return new CategoryDTO(category);
    }


    public static Product createProduct() {
        Product product = new Product(null
                , "Phone", "Good Phone", 800.0);
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

}
