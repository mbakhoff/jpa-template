package petclinic;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

  // https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html
  // petClinic defined in META-INF/persistence.xml
  public static void main(String[] args) throws Exception {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("petClinic");
    try {
      new ClinicApp(entityManagerFactory).runInteractive();
    } finally {
      entityManagerFactory.close();
    }
  }
}
