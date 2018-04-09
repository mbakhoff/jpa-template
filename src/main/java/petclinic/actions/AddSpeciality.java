package petclinic.actions;

import petclinic.model.Vet;

import javax.persistence.EntityManager;

public class AddSpeciality extends TransactionalCommand {

  public AddSpeciality() {
    super("addspec");
  }

  @Override
  protected void handleInTransaction(EntityManager entityManager, String command) throws Exception {
    String[] params = command.split(" ");
    if (params.length != 3) {
      System.out.println("addspec <vet-id> <speciality>");
      return;
    }

    Long id = Long.parseLong(params[1]);
    String speciality = params[2];
    Vet vet = entityManager.find(Vet.class, id);
    if (vet == null) {
      System.out.println("No vets matched.");
      return;
    }
    vet.addSpecialty(speciality);
  }
}
