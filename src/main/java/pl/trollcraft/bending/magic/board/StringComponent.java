package pl.trollcraft.bending.magic.board;

public class StringComponent {

    private String[] data;
    private int[] colors;

    public StringComponent (int length) {
        data = new String[length];
        for (int i = 0 ; i < length ; i++)
            data[i] = "";
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    // -------- -------- --------

    public void put(int pos, String str) {
        data[pos] = str;
    }

    public void clear(int pos) {
        data[pos] = "";
    }

    // -------- -------- --------

    public String[] split() {

        String comp = toString();

        if (comp.length() > 16) {

            StringBuilder format = new StringBuilder();
            for (int i = 0 ; i < colors.length ; i++)
                format.append(data[colors[i]]);

            String[] ps = new String[2];

            ps[0] = comp.substring(0, 15);
            ps[1] = format.toString() + comp.substring(15);

            return ps;
        }


        return new String[] { comp, ""  };
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0 ; i < data.length ; i++)
            builder.append(data[i]);
        return builder.toString();
    }
}
