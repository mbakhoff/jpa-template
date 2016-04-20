package petclinic;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {

  // https://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/Hibernate_User_Guide.html
  // petClinic defined in META-INF/persistence.xml
  public static void main(String[] args) throws Exception {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("petClinic");
    try {
      Scanner console = new Scanner(System.in);
      new ClinicApp(entityManagerFactory).run(console);
    } finally {
      entityManagerFactory.close();
    }
  }
}
