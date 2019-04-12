package petclinic.actions;

import petclinic.CommandHandler;
import petclinic.TransactionSupport;
import petclinic.model.Vet;

import javax.persistence.EntityManager;

// addspec <vet-id> <speciality>
public class AddSpeciality implements CommandHandler {

  @Override
  public void handle(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 3 || !params[0].equals("addspec")) {
      return;
    }

    TransactionSupport.runInTransaction(entityManager, () -> {
      Long id = Long.parseLong(params[1]);
      String speciality = params[2];
      Vet vet = entityManager.find(Vet.class, id);
      if (vet == null) {
        System.out.println("No vets matched.");
        return;
      }
      vet.addSpecialty(speciality);
    });
  }
}
