package com.java_app.demo.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, String> {
  boolean existsBySourceAndTarget(String target, String s);

  Exchange findBySourceAndTarget(String source, String target);
}
