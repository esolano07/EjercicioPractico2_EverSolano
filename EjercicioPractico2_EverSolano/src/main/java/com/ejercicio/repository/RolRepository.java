
package com.ejercicio.repository;

import com.ejercicio.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ever1
 */

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    
}
