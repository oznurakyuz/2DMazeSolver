public class Square {
    /*
    LABİRENT ÜZERİNDEKİ HER BİR SQUARE İÇİN OBJE OLUSTURULACAK.
     */
    private boolean up; // duvarlar için
    private boolean down;
    private boolean right;
    private boolean left;
    
    boolean expanded; // node ile i�im bitti mi
    boolean generated; // node generate edildi mi
    
    private int row; //square location
    private int column;
    private String squareType;


    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public String getSquareType() {
        return squareType;
    }

    public void setSquareType(String squareType) {
        this.squareType = squareType;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Square{" +
                "up=" + up +
                ", down=" + down +
                ", right=" + right +
                ", left=" + left +
                ", row=" + row +
                ", column=" + column +
                ", squareType='" + squareType + '\'' +
                '}';
    }
}
