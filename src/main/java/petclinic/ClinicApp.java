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
  private final List<CommandHandler> commandHandlers;

  public ClinicApp(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
    this.commandHandlers = loadCommandHandlers();
  }

  public void runInteractive() throws Exception {
    try (Scanner console = new Scanner(System.in)) {
      while (true) {
        System.out.print("clinic > ");
        String command = console.nextLine();
        if (command.equals(":q"))
          break;

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
          for (CommandHandler commandHandler : commandHandlers) {
            commandHandler.handle(entityManager, command);
          }
        } finally {
          entityManager.close();
        }
      }
    }
  }

  private List<CommandHandler> loadCommandHandlers() {
    return Arrays.asList(
        new ViewVets(),
        new AddVet(),
        new AddSpeciality()
        // TODO add your actions here
    );
  }
}
