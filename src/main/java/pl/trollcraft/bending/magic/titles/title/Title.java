package pl.trollcraft.bending.magic.titles.title;

import pl.trollcraft.bending.help.locale.Localized;
import pl.trollcraft.bending.magic.titles.rarity.Rarity;

public class Title {

    private String id;

    private Localized name;
    private Localized desc;

    private Rarity rarity;

    // -------- -------- -------- --------

    public Title(String id) {
        this.id = id;
        name = new Localized();
        desc = new Localized();
    }


    public Title(String id, Localized name, Localized desc, Rarity rarity) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.rarity = rarity;
    }

    // -------- -------- -------- --------

    public String getId() { return id; }
    public Localized getName() { return name; }
    public Localized getDesc() { return desc; }
    public Rarity getRarity() { return rarity; }

    public String getName(String locale) { return name.text(locale); }
    public String getDesc(String locale) { return desc.text(locale); }

    public void setName(Localized name) { this.name = name; }
    public void setDesc(Localized desc) { this.desc = desc; }
    public void setRarity(Rarity rarity) { this.rarity = rarity; }

}
