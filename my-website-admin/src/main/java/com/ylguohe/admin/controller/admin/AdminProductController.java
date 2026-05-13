package com.ylguohe.admin.controller.admin;

import com.ylguohe.admin.entity.Product;
import com.ylguohe.admin.entity.enums.ProductCategory;
import com.ylguohe.admin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @GetMapping
    public String list(@RequestParam(required = false) String category, Model model) {
        if (category != null) {
            model.addAttribute("products", productService.findByCategory(ProductCategory.valueOf(category.toUpperCase())));
            model.addAttribute("selectedCategory", category);
        } else {
            model.addAttribute("products", productService.findAll());
        }
        model.addAttribute("categories", ProductCategory.values());
        model.addAttribute("currentPage", "products");
        return "admin/products/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", ProductCategory.values());
        model.addAttribute("currentPage", "products");
        return "admin/products/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categories", ProductCategory.values());
        model.addAttribute("currentPage", "products");
        return "admin/products/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Product product, RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "产品保存成功！");
        return "redirect:/admin/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "产品已删除！");
        return "redirect:/admin/products";
    }
}
