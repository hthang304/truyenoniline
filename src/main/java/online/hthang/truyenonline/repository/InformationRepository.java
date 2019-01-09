package online.hthang.truyenonline.repository;

import online.hthang.truyenonline.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Huy Thang
 */
@Repository
public interface InformationRepository extends JpaRepository<Information, Integer> {

    public Information findFirstByOrderByInfoIDDesc();
}
