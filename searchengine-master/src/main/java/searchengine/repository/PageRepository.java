package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import searchengine.model.Page;
import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
    int countAllByPath(String path);
    Optional<Page> findByPath(String path);
    Optional<Page> findByPathAndSite_id(String path, int site_id);
    List<Page> findByPathStartingWith(String path);
    @Query(value = "SELECT COUNT(*) FROM `page` WHERE `site_id` =:site_id", nativeQuery = true)
    int countPagesBySiteId(@Param("site_id") int site_id);
}
