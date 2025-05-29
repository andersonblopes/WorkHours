package com.lopes.workhours.controller;

import com.lopes.workhours.domain.filter.WorkLogFilter;
import com.lopes.workhours.service.ReportService;
import com.lopes.workhours.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/export")
public class ReportController {

    private final ReportService service;
    
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> exportPdf(final @ModelAttribute WorkLogFilter filter, final Pageable pageable) {

        byte[] pdfBytes = service.generatePdf(filter, pageable);

        String filename = AppUtil.generateFileName("pdf");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
