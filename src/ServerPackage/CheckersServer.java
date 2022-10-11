package ServerPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Date;

/**
 * serwer gry
 */
public class CheckersServer {

    private static final int SERVER_PORT = 8901;
    private static int matchNumber = 1;

    /**
     * metoda startowa
     * @param args argumenty przekazane przy uruchomieniu programu
     * @throws IOException nieudana próba połączenia klienta
     */
    public static void main(String[] args) throws IOException {

        Date nowDate = new Date();
        PrintWriter firstWrite = new PrintWriter(new FileWriter("log.txt", true));
        ServerSocket serversocket = new ServerSocket(SERVER_PORT);
        System.out.println(nowDate + " > Uruchomienie serwera");
        firstWrite.println(nowDate + " > Uruchomienie serwera");
        firstWrite.close();

        try {
            while (true) {
                PrintWriter secondWrite = new PrintWriter(new FileWriter("log.txt", true));
                Match match = new Match(matchNumber);
                System.out.println(nowDate + " > Czekanie na graczy...");
                secondWrite.println(nowDate + " > Czekanie na graczy...");
                try {
                    Match.Player playerWhite = match.new Player(serversocket.accept(), GameData.WHITE);
                    System.out.println(nowDate + " > Mecz #" + matchNumber + ": gracz #1 dołączył.");
                    secondWrite.println(nowDate + " > Mecz #" + matchNumber + ": gracz #1 dołączył.");

                    Match.Player playerBlack = match.new Player(serversocket.accept(), GameData.BLACK);
                    System.out.println(nowDate + " > Mecz #" + matchNumber + ": gracz #2 dołączył.");
                    secondWrite.println(nowDate + " > Mecz #" + matchNumber + ": gracz #2 dołączył.");

                    System.out.println(nowDate + " > Mecz #" + matchNumber + " się rozpoczą.");
                    secondWrite.println(nowDate + " > Mecz #" + matchNumber + " się rozpoczą.");

                    playerWhite.start();
                    playerBlack.start();
                    matchNumber++;
                    secondWrite.close();

                } catch (IOException e) {
                    System.out.println("IOError");
                }
            }
        } finally {
            serversocket.close();
        }
    }
}