package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
