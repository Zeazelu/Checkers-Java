package CommonPackage;

import java.io.Serializable;

/**
 * reprezentacja ruchów w grze
 */
public class CheckersMove implements Serializable {

    private int moveFromRow, moveFromCol; //współrzędne pionka, który ma sie poruszyć

    private int moveToRow, moveToCol; //współrzędne gdzie pionek może się poruszyc

    //flagi uniemożliwiają wykonanie 2 ruchów (gdy pierwszy nie jest biciem, a drugi jest)
    private boolean movePerformedByQueen = false; //flaga nadzorująca ruchy królowej

    private boolean beatingPerformedByQueen = false; //flaga nadzorująca bicia królowej

    /**
     * odbiera informację o biciu wykonywanym przez królową
     * @return bicie wykonywane przez królową
     */
    public boolean isBeatingPerformedByQueen() {
        return beatingPerformedByQueen;
    }

    /**
     * ustawia flagę informującą o biciu wykonywanym przez królową
     * @param beatingPerformedByQueen bicie wykonywane przez królową
     */
    public void setBeatingPerformedByQueen(boolean beatingPerformedByQueen) {
        this.beatingPerformedByQueen = beatingPerformedByQueen;
    }

    /**
     * odbiera informacje o ruchu wykonywanym przez królową
     * @return ruch wykonywany przez królową
     */
    public boolean isMovePerformedByQueen() {
        return movePerformedByQueen;
    }

    /**
     * ustawia flagę infromującą o ruchu wykonywanym przez królową
     * @param movePerformedByQueen ruch wykonywany przez królową
     */
    public void setMovePerformedByQueen(boolean movePerformedByQueen) {
        this.movePerformedByQueen = movePerformedByQueen;
    }

    /**
     * odbiera informacje o ruchu, który może być biciem
     * @return informacja o ruchu, który może być biciem
     */
    public boolean isMoveBeating() { //jeśli normalny pionek bije zwraca true
        return (moveFromCol - moveToCol == 2 || moveFromCol - moveToCol == -2);
    }

    /**
     * odbiera informację o numerze wiersza, z którego pionek wykona ruch
     * @return numer wiersza, z którego pionek wykona ruch
     */
    public int getMoveFromRow() {
        return moveFromRow;
    }

    /**
     * odbiera informację o numerze kolumny, z której pionek wykona ruch
     * @return numer kolumny, z której pionek wykona ruch
     */
    public int getMoveFromCol() {
        return moveFromCol;
    }

    /**
     * odbiera informację o numerze wiersza, do którego pionek wykona ruch
     * @return numer wiersza, do którego pionek wykona ruch
     */
    public int getMoveToRow() {
        return moveToRow;
    }

    /**
     * odbiera informację o numerze kolumny, do której pionek wykona ruch
     * @return numer kolumny, do której pionek wykona ruch
     */
    public int getMoveToCol() {
        return moveToCol;
    }

    /**
     * ustawia dane związane z położeniem pionka
     * @param moveFromRow numer wiersza, z którego pionek wykona ruch
     * @param moveFromCol numer kolumny, z której pionek wykona ruch
     * @param moveToRow numer wiersza, do którego pionek wykona ruch
     * @param moveToCol numer kolumny, do której pionek wykona ruch
     */
    public CheckersMove(int moveFromRow, int moveFromCol, int moveToRow, int moveToCol) {
        this.moveFromRow = moveFromRow;
        this.moveFromCol = moveFromCol;
        this.moveToRow = moveToRow;
        this.moveToCol = moveToCol;
    }
}

