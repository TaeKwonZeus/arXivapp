package com.github.taekwonzeus.arxivapp.api.model;

import androidx.annotation.Nullable;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class ArxivEntry {
    @XmlElement(name = "title", namespace = "http://www.w3.org/2005/Atom")
    private String title;

    @XmlElement(name = "id", namespace = "http://www.w3.org/2005/Atom")
    private String id;

    @XmlElement(name = "summary", namespace = "http://www.w3.org/2005/Atom")
    private String summary;

    @XmlElement(name = "published", namespace = "http://www.w3.org/2005/Atom")
    private String published;

    @XmlElement(name = "author", namespace = "http://www.w3.org/2005/Atom")
    private List<ArxivAuthor> authors;

    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private List<ArxivLink> links;

    @Nullable
    String getPdfLink() {
        var link = links.stream().filter(l -> "application/pdf".equals(l.getType())).findFirst();
        if (link.isEmpty()) return null;
        return link.get().getHref();
    }
}
