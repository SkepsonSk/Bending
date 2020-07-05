package pl.trollcraft.bending.magic.board;

import pl.trollcraft.bending.magic.bender.Benders;

import java.util.ArrayList;

public class Boards {

    private static ArrayList<Board> boards = new ArrayList<>();

    public static Board create(String playerName, boolean hidden, String locale) {
        Board board = new Board(Benders.find(playerName), hidden, locale);
        boards.add(board);
        return board;
    }

    public static void dispose(Board board) { boards.remove(board); }

    public static Board find(String playerName) {
        for (Board board : boards)
            if (board.getBender().getName().equals(playerName))
                return board;
        return null;
    }

}
