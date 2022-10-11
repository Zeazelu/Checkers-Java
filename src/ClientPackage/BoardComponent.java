package ClientPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

/**
 * tworzenie planszy
 */
public class BoardComponent extends JComponent implements ActionListener, MouseListener {

    private static final int PREF_W = 400;
    private static final int PREF_H = 400;

    /**
     * obsługa zdarzeń myszy i przycisków
     */
    public BoardComponent() {
        addMouseListener(this);
        CheckersGame.startButton.addActionListener(this);
        CheckersGame.stopButton.addActionListener(this);
    }

    /**
     * wielkość okna planszy
     * @return wielkość planszy
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    /**
     * wygląd komponentów planszy
     * @param g wybrane pole
     */
    @Override
    public void paintComponent(Graphics g) {

        //obramowanie wokół planszy
        setBorder(new LineBorder(Color.black));
        setBackground(Color.decode("#00cccc"));

        for (int row = 0; row < 8; row++) { //rysuje pola
            for (int col = 0; col < 8; col++) {
                if (row % 2 == 0 && col % 2 == 0 || row % 2 != 0 && col % 2 != 0) { //pola białe
                    g.setColor(Color.decode("#ffffff"));
                    g.fillRect(col * 50, row * 50, 50, 50);
                }
                else { //pola czarne
                    g.setColor(Color.decode("#a3a3a3"));
                    g.fillRect(col * 50, row * 50, 50, 50);
                }

                switch (GameFlowClient.getFieldOnBoard(row, col)) { //rysuje pionki na planszy

                    case GameFlowClient.WHITE:
                        g.setColor(Color.WHITE);
                        g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);
                        break;
                    case GameFlowClient.WHITE_QUEEN:
                        g.setColor(Color.WHITE);
                        g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);
                        g.setColor(Color.BLACK);
                        g.drawString("Q", (col * 50) + 20, (row * 50) + 30);
                        break;
                    case GameFlowClient.BLACK:
                        g.setColor(Color.BLACK);
                        g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);
                        break;
                    case GameFlowClient.BLACK_QUEEN:
                        g.setColor(Color.BLACK);
                        g.fillOval((col * 50) + 5, (row * 50) + 5, 40, 40);
                        g.setColor(Color.WHITE);
                        g.drawString("Q", (col * 50) + 20, (row * 50) + 30);
                        break;
                }
            }
        }

        if (GameFlowClient.gameRunning && GameFlowClient.getMyColor() == GameFlowClient.getCurrentPlayer()) {
            CheckersGame.stopButton.setEnabled(true);
            CheckersGame.infoLabel.setText("Twój ruch.");

            g.setColor(Color.decode("#000000")); //rysuje ramke wokół pionków którymi można się ruszyć

            for (int i = 0; i < GameFlowClient.possibleMoves.length; i++) {
                g.drawRect(GameFlowClient.possibleMoves[i].getMoveFromCol() * 50,
                        GameFlowClient.possibleMoves[i].getMoveFromRow() * 50, 49, 49);
            }

            //rysuje zieloną ramke wokół wybranego pionka i czerwoną wokół pól na które wykonać ruch
            if (GameFlowClient.chosenRow >= 0) {
                g.setColor(Color.green);
                g.drawRect(GameFlowClient.chosenCol * 50, GameFlowClient.chosenRow * 50, 49, 49);
                g.setColor(Color.decode("#ff0000"));
                for (int i = 0; i < GameFlowClient.possibleMoves.length; i++) {
                    if (GameFlowClient.possibleMoves[i].getMoveFromCol() == GameFlowClient.chosenCol
                            && GameFlowClient.possibleMoves[i].getMoveFromRow() == GameFlowClient.chosenRow) {
                        g.drawRect(GameFlowClient.possibleMoves[i].getMoveToCol() * 50,
                                GameFlowClient.possibleMoves[i].getMoveToRow() * 50, 49, 49);
                    }
                }
            }

        //informacje wyświetlane pod planszą
        } else if (GameFlowClient.gameRunning && GameFlowClient.getMyColor() != GameFlowClient.getCurrentPlayer()) {
            CheckersGame.infoLabel.setText("Poczekaj na ruch  przeciwnika.");
            CheckersGame.stopButton.setEnabled(true);
        } else if (!GameFlowClient.gameRunning && GameFlowClient.isTryingToConnect()) {
            CheckersGame.infoLabel.setText("Łączenie z serwerem...");
            CheckersGame.stopButton.setEnabled(false);
        } else if (!GameFlowClient.gameRunning && Connecting.connectedToServer == false) {
            CheckersGame.infoLabel.setText("Błąd połączenia z serwerem!");
            CheckersGame.stopButton.setEnabled(false);
        } else if (!GameFlowClient.gameRunning && GameFlowClient.getWinner() == GameFlowClient.getMyColor()) {
            CheckersGame.infoLabel.setText("Wygrałeś!");
        } else if (!GameFlowClient.gameRunning && GameFlowClient.getWinner() != GameFlowClient.getMyColor()
                && GameFlowClient.getWinner() != -1) {
            CheckersGame.infoLabel.setText("Przegrałeś!");
        } else if (!GameFlowClient.gameRunning && GameFlowClient.getWinner() != GameFlowClient.getMyColor()) {
            CheckersGame.infoLabel.setText("");
        }
    }

    /**
     * zdarzenia związane z naciśnięciem i zwolnieniem przycisku myszy na komponencie
     * @param e zdarzenie do przetworzenia
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * zdarzenie związane z najechanaiem kursora myszy na komponent
     * @param e zdarzenie do przetworzenia
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * zdarzenie związane z opuszczeniem przez kursor myszy komponentu
     * @param e zdarzenie do przetworzenia
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * zdarzenia związane ze zwolnieniem przycisku myszy na komponencie
     * @param e zdarzenie do przetworzenia
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * odbiera współrzędne klikniętego pionka i wysyła do serwera
     * @param e zdarzenie do przetworzenia
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (GameFlowClient.gameRunning == false)
            CheckersGame.infoLabel.setText("Kliknij przycisk START aby rozpocząć nową grę!");
        else {
            int col = (e.getX() / 50);
            int row = (e.getY() / 50);
            if (col >= 0 && col < 8 && row >= 0 && row < 8)
                Connecting.sendMessageToServer(row, col, GameFlowClient.isResign());
        }
    }

    /**
     * obsługa działania przycisków
     * @param e przycisk myszki
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == CheckersGame.startButton) {
            GameFlowClient.setTryingToConnect(true);
            GameFlowClient.startNewGame();
        }
        else if (e.getSource() == CheckersGame.stopButton) {
            GameFlowClient.resignGame();
            Connecting.sendMessageToServer(-1, -1, GameFlowClient.isResign());
        }
    }
}
