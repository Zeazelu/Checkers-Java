package CommonPackage;

import java.io.Serializable;

/**
 * wiadomość, którą serwer wysyła do klienta
 */
public class MessageFromServer implements Serializable {

    private int[][] board = new int[8][8]; //tablica z bieżącym  stanem planszy
    private boolean gameRunning;
    private int currentPlayer; //obecny gracz
    private CommonPackage.CheckersMove[] possibleMoves; //tablica z możliwymi ruchami obencego gracza
    private int chosenRow;
    private int chosenCol;
    private int winner; //zawiera zwycięzcę (czarny - 0 , biały 1)
    private int myColor;

    /**
     * odbiera kolor gracza
     * @return kolor gracza
     */
    public int getMyColor() {
        return myColor;
    }

    /**
     * ustawia kolor gracza
     * @param myColor kolor gracza
     */
    public void setMyColor(int myColor) {
        this.myColor = myColor;
    }

    /**
     * odbiera numer wiersza
     * @return numer wiersza
     */
    public int getChosenRow() {
        return chosenRow;
    }

    /**
     * ustawia numer wiersza
     * @param chosenRow numer wiersza
     */
    public void setChosenRow(int chosenRow) {
        this.chosenRow = chosenRow;
    }

    /**
     * odbiera numer kolumny
     * @return numer kolumny
     */
    public int getChosenCol() {
        return chosenCol;
    }

    /**
     * ustawia numer kolumny
     * @param chosenCol numer kolumny
     */
    public void setChosenCol(int chosenCol) {
        this.chosenCol = chosenCol;
    }

    /**
     * odbiera pozycje pola
     * @return pozycje pola
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * ustawia pozycje pola
     * @param board pozycje pola
     */
    public void setBoard(int[][] board) {
        this.board = board;
    }

    /**
     * odbiera informacje o zwycięstwie
     * @return zwycięstwo
     */
    public int getWinner() {
        return winner;
    }

    /**
     * ustawia informacje o zwycięstwie
     * @param winner zwycięstwo
     */
    public void setWinner(int winner) {
        this.winner = winner;
    }

    /**
     * odbiera informacje o stanie gry
     * @return flaga informująca o stanie gry
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * ustawia flage infromującą o stanie gry
     * @param gameRunning flaga informująca o stanie gry
     */
    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * odbiera informacje o obecnym graczu
     * @return obecny gracz
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * ustawia informacje o obecnym graczu
     * @param currentPlayer obecny gracz
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * odbiera tablicę z możliwymi ruchami pionka
     * @return możliwe ruchy pionka
     */
    public CommonPackage.CheckersMove[] getPossibleMoves() {
        return possibleMoves;
    }

    /**
     * ustawia tablicę z możliwymi ruchami pionka
     * @param possibleMoves możliwe ruchy pionka
     */
    public void setPossibleMoves(CommonPackage.CheckersMove[] possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
}

