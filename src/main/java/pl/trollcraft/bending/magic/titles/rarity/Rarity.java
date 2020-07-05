package pl.trollcraft.bending.magic.titles.rarity;

import pl.trollcraft.bending.help.locale.Localized;

public class Rarity {

    private String id;

    private Localized name;
    private Localized desc;

    private String lightColor;
    private String darkColor;

    // -------- -------- -------- --------

    public Rarity (String id) {
        this.id = id;
        name = new Localized();
        desc = new Localized();
    }

    public Rarity(String id, Localized name, Localized desc, String lightColor, String darkColor) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.lightColor = lightColor;
        this.darkColor = darkColor;
    }

    // -------- -------- -------- --------

    public String getId() { return id; }
    public Localized getName() { return name; }
    public Localized getDesc() { return desc; }
    public String getLightColor() { return lightColor; }
    public String getDarkColor() { return darkColor; }

    public String getName(String locale) { return name.text(locale); }
    public String getDesc(String locale) { return desc.text(locale); }

    public void setName(Localized name) { this.name = name; }
    public void setDesc(Localized desc) { this.desc = desc; }
    public void setLightColor(String lightColor) { this.lightColor = lightColor; }
    public void setDarkColor(String darkColor) { this.darkColor = darkColor; }
}
