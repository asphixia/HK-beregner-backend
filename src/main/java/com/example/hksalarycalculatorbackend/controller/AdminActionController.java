package com.example.hksalarycalculatorbackend.controller;

import com.example.hksalarycalculatorbackend.model.AdminAction;
import com.example.hksalarycalculatorbackend.service.AdminActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/adminactions")
public class AdminActionController {

        @Autowired
        private AdminActionService adminActionService;

        @GetMapping
        public ResponseEntity<List<AdminAction>> getAllAdminActions() {
                List<AdminAction> adminActions = adminActionService.getAllAdminActions();
                return ResponseEntity.ok(adminActions);
        }

}
