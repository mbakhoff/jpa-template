package petclinic.actions;

import petclinic.model.Vet;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class AddSpeciality extends TransactionalCommand {
  public AddSpeciality() {
    super("addspec");
  }

  @Override
  protected void run(EntityManager entityManager, String[] params) throws Exception {
    if (params.length != 3) {
      System.out.println("addspec <vet-id> <speciality>");
      return;
    }

    Long id = Long.parseLong(params[1]);
    String speciality = params[2];
    try {
      Vet vet = entityManager.find(Vet.class, id);
      vet.addSpecialty(speciality);
    } catch (NoResultException e) {
      System.out.println("No vets matched.");
    }
  }
}
