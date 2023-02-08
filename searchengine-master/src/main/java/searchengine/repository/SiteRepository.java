package searchengine.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.Site;
import searchengine.model.Status;
import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {
    Optional<Site> findByUrl(String url);
    Site getByUrl(String url);
    List<Site> findByStatus(Status status);
    /*@Transactional
    @Modifying
    @Query(value = "UPDATE site set status =:status, status_time =:statusTime", nativeQuery = true)
    void updateStatusAndStatusTime(@Param("status") Status status, @Param("statusTime") LocalDateTime statusTime);*/

    /*@Query(value = "SELECT i.page_id AS id, sum(i.rank) AS sum_rank " +
            "FROM lemma l JOIN index i ON l.id = i.lemma_id " +
            "WHERE l.lemma In (:lemmaList) " +
            "GROUP BY i.page_id " +
            "HAVING count(i.page_id) = :countPage " +
            "order by Sum_rank DESC " +
            "limit :limit offset :offset",
            countQuery = "SELECT count(i.page_id) " +
                    "FROM lemma l JOIN index i ON l.id = i.lemma_id " +
                    "WHERE l.lemma In (:lemmaList) " +
                    "GROUP BY i.page_id " +
                    "HAVING count(i.page_id) = :countPage", nativeQuery = true)
    List<Search> searchPage(@Param("lemmaList") List<String> lemmaList,
                            @Param("countPage") int countLemma,
                            @Param("limit") int limit,
                            @Param("offset") int offset);

    @Query(value = "SELECT i.page_id AS id, sum(i.rank) AS sum_rank " +
            "FROM lemma l JOIN index i ON l.id = i.lemma_id " +
            "WHERE l.lemma In (:lemmaList) AND l.site_id = :site " +
            "GROUP BY i.page_id " +
            "HAVING count(i.page_id) = :countPage " +
            "order by Sum_rank DESC " +
            "limit :limit offset :offset",
            countQuery = "SELECT count(i.page_id) " +
                    "FROM lemma l JOIN index i ON l.id = i.lemma_id " +
                    "WHERE l.lemma In (:lemmaList) " +
                    "GROUP BY i.page_id " +
                    "HAVING count(i.page_id) = :countPage", nativeQuery = true)
    List<Search> searchPage(@Param("lemmaList") List<String> lemmaList,
                            @Param("countPage") int countLemma,
                            @Param("site") int siteId,
                            @Param("limit") int limit,
                            @Param("offset") int offset);

    @Query(value = "SELECT count(c.page_id) FROM (" +
            "SELECT i.page_id " +
            "FROM lemma l JOIN index i ON l.id = i.lemma_id " +
            "WHERE l.lemma In (:lemmaList) " +
            "GROUP BY i.page_id " +
            "HAVING count(i.page_id) = :countPage) c", nativeQuery = true)
    int countSearchPage(@Param("lemmaList") List<String> lemmaList,
                        @Param("countPage") int countLemma);

    @Query(value = "SELECT count(c.page_id) FROM (" +
            "SELECT i.page_id " +
            "FROM lemma l JOIN index i ON l.id = i.lemma_id " +
            "WHERE l.lemma In (:lemmaList) AND l.site_id = :site " +
            "GROUP BY i.page_id " +
            "HAVING count(i.page_id) = :countPage) c", nativeQuery = true)
    int countSearchPage(@Param("lemmaList") List<String> lemmaList,
                        @Param("countPage") int countLemma, @Param("site") int siteId);*/
}
