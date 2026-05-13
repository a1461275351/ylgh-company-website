package com.ylguohe.admin.controller.api;

import com.ylguohe.admin.entity.LogisticsRoute;
import com.ylguohe.admin.entity.Warehouse;
import com.ylguohe.admin.entity.enums.LogisticsType;
import com.ylguohe.admin.service.LogisticsRouteService;
import com.ylguohe.admin.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/global")
@RequiredArgsConstructor
public class GlobalApiController {

    private final LogisticsRouteService routeService;
    private final WarehouseService warehouseService;

    @GetMapping("/routes")
    public ResponseEntity<List<LogisticsRoute>> getRoutes(@RequestParam(required = false) String type) {
        if (type != null) {
            return ResponseEntity.ok(routeService.findActiveByType(LogisticsType.valueOf(type.toUpperCase())));
        }
        return ResponseEntity.ok(routeService.findAllActive());
    }

    @GetMapping("/warehouses")
    public ResponseEntity<List<Warehouse>> getWarehouses() {
        return ResponseEntity.ok(warehouseService.findAllActive());
    }
}
