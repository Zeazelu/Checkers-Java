package ServerPackage;

/**
 * zarządza działaniem gry, przechowuje dane gry używane po stronie serwera
 */
public class GameFlow {

    private boolean gameRunning = false;
    private int winner = ServerPackage.GameData.EMPTY;

    ServerPackage.GameData boardData;
    private int currentPlayer;

    private int chosenRow = -1;
    private int chosenCol = -1;

    CommonPackage.CheckersMove[] possibleMoves;

    public synchronized int getChosenRow() {
        return chosenRow;
    }
    public synchronized int getCurrentPlayer() {
        return currentPlayer;
    }
    public synchronized int getChosenCol() {
        return chosenCol;
    }
    public synchronized CommonPackage.CheckersMove[] getPossibleMoves() {
        return possibleMoves;
    }
    public synchronized int getWinner() {
        return winner;
    }
    public synchronized boolean isGameRunning() {
        return gameRunning;
    }
    public synchronized void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    /**
     * inicjalizuje grę
     */
    public GameFlow() {
        initializeGame();
    }

    /**
     * inicjalizuje grę
     */
    private void initializeGame() {
        if (gameRunning == true) {
            return;
        }
        boardData = new ServerPackage.GameData();
        currentPlayer = ServerPackage.GameData.WHITE; //białe zaczynają
        possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
        gameRunning = true;
    }

    /**
     * kończy grę
     * @param winner zwycięztwo
     */
    private void gameIsOver(int winner) {
        gameRunning = false;
        this.winner = winner;
    }

    /**
     * pobiera informacje o wybranym polu
     * @param row numer wiersza
     * @param col numer kolumny
     * @param resign flaga informująca o poddaniu gry
     */
    synchronized void makeClick(int row, int col, boolean resign) {
        if (resign == true) { //kiedy gracz się podda
            if (currentPlayer == ServerPackage.GameData.WHITE)
                gameIsOver(ServerPackage.GameData.BLACK);
            else
                gameIsOver(ServerPackage.GameData.WHITE);
        } else {
            for (int i = 0; i < possibleMoves.length; i++)
                if (possibleMoves[i].getMoveFromRow() == row && possibleMoves[i].getMoveFromCol() == col) {
                    chosenRow = row;
                    chosenCol = col;
                    return;
                }
            if (chosenRow < 0) {
                return;
            }
            for (int i = 0; i < possibleMoves.length; i++)
                if (possibleMoves[i].getMoveFromRow() == chosenRow && possibleMoves[i].getMoveFromCol() == chosenCol
                        && possibleMoves[i].getMoveToRow() == row && possibleMoves[i].getMoveToCol() == col) {
                    performMove(possibleMoves[i]);
                    return;
                }
        }
    }

    /**
     * wykonaj ruch
     * @param checkerMove ruch w grze
     */
    synchronized private void performMove(CommonPackage.CheckersMove checkerMove) {
        boardData.makeMove(checkerMove);
        if ((checkerMove.isMoveBeating() && !checkerMove.isMovePerformedByQueen())
                || checkerMove.isBeatingPerformedByQueen()) {
            possibleMoves = boardData.getPossibleSecondBeating(currentPlayer, checkerMove.getMoveToRow(),
                    checkerMove.getMoveToCol());
            if (possibleMoves != null) {
                chosenRow = checkerMove.getMoveToRow(); //wybierz pionek jeśli jest jedynym który może sie ruszyć
                chosenCol = checkerMove.getMoveToCol();
                return;
            }
        }
        //przywrócić wartości domyślne dla flag związanych z królową
        checkerMove.setMovePerformedByQueen(false);
        checkerMove.setBeatingPerformedByQueen(false);

        if (currentPlayer == ServerPackage.GameData.WHITE) {
            currentPlayer = ServerPackage.GameData.BLACK;
            possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
            if (possibleMoves == null)
                gameIsOver(ServerPackage.GameData.WHITE);
        } else {
            currentPlayer = ServerPackage.GameData.WHITE;
            possibleMoves = boardData.getPossibleMovesForPlayer(currentPlayer);
            if (possibleMoves == null)
                gameIsOver(ServerPackage.GameData.BLACK);
        }
        chosenRow = -1;
        chosenCol = -1;

        if (possibleMoves != null) {
            boolean sameSquare = true;
            for (int i = 1; i < possibleMoves.length; i++)
                if (possibleMoves[i].getMoveFromRow() != possibleMoves[0].getMoveFromRow()
                        || possibleMoves[i].getMoveFromCol() != possibleMoves[0].getMoveFromCol()) {
                    sameSquare = false;
                    break;
                }
            if (sameSquare) {
                chosenRow = possibleMoves[0].getMoveFromRow();
                chosenCol = possibleMoves[0].getMoveFromCol();
            }
        }
    }
}

