package ClientPackage;

/**
 * zarządza działaniem gry, przechowuje dane gry używane po stronie klienta
 */
public class GameFlowClient {

    static boolean gameRunning = false; //flaga działania gry

    static final int EMPTY = 0, WHITE = 1, WHITE_QUEEN = 2, BLACK = 3, BLACK_QUEEN = 4; //numer odpowiadający figurze
    static private int[][] board = new int[8][8];// tablica z obecnym statusem planszy

    static CommonPackage.CheckersMove[] possibleMoves;// tablica z potencjalnymi ruchami aktualnego gracza

    static int currentPlayer;
    static int chosenRow = -1; //współrzędne wiersza wybranego pionka (-1 oznacza, że nie wybrano)
    static int chosenCol = -1; //współrzędne kolumny wybranego pionka (-1 oznacza, że nie wybrano)
    static int myColor;
    static int winner = -1; //informacja o zwycięzcy (-1 oznacza, że nie ma)
    static boolean resign = false;
    static boolean tryingToConnect = false;
    static ClientPackage.Connecting connecting;

    /**
     * odbiera informację o dołączeniu do serwera
     * @return dołączenie do serwera
     */
    public static boolean isTryingToConnect() {
        return tryingToConnect;
    }

    /**
     * ustawia flagę informującą o dołączeniu do serwera
     * @param tryingToConnect dołączenie do serwera
     */
    public static void setTryingToConnect(boolean tryingToConnect) {
        GameFlowClient.tryingToConnect = tryingToConnect;
    }

    /**
     * odbiera informację o rezygnacji gracza
     * @return rezygnacja
     */
    public static boolean isResign() {
        return resign;
    }

    /**
     * ustawia flagę informującą o rezygnacji gracza
     * @param resign rezygnacja
     */
    public static void setResign(boolean resign) {
        GameFlowClient.resign = resign;
    }

    /**
     * odbiera informację o zwycięzcy
     * @return zwycięzca
     */
    public static int getWinner() {
        return winner;
    }

    /**
     * ustawia zwycięzcę
     * @param winner zwycięzca
     */
    public static void setWinner(int winner) {
        GameFlowClient.winner = winner;
    }

    /**
     * odbiera informacje o kolorze gracza
     * @return kolor gracza
     */
    public static int getMyColor() {
        return myColor;
    }

    /**
     * ustawia kolor gracza
     * @param myColor kolor gracza
     */
    public static void setMyColor(int myColor) {
        GameFlowClient.myColor = myColor;
    }

    /**
     * ustawia flage stanu gry
     * @param gameRunning stan gry
     */
    public static void setGameRunning(boolean gameRunning) {
        GameFlowClient.gameRunning = gameRunning;
    }

    /**
     * ustawia numer wiersza
     * @param chosenRow numer wiersza
     */
    public static void setChosenRow(int chosenRow) {
        GameFlowClient.chosenRow = chosenRow;
    }

    /**
     * ustawia numer kolumny
     * @param chosenCol numer kolumny
     */
    public static void setChosenCol(int chosenCol) {
        GameFlowClient.chosenCol = chosenCol;
    }

    /**
     * odbiera informacje o obecnym graczu
     * @return obecny gracz
     */
    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * ustawia obecnego gracza
     * @param currentPlayer obecny gracz
     */
    public static void setCurrentPlayer(int currentPlayer) {
        GameFlowClient.currentPlayer = currentPlayer;
    }

    /**
     * ustawia położenie pionka
     * @param board położenie pionka
     */
    public static void setBoard(int[][] board) {
        GameFlowClient.board = board;
    }

    /**
     * ustawia możliwe ruchy pionka
     * @param possibleMoves możliwe ruchy pionka
     */
    public static void setPossibleMoves(CommonPackage.CheckersMove[] possibleMoves) {
        GameFlowClient.possibleMoves = possibleMoves;
    }

    /**
     * inicjalizuje grę
     */
    public GameFlowClient() {
        initializeGame();
    }

    /**
     * inicjacja początkowego położenie pionków
     */
    private void initializeGame() {
        setElementsOnStart();
    }

    /**
     * ustawia początkowe położenie pionków
     */
    public static void setElementsOnStart() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 != col % 2) {
                    if (row < 3)
                        board[row][col] = BLACK;
                    else if (row > 4)
                        board[row][col] = WHITE;
                    else
                        board[row][col] = EMPTY;
                } else {
                    board[row][col] = EMPTY;
                }
            }
        }
    }

    /**
     * odbiera obecnie wybrany pionek
     * @param row numer wiersza
     * @param col numer kolumny
     * @return pozycja pola
     */
    public static int getFieldOnBoard(int row, int col) {
        return board[row][col];
    }

    /**
     * inicjuje rozpoczęcie nowej gry
     */
    static void startNewGame() {
        ClientPackage.CheckersGame.startButton.setEnabled(false);
        ClientPackage.CheckersGame.stopButton.setEnabled(true);

        connecting = new ClientPackage.Connecting();
        connecting.start();
    }

    /**
     * ustawia wartość flagi rezygnacji po naciśnięciu przycisku STOP
     */
    static void resignGame() {
        resign = true;
    }
}

