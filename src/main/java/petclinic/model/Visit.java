package petclinic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Visit {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Pet pet;

  @ManyToOne
  private Vet vet;

  private String description;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Pet getPet() {
    return pet;
  }

  public void setPet(Pet pet) {
    this.pet = pet;
  }

  public Vet getVet() {
    return vet;
  }

  public void setVet(Vet vet) {
    this.vet = vet;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Visit{" +
        "id=" + id +
        ", pet=" + pet +
        ", vet=" + vet +
        ", description='" + description + '\'' +
        '}';
  }
}
