package org.mmi.MMIPlatform.application.controllers;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.mmi.MMIPlatform.application.services.XlsApplicationService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/api/v1/export")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class XlsController {

    private final XlsApplicationService xlsApplicationService;

    @Operation(summary = "Export matrix by promo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "matrix exported")})
    @GetMapping("/students")
    public ResponseEntity<byte[]> exportStudents(@RequestParam String promo, @RequestParam String semester) {
        try {
            byte[] excelData = this.xlsApplicationService.exportMatrixByPromo(promo, semester);
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String filename = String.format("%s_%s_%s_matrice.xlsx", promo, semester, date);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + filename);
            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
