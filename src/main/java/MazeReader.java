import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MazeReader {
    private ArrayList<Node> verticleWalls;
    private ArrayList<Node> horizontalWalls;
    private ArrayList<Node> goalSquares;
    private ArrayList<Node> trapSquares;
    private Node startSquare;
    private int row, column;

    public MazeReader() {
        this.verticleWalls = new ArrayList<>();
        this.horizontalWalls = new ArrayList<>();
        this.goalSquares = new ArrayList<>();
        this.trapSquares = new ArrayList<>();
        this.startSquare = null;
        this.row = 0;
        this.column = 0;
    }

    public void readMazeFile(String fileName) {
        String inputLine;
        String[] input;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            inputLine = reader.readLine(); //input dosyasında ilk satır labirentin boyutunu içeriyor
            input = inputLine.split("\\s");
            row = Integer.parseInt(input[0]);
            column = Integer.parseInt(input[1]);

            while ((inputLine = reader.readLine()) != null) { // her bir line squaretype row column şeklinde
                input = inputLine.split("\\s");

                switch (input[0]) {
                    case "v":  //eğer type v ise, labirentte vertical bir duvar var
                        verticleWalls.add(new Node(Integer.parseInt(input[1]), Integer.parseInt(input[2])));
                        break;

                    case "h": //eğer type h ise, labirentte horizontal bir duvar var
                        horizontalWalls.add(new Node(Integer.parseInt(input[1]), Integer.parseInt(input[2])));
                        break;

                    case "s": //eğer type s ise, start point
                        startSquare = new Node(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                        break;

                    case "t": //eğer type t ise, trap var
                        trapSquares.add(new Node(Integer.parseInt(input[1]), Integer.parseInt(input[2])));
                        break;

                    case "g": //eğer type g ise, goal var
                        goalSquares.add(new Node(Integer.parseInt(input[1]), Integer.parseInt(input[2])));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    ÖRNEĞİN LABİRENT BOYUTU 8-8 VERİLMİŞSE, İNDEKSLER 0-7 ARASINDA OLACAK
     */
    public Square[][] createMazeSquares() {
        Square[][] squares = new Square[this.row][this.column];
        for (int i = 0; i < this.row; i++) { //size'a göre squareleri create ediyor. hepsi şimdilik normal(n) square, coordinate veriliyor
            for (int j = 0; j < this.column; j++) {
                squares[i][j] = new Square();
                squares[i][j].setRow(i);
                squares[i][j].setColumn(j);
                squares[i][j].setSquareType("n");
            }
        }
        squares[startSquare.getRow()][startSquare.getColumn()].setSquareType("s"); //start(s) square

        for (Node node : goalSquares) {
            squares[node.getRow()][node.getColumn()].setSquareType("g"); //goal(g) square
        }

        for (Node node : trapSquares) {
            squares[node.getRow()][node.getColumn()].setSquareType("t"); //trap(t) square
        }

        for (Node node : verticleWalls) {// eğer verticle bi duvar varsa
            squares[node.getRow()][node.getColumn()].setRight(true);//current square de right'a
            squares[node.getRow()][node.getColumn() + 1].setLeft(true); //columnda bir sonrakininde(yanındaki square) left'e duvar konuyor
        }

        for (Node node : horizontalWalls) {// eğer horizontal bi duvar varsa
            squares[node.getRow()][node.getColumn()].setDown(true);//current square de down'a
            squares[node.getRow() + 1][node.getColumn()].setUp(true);//rowda bir sonrakininde(altındaki square) top'a duvar konuyor
        }

        for (int i = 0; i < this.column; i++) {//alt-üst kenar squarelerde
            squares[1][i].setUp(true); //en üstteki karelerin üstüne duvar koy
            squares[this.row - 1][i].setDown(true); //en alttaki karelerin altına duvar koy
        }
        for (int i = 0; i < this.row; i++) {//sağ-sol kenar squarelerde
            squares[i][1].setLeft(true);//en soldaki karelerin soluna duvar koy
            squares[i][this.column - 1].setRight(true); //en sagdaki karelerin sağına duvar koy
        }

        /*for(int i = 0 ; i < this.row; i++){
            for(int j = 0; j < this.column; j++){
                System.out.println(squares[i][j]);
            }
        }*/
        return squares;
    }

}
