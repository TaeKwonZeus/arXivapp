package com.github.taekwonzeus.arxivapp.api.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class ArxivAuthor {
    @XmlElement(name = "name", namespace = "http://www.w3.org/2005/Atom")
    private String name;
}
