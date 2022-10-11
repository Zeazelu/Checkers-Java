package ServerPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import CommonPackage.*;

/**
 * odpowiada za przebieg meczu
 */
public class Match {

    int matchNumber; //numer rozpoczętej gry

    ServerPackage.GameFlow gameFlow;

    /**
     * rozpoczyna nową grę
     * @param matchNumber numer rozpoczętej gry
     */
    public Match(int matchNumber) {
        this.matchNumber = matchNumber;
        gameFlow = new ServerPackage.GameFlow();
        gameFlow.setGameRunning(true);
    }

    /**
     * przechowuje informacje o kliencie
     */
    class Player extends Thread {
        private int myColor;
        private Socket mySocket;

        private ObjectInputStream myInput;
        private ObjectOutputStream myOutput;
        private MessageFromClient messageFromClient = new MessageFromClient();
        private MessageFromServer messageToClient = new MessageFromServer();
        private volatile boolean threadRunning = true; //flaga, mówiąca czy wątek działa

        public boolean resign = false;

        /**
         * ustawia informacje o gnieździe i kolorze gracza
         * @param mySocket gniazdo połączenia gracza
         * @param myColor kolor gracza
         */
        public Player(Socket mySocket, int myColor) {
            this.mySocket = mySocket;
            this.myColor = myColor;
        }

        /**
         * uruchomienie wąatku dla gry
         */
        public void run() {
            while (threadRunning) {
                try {
                    myInput = new ObjectInputStream(mySocket.getInputStream());
                    myOutput = new ObjectOutputStream(mySocket.getOutputStream());
                    myOutput.flush();
                } catch (IOException e1) {
                    System.out.println("Gracz wyszedł!: " + e1);
                    resign = true;
                    gameFlow.makeClick(-1, -1, resign);
                    threadRunning = false;
                }
                //przygotowuje dane do wysłania dla klienta
                if (resign != true) {
                    try {
                        prepareMessageToClient(gameFlow.boardData.getBoard(), gameFlow.getChosenCol(),
                                gameFlow.getChosenRow(), true, gameFlow.getCurrentPlayer(), gameFlow.getPossibleMoves(),
                                ServerPackage.GameData.EMPTY, myColor);
                        myOutput.writeObject(messageToClient);
                        while (threadRunning) {
                            if (gameFlow.getCurrentPlayer() == myColor && gameFlow.isGameRunning()) {

                                prepareMessageToClient(gameFlow.boardData.getBoard(), gameFlow.getChosenCol(),
                                        gameFlow.getChosenRow(), gameFlow.isGameRunning(), gameFlow.getCurrentPlayer(),
                                        gameFlow.getPossibleMoves(), gameFlow.getWinner(), myColor);
                                myOutput.reset();
                                myOutput.writeObject(messageToClient);

                                messageFromClient = (MessageFromClient) myInput.readObject();

                                gameFlow.makeClick(messageFromClient.getChosenRow(), messageFromClient.getChosenCol(),
                                        messageFromClient.isResign());

                                prepareMessageToClient(gameFlow.boardData.getBoard(), gameFlow.getChosenCol(),
                                        gameFlow.getChosenRow(), gameFlow.isGameRunning(), gameFlow.getCurrentPlayer(),
                                        gameFlow.getPossibleMoves(), gameFlow.getWinner(), myColor);
                                myOutput.reset();
                                myOutput.writeObject(messageToClient);

                            } else if (!gameFlow.isGameRunning() && gameFlow.getWinner() != ServerPackage.GameData.EMPTY) { //koniec gry
                                prepareMessageToClient(gameFlow.boardData.getBoard(), gameFlow.getChosenCol(),
                                        gameFlow.getChosenRow(), gameFlow.isGameRunning(), gameFlow.getCurrentPlayer(),
                                        gameFlow.getPossibleMoves(), gameFlow.getWinner(), myColor);
                                myOutput.reset();
                                myOutput.writeObject(messageToClient);
                                threadRunning = false;
                            }
                        }
                    } catch (IOException e) {
                        //kiedy przeciwnik sie podda lub wyjdzie wygrywasz
                        resign = true;
                        gameFlow.makeClick(-1, -1, resign);
                        threadRunning = false;
                    } catch (ClassNotFoundException e) {
                        resign = true;
                        gameFlow.makeClick(-1, -1, resign);
                        threadRunning = false;
                    }
                    finally{
                        try {
                            myOutput.close();
                            myInput.close();
                        } catch (IOException e) {}
                    }
                }
            }
        }

        /**
         * ustawia dane dla klienta
         * @param board wybrane pole
         * @param chosenCol numer kolumny
         * @param chosenRow numer wiersza
         * @param gameRunning flaga informująca o stanie gry
         * @param currentPlayer obecny gracz
         * @param possibleMoves tablica możliwych ruchów pionka
         * @param winner zwycięstwo
         * @param myColor kolor gracza
         */
        private void prepareMessageToClient(int[][] board, int chosenCol, int chosenRow, boolean gameRunning,
                                            int currentPlayer, CommonPackage.CheckersMove[] possibleMoves, int winner, int myColor) {
            messageToClient.setBoard(board);
            messageToClient.setChosenCol(chosenCol);
            messageToClient.setChosenRow(chosenRow);
            messageToClient.setGameRunning(gameRunning);
            messageToClient.setCurrentPlayer(currentPlayer);
            messageToClient.setPossibleMoves(possibleMoves);
            messageToClient.setWinner(winner);
            messageToClient.setGameRunning(gameRunning);
            messageToClient.setMyColor(myColor);
        }
    }
}

