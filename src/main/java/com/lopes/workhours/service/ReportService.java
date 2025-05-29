package com.lopes.workhours.service;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.filter.WorkLogFilter;
import com.lopes.workhours.domain.repository.WorkLogRepository;
import com.lopes.workhours.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final WorkLogRepository repository;
    private final MessageSource messageSource;

    public byte[] generatePdf(final WorkLogFilter filter, final Pageable pageable) {

        List<WorkLog> logs = repository.findByFilter(filter, pageable).getContent();

        try (PDDocument document = new PDDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float y = PDRectangle.A4.getHeight() - 50;
            float margin = 50;
            float width = page.getMediaBox().getWidth() - 2 * margin;

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.newLineAtOffset(margin, y);
            contentStream.showText("Work Logs - "
                    .concat(AppUtil.formatDate(filter.getStartDate()))
                    .concat("_")
                    .concat(AppUtil.formatDate(filter.getEndDate())));
            contentStream.endText();

            y -= 30;

            Locale locale = LocaleContextHolder.getLocale();

            String executionDate = messageSource.getMessage("app.lbl.execution_date", null, locale);
            String duration = messageSource.getMessage("app.lbl.duration", null, locale);
            String apartment = messageSource.getMessage("app.lbl.apartment", null, locale);
            String employee = messageSource.getMessage("app.lbl.employee", null, locale);
            String currencyValue = messageSource.getMessage("app.lbl.value", null, locale);
            String totalDurationLabel = messageSource.getMessage("app.lbl.total_duration", null, locale);
            String totalLabel = messageSource.getMessage("app.lbl.total", null, locale);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setNonStrokingColor(Color.BLACK);
            y = drawRow(contentStream, y, margin, width,
                    executionDate, duration, apartment, employee, currencyValue);

            contentStream.setFont(PDType1Font.HELVETICA, 12);
            BigDecimal total = BigDecimal.ZERO;
            Long totalDuration = 0L;
            DecimalFormat df = new DecimalFormat("0.00");

            for (WorkLog log : logs) {
                if (y <= 70) {
                    contentStream.close();

                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);

                    contentStream = new PDPageContentStream(document, page);
                    y = PDRectangle.A4.getHeight() - 50;

                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    y = drawRow(contentStream, y, margin, width,
                            executionDate, duration, apartment, employee, currencyValue);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                }

                String value = df.format(log.getTotal()).replace(".", ",") + " €";
                y = drawRow(contentStream, y, margin, width,
                        AppUtil.formatDate(log.getExecutionDate()),
                        "    ".concat(String.valueOf(log.getDuration())),
                        AppUtil.truncate(log.getApartment().getDescriptionFormated(), 18, "..."),
                        AppUtil.truncate(log.getEmployee().getNickName(), 18, "..."),
                        value);

                total = total.add(log.getTotal());
                totalDuration += log.getDuration();
            }

            y -= 10;
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            y = drawRow(
                    contentStream,
                    y,
                    margin,
                    width,
                    totalDurationLabel,
                    totalDuration.toString(), "",
                    totalLabel.concat(":"),
                    df.format(total.setScale(2,
                                    RoundingMode.HALF_UP))
                            .replace(".", ",") + " €");

            contentStream.close();

            document.save(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    // Helper function to draw each row of the table
    private float drawRow(PDPageContentStream contentStream, float y, float margin, float leading, String... cells) throws IOException {
        float[] colWidths = {100, 70, 100, 100, 80};
        float x = margin;

        contentStream.beginText();
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(x, y);

        for (int i = 0; i < cells.length; i++) {
            contentStream.showText(cells[i] != null ? cells[i] : "");
            contentStream.newLineAtOffset(colWidths[i], 0);
        }

        contentStream.endText();

        return y - leading;
    }
}
