package pl.trollcraft.bending.magic.ability;

import pl.trollcraft.bending.magic.element.Element;
import pl.trollcraft.bending.help.locale.Localized;

public class Ability {

    private String id;
    private Element element;
    private Localized name;
    private Localized desc;

    // -------- -------- -------- --------

    public Ability (String id) {
        this.id = id;
        name = new Localized();
        desc = new Localized();
    }

    public Ability (String id, Element element, Localized name, Localized desc) {
        this.id = id;
        this.element = element;
        this.name = name;
        this.desc = desc;
    }

    // -------- -------- -------- --------

    public String getId() { return id; }
    public Element getElement() { return element; }

    public String getName(String locale) { return name.text(locale); }
    public String getDesc(String locale) { return desc.text(locale); }

    public Localized getName() { return name; }
    public Localized getDesc() { return desc; }

    public void setElement(Element element) { this.element = element; }

}
