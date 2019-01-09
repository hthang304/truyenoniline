package online.hthang.truyenonline.repository;

import online.hthang.truyenonline.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Huy Thang
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
