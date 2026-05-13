package com.ylguohe.admin.controller.admin;

import com.ylguohe.admin.entity.VehicleDestination;
import com.ylguohe.admin.service.VehicleDestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/vehicles")
@RequiredArgsConstructor
public class AdminVehicleController {

    private final VehicleDestinationService vehicleDestinationService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("vehicles", vehicleDestinationService.findAll());
        model.addAttribute("currentPage", "vehicles");
        return "admin/vehicles/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("vehicle", new VehicleDestination());
        model.addAttribute("currentPage", "vehicles");
        return "admin/vehicles/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("vehicle", vehicleDestinationService.findById(id));
        model.addAttribute("currentPage", "vehicles");
        return "admin/vehicles/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute VehicleDestination vehicle, RedirectAttributes ra) {
        vehicleDestinationService.save(vehicle);
        ra.addFlashAttribute("message", "目的地保存成功！");
        return "redirect:/admin/vehicles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        vehicleDestinationService.deleteById(id);
        ra.addFlashAttribute("message", "目的地已删除！");
        return "redirect:/admin/vehicles";
    }
}
