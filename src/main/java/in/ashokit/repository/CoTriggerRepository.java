package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.ashokit.entity.CoTriggerModel;

public interface CoTriggerRepository extends JpaRepository<CoTriggerModel, Integer>{

	@Query("SELECT t  FROM CoTriggerModel t WHERE t.caseNo=?1")
	public CoTriggerModel findByCaseNo(Integer caseNo);
}
