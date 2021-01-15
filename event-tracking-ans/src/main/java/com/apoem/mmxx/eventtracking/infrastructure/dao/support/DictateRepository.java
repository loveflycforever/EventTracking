package com.apoem.mmxx.eventtracking.infrastructure.dao.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author papafan
 */
@Repository
public interface DictateRepository extends JpaRepository<Dictate,Integer> {
}