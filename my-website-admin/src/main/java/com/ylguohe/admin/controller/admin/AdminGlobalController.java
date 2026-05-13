package com.ylguohe.admin.controller.admin;

import com.ylguohe.admin.entity.LogisticsRoute;
import com.ylguohe.admin.entity.Warehouse;
import com.ylguohe.admin.entity.enums.LogisticsType;
import com.ylguohe.admin.service.LogisticsRouteService;
import com.ylguohe.admin.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/global")
@RequiredArgsConstructor
public class AdminGlobalController {

    private final LogisticsRouteService routeService;
    private final WarehouseService warehouseService;

    @GetMapping("/routes")
    public String routeList(@RequestParam(required = false) String type, Model model) {
        if (type != null) {
            model.addAttribute("routes", routeService.findByType(LogisticsType.valueOf(type.toUpperCase())));
            model.addAttribute("selectedType", type);
        } else {
            model.addAttribute("routes", routeService.findAll());
        }
        model.addAttribute("types", LogisticsType.values());
        model.addAttribute("currentPage", "routes");
        return "admin/global/routes";
    }

    @GetMapping("/routes/new")
    public String routeCreateForm(Model model) {
        model.addAttribute("route", new LogisticsRoute());
        model.addAttribute("types", LogisticsType.values());
        model.addAttribute("currentPage", "routes");
        return "admin/global/route-form";
    }

    @GetMapping("/routes/{id}/edit")
    public String routeEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("route", routeService.findById(id));
        model.addAttribute("types", LogisticsType.values());
        model.addAttribute("currentPage", "routes");
        return "admin/global/route-form";
    }

    @PostMapping("/routes/save")
    public String saveRoute(@ModelAttribute LogisticsRoute route, RedirectAttributes ra) {
        routeService.save(route);
        ra.addFlashAttribute("message", "物流路线保存成功！");
        return "redirect:/admin/global/routes";
    }

    @PostMapping("/routes/{id}/delete")
    public String deleteRoute(@PathVariable Long id, RedirectAttributes ra) {
        routeService.deleteById(id);
        ra.addFlashAttribute("message", "物流路线已删除！");
        return "redirect:/admin/global/routes";
    }

    @GetMapping("/warehouses")
    public String warehouseList(Model model) {
        model.addAttribute("warehouses", warehouseService.findAll());
        model.addAttribute("currentPage", "warehouses");
        return "admin/global/warehouses";
    }

    @GetMapping("/warehouses/new")
    public String warehouseCreateForm(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        model.addAttribute("currentPage", "warehouses");
        return "admin/global/warehouse-form";
    }

    @GetMapping("/warehouses/{id}/edit")
    public String warehouseEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("warehouse", warehouseService.findById(id));
        model.addAttribute("currentPage", "warehouses");
        return "admin/global/warehouse-form";
    }

    @PostMapping("/warehouses/save")
    public String saveWarehouse(@ModelAttribute Warehouse warehouse, RedirectAttributes ra) {
        warehouseService.save(warehouse);
        ra.addFlashAttribute("message", "仓库保存成功！");
        return "redirect:/admin/global/warehouses";
    }

    @PostMapping("/warehouses/{id}/delete")
    public String deleteWarehouse(@PathVariable Long id, RedirectAttributes ra) {
        warehouseService.deleteById(id);
        ra.addFlashAttribute("message", "仓库已删除！");
        return "redirect:/admin/global/warehouses";
    }
}
