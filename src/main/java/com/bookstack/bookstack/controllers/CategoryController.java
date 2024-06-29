package com.bookstack.bookstack.controllers;

import com.bookstack.bookstack.models.Category;
import com.bookstack.bookstack.services.CategoryService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @QueryMapping
    public List<Category> allCategories() {
        return categoryService.getAllCategories();
    }

    @MutationMapping
    public Category addCategory(@Argument String name) {
        return categoryService.addCategory(name);
    }
}
