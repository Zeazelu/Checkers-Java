package ServerPackage;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class CheckersServerTest {

    private static final int SERVER_PORT = 1111;
    private static int matchNumber = 1;

    @Test
    void main() throws IOException {
        ServerSocket serversocket = new ServerSocket(SERVER_PORT);
        System.out.println("Uruchomienie serwera");

        try {
            while (true) {
                Match match = new Match(matchNumber);
                System.out.println("Czekanie na graczy...");
                try {
                    Match.Player playerWhite = match.new Player(serversocket.accept(), GameData.WHITE);
                    System.out.println("Mecz #" + matchNumber + ": gracz #1 dołączył.");

                    Match.Player playerBlack = match.new Player(serversocket.accept(), GameData.BLACK);
                    System.out.println("Mecz #" + matchNumber + ": gracz #2 dołączył.");

                    System.out.println("Mecz #" + matchNumber + " się rozpoczą.");

                    playerWhite.start();
                    playerBlack.start();
                    matchNumber++;
                } catch (IOException e) {
                    System.out.println("IOError");
                }
            }
        } finally {
            serversocket.close();
        }
    }
}