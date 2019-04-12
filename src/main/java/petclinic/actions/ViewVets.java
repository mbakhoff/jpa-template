package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Vet;

import javax.persistence.EntityManager;
import java.util.List;

// viewvets
public class ViewVets implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    if (!command.equals("viewvets"))
      return;

    TransactionSupport.runInTransaction(entityManager, () -> {
      List<Vet> vets = entityManager.createQuery("from Vet", Vet.class).getResultList();
      if (vets.isEmpty()) {
        System.out.println("No vets.");
      }
      for (Vet vet : vets) {
        System.out.println(vet);
      }
    });
  }

}
