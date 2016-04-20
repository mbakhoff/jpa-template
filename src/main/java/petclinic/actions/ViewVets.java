package petclinic.actions;

import petclinic.model.Vet;

import javax.persistence.EntityManager;
import java.util.List;

public class ViewVets extends TransactionalCommand {

  public ViewVets() {
    super("viewvets");
  }

  @Override
  protected void run(EntityManager entityManager, String[] params) throws Exception {
    List<Vet> vets = entityManager.createQuery("from Vet", Vet.class).getResultList();
    if (vets.isEmpty()) {
      System.out.println("No vets.");
    }
    for (Vet vet : vets) {
      System.out.println(vet);
    }
  }

}
