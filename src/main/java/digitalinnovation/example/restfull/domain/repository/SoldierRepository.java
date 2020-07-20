package digitalinnovation.example.restfull.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import digitalinnovation.example.restfull.domain.entity.Soldier;

public interface SoldierRepository extends JpaRepository<Soldier, Long> {}
