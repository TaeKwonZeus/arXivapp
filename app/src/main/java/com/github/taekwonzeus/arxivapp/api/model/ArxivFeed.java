package com.github.taekwonzeus.arxivapp.api.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "feed", namespace = "http://www.w3.org/2005/Atom")
@Data
@NoArgsConstructor
public class ArxivFeed {
    @XmlElement(name = "title", namespace = "http://www.w3.org/2005/Atom")
    private String title;

    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private ArxivLink link;

    @XmlElement(name = "updated", namespace = "http://www.w3.org/2005/Atom")
    private String updated;

    @XmlElement(name = "entry", namespace = "http://www.w3.org/2005/Atom")
    private List<ArxivEntry> entries;
}
