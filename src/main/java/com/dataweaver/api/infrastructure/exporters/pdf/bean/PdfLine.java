package com.dataweaver.api.infrastructure.exporters.pdf.bean;

import com.dataweaver.api.infrastructure.exporters.pdf.enums.EnumAlignment;
import lombok.Data;

@Data
public class PdfLine {

    public PdfLine(String value, Integer align) {
        this.value = value;
        this.align = align;
    }

    public PdfLine(String value) {
        this.value = value;
        this.align = EnumAlignment.getDefault();
    }

    private String value;

    private Integer align;

}
