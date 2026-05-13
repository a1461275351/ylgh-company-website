package com.ylguohe.admin.controller.admin;

import com.ylguohe.admin.entity.enums.InquiryStatus;
import com.ylguohe.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final ProductService productService;
    private final NewsArticleService newsArticleService;
    private final ContactInquiryService contactInquiryService;
    private final VehicleDestinationService vehicleDestinationService;

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        model.addAttribute("productCount", productService.count());
        model.addAttribute("newsCount", newsArticleService.count());
        model.addAttribute("inquiryCount", contactInquiryService.count());
        model.addAttribute("pendingInquiryCount", contactInquiryService.countByStatus(InquiryStatus.PENDING));
        model.addAttribute("vehicleCount", vehicleDestinationService.count());
        model.addAttribute("latestNews", newsArticleService.findLatest());
        model.addAttribute("currentPage", "dashboard");
        return "admin/dashboard";
    }
}
