package com.github.taekwonzeus.arxivapp.api.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import lombok.Data;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class ArxivLink {
    @XmlAttribute
    private String title = "";

    @XmlAttribute
    private String href;

    @XmlAttribute
    private String rel;

    @XmlAttribute
    private String type;
}
