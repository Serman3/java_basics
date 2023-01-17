package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.model.Site;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {


    /*@Transactional
    @Modifying
    @Query(value = "UPDATE site set status =:status, status_time =:statusTime", nativeQuery = true)
    void updateStatusAndStatusTime(@Param("status") Status status, @Param("statusTime") LocalDateTime statusTime);*/

}
