package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Owner;
import petclinic.model.Pet;

import javax.persistence.EntityManager;
import java.time.LocalDate;

// pet2owner <ownerId> <petName> <petBirtyday>
public class AddPetToOwner implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 4 || !params[0].equals("pet2owner")) {
      return;
    }

    TransactionSupport.runInTransaction(entityManager, () -> {
      Owner owner = entityManager.find(Owner.class, Long.parseLong(params[1]));
      if (owner == null)
        throw new IllegalStateException("no such owner");

      Pet pet = new Pet();
      pet.setName(params[2]);
      pet.setBirthDate(LocalDate.parse(params[3]));
      pet.setOwner(owner);
      entityManager.persist(pet);
      owner.addPet(pet);
      System.out.println("added " + pet);
    });
  }
}
