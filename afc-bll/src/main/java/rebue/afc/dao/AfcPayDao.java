package rebue.afc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rebue.afc.jo.AfcPayJo;

public interface AfcPayDao extends JpaRepository<AfcPayJo, java.lang.Long> {
}
