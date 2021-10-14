package com.pinhobrunodev.brunolanches.util;

import com.pinhobrunodev.brunolanches.entities.Product;
import com.pinhobrunodev.brunolanches.services.exceptions.ValidatingNumberOfCategoriesException;

public class ValidatingNumberOfCategories {

	public static void validating(Product entity) {
		if (entity.getCategories().size() == 0) {
			throw new ValidatingNumberOfCategoriesException("Number of categories must be equals or higher than 1");
		}
	}

}
