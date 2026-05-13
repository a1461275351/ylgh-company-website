package com.ylguohe.admin.controller.api;

import com.ylguohe.admin.entity.ContactInfo;
import com.ylguohe.admin.entity.ContactInquiry;
import com.ylguohe.admin.entity.enums.ContactType;
import com.ylguohe.admin.service.ContactInfoService;
import com.ylguohe.admin.service.ContactInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactApiController {

    private final ContactInfoService contactInfoService;
    private final ContactInquiryService contactInquiryService;

    @GetMapping("/info")
    public ResponseEntity<List<ContactInfo>> getMainContact() {
        return ResponseEntity.ok(contactInfoService.findByType(ContactType.MAIN));
    }

    @GetMapping("/overseas")
    public ResponseEntity<List<ContactInfo>> getOverseasContacts() {
        return ResponseEntity.ok(contactInfoService.findByType(ContactType.OVERSEAS));
    }

    @PostMapping("/inquiry")
    public ResponseEntity<ContactInquiry> submitInquiry(@RequestBody ContactInquiry inquiry) {
        return ResponseEntity.ok(contactInquiryService.save(inquiry));
    }
}
