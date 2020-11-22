package Model;

public interface ModelUpdateListener {
    public void modelUpdated();

    public void gameOver();

    public void announceElimination(int playerNumber);
}
