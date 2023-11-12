package linea;
public abstract class StateOfGame {
    public static String notPlayersTurnError = "No es tu turno";
    public static String alreadyFinishedGameError = "El juego ya termino";
    public abstract StateOfGame nextState(Linea partida);
    public abstract void moveBlue();
    public abstract void moveRed();
    public abstract boolean isFinished(Linea partida);
    public abstract String show();
    public abstract boolean isBlueWin();
    public abstract boolean isRedWin();
    public abstract boolean isRedTurn();
    public abstract boolean isBlueTurn();
}
