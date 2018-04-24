package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Pet;

import javax.persistence.EntityManager;
import java.util.List;

// viewpets
public class ViewPets implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    if (!command.equals("viewpets"))
      return;

    TransactionSupport.runInTransaction(entityManager, () -> {
      List<Pet> pets = entityManager.createQuery("from Pet", Pet.class).getResultList();
      if (pets.isEmpty()) {
        System.out.println("No pets.");
      }
      for (Pet pet : pets) {
        System.out.println(pet);
      }
    });
  }
}
