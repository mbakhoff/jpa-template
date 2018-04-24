package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Owner;

import javax.persistence.EntityManager;

// addowner <firstName> <lastName>
public class AddOwner implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 3 || !params[0].equals("addowner")) {
      return;
    }

    TransactionSupport.runInTransaction(entityManager, () -> {
      Owner owner = new Owner();
      owner.setFirstName(params[1]);
      owner.setLastName(params[2]);
      entityManager.persist(owner);
      System.out.println("added " + owner);
    });
  }
}
