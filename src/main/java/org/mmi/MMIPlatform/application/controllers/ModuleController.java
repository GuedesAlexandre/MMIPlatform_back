package org.mmi.MMIPlatform.application.controllers;


import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.ModuleDto;
import org.mmi.MMIPlatform.application.services.ModuleApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/module")
public class ModuleController {

        private final ModuleApplicationService moduleApplicationService;

        @GetMapping("/all")
        public ResponseEntity<List<ModuleDto>> getAllModules() {
            return ResponseEntity.ok(moduleApplicationService.getAllModules());
        }


}
