package petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Owner extends Person {

  // one Owner has many Pets
  @OneToMany(cascade = CascadeType.ALL)
  private Set<Pet> pets = new HashSet<>();

  public Set<Pet> getPets() {
    return Collections.unmodifiableSet(this.pets);
  }

  public void addPet(Pet pet) {
    pets.add(pet);
    pet.setOwner(this);
  }

  @Override
  public String toString() {
    return super.toString() + " + Owner{" +
        "pets=" + pets +
        '}';
  }
}
