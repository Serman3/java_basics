package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.Lemma;

import java.util.List;
import java.util.Optional;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Integer> {
    Optional<Lemma> findFirstByLemma(String lemma);

    @Query(value = "SELECT COUNT(*) FROM `lemma` WHERE `site_id` =:site_id", nativeQuery = true)
    int countLemmasBySiteId(@Param("site_id") int site_id);

    @Query(value = "SELECT * FROM lemma where lemma.lemma IN :searched_lemma" +
    "and lemma.frequency < 100 and lemma.site_id =:id order by lemma.frequency", nativeQuery = true)
    List<Lemma> getRelevantLemmas(@Param("searched_lemma") String searched_lemma, @Param("id") int site_id);
}
