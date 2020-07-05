package pl.trollcraft.bending.magic.titles.titleuser;

import java.util.List;

public class TitleUser {

    private String playerName;
    private List<String> titles;
    private String selectedTitle;

    // -------- -------- -------- --------

    public TitleUser (String playerName, List<String> titles, String selectedTitle) {
        this.playerName = playerName;
        this.titles = titles;
        this.selectedTitle = selectedTitle;
    }

    // -------- -------- -------- --------

    public String getPlayerName() { return playerName; }
    public List<String> getTitles() { return titles; }
    public String getSelectedTitle() { return selectedTitle; }

    public void setSelectedTitle (String selectedTitle) { this.selectedTitle = selectedTitle; }

}
