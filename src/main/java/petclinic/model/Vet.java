package petclinic.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Vet extends Person {

  @ElementCollection
  private Set<String> specialties = new HashSet<>();

  public Set<String> getSpecialties() {
    return Collections.unmodifiableSet(this.specialties);
  }

  public void addSpecialty(String specialty) {
    specialties.add(specialty);
  }

  @Override
  public String toString() {
    return super.toString() + " + Vet{" +
        "specialties=" + specialties +
        '}';
  }
}
