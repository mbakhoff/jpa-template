package petclinic;

import petclinic.actions.AddOwner;
import petclinic.actions.AddPetToOwner;
import petclinic.actions.AddSpeciality;
import petclinic.actions.AddVet;
import petclinic.actions.AddVisit;
import petclinic.actions.DeletePet;
import petclinic.actions.EditPetName;
import petclinic.actions.ViewPets;
import petclinic.actions.ViewPetsOfOwner;
import petclinic.actions.ViewVets;
import petclinic.actions.ViewVisits;

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

  private List<CommandHandler> loadCommandHandlers() {
    return Arrays.asList(
        new ViewVets(),
        new AddVet(),
        new AddSpeciality(),
        new AddOwner(),
        new AddPetToOwner(),
        new ViewPets(),
        new ViewPetsOfOwner(),
        new EditPetName(),
        new AddVisit(),
        new ViewVisits(),
        new DeletePet()
    );
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
}
