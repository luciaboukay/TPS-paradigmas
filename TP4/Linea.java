package linea;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.StringJoiner;

public class Linea {
    public static String InvalidPositionError = "Posicion invalida";
    public static String RedPlayed = "X";
    public static String BluePlayed = "O";
    private int base;
    private int altura;
    private GameMode gamemode;
    private StateOfGame estadoDeJuego;
    private String actualshow;
    private List<List<String>> lineas;

    public Linea(int base, int altura, char gamemode){
        this.base = base;
        this.altura = altura;
        this.gamemode = setGameMode(gamemode);
        estadoDeJuego =  new PlaysRed();
        lineas = IntStream.range(0, base)
                .mapToObj(i -> new ArrayList<String>())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean finished() {
        return altura <=0 || base <=0 || estadoDeJuego.isFinished(this);
    }

    public void playRedAt( int position ){
        checkCloumn(position);
        estadoDeJuego.moveRed();
        addMoveToGame(position, RedPlayed);
    }

    public void playBlueAt( int position ){
        checkCloumn(position);
        estadoDeJuego.moveBlue();
        addMoveToGame(position, BluePlayed);

    }

    public boolean goesRed(){
        return estadoDeJuego.isRedTurn();
    }

    public boolean goesBlue(){
        return estadoDeJuego.isBlueTurn();
    }

    public String show() {
        actualshow = "";
        actualshow += "Juego: " + base + "x" + altura + "\n";
        actualshow += "Modo: " + gamemode.show() + "\n";
        actualshow += IntStream.range(0, altura)
                .mapToObj(j -> {
                    StringJoiner joiner = new StringJoiner(" ");
                    IntStream.range(0, base).forEach(i -> joiner.add(lineas.get(i).size() > (altura - 1 - j) ? "| " + lineas.get(i).get(altura - 1 - j) : "| -"));
                    return joiner.toString();
                })
                .collect(Collectors.joining(" |\n"));
        actualshow += " |\n" + showGameResult();

        return actualshow;
    }

    public boolean isFicha(int columna, int fila, String ficha) {
        if (columna >= 0 && columna < lineas.size() && (fila >= 0 && fila < lineas.get(columna).size())) {
            return lineas.get(columna).get(fila).equals(ficha);
        }
        return false;
    }

    public boolean isThereTie() {
        return (lineas.stream().map(List::size).reduce(0, Integer::sum) == base * altura) && !(gamemode.rojoGanaPartida(this) || gamemode.azulGanaPartida(this));

    }

    public boolean triunfoDiagonalDeJugador(String jugador) {
        return (base * altura >= 16) && (IntStream.range(0, base - 3)
                .anyMatch(i -> IntStream.range(0, altura - 3)
                        .anyMatch(j -> IntStream.range(0, 4)
                                .allMatch(k -> isFicha(i + k, j + k, jugador))
                                || IntStream.range(0, 4).allMatch(k -> isFicha(i + k, j + 3 - k, jugador)))));

    }

    public boolean jugadorganoHorizontal(String jugador) {
        return IntStream.range(0, base - 3)
                .anyMatch(i -> IntStream.range(0, altura)
                        .anyMatch(j -> IntStream.range(0,4)
                                .allMatch(k -> isFicha(i + k, j, jugador))));
    }

    public boolean jugadorganoVertical( String jugador) {
        return IntStream.range(0, base)
                .anyMatch(i -> IntStream.range(0, altura - 3)
                        .anyMatch(j -> IntStream.range(0, 4)
                                .allMatch(k -> isFicha(i, j + k, jugador))));

    }

    public String showGameResult(){
        return estadoDeJuego.show();
    }

    public boolean blueWon(){
        return estadoDeJuego.isBlueWin();
    }

    public boolean redWon(){
        return estadoDeJuego.isRedWin();
    }

    private void checkCloumn(int position) {
        if ((position > base || position <= 0 || lineas.get(position - 1).size() == altura) && !finished()) {
            throw new RuntimeException(InvalidPositionError);
        }
    }

    private void addMoveToGame(int position , String ficha) {
        lineas.get(position -1).add(ficha);
        setLineas(lineas);
        setEstadoDeJuego(estadoDeJuego.nextState(this));
    }

    public GameMode getModoDeJuego(){
        return gamemode;
    }

    public GameMode setGameMode(char attemptedGameMode){
        return GameMode.gameModesAvailable.stream()
                .filter(gameModeAvailable -> gameModeAvailable.matches(attemptedGameMode))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(GameMode.notAvailableGameModeError));
    }

    public void setLineas(List<List<String>> lineas) {
        this.lineas = lineas;
    }

    public void setEstadoDeJuego(StateOfGame estadoDeJuego) {
        this.estadoDeJuego = estadoDeJuego;
    }

}
