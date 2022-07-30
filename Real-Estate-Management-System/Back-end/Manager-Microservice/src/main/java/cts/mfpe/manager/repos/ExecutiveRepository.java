package cts.mfpe.manager.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cts.mfpe.manager.entities.Executive;

public interface ExecutiveRepository extends JpaRepository<Executive, Integer> {

	List<Executive> findByLocality(String locality);
}
