package petclinic.actions;

import petclinic.CommandHandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public abstract class TransactionalCommand implements CommandHandler {

  private final String commandPrefix;

  public TransactionalCommand(String commandPrefix) {
    this.commandPrefix = commandPrefix;
  }

  @Override
  public void handleCommand(EntityManager entityManager, String command) throws Exception {
    if (!command.startsWith(commandPrefix))
      return;

    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try {
      run(entityManager, command.split(" "));
      transaction.commit();
    } catch (Exception e) {
      if (transaction.isActive())
        transaction.rollback();
      throw e;
    }
  }

  protected abstract void run(EntityManager entityManager, String[] params) throws Exception;

}
