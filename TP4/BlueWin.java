package linea;
public class BlueWin extends StateOfGame{

    public StateOfGame nextState(Linea partida){
        return new BlueWin();
    }

    public void moveBlue(){
        throw new RuntimeException(alreadyFinishedGameError);
    }
    public void moveRed(){
        throw new RuntimeException(alreadyFinishedGameError);
    }

    public boolean isFinished(Linea partida){
        return true;
    }

    public String show() {
        return "Resultado: Gano Azul";
    }

    public boolean isBlueWin(){
        return true;
    }

    public boolean isRedWin(){
        return false;
    }

    public boolean isBlueTurn(){
        return false;
    }

    public boolean isRedTurn(){
        return false;
    }
}
