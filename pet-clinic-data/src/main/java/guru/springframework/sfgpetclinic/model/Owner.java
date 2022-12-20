package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person{
    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        if (pets != null)
            this.pets = pets;
    }

    public Owner(String address, String city, String telephone, Set<Pet> pets) {
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @return a pet if pet name is already in use
     */
    public Pet getPet(String name) {
        return getPet(name, false);
    }

    /**
     * Return the Pet with the given id, or null if none found for this Owner.
     * @param id to test
     * @return a pet if pet id is already in use
     */
    public Pet getPet(Integer id) {
        for (Pet pet : getPets()) {
            if (!pet.isNew()) {
                Long compId = pet.getId();
                if (compId.equals(id)) {
                    return pet;
                }
            }
        }
        return null;
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     * @param name to test
     * @return a pet if pet name is already in use
     */
    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : getPets()) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName == null ? "" : compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }

}
