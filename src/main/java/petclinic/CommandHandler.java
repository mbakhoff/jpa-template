package petclinic;

import javax.persistence.EntityManager;

public interface CommandHandler {
  void handleCommand(EntityManager entityManager, String command) throws Exception;
}
