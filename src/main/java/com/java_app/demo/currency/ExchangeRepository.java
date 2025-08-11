package com.java_app.demo.currency;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, String> {

  Optional<Exchange> findBySourceAndTarget(String source, String target);
}
