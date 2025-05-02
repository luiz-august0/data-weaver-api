package com.dataweaver.api.infrastructure.exporters.pdf.bean;

import lombok.Data;

@Data
public class PdfTotalizer {

    private String header;

    private String value;

    public PdfTotalizer(String header, String value) {
        this.header = header;
        this.value = value;
    }

}
