package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Pet;
import petclinic.model.Vet;
import petclinic.model.Visit;

import javax.persistence.EntityManager;

// addvisit <petId> <vetId> <description>
public class AddVisit implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 4 || !params[0].equals("addvisit")) {
      return;
    }

    TransactionSupport.runInTransaction(entityManager, () -> {
      Pet pet = entityManager.find(Pet.class, Long.parseLong(params[1]));
      if (pet == null)
        throw new IllegalStateException("no such pet");

      Vet vet = entityManager.find(Vet.class, Long.parseLong(params[2]));
      if (vet == null)
        throw new IllegalStateException("no such vet");

      Visit visit = new Visit();
      visit.setPet(pet);
      visit.setVet(vet);
      visit.setDescription(params[3]);
      entityManager.persist(visit);
      System.out.println("added " + visit);
    });
  }
}
