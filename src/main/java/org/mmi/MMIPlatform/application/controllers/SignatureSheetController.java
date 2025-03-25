package org.mmi.MMIPlatform.application.controllers;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mmi.MMIPlatform.application.dto.SignatureSheetDto;
import org.mmi.MMIPlatform.application.services.SignatureSheetApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sheets")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class SignatureSheetController {

    private final SignatureSheetApplicationService signatureSheetApplicationService;

    @GetMapping("/all")
    public ResponseEntity<List<SignatureSheetDto>> getAllSignatureSheets() throws Exception {
        return ResponseEntity.ok(signatureSheetApplicationService.getSignatureSheetList());
    }

    @GetMapping("/module/{moduleName}")
    public ResponseEntity<List<SignatureSheetDto>> getSignatureSheetsByModule(@PathVariable String moduleName) throws Exception {
        return ResponseEntity.ok(signatureSheetApplicationService.getSignatureSheetByModuleName(moduleName));
    }

    @GetMapping("/promo/{promo}")
    public ResponseEntity<List<SignatureSheetDto>> getSignatureSheetsByPromo(@PathVariable String promo) throws Exception {
        return ResponseEntity.ok(signatureSheetApplicationService.getSignatureSheetListByPromo(promo));
    }

    @GetMapping("/{promo}/{numEtu}")
    public ResponseEntity<List<SignatureSheetDto>> getSignatureSheetListByPromoAndNumEtu(@PathVariable String promo, @PathVariable String numEtu) throws Exception {
        return ResponseEntity.ok(this.signatureSheetApplicationService.getSignatureSheetListByPromoAndNumEtu(promo, numEtu));
    }

    @GetMapping("/{moduleName}/{promo}/{createdAt}/{finishAt}")
    public ResponseEntity<SignatureSheetDto> getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(@PathVariable String moduleName, @PathVariable String promo, @PathVariable String createdAt, @PathVariable String finishAt) throws Exception {
        return ResponseEntity.ok(signatureSheetApplicationService.getSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, promo, createdAt, finishAt));
    }

    @PostMapping
    public ResponseEntity<SignatureSheetDto> postSignatureSheet(@RequestBody SignatureSheetDto signatureSheetDto) throws Exception {
        return ResponseEntity.ok(signatureSheetApplicationService.postSignatureSheet(signatureSheetDto));
    }

    @DeleteMapping("/{moduleName}/{promo}/{createdAt}/{finishAt}")
    public ResponseEntity<String> deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(@PathVariable String moduleName, @PathVariable String promo, @PathVariable String createdAt, @PathVariable String finishAt) throws Exception {
        return ResponseEntity.ok(signatureSheetApplicationService.deleteSignatureSheetByModuleNameAndPromoAndCreatedAtAndFinishAt(moduleName, promo, createdAt, finishAt));
    }

    @PutMapping("/{moduleName}/{promo}/{createdAt}/{finishAt}/{numEtu}")
    public ResponseEntity<String> justifyMissingForOneStudent(@PathVariable String moduleName, @PathVariable String promo, @PathVariable String createdAt, @PathVariable String finishAt, @PathVariable String numEtu) throws Exception {
        return ResponseEntity.ok(signatureSheetApplicationService.justifyMissingForOneStudent(moduleName, promo, createdAt, finishAt, numEtu));
    }

}
