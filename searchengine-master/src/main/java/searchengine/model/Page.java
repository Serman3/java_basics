package searchengine.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "page")
@Getter
@Setter
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Site.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id", nullable = false)
    private Site site;

    @Column(columnDefinition = "TEXT NOT NULL, UNIQUE KEY pathIndex (path(512),site_id)")
    private String path;

    @Column(nullable = false)
    private int code;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "page", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    private List<Index> indexList;

    public Page(){};

    public Page(Site site, String path, int code, String content) {
        this.site = site;
        this.path = path;
        this.code = code;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return id == page.id && code == page.code && site.equals(page.site) && path.equals(page.path) && content.equals(page.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, site, path, code, content);
    }
}
