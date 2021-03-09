package com.ecommerce.ecommerceTTS.controller;

import com.ecommerce.ecommerceTTS.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@ControllerAdvice
public class MainController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @ModelAttribute(value = "products")
    public List<Product> produts() {
        return productService.finalAll();
    }

    @ModelAttribute(value = "categories")
    public List<String> categories() {
        return product.Service.findDistinctCategories();

    }

    @ModelAttribute(value = "brands")
    public List<String> brands() {
        return productService.findDistinctBrands();
    }

    @GetMapping(value = "/filter")
    public String filter(@RequestParam(required = false) String category,
                         @RequestParam(required = false) String brand, Model model) {

        List<Product> filtered = productService.findByBrandAndOrCategory(brand, category);
        model.addAttribute("products", filtered);

        return "main";
    }

    @GetMapping(value = "about")
    public String about() {
        return "about";
    }
}