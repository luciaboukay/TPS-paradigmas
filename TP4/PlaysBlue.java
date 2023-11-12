package linea;
public class PlaysBlue extends StateOfGame {

    public StateOfGame nextState(Linea partida) {
        if (partida.getModoDeJuego().azulGanaPartida(partida)){
            return new BlueWin();
        }
        else if (partida.isThereTie()) {
            return new Tie();
        }
        return new PlaysRed();
    }

    public void moveBlue(){
       return;
    }

    public void moveRed(){
        throw new RuntimeException(notPlayersTurnError);
    }

    public boolean isFinished(Linea partida){
        return false;
    }

    public String show(){
        return "Partida en curso: Turno de Azul";
    }

    public boolean isBlueWin(){
        return false;
    }

    public boolean isRedWin(){
        return false;
    }

    public boolean isBlueTurn(){
        return true;
    }

    public boolean isRedTurn(){
        return false;
    }
}