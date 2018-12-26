package rebue.afc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rebue.afc.jo.AfcAccountJo;

public interface AfcAccountDao extends JpaRepository<AfcAccountJo, java.lang.Long> {
}
