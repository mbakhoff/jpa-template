package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Pet;

import javax.persistence.EntityManager;

// petname <petId> <newName>
public class EditPetName implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 3 || !params[0].equals("petname")) {
      return;
    }

    TransactionSupport.runInTransaction(entityManager, () -> {
      Pet pet = entityManager.find(Pet.class, Long.parseLong(params[1]));
      if (pet == null)
        throw new IllegalStateException("no such pet");

      pet.setName(params[2]);
      System.out.println("name changed");
    });
  }
}
