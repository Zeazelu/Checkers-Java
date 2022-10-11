package CommonPackage;

import java.io.Serializable;

/**
 * wiadomość, którą klient wysyła do serwerera
 */
public class MessageFromClient implements Serializable {

    private int row;
    private int col;
    private boolean resign;

    /**
     * odbiera informacje o poddaniu gry
     * @return flaga informująca o poddaniu gry
     */
    public boolean isResign() {
        return resign;
    }

    /**
     * ustawia flagę informującą o poddaniu gry
     * @param resign flaga informująca o poddaniu gry
     */
    public void setResign(boolean resign) {
        this.resign = resign;
    }

    /**
     * odbiera numer wiersza
     * @return numer wiersza
     */
    public int getChosenRow() {
        return row;
    }

    /**
     * ustawia numer wiersza
     * @param row numer wiersza
     */
    public void setChosenRow(int row) {
        this.row = row;
    }

    /**
     * odbiera numer kolumny
     * @return numer kolumny
     */
    public int getChosenCol() {
        return col;
    }

    /**
     * ustawia numer kolumny
     * @param col numer kolumny
     */
    public void setChosenCol(int col) {
        this.col = col;
    }
}

