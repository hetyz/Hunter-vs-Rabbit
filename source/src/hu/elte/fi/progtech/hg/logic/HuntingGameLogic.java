package hu.elte.fi.progtech.hg.logic;

public class HuntingGameLogic {
    private int size;
    private int steps;
    private int countNeighbor;
    private int prevX;
    private int prevY;
    private GameStage stage;
    private HuntingGameFieldModel[][] board;

    private PlayerState playerState;

    private boolean selected;
    private boolean availableMove;

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getSize() {
        return size;
    }

    public int getSteps() {
        return steps;
    }

    public HuntingGameLogic() {
        stage = GameStage.NOT_STARTED;
    }

    public void newGame(int size) {
        this.size = size;
        this.steps = 0;
        this.selected = false;
        this.availableMove = true;
        this.prevX = 0;
        this.prevY = 0;
        this.playerState = PlayerState.HUNTER_TURN;
        this.board = new HuntingGameFieldModel[size][size];

        initializeBoard();

        this.stage = GameStage.PLAYING;
    }

    private void initializeBoard() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                board[row][column] = new HuntingGameFieldModel();
                if (row == size / 2 && column == size / 2) {
                    board[row][column].setFieldState(FieldState.RABBIT);
                } else if (row == 0 && column == 0 || row == size - 1 && column == 0 ||
                        column == size - 1 && row == 0 || column == size - 1 && row == size - 1) {
                    board[row][column].setFieldState(FieldState.HUNTER);
                } else {
                    board[row][column].setFieldState(FieldState.FIELD);
                }
            }
        }
    }

    public void availableMove(int row, int column) {
        cleanMap();

        if (getFieldValue(prevX, prevY) == FieldState.HUNTER && playerState == PlayerState.HUNTER_TURN ||
                getFieldValue(prevX, prevY) == FieldState.RABBIT && playerState == PlayerState.RABBIT_TURN) {
            changeFieldValues(row, column);
            changeFieldValues(row - 1, column);
            changeFieldValues(row + 1, column);
            changeFieldValues(row, column - 1);
            changeFieldValues(row, column + 1);
        }

        changeSingleFieldValue(row, column);
    }

    private void changeFieldValues(int row, int column) {
        if (!(row < 0 || column < 0 || row >= size || column >= size)) {
            if (board[row][column].getFieldState() == FieldState.FIELD) {
                board[row][column].setFieldState(FieldState.NEIGHBOR);
            }
        }
    }

    private void changeSingleFieldValue(int row, int column) {
        if (row == prevX && column == prevY + 1 || row == prevX && column == prevY - 1 ||
                row == prevX + 1 && column == prevY || row == prevX - 1 && column == prevY) {
            if (getFieldValue(row, column) == FieldState.NEIGHBOR) {
                playerMove(row, column);
            }
        }
    }

    private void playerMove(int row, int column) {
        selected = false;
        board[prevX][prevY].setFieldState(FieldState.FIELD);
        if (playerState == PlayerState.HUNTER_TURN) {

            board[row][column].setFieldState(FieldState.HUNTER);
            playerState = PlayerState.RABBIT_TURN;
            ++steps;
        } else {
            board[row][column].setFieldState(FieldState.RABBIT);
            playerState = PlayerState.HUNTER_TURN;
        }
        countNeighbor = 0;
    }

    public FieldState getFieldValue(int row, int column) {
        return board[row][column].getFieldState();
    }

    public boolean isAvailableMove() {
        return availableMove;
    }

    public boolean isGameEnd() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                HuntingGameFieldModel field = board[row][column];
                if (field.getFieldState() == FieldState.RABBIT) {
                    countNeighbor(row - 1, column);
                    countNeighbor(row + 1, column);
                    countNeighbor(row, column - 1);
                    countNeighbor(row, column + 1);
                    if (countNeighbor == 0) {
                        stage = GameStage.HUNTER_WON;
                        availableMove = false;
                    }
                }
            }
        }

        if (steps == 4 * size && countNeighbor != 0) {
            stage = GameStage.RABBIT_WON;
        }

        return steps == 4 * size || !availableMove;
    }

    private void countNeighbor(int row, int column) {
        if (!(row < 0 || column < 0 || row >= size || column >= size)) {
            HuntingGameFieldModel field = board[row][column];
            if (field.getFieldState() == FieldState.FIELD) {
                countNeighbor++;
            }
        }
    }

    private void cleanMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getFieldState() == FieldState.NEIGHBOR) {
                    board[i][j].setFieldState(FieldState.FIELD);
                }
            }
        }
    }

    public String getPlayer() {
        return (playerState == PlayerState.HUNTER_TURN) ? "Hunter" : "Rabbit";
    }

    public GameStage getStage() {
        return stage;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }
}
