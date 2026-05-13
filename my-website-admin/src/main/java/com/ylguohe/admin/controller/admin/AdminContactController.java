package com.ylguohe.admin.controller.admin;

import com.ylguohe.admin.entity.ContactInfo;
import com.ylguohe.admin.entity.enums.ContactType;
import com.ylguohe.admin.entity.enums.InquiryStatus;
import com.ylguohe.admin.service.ContactInfoService;
import com.ylguohe.admin.service.ContactInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/contact")
@RequiredArgsConstructor
public class AdminContactController {

    private final ContactInfoService contactInfoService;
    private final ContactInquiryService contactInquiryService;

    @GetMapping("/info")
    public String infoList(Model model) {
        model.addAttribute("contacts", contactInfoService.findAll());
        model.addAttribute("types", ContactType.values());
        model.addAttribute("currentPage", "contactInfo");
        return "admin/contact/info";
    }

    @GetMapping("/info/new")
    public String infoCreateForm(Model model) {
        model.addAttribute("contact", new ContactInfo());
        model.addAttribute("types", ContactType.values());
        model.addAttribute("currentPage", "contactInfo");
        return "admin/contact/info-form";
    }

    @GetMapping("/info/{id}/edit")
    public String infoEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("contact", contactInfoService.findById(id));
        model.addAttribute("types", ContactType.values());
        model.addAttribute("currentPage", "contactInfo");
        return "admin/contact/info-form";
    }

    @PostMapping("/info/save")
    public String saveInfo(@ModelAttribute ContactInfo info, RedirectAttributes ra) {
        contactInfoService.save(info);
        ra.addFlashAttribute("message", "联系信息保存成功！");
        return "redirect:/admin/contact/info";
    }

    @PostMapping("/info/{id}/delete")
    public String deleteInfo(@PathVariable Long id, RedirectAttributes ra) {
        contactInfoService.deleteById(id);
        ra.addFlashAttribute("message", "联系信息已删除！");
        return "redirect:/admin/contact/info";
    }

    @GetMapping("/inquiries")
    public String inquiryList(@RequestParam(required = false) String status, Model model) {
        if (status != null) {
            model.addAttribute("inquiries", contactInquiryService.findByStatus(InquiryStatus.valueOf(status.toUpperCase())));
            model.addAttribute("selectedStatus", status);
        } else {
            model.addAttribute("inquiries", contactInquiryService.findAll());
        }
        model.addAttribute("statuses", InquiryStatus.values());
        model.addAttribute("currentPage", "inquiries");
        return "admin/contact/inquiries";
    }

    @GetMapping("/inquiries/{id}")
    public String inquiryDetail(@PathVariable Long id, Model model) {
        model.addAttribute("inquiry", contactInquiryService.findById(id));
        model.addAttribute("statuses", InquiryStatus.values());
        model.addAttribute("currentPage", "inquiries");
        return "admin/contact/inquiry-detail";
    }

    @PostMapping("/inquiries/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status,
                               @RequestParam(required = false) String adminNote, RedirectAttributes ra) {
        contactInquiryService.updateStatus(id, InquiryStatus.valueOf(status.toUpperCase()), adminNote);
        ra.addFlashAttribute("message", "询盘状态已更新！");
        return "redirect:/admin/contact/inquiries/" + id;
    }
}
