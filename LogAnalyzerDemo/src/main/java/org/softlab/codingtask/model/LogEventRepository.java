/**
 * @author Paweł Włoch ©SoftLab
 * @date 14.11.2018
 */
package org.softlab.codingtask.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEventRepository extends JpaRepository<LogEvent,String> {
}
