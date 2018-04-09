package petclinic;

import javax.persistence.EntityManager;

public interface CommandHandler {
  void handle(EntityManager entityManager, String command) throws Exception;
}
