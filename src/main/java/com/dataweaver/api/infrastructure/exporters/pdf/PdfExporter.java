package com.dataweaver.api.infrastructure.exporters.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.dataweaver.api.infrastructure.exceptions.ApplicationGenericsException;
import com.dataweaver.api.infrastructure.exporters.pdf.bean.PdfLine;
import com.dataweaver.api.infrastructure.exporters.pdf.enums.EnumAlignment;
import com.dataweaver.api.infrastructure.exporters.pdf.utils.PdfComponentUtils;
import com.dataweaver.api.utils.StringUtil;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
public class PdfExporter {

    private final String title;

    private final List<PdfLine> headers;

    private final List<PdfLine> data;

    private List<PdfLine> totalizers;

    private int headerTableFontSize = 10;

    private int dataTableFontSize = 10;

    public PdfExporter(String title, List<PdfLine> headers, List<PdfLine> data, int headerTableFontSize, int dataTableFontSize) {
        this.title = title;
        this.headers = headers;
        this.data = data;
        this.headerTableFontSize = headerTableFontSize;
        this.dataTableFontSize = dataTableFontSize;
    }

    public PdfExporter(String title, List<PdfLine> headers, List<PdfLine> data) {
        this.title = title;
        this.headers = headers;
        this.data = data;
    }

    public ByteArrayOutputStream export() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
            PdfPageEvent event = new PdfPageEvent(headers, title, headerTableFontSize, totalizers);

            pdfWriter.setPageEvent(event);

            document.open();

            PdfPTable table = event.mountTable();
            mountTableData(table);

            document.add(table);
            document.close();

            return baos;
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    public void mountTableData(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        PdfComponentUtils.addCellStyle(cell);

        data.forEach(dataValue -> {
            cell.setPhrase(new Phrase(dataValue.getValue(), PdfComponentUtils.mountFont(dataTableFontSize, Color.BLACK)));
            cell.setHorizontalAlignment(dataValue.getAlign());

            table.addCell(cell);
        });
    }

    public static ResponseEntity<ByteArrayResource> returnResponse(PdfExporter pdfExporter, String filename) {
        ByteArrayOutputStream pdf = pdfExporter.export();
        ByteArrayResource resource = new ByteArrayResource(pdf.toByteArray());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header("Content-Disposition", "attachment; filename=" + filename)
                .body(resource);
    }

    public static Integer getAlignment(String align) {
        align = (StringUtil.isNotNullOrEmpty(align) ? align : "CENTER").toUpperCase().trim();

        return EnumAlignment.valueOf(align).getElement();
    }

    public static List<PdfLine> mountWithDefaultAlign(String[] values) {
        return mountLines(Arrays.stream(values));
    }

    public static List<PdfLine> mountWithDefaultAlign(List<String> values) {
        return mountLines(values.stream());
    }

    private static List<PdfLine> mountLines(Stream<String> stream) {
        return stream.map(PdfLine::new).collect(Collectors.toList());
    }

}