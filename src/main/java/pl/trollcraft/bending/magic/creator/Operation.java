package pl.trollcraft.bending.magic.creator;

public enum Operation {

    LOCALE ("Ustaw jezyk."),
    NAME ("Ustaw nazwe."),
    DESC ("Ustaw opis."),
    COLORS ("Ustaw kolory.");

    private String message;

    Operation (String message) {
        this.message = message;
    }

    public String message() { return message; }

}
