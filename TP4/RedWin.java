package linea;
public class RedWin extends StateOfGame{
    public StateOfGame nextState(Linea partida){
        return new RedWin();
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

    public String show(){
        return "Resultado: Gano Rojo";
    }

    public boolean isBlueWin(){
        return false;
    }

    public boolean isRedWin(){
        return true;
    }

    public boolean isBlueTurn(){
        return false;
    }

    public boolean isRedTurn(){
        return false;
    }
}

