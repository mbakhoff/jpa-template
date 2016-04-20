package petclinic;

import javax.persistence.EntityManager;

public interface Processor {
  void process(EntityManager entityManager, String command) throws Exception;
}
