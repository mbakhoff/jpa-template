package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Vet;

import javax.persistence.EntityManager;

// addvet <firstName> <lastName>
public class AddVet implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 3 || !params[0].equals("addvet")) {
      return;
    }

    TransactionSupport.runInTransaction(entityManager, () -> {
      Vet vet = new Vet();
      vet.setFirstName(params[1]);
      vet.setLastName(params[2]);
      entityManager.persist(vet);
      System.out.println("added " + vet);
    });
  }
}
