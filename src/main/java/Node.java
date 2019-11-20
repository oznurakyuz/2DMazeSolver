public class Node {
    //TODO şimdilik nodeların coordinate bilgisi tutuluyor, ileride parent ve cost gibi bilgilerde eklenebilir.
    private int row;
    private int column;

    public Node() {
    }

    public Node(int row, int column) {
        this.row = row;
        this.column = column;
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
        return "Node{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}