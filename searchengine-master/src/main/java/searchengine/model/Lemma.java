package searchengine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lemma")
@NoArgsConstructor
@Getter
@Setter
public class Lemma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Site.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id", nullable = false)
    private Site site;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String lemma;

    @Column(nullable = false)
    private int frequency;

    @OneToMany(mappedBy = "lemma", cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.REPLICATE)
    private List<Index> indexList;
}
