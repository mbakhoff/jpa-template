package petclinic.actions;

import petclinic.model.Vet;

import javax.persistence.EntityManager;

public class AddVet extends TransactionalCommand {

  public AddVet() {
    super("addvet");
  }

  @Override
  protected void run(EntityManager entityManager, String[] params) throws Exception {
    if (params.length != 3) {
      System.out.println("addvet <firstName> <lastName>");
      return;
    }

    Vet vet = new Vet();
    vet.setFirstName(params[1]);
    vet.setLastName(params[2]);
    entityManager.persist(vet);
    System.out.println("added " + vet);
  }
}
