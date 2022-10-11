package ClientPackage;

import java.awt.*;

import javax.swing.*;

/**
 * zarządzanie klientem
 */
public class CheckersGame {

    private JPanel thePanel = new JPanel();
    static JButton startButton = new JButton("START"); //przycisk do rozpoczęcia gry
    static JButton stopButton = new JButton(" STOP "); //przycisk do przerwania  gry
    static JLabel infoLabel = new JLabel(); //pole do wyświetlania wiadomości
    static JLabel playerLabel = new JLabel(); //pole do wyświetlania nazwy gracza

    /**
     * metoda startowa
     * @param args argumenty przekazane przy uruchomieniu programu
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() { //uruchomienie wątku
                JFrame window = new JFrame("Warcaby");
                window.setPreferredSize(new Dimension(550, 550));
                window.setContentPane(new CheckersGame().getThePanel());
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.pack();
                window.setResizable(false);
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });
    }

    /**
     * okno do pobierania nazwy gracza
     * @return okno główne
     */
    protected JPanel getThePanel() {
        return thePanel;
    }

    /**
     * ustawia planszę i komponenty w oknie gry
     */
    public CheckersGame() {

        thePanel.setLayout(new GridBagLayout());

        GameFlowClient game = new GameFlowClient(); //odpowiada za działanie gry

        BoardComponent boardComponent = new BoardComponent(); //rysuje plansze

        Font font = new Font("Helvetica", Font.PLAIN, 18);
        Font font2 = new Font("Helvetica", Font.PLAIN, 15);
        infoLabel.setFont(font);
        playerLabel.setFont(font2);
        playerLabel.setForeground(Color.blue);

        String name = JOptionPane.showInputDialog(thePanel,
                "Podaj nazwe gracza (max 30 znaków):", "Nazwa gracza", JOptionPane.INFORMATION_MESSAGE);
        if(name.length()>30)
            name = name.substring(0,29);
        CheckersGame.playerLabel.setText(name);

        Box theBox = Box.createVerticalBox();
        theBox.add(startButton);
        theBox.add(Box.createVerticalStrut(30)); //określony odstęp
        theBox.add(stopButton);

        //pozycja komponentów okna
        addComp(thePanel, infoLabel, 0, 0, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.NONE);
        addComp(thePanel, boardComponent, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
        addComp(thePanel, theBox, 0, 0, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
        addComp(thePanel, playerLabel, 0, 0, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE);
    }

    /**
     * nadaje wartości komponentom
     * @param thePanel okno główne
     * @param comp rodzaj komponentu
     * @param xPos pozycja w osi x
     * @param yPos pozycja w osi y
     * @param compWidth długość komponentu
     * @param compHeight szerokość komponentu
     * @param place miejsce komponentu
     * @param stretch obszar komponentu
     */
    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place,
                         int stretch) {

        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = xPos;
        gridConstraints.gridy = yPos;
        gridConstraints.gridwidth = compWidth;
        gridConstraints.gridheight = compHeight;
        gridConstraints.weightx = 50;
        gridConstraints.weighty = 50;
        gridConstraints.insets = new Insets(0, 20, 25, 20);
        gridConstraints.anchor = place;
        gridConstraints.fill = stretch;

        thePanel.add(comp, gridConstraints);
    }
}