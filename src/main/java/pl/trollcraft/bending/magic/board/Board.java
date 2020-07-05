package pl.trollcraft.bending.magic.board;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.magic.ability.Abilities;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.bender.Bender;
import pl.trollcraft.bending.magic.bender.Bindings;

public class Board {

    private Bender bender;
    private String locale;
    private boolean hidden;

    private Scoreboard scoreboard;
    private Objective objective;

    private StringComponent[] cache;

    // -------- -------- -------- --------

    public Board (Bender bender, boolean hidden, String locale) {
        this.bender = bender;
        this.hidden = hidden;
        this.locale = locale;

        cache = new StringComponent[9];

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective(Help.color("&e&l" + bender.getName()), "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score spacer = objective.getScore(Help.color("&b"));
        spacer.setScore(10);

        registerTeams();

        setHidden(hidden);
    }

    private void registerTeams() {

        Team t;
        String entry;

        for (int i = 1 ; i <= 9 ; i++) {
            t = scoreboard.registerNewTeam("slot" + i);
            entry = Help.color("&" + i);
            t.addEntry(entry);
            t.setPrefix(Help.color("&fslot " + i + ""));
            objective.getScore(entry).setScore(10 - i);
        }

    }

    // -------- -------- --------

    public void toggleHidden() {
        hidden = !hidden;
        setHidden(hidden);
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;

        if (hidden)
            Bukkit.getPlayer(bender.getName())
                    .setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        else
            Bukkit.getPlayer(bender.getName()).setScoreboard(scoreboard);
    }

    public void setLocale(String locale) {
        this.locale = locale;
        reloadLocale();
    }

    // -------- -------- --------

    public void render() {
        int slot;
        Team t;
        String[] split;
        for (int i = 0 ; i < cache.length ; i++) {
            slot = i + 1;
            t = scoreboard.getTeam("slot" + slot);
            split = cache[i].split();

            t.setPrefix(split[0]);
            t.setSuffix(split[1]);
        }
    }

    public void init(int currentSlot) {
        int slot = currentSlot + 1;
        Bindings bindings = bender.getBindings();
        String abilityName;
        Ability ability;
        StringComponent comp;

        for (int i = 1 ; i <= 9 ; i++) {
            abilityName = bindings.get(i);
            comp = new StringComponent(4);
            comp.setColors(new int[] {1, 2});

            if (i == slot)
                comp.put(0, Help.color("&e> "));

            if (abilityName == null)
                comp.put(3, "-- Slot " + i + " --");


            else {
                ability = Abilities.find(abilityName);
                comp.put(1, ability.getElement().getLightColor());
                comp.put(3, "-- " + ability.getName(locale) + " --");
            }

            cache[i - 1] = comp;
        }
    }

    private void reloadLocale() {

        String abilityName;
        Ability ability;
        for (int i = 1 ; i <= 9 ; i++) {

            abilityName = bender.getBindings().get(i);
            if (abilityName != null) {
                ability = Abilities.find(abilityName);
                cache[i - 1].put(3, "-- " + ability.getName(locale) + " --");
            }

        }

    }

    // -------- -------- -------- --------

    public void changeSlots(int from, int to) {
        cache[from].clear(0);
        cache[to].put(0, Help.color("&e> "));
    }

    public void bind(Ability ability, int to) {
        int slot = to - 1;
        cache[slot].put(1, ability.getElement().getLightColor());
        cache[slot].put(3, "-- " + ability.getName(locale) + " --");
    }

    public void clear() {
        for (int i = 0 ; i < 9 ; i++) {
            cache[i].put(1, "");
            cache[i].put(2, "");
            cache[i].put(3, "-- Slot " + (i + 1) + " --");
        }
    }

    // -------- -------- -------- --------

    public void cooldownOn(String abilityId) {
        int slot = bender.getBindings().find(abilityId) - 1;
        if (slot > 0)
            cache[slot].put(2, Help.color("&m"));
    }

    public void cooldownOff(String abilityId) {
        int slot = bender.getBindings().find(abilityId) - 1;
        if (slot > 0)
            cache[slot].clear(2);
    }

    // -------- -------- -------- --------

    public Bender getBender() { return bender; }

}
