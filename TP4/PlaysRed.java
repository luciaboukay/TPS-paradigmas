package linea;
public class PlaysRed extends StateOfGame {

    public StateOfGame nextState(Linea partida){
        if (partida.getModoDeJuego().rojoGanaPartida(partida)) {
            return new RedWin();
        }
        else if (partida.isThereTie()){
            return new Tie();
        }
        return new PlaysBlue();
    }

    public void moveRed(){
        return;
    }

    public void moveBlue(){
        throw new RuntimeException(notPlayersTurnError);
    }

    public boolean isFinished(Linea partida){
        return false;
    }

    public String show(){
        return "Partida en curso: Turno de Rojo";
    }

    public boolean isBlueWin(){
        return false;
    }

    public boolean isRedWin(){
        return false;
    }

    public boolean isBlueTurn(){
        return false;
    }

    public boolean isRedTurn(){
        return true;
    }
}
