package searchengine.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "page", uniqueConstraints = { @UniqueConstraint(name = "UniqueSite_idAndPath", columnNames = { "site_id", "path" } ) })
@Getter
@Setter
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)*/
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id", nullable = false)
    private Site site;

    @Column(name = "path", length = 500,nullable = false/*columnDefinition = "TEXT NOT NULL, UNIQUE KEY pathIndex (path(512),site_id)"*/)
    private String path;

    @Column(nullable = false)
    private int code;

    @Column(length = 16777215, columnDefinition = "mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci", nullable = false)
    private String content;

    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL/*mappedBy = "page", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true*/)
    //@Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    @BatchSize(size = 2)
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
