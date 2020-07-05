package pl.trollcraft.bending.magic.bender;

import pl.trollcraft.bending.magic.ability.Abilities;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.element.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Bender {

    private String name;

    private ArrayList<Ability> abilities;
    private Bindings bindings;

    // -------- -------- --------

    public Bender (String name, ArrayList<Ability> abilities) {
        this.name = name;
        this.abilities = abilities;
        bindings = new Bindings(new HashMap<>());
    }

    public Bender (String name) {
        this.name = name;
        abilities = new ArrayList<>();
        bindings = new Bindings(new HashMap<>());
    }

    // -------- -------- --------

    public String getName() { return name; }

    public List<Ability> getAbilities() { return abilities; }
    public List<Ability> getAbilities(Element element) {
        return abilities.stream()
                .filter(ab -> ab.getElement().equals(element))
                .collect(Collectors.toList());
    }

    public Bindings getBindings() { return bindings; }

    // -------- -------- --------

    public List<String> exportAbilities() {
        ArrayList<String> abilities = new ArrayList<>();
        this.abilities.forEach(a -> abilities.add(a.getId()));
        return abilities;
    }

    public void importAbilities(List<String> abilities) {
        abilities.forEach( abilityName -> { this.abilities.add(Abilities.find(abilityName)); } );
    }

}
