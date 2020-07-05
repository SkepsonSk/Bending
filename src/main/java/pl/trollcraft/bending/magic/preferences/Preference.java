package pl.trollcraft.bending.magic.preferences;

import pl.trollcraft.bending.magic.element.Element;

public class Preference {

    private String playerName;

    private String locale;
    private boolean boardHidden;

    private Element element;

    // -------- -------- --------

    public Preference (String playerName) {
        this.playerName = playerName;
    }

    public Preference (String playerName, String locale, boolean boardHidden) {
        this.playerName = playerName;
        this.locale = locale;
        this.boardHidden = boardHidden;
    }

    // -------- -------- --------

    public String getPlayerName() { return playerName; }
    public String getLocale() { return locale; }
    public boolean hasBoardHidden() { return boardHidden; }
    public Element getElement() { return element; }

    public void setLocale (String locale) { this.locale = locale; }
    public void setBoardHidden(boolean boardHidden) { this.boardHidden = boardHidden; }
    public void setElement(Element element) { this.element = element; }

    public boolean switchBoard() {
        boardHidden = !boardHidden;
        return boardHidden;
    }
}
