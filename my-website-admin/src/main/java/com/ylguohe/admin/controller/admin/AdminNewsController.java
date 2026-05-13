package com.ylguohe.admin.controller.admin;

import com.ylguohe.admin.entity.NewsArticle;
import com.ylguohe.admin.entity.enums.NewsCategory;
import com.ylguohe.admin.service.NewsArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/news")
@RequiredArgsConstructor
public class AdminNewsController {

    private final NewsArticleService newsArticleService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("articles", newsArticleService.findAll());
        model.addAttribute("categories", NewsCategory.values());
        model.addAttribute("currentPage", "news");
        return "admin/news/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("article", new NewsArticle());
        model.addAttribute("categories", NewsCategory.values());
        model.addAttribute("currentPage", "news");
        return "admin/news/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("article", newsArticleService.findById(id));
        model.addAttribute("categories", NewsCategory.values());
        model.addAttribute("currentPage", "news");
        return "admin/news/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute NewsArticle article, RedirectAttributes redirectAttributes) {
        newsArticleService.save(article);
        redirectAttributes.addFlashAttribute("message", "新闻保存成功！");
        return "redirect:/admin/news";
    }

    @PostMapping("/{id}/toggle-featured")
    public String toggleFeatured(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        newsArticleService.toggleFeatured(id);
        redirectAttributes.addFlashAttribute("message", "置顶状态已更新！");
        return "redirect:/admin/news";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        newsArticleService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "新闻已删除！");
        return "redirect:/admin/news";
    }
}
