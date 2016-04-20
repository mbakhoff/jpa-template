package petclinic;

import petclinic.actions.AddSpeciality;
import petclinic.actions.AddVet;
import petclinic.actions.ViewVets;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClinicApp {
  private final EntityManagerFactory entityManagerFactory;
  private final List<Processor> processors;

  public ClinicApp(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
    this.processors = loadProcessors();
  }

  public void run(Scanner console) throws Exception {
    while (true) {
      System.out.print("clinic > ");
      String command = console.nextLine();
      if (command.equals(":q"))
        break;

      EntityManager entityManager = entityManagerFactory.createEntityManager();
      try {
        for (Processor processor : processors) {
          processor.process(entityManager, command);
        }
      } finally {
        entityManager.close();
      }
    }
  }

  private List<Processor> loadProcessors() {
    return Arrays.asList(
        new ViewVets(),
        new AddVet(),
        new AddSpeciality()
    );
  }
}
