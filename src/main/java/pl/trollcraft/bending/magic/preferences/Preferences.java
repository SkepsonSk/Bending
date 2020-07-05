package pl.trollcraft.bending.magic.preferences;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import pl.trollcraft.bending.help.database.DatabaseManager;

import java.util.ArrayList;

public class Preferences {

    private static ArrayList<Preference> preferences = new ArrayList<>();

    public static Preference create(String playerName, String locale, boolean boardHidden) {
        Preference p = new Preference(playerName, locale, boardHidden);
        preferences.add(p);
        return p;
    }

    public static void register(Preference p) { preferences.add(p); }
    public static void dispose(Preference p) { preferences.remove(p); }

    public static Preference find(String playerName){
        for (Preference p : preferences)
            if (p.getPlayerName().equals(playerName))
                return p;
        return null;
    }

    public static void save(Preference p) {
        String playerName = p.getPlayerName();

        RedissonClient client = DatabaseManager.getRedisson();

        RBucket<String> locale = client.getBucket("preferences/" + playerName + "/locale");
        RBucket<Boolean> boardHidden = client.getBucket("preferences/" + playerName + "/boardHidden");

        locale.set(p.getLocale());
        boardHidden.set(p.hasBoardHidden());
    }

    public static Preference load(String playerName) {
        Preference p = new Preference(playerName);

        RedissonClient client = DatabaseManager.getRedisson();

        RBucket<String> locale = client.getBucket("preferences/" + playerName + "/locale");
        RBucket<Boolean> boardHidden = client.getBucket("preferences/" + playerName + "/boardHidden");

        String localeStr = locale.get();
        if (localeStr == null || localeStr.isEmpty())
            localeStr = "pl";

        p.setLocale(localeStr);

        Boolean boardHiddenBool = boardHidden.get();
        if (boardHiddenBool == null)
            boardHiddenBool = false;

        p.setBoardHidden(boardHiddenBool);

        return p;
    }

}
