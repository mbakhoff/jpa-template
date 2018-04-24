package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Visit;

import javax.persistence.EntityManager;
import java.util.List;

// viewvisits <petId>
public class ViewVisits implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 2 || !params[0].equals("viewvisits")) {
      return;
    }

    TransactionSupport.runInTransaction(entityManager, () -> {
      List<Visit> visits = entityManager
          .createQuery("from Visit v where v.pet.id = :id", Visit.class)
          .setParameter("id", Long.parseLong(params[1]))
          .getResultList();

      if (visits.isEmpty()) {
        System.out.println("No visits.");
      }
      for (Visit visit : visits) {
        System.out.println(visit);
      }
    });
  }
}
