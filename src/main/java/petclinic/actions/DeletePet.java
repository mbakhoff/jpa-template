package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Pet;
import petclinic.model.Visit;

import javax.persistence.EntityManager;

// deletepet <petId>
public class DeletePet implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 2 || !params[0].equals("deletepet")) {
      return;
    }

    TransactionSupport.runInTransaction(entityManager, () -> {
      Pet pet = entityManager.find(Pet.class, Long.parseLong(params[1]));
      pet.getOwner().getPets().remove(pet);
      for (Visit visit : pet.getVisits()) {
        entityManager.remove(visit);
      }
      entityManager.remove(pet);
      System.out.println("deleted.");
    });
  }
}
