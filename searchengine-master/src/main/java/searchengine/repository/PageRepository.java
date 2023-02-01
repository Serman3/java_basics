package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.model.Page;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
    int countAllByPath(String path);
    List<Page> findByPathStartingWith(String path);

    /*@Transactional
    @Modifying
    @Query(value = "UPDATE site set status =:status, status_time =:statusTime", nativeQuery = true)
    List<Page> getPagesFromOneSite(@Param("status") Status status, @Param("statusTime") LocalDateTime statusTime);*/
}
