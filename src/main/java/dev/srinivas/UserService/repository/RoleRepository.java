package dev.srinivas.UserService.repository;

import dev.srinivas.UserService.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Repository interface for managing Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

        /**
         * Finds all roles with the given IDs.
         * @param roleIds the list of role IDs to find
         * @return a set of roles with the given IDs
         */
        Set<Role> findAllByIdIn(List<Long> roleIds);
        /*
         * SQL equivalent: SELECT * FROM Role WHERE id IN (:roleIds)
         */
}