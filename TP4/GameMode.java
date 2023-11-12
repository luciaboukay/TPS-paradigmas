package linea;
import java.util.Arrays;
import java.util.List;

public abstract class GameMode {
    public static String notAvailableGameModeError = "Modo de juego no disponible";
    private char key;
    public GameMode(char key){
        this.key = key;
    }

    public static final List<GameMode> gameModesAvailable = Arrays.asList(new VerticalAndHorizontalMode(), new DiagonalMode(), new VerticalHorizontalAndDiagonalMode());

    protected boolean matches(char key){
        return this.key == key;
    }

    public abstract boolean rojoGanaPartida(Linea partida);

    public abstract boolean azulGanaPartida(Linea partida);

    public abstract String show();

}
