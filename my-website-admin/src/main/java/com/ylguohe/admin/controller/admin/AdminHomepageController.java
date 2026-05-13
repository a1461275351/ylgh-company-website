package com.ylguohe.admin.controller.admin;

import com.ylguohe.admin.entity.HeroBanner;
import com.ylguohe.admin.entity.Statistic;
import com.ylguohe.admin.service.HeroBannerService;
import com.ylguohe.admin.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/homepage")
@RequiredArgsConstructor
public class AdminHomepageController {

    private final HeroBannerService heroBannerService;
    private final StatisticService statisticService;

    @GetMapping("/banners")
    public String bannerList(Model model) {
        model.addAttribute("banners", heroBannerService.findAll());
        model.addAttribute("currentPage", "banners");
        return "admin/homepage/banners";
    }

    @GetMapping("/banners/new")
    public String bannerCreateForm(Model model) {
        model.addAttribute("banner", new HeroBanner());
        model.addAttribute("currentPage", "banners");
        return "admin/homepage/banner-form";
    }

    @GetMapping("/banners/{id}/edit")
    public String bannerEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("banner", heroBannerService.findById(id));
        model.addAttribute("currentPage", "banners");
        return "admin/homepage/banner-form";
    }

    @PostMapping("/banners/save")
    public String saveBanner(@ModelAttribute HeroBanner banner, RedirectAttributes ra) {
        heroBannerService.save(banner);
        ra.addFlashAttribute("message", "轮播图保存成功！");
        return "redirect:/admin/homepage/banners";
    }

    @PostMapping("/banners/{id}/delete")
    public String deleteBanner(@PathVariable Long id, RedirectAttributes ra) {
        heroBannerService.deleteById(id);
        ra.addFlashAttribute("message", "轮播图已删除！");
        return "redirect:/admin/homepage/banners";
    }

    @GetMapping("/statistics")
    public String statisticList(Model model) {
        model.addAttribute("statistics", statisticService.findAll());
        model.addAttribute("currentPage", "statistics");
        return "admin/homepage/statistics";
    }

    @GetMapping("/statistics/new")
    public String statisticCreateForm(Model model) {
        model.addAttribute("statistic", new Statistic());
        model.addAttribute("currentPage", "statistics");
        return "admin/homepage/statistic-form";
    }

    @GetMapping("/statistics/{id}/edit")
    public String statisticEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("statistic", statisticService.findById(id));
        model.addAttribute("currentPage", "statistics");
        return "admin/homepage/statistic-form";
    }

    @PostMapping("/statistics/save")
    public String saveStatistic(@ModelAttribute Statistic statistic, RedirectAttributes ra) {
        statisticService.save(statistic);
        ra.addFlashAttribute("message", "统计数据保存成功！");
        return "redirect:/admin/homepage/statistics";
    }

    @PostMapping("/statistics/{id}/delete")
    public String deleteStatistic(@PathVariable Long id, RedirectAttributes ra) {
        statisticService.deleteById(id);
        ra.addFlashAttribute("message", "统计数据已删除！");
        return "redirect:/admin/homepage/statistics";
    }
}
