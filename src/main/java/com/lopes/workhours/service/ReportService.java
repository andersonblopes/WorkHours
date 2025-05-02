package com.lopes.workhours.service;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.filter.WorkLogFilter;
import com.lopes.workhours.domain.repository.WorkLogRepository;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final WorkLogRepository repository;

    public byte[] generateCSV(final WorkLogFilter filter, final Pageable pageable) {

        List<WorkLog> filteredLogs = repository.findByFilter(filter, pageable).getContent();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("ID,Execution Date,Duration,Apartment,Employee,Value (€) \n");

        BigDecimal totalCurrency = BigDecimal.ZERO;

        for (WorkLog log : filteredLogs) {
            csvBuilder
                    .append(log.getId())
                    .append(",");
            csvBuilder
                    .append(log.getExecutionDate())
                    .append(",");
            csvBuilder
                    .append(log.getDuration())
                    .append(",");
            csvBuilder.append("\"")
                    .append(log.getApartment().descriptionFormated())
                    .append("\",");
            csvBuilder.append("\"")
                    .append(log.getEmployee().getNickName())
                    .append("\",");
            csvBuilder
                    .append(log.getTotal())
                    .append(" € \n");

            totalCurrency = totalCurrency.add(log.getTotal());
        }

        // Add a blank line and then the total
        csvBuilder.append(",,,,Total,")
                .append(totalCurrency.setScale(2, RoundingMode.HALF_UP))
                .append(" € \n");

        return csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] generatePdf(final WorkLogFilter filter, final Pageable pageable) {

        List<WorkLog> logs = repository.findByFilter(filter, pageable).getContent();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);

            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Work Logs", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);

            // Table
            PdfPTable table = new PdfPTable(5); // 5 columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            addTableHeader(table);
            addRowsWithTotal(table, logs);

            document.add(table);

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void addTableHeader(PdfPTable table) {
        table.addCell("Execution Date");
        table.addCell("Duration");
        table.addCell("Apartment");
        table.addCell("Employee");
        table.addCell("Value (€)");
    }

    private void addRowsWithTotal(PdfPTable table, List<WorkLog> logs) {
        BigDecimal totalCurrency = BigDecimal.ZERO;

        for (WorkLog log : logs) {
            table.addCell(String.valueOf(log.getExecutionDate()));
            table.addCell(String.valueOf(log.getDuration()));
            table.addCell(log.getApartment().descriptionFormated());
            table.addCell(log.getEmployee().getNickName());
            table.addCell(log.getTotal().setScale(2, RoundingMode.HALF_UP) + " €");

            totalCurrency = totalCurrency.add(log.getTotal());
        }

        // Add total row
        PdfPCell emptyCell = new PdfPCell(new Phrase(""));
        emptyCell.setColspan(3);
        emptyCell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(emptyCell);

        PdfPCell totalLabelCell = new PdfPCell(new Phrase("Total:"));
        totalLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalLabelCell);

        PdfPCell totalValueCell = new PdfPCell(new Phrase(totalCurrency.setScale(2, RoundingMode.HALF_UP) + " €"));
        totalValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalValueCell);
    }


}
