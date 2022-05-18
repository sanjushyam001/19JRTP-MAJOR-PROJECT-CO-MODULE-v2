package in.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.ashokit.entity.EDModel;



public interface EligdRepository extends JpaRepository<EDModel, Integer> {

	@Query("SELECT e FROM EDModel e WHERE e.caseNo=?1")
	public EDModel findEdModelByCaseNo(Integer caseNo); 
}
