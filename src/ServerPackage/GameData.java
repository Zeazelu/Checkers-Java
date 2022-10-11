package ServerPackage;

import java.util.ArrayList;

/**
 * przechowywuje dane
 */
public class GameData {

    //pionki na planszy
    static final int EMPTY = 0, WHITE = 1, WHITE_QUEEN = 2, BLACK = 3, BLACK_QUEEN = 4;
    private int[][] board = new int[8][8]; //tablica z obecnym stanem gry

    /**
     * odbiera pozycje pola
     * @return pozycja pola
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * inicjuje początkowe ustawienie pionków
     */
    public GameData() {
        setElementsOnStart();
    }

    /**
     * przygotowuje pionki na planszy do startu gry
     */
    public void setElementsOnStart() {
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
     * wykonaj ruch, jeśli jest zgodny z zasadami
     * @param move informacja o ruchu
     */
    public void makeMove(CommonPackage.CheckersMove move) {

        //jeśli rusza się królowa
        if (board[move.getMoveFromRow()][move.getMoveFromCol()] == BLACK_QUEEN
                || board[move.getMoveFromRow()][move.getMoveFromCol()] == WHITE_QUEEN) {
            removeOpponentCheckerIfBeating(move);
            moveChecker(move);
        }

        //jeśli rusza sie normalnuy pionek
        else if (move.isMoveBeating()) { //sprawdza czy ruch pionka jest biciem
            removeOpponentChecker(move); //usuwa pionka przeciwnika
            moveChecker(move);
        } else
            moveChecker(move); //pionek rusza sie

        checkIfNewQueen(move);
    }

    /**
     * sprawdza czy ruch królowej jest biciem
     * @param move informacja o ruchu
     */
    private void removeOpponentCheckerIfBeating(CommonPackage.CheckersMove move) {

        //współrzędne pionka przeciwnika który może byc skuty
        int opponentCheckerRow;
        int opponentCheckerCol;

        //zaczyna sprawdzanie od pozycji królowej
        int checkRow = move.getMoveFromRow();
        int checkCol = move.getMoveFromCol();

        //zacznij szukać pionka  przeciwnika w 4 możliwych kierunkach
        if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
            while (checkCol < move.getMoveToCol() && checkRow < move.getMoveToRow()) {
                checkCol++;
                checkRow++;
                if (board[checkRow][checkCol] != EMPTY) {
                    move.setBeatingPerformedByQueen(true);//bije -true, tylko się rusza -false
                    move.setMovePerformedByQueen(false);
                    break;
                }
            }
        } else if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
            while (checkCol > move.getMoveToCol() && checkRow < move.getMoveToRow()) {
                checkCol--;
                checkRow++;
                if (board[checkRow][checkCol] != EMPTY) {
                    move.setBeatingPerformedByQueen(true);
                    move.setMovePerformedByQueen(false);
                    break;
                }
            }
        } else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
            while (checkCol < move.getMoveToCol() && checkRow > move.getMoveToRow()) {
                checkCol++;
                checkRow--;
                if (board[checkRow][checkCol] != EMPTY) {
                    move.setBeatingPerformedByQueen(true);
                    move.setMovePerformedByQueen(false);
                    break;
                }
            }
        } else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
            while (checkCol > move.getMoveToCol() && checkRow > move.getMoveToRow()) {
                checkCol--;
                checkRow--;
                if (board[checkRow][checkCol] != EMPTY) {
                    move.setBeatingPerformedByQueen(true);
                    move.setMovePerformedByQueen(false);
                    break;
                }
            }
        }

        opponentCheckerCol = checkCol;
        opponentCheckerRow = checkRow;

        board[opponentCheckerRow][opponentCheckerCol] = EMPTY; //usuwa pionek przeciwnika

        if (move.isBeatingPerformedByQueen() == false) {
            //jeśli królowa nie biła kolejny ruch w tej samej turze jest blokowany
            move.setMovePerformedByQueen(true);
        }
    }

    /**
     * tworzy nową królową
     * @param move informacje o ruchu
     */
    private void checkIfNewQueen(CommonPackage.CheckersMove move) {
        if (move.getMoveToRow() == 0 && board[move.getMoveToRow()][move.getMoveToCol()] == WHITE) {
            move.setMovePerformedByQueen(true); //aby zapobiec biciu przez nową królową
            board[move.getMoveToRow()][move.getMoveToCol()] = WHITE_QUEEN;
        }
        if (move.getMoveToRow() == 7 && board[move.getMoveToRow()][move.getMoveToCol()] == BLACK) {
            move.setMovePerformedByQueen(true); //aby zapobiec biciu przez nową królową
            board[move.getMoveToRow()][move.getMoveToCol()] = BLACK_QUEEN;
        }
    }

    /**
     * normalny ruch pionka
     * @param move informacje o ruchu
     */
    private void moveChecker(CommonPackage.CheckersMove move) {
        board[move.getMoveToRow()][move.getMoveToCol()] = board[move.getMoveFromRow()][move.getMoveFromCol()];
        board[move.getMoveFromRow()][move.getMoveFromCol()] = EMPTY;
    }

    /**
     * usuniecie pionka po biciu
     * @param move informacje o ruchu
     */
    private void removeOpponentChecker(CommonPackage.CheckersMove move) {

        int opponentCheckerRow = 0;
        int opponentCheckerCol = 0;

        if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
            opponentCheckerRow = move.getMoveToRow() - 1;
            opponentCheckerCol = move.getMoveToCol() - 1;
        } else if (move.getMoveFromRow() < move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
            opponentCheckerRow = move.getMoveToRow() - 1;
            opponentCheckerCol = move.getMoveToCol() + 1;
        } else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() < move.getMoveToCol()) {
            opponentCheckerRow = move.getMoveToRow() + 1;
            opponentCheckerCol = move.getMoveToCol() - 1;
        } else if (move.getMoveFromRow() > move.getMoveToRow() && move.getMoveFromCol() > move.getMoveToCol()) {
            opponentCheckerRow = move.getMoveToRow() + 1;
            opponentCheckerCol = move.getMoveToCol() + 1;
        }
        board[opponentCheckerRow][opponentCheckerCol] = EMPTY;
    }

    /**
     * tworzy tablicę zawierającą możliwe ruchy
     * @param player gracz
     * @return tablica tymczasowych możliwych ruchów
     */
    public CommonPackage.CheckersMove[] getPossibleMovesForPlayer(int player) {
        if (player != WHITE && player != BLACK)
            return null;
        int playerQueen; //zmiena reprezentująca królową należącą do gracza

        if (player == WHITE)
            playerQueen = WHITE_QUEEN;
        else
            playerQueen = BLACK_QUEEN;

        //tymczasowa tablica dla możliwych ruchów
        ArrayList<CommonPackage.CheckersMove> moves = new ArrayList<>();

        //sprawdź możliwe bicie - jeśli tak, gracz musi bić
        checkPossibleBeating(moves, player, playerQueen);

        //jeśli nie jest możliwe bicie, sprawdź normalne ruchy
        if (moves.size() == 0) {
            checkPossibleRegularMoves(moves, player, playerQueen);
        }

        // jeśli nie ma możliwości ruchu zwraca null
        if (moves.size() == 0) {
            return null;
        } else {
            CommonPackage.CheckersMove[] arrayOfPossibleMoves = new CommonPackage.CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++) {
                arrayOfPossibleMoves[i] = moves.get(i);
            }
            return arrayOfPossibleMoves;
        }
    }

    /**
     * szuka możliwości normalnych ruchów
     * @param moves informacja o ruchu
     * @param player gracz
     * @param playerQueen informacjaa o królowej gracza
     */
    private void checkPossibleRegularMoves(ArrayList<CommonPackage.CheckersMove> moves, int player, int playerQueen) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player) { //sprawdź możliwe normalne ruchy w 4 kierunkach
                    if (canMove(player, row, col, row + 1, col + 1))
                        moves.add(new CommonPackage.CheckersMove(row, col, row + 1, col + 1));
                    if (canMove(player, row, col, row - 1, col + 1))
                        moves.add(new CommonPackage.CheckersMove(row, col, row - 1, col + 1));
                    if (canMove(player, row, col, row + 1, col - 1))
                        moves.add(new CommonPackage.CheckersMove(row, col, row + 1, col - 1));
                    if (canMove(player, row, col, row - 1, col - 1))
                        moves.add(new CommonPackage.CheckersMove(row, col, row - 1, col - 1));
                }
                else if (board[row][col] == playerQueen) { //sprawdź możliwe ruchy, jeśli jest to królowa
                    canQueenMove(moves, player, row, col);
                }
            }
        }
    }

    /**
     * znajdź normalne ruchy królowej w 4 kierunkach
     * @param moves informacja o ruchu
     * @param player gracz
     * @param rowFrom numer wiersza, z którego przemieszcza się pionek
     * @param colFrom numer kolumny, z której przemieszcza się pionek
     */
    private void canQueenMove(ArrayList<CommonPackage.CheckersMove> moves, int player, int rowFrom, int colFrom) {

        //zaczyna sprawdzać od pozycji królowej
        int rowToCheck = rowFrom;
        int colToCheck = colFrom;

        //sprawdza biały czy czarny gracz
        if (player == WHITE) { //biała królowa
            // pierwszy kierunek - od prawego dolnego rogu do lewego górnego rogu
            while (--rowToCheck >= 0 && --colToCheck >= 0) {
                if (board[rowToCheck][colToCheck] != EMPTY) {
                    break;
                }
                moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;

            // drugi kierunek - od lewego dolnego rogu do prawego górnego rogu
            while (--rowToCheck >= 0 && ++colToCheck <= 7) {
                if (board[rowToCheck][colToCheck] != EMPTY) {
                    break;
                }
                moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;

            //trzeci kierunek - od prawego górnego rogu do lewego dolnego rogu
            while (++rowToCheck <= 7 && --colToCheck >= 0) {
                if (board[rowToCheck][colToCheck] != EMPTY) {
                    break;
                }
                moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;

            //czwarty kierunek - od lewego górnego rogu do prawego dolnego rogu
            while (++rowToCheck <= 7 && ++colToCheck <= 7) {
                if (board[rowToCheck][colToCheck] != EMPTY) {
                    break;
                }
                moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
            }
        } else { //czarna królowa
            //pierwszy kierunek - od prawego dolnego rogu do lewego górnego rogu
            while (--rowToCheck >= 0 && --colToCheck >= 0) {
                if (board[rowToCheck][colToCheck] != EMPTY) {
                    break;
                }
                moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;

            //drugi kierunek - od lewego dolnego rogu do prawego górnego rogu
            while (--rowToCheck >= 0 && ++colToCheck <= 7) {
                if (board[rowToCheck][colToCheck] != EMPTY) {
                    break;
                }
                moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;

            //trzeci kierunek - od prawego górnego rogu do lewego dolnego rogu
            while (++rowToCheck <= 7 && --colToCheck >= 0) {
                if (board[rowToCheck][colToCheck] != EMPTY) {
                    break;
                }
                moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;

            //czwarty kierunek - od lewego górnego rogu do prawego dolnego rogu
            while (++rowToCheck <= 7 && ++colToCheck <= 7) {
                if (board[rowToCheck][colToCheck] != EMPTY) {
                    break;
                }
                moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
            }
        }
    }

    /**
     * znajdź regularne ruchy dla normalnych pionków - 1 pole w każdym kierunku
     * @param player gracz
     * @param rowFrom numer wiersza, z którego przemieszcza się pionek
     * @param colFrom numer kolumny, z której przemieszcza się pionek
     * @param rowTo numer wiersza, do którego przemieszcza się pionek
     * @param colTo numer kolumny, do której przemieszcza się pionek
     * @return flaga informująca o możliwości ruchu
     */
    private boolean canMove(int player, int rowFrom, int colFrom, int rowTo, int colTo) {
        if (colTo > 7 || colTo < 0 || rowTo > 7 || rowTo < 0)
            return false;
        if (board[rowTo][colTo] != EMPTY)//rowTo, colTo są zajęte przez inny pionek
            return false;
        if (player == WHITE) { //sprawdź biały lub czarny gracz
            if (rowTo > rowFrom)
                return false; //zwykły biały pionek może poruszać się tylko w górę
            return true;
        } else {
            if (rowTo < rowFrom)
                return false; //zwykły czarny pionek może poruszać się tylko w dół
            return true;
        }
    }

    /**
     * znajdź możliwe bicia
     * @param moves informacja o ruchu
     * @param player gracz
     * @param playerQueen informacja o królowej gracza
     */
    private void checkPossibleBeating(ArrayList<CommonPackage.CheckersMove> moves, int player, int playerQueen) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player) { //sprawdź możliwe bicie dla pionka w 4 kierunkach
                    if (canBeat(player, row, col, row + 1, col + 1, row + 2, col + 2))
                        moves.add(new CommonPackage.CheckersMove(row, col, row + 2, col + 2));
                    if (canBeat(player, row, col, row - 1, col + 1, row - 2, col + 2))
                        moves.add(new CommonPackage.CheckersMove(row, col, row - 2, col + 2));
                    if (canBeat(player, row, col, row + 1, col - 1, row + 2, col - 2))
                        moves.add(new CommonPackage.CheckersMove(row, col, row + 2, col - 2));
                    if (canBeat(player, row, col, row - 1, col - 1, row - 2, col - 2))
                        moves.add(new CommonPackage.CheckersMove(row, col, row - 2, col - 2));
                }
                else if (board[row][col] == playerQueen) { //sprawdź możliwe bicie, jeśli jest to królowa
                    canQueenBeat(moves, player, row, col);
                }
            }
        }
    }

    /**
     * znajdź możliwe ruchy królowej w 4 kierunkach o dowolną liczbę pól
     * @param moves informacja o ruchu
     * @param player gracz
     * @param rowFrom numer wiersza, z którego przemieszcza się pionek
     * @param colFrom numer kolumny, z której przemieszcza się pionek
     */
    private void canQueenBeat(ArrayList<CommonPackage.CheckersMove> moves, int player, int rowFrom, int colFrom) {
        int rowToCheck = rowFrom;
        int colToCheck = colFrom;
        boolean enemyCheckerFound = false;

        if (player == WHITE) { //dla białego gracza

            // pierwszy kierunek - od prawego dolnego rogu do lewego górnego rogu
            while (--rowToCheck >= 0 && --colToCheck >= 0) {
                //gdy znajdziesz ten sam pionek, przestań szukać
                if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
                    break;
                } else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
                    enemyCheckerFound = true; //znaleziono czarnego pionka
                } else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
                    moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck)); //możliwe bicie
                }
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;
            enemyCheckerFound = false;

            //drugi kierunek - od prawego górnego rogu do lewego dolnego rogu
            while (--rowToCheck >= 0 && ++colToCheck <= 7) {
                if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
                    break;
                } else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
                    enemyCheckerFound = true;
                } else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
                    moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
                }
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;
            enemyCheckerFound = false;

            //trzeci kierunek - od lewego dolnego rogu do prawego górnego rogu
            while (++rowToCheck <= 7 && --colToCheck >= 0) {
                if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
                    break;
                } else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
                    enemyCheckerFound = true;
                } else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
                    moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
                }
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;
            enemyCheckerFound = false;

            //czwarty kierunek - od lewego górnego rogu do prawego dolnego rogu
            while (++rowToCheck <= 7 && ++colToCheck <= 7) {
                if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
                    break;
                } else if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
                    enemyCheckerFound = true;
                } else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
                    moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
                }
            }
        } else { //dla czarnego gracza

            //pierwszy kierunek - od prawego dolnego rogu do lewego górnego rogu
            while (--rowToCheck >= 0 && --colToCheck >= 0) {
                if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
                    break;
                } else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
                    enemyCheckerFound = true;
                } else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
                    moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
                }
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;
            enemyCheckerFound = false;

            //drugi kierunek - od prawego górnego rogu do lewego dolnego rogu
            while (--rowToCheck >= 0 && ++colToCheck <= 7) {
                if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
                    break;
                } else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
                    enemyCheckerFound = true;
                } else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
                    moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
                }
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;
            enemyCheckerFound = false;

            //trzeci kierunek - od lewego dolnego rogu do praweg górnego rogu
            while (++rowToCheck <= 7 && --colToCheck >= 0) {
                if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
                    break;
                } else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
                    enemyCheckerFound = true;
                } else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
                    moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
                }
            }
            rowToCheck = rowFrom;
            colToCheck = colFrom;
            enemyCheckerFound = false;

            //czwarty kierunek - od lewego górnego rogu do prawego dolnego rogu
            while (++rowToCheck <= 7 && ++colToCheck <= 7) {
                if (board[rowToCheck][colToCheck] == BLACK || board[rowToCheck][colToCheck] == BLACK_QUEEN) {
                    break;
                } else if (board[rowToCheck][colToCheck] == WHITE || board[rowToCheck][colToCheck] == WHITE_QUEEN) {
                    enemyCheckerFound = true;
                } else if (enemyCheckerFound == true && board[rowToCheck][colToCheck] == EMPTY) {
                    moves.add(new CommonPackage.CheckersMove(rowFrom, colFrom, rowToCheck, colToCheck));
                }
            }
        }
    }

    /**
     * sprawdza czy bicie może wykonać inny pionek - tylko do przodu
     * @param player gracz
     * @param rowFrom numer wiersza, z którego przemieszcza się pionek
     * @param colFrom numer kolumny, z której przemieszcza się pionek
     * @param rowJumped numer wiersza, nad którym został wykonany ruch pionka
     * @param colJumped numer kolumny, nad którym został wykonany ruch pionka
     * @param rowTo numer wiersza, do którego przemieszcza się pionek
     * @param colTo numer kolumny, do której przemieszcza się pionek
     * @return flaga informująca o możliwości bicia
     */
    private boolean canBeat(int player, int rowFrom, int colFrom, int rowJumped, int colJumped, int rowTo, int colTo) {
        if (colTo > 7 || colTo < 0 || rowTo > 7 || rowTo < 0) {
            return false;
        }
        if (board[rowTo][colTo] != EMPTY) { //rowTo, colTo są zajęte
            return false;
        }
        if (player == WHITE) { //biały pionek
            if (rowTo > rowFrom && board[rowFrom][colFrom] == WHITE) {
                return false;
            }
            if (board[rowJumped][colJumped] != BLACK && board[rowJumped][colJumped] != BLACK_QUEEN) {
                return false;
            }
            return true;
        } else { //czarny pionek
            if (rowTo < rowFrom && board[rowFrom][colFrom] == BLACK) {
                return false;
            }
            if (board[rowJumped][colJumped] != WHITE && board[rowJumped][colJumped] != WHITE_QUEEN) {
                return false;
            }
            return true;
        }
    }

    /**
     * jeśli wykonano bicie sprawdza czy możliwe jest kolejne bicie
     * @param player gracz
     * @param rowFrom numer wiersza, z którego przemieszcza się pionek
     * @param colFrom numer kolumny, z której przemieszcza się pionek
     * @return drugi ruch będący biciem
     */
    public CommonPackage.CheckersMove[] getPossibleSecondBeating(int player, int rowFrom, int colFrom) {
        if (player != WHITE && player != BLACK)
            return null;
        int playerQueen;
        if (player == WHITE)
            playerQueen = WHITE_QUEEN;
        else
            playerQueen = BLACK_QUEEN;

        //tymczasowa tablica na możliwe ruchy
        ArrayList<CommonPackage.CheckersMove> moves = new ArrayList<CommonPackage.CheckersMove>();

        //sprazwdza czy są możliwe bicia
        checkPossibleSecondBeating(moves, player, playerQueen, rowFrom, colFrom);

        //jeśli nie ma róchów zwróci null
        if (moves.size() == 0) {
            return null;

        } else {
            CommonPackage.CheckersMove[] arrayOfSecondBeat = new CommonPackage.CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                arrayOfSecondBeat[i] = moves.get(i);
            return arrayOfSecondBeat;
        }
    }

    /**
     * sprawdza czy możliwe jest drugie bicie dla danych współrzędnych
     * @param moves informacja o ruchu
     * @param player gracz
     * @param playerQueen informacja o królowej gracza
     * @param row numer wiersza
     * @param col numer kolumny
     */
    private void checkPossibleSecondBeating(ArrayList<CommonPackage.CheckersMove> moves, int player, int playerQueen, int row,
                                            int col) {

        if (board[row][col] == player) { //sprawdza możliwe drugie bicie dla pionka w 4 kierunkach

            if (canBeat(player, row, col, row + 1, col + 1, row + 2, col + 2))
                moves.add(new CommonPackage.CheckersMove(row, col, row + 2, col + 2));
            if (canBeat(player, row, col, row - 1, col + 1, row - 2, col + 2))
                moves.add(new CommonPackage.CheckersMove(row, col, row - 2, col + 2));
            if (canBeat(player, row, col, row + 1, col - 1, row + 2, col - 2))
                moves.add(new CommonPackage.CheckersMove(row, col, row + 2, col - 2));
            if (canBeat(player, row, col, row - 1, col - 1, row - 2, col - 2))
                moves.add(new CommonPackage.CheckersMove(row, col, row - 2, col - 2));
        }
        else if (board[row][col] == playerQueen) { //jeśli pionkiem jest królowa
            canQueenBeat(moves, player, row, col);
        }
    }
}
