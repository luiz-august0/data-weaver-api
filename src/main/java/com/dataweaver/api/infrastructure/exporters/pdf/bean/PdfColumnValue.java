package com.dataweaver.api.infrastructure.exporters.pdf.bean;

import com.dataweaver.api.infrastructure.exporters.pdf.enums.EnumAlignment;
import lombok.Data;

@Data
public class PdfColumnValue {

    private String value;

    private Integer align;

    public PdfColumnValue(String value, Integer align) {
        this.value = value;
        this.align = align;
    }

    public PdfColumnValue(String value) {
        this.value = value;
        this.align = EnumAlignment.getDefault();
    }

}
