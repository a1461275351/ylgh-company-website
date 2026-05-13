package com.ylguohe.admin.controller.admin;

import com.ylguohe.admin.entity.CompanyTimeline;
import com.ylguohe.admin.entity.Qualification;
import com.ylguohe.admin.service.CompanyTimelineService;
import com.ylguohe.admin.service.QualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/company")
@RequiredArgsConstructor
public class AdminCompanyController {

    private final CompanyTimelineService timelineService;
    private final QualificationService qualificationService;

    @GetMapping("/timeline")
    public String timelineList(Model model) {
        model.addAttribute("timelines", timelineService.findAll());
        model.addAttribute("currentPage", "timeline");
        return "admin/company/timeline";
    }

    @GetMapping("/timeline/new")
    public String timelineCreateForm(Model model) {
        model.addAttribute("timeline", new CompanyTimeline());
        model.addAttribute("currentPage", "timeline");
        return "admin/company/timeline-form";
    }

    @GetMapping("/timeline/{id}/edit")
    public String timelineEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("timeline", timelineService.findById(id));
        model.addAttribute("currentPage", "timeline");
        return "admin/company/timeline-form";
    }

    @PostMapping("/timeline/save")
    public String saveTimeline(@ModelAttribute CompanyTimeline timeline, RedirectAttributes ra) {
        timelineService.save(timeline);
        ra.addFlashAttribute("message", "里程碑保存成功！");
        return "redirect:/admin/company/timeline";
    }

    @PostMapping("/timeline/{id}/delete")
    public String deleteTimeline(@PathVariable Long id, RedirectAttributes ra) {
        timelineService.deleteById(id);
        ra.addFlashAttribute("message", "里程碑已删除！");
        return "redirect:/admin/company/timeline";
    }

    @GetMapping("/qualifications")
    public String qualificationList(Model model) {
        model.addAttribute("qualifications", qualificationService.findAll());
        model.addAttribute("currentPage", "qualifications");
        return "admin/company/qualifications";
    }

    @GetMapping("/qualifications/new")
    public String qualificationCreateForm(Model model) {
        model.addAttribute("qualification", new Qualification());
        model.addAttribute("currentPage", "qualifications");
        return "admin/company/qualification-form";
    }

    @GetMapping("/qualifications/{id}/edit")
    public String qualificationEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("qualification", qualificationService.findById(id));
        model.addAttribute("currentPage", "qualifications");
        return "admin/company/qualification-form";
    }

    @PostMapping("/qualifications/save")
    public String saveQualification(@ModelAttribute Qualification qualification, RedirectAttributes ra) {
        qualificationService.save(qualification);
        ra.addFlashAttribute("message", "资质保存成功！");
        return "redirect:/admin/company/qualifications";
    }

    @PostMapping("/qualifications/{id}/delete")
    public String deleteQualification(@PathVariable Long id, RedirectAttributes ra) {
        qualificationService.deleteById(id);
        ra.addFlashAttribute("message", "资质已删除！");
        return "redirect:/admin/company/qualifications";
    }
}
