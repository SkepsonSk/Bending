package pl.trollcraft.bending.magic.titles.titleuser;

import org.bukkit.Bukkit;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.help.database.DatabaseManager;
import pl.trollcraft.bending.magic.titles.title.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TitleUsers {

    private static ArrayList<TitleUser> users = new ArrayList<>();

    public static void register(TitleUser titleUser) { users.add(titleUser); }

    public static void dispose(TitleUser titleUser) { users.remove(titleUser); }

    public static TitleUser create(String playerName, List<String> titles, String selectedTitle) {
        TitleUser user = new TitleUser(playerName, titles, selectedTitle);
        register(user);
        return user;
    }

    // -------- -------- -------- --------

    public static void save(TitleUser titleUser) {
        RedissonClient client = DatabaseManager.getRedisson();

        String playerName = titleUser.getPlayerName();

        RList<String> titles = client.getList("titleusers/" + playerName + "/titles");
        Help.changeList(titleUser.getTitles(), titles);

        //TODO EXCLUDE
        /*titles.removeIf(tit -> !titleUser.getTitles().contains(tit));
        for (String title : titleUser.getTitles())
            if (!titles.contains(title))
                titles.add(title);*/


        RBucket<String> selectedTitle = client.getBucket("titleusers/" + playerName + "/selectedTitle");
        selectedTitle.set(titleUser.getSelectedTitle());
    }

    public static TitleUser load(String playerName) {
        RedissonClient client = DatabaseManager.getRedisson();
        RList<String> titles = client.getList("titleusers/" + playerName + "/titles");
        RBucket<String> selectedTitle = client.getBucket("titleusers/" + playerName + "/selectedTitle");
        TitleUser user = create(playerName, titles, selectedTitle.get());
        return user;
    }

    // -------- -------- -------- --------

    public static TitleUser find(String playerName) {
        for (TitleUser tu : users)
            if (tu.getPlayerName().equals(playerName))
                return tu;
        return null;
    }

}
