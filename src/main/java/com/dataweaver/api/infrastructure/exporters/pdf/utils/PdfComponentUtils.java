package com.dataweaver.api.infrastructure.exporters.pdf.utils;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.*;

public class PdfComponentUtils {

    public static void addHeaderStyle(PdfPCell cell) {
        cell.setPadding(5);
        cell.setBackgroundColor(Color.decode("#F5F5F5"));
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(2);
        cell.setBorderColor(Color.LIGHT_GRAY);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
    }

    public static void addCellStyle(PdfPCell cell) {
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(Color.LIGHT_GRAY);
        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
    }

    public static Font mountFont(Integer fontSize, Color color) {
        Font font = FontFactory.getFont("sansserif", fontSize);
        font.setColor(color);

        return font;
    }

    public static PdfPTable mountTable(Integer tableSize) {
        PdfPTable table = new PdfPTable(tableSize);
        table.setWidthPercentage(100f);

        return table;
    }

}