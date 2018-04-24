package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Pet;

import javax.persistence.EntityManager;
import java.util.List;

// petsof <ownerId>
public class ViewPetsOfOwner implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 2 || !params[0].equals("petsof")) {
      return;
    }

    TransactionSupport.runInTransaction(entityManager, () -> {
      List<Pet> pets = entityManager
          .createQuery("from Pet p where p.owner.id = :id", Pet.class)
          .setParameter("id", Long.parseLong(params[1]))
          .getResultList();

      if (pets.isEmpty()) {
        System.out.println("No pets.");
      }
      for (Pet pet : pets) {
        System.out.println(pet);
      }
    });
  }
}
