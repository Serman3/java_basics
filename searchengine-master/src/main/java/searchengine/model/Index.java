package searchengine.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`index`")
@NoArgsConstructor
@Getter
@Setter
public class Index {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int id;

    //@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "page_id", nullable = false)
    private List<Page> pageId;

    //@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "lemma_id", nullable = false)
    private List<Lemma> lemmaId;

    @Column(name = "`rank`",nullable = false)
    private float rank;
}
