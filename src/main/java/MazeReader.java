import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MazeReader {
    private ArrayList<Node> verticleWalls;
    private ArrayList<Node> horizontalWalls;
    private ArrayList<Node> goalSquares;
    private ArrayList<Node> trapSquares;
    private Square [][] squares;	// all squares must be global
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
        squares = new Square[this.row][this.column];
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
            squares[0][i].setUp(true); //en üstteki karelerin üstüne duvar koy
            squares[this.row - 1][i].setDown(true); //en alttaki karelerin altına duvar koy
        }
        for (int i = 0; i < this.row; i++) {//sağ-sol kenar squarelerde
            squares[i][0].setLeft(true);//en soldaki karelerin soluna duvar koy
            squares[i][this.column - 1].setRight(true); //en sagdaki karelerin sağına duvar koy
        }

        /*for(int i = 0 ; i < this.row; i++){
            for(int j = 0; j < this.column; j++){
                System.out.println(squares[i][j]);
            }
        }*/
        BFS(); // şimdilik sadece bunu çalıştırsın
        return squares;
    }
    
    public void DFS() {}
    public void BFS() {
    	
    	Queue frontier = new LinkedList();	// frontierin FIFO modunda olması lazım o yüzden queue
    	ArrayList<Square> array = new ArrayList<Square>();	// frontierda path'i tutmamız lazım
    	array.add(squares[startSquare.getRow()][startSquare.getColumn()]);	// başlangıçta start node var frontierda
    	frontier.add(array);
    	squares[startSquare.getRow()][startSquare.getColumn()].generated = true;	// frontiera koyuldu bilgisi
    	
    	while(!frontier.isEmpty()) {
    		ArrayList<Square> path = (ArrayList<Square>) frontier.remove();	// frontierda ilk arraylisti çıkar
    		Square s = path.get(path.size() - 1);	// çıkarılan arraylisten ilk square getir
    		    		
    		checkEast(s, path, frontier);	// squarin ilk doğusuna (labirentte sağ taraf) bak
    		checkSouth(s, path, frontier);	// sonra güneyine (labirentte alt taraf) bak
    		checkWest(s, path, frontier);	// sonra batısına (labirentte sol taraf) bak
    		checkNorth(s, path, frontier);	// sonra kuzeyine (labirentte üst taraf) bak
    		s.expanded = true;	// bütün yönlerine baktım
    		if (s.getSquareType().equals("g")) {
    			System.out.println("BFS Have Found the goal state. And it is :");
    			System.out.println(s);
    			System.out.print("And the cost is : ");
    			calculateTotalCost(path);
    			break; 
    		}
    	}
    }
    public void ID() {}
    public void UCS() {}
    public void GBFS() {}
    public void Astar() {}
    public void checkEast(Square s, ArrayList<Square> path, Queue f) {
    	ArrayList<Square> neighbor;
    	if (!s.isRight() && !squares[s.getRow()][s.getColumn()+1].generated) {	// sağ tarafında duvar var mı ve sağ tarafı frontierda var mı
    		neighbor = new ArrayList<Square>();	// yoksa bütün listeyi buna ekle
    		for (Square sq : path) {
    			neighbor.add(sq);
    		}
    		
    		neighbor.add(squares[s.getRow()][s.getColumn()+1]);		// eski listeye yeni elemanı ekle (sağ taraf)
    		f.add(neighbor);	// frontiera da ekle yeni elemanı
    		squares[s.getRow()][s.getColumn()+1].generated = true;	// frontiera ekledim bilgisi
    		//System.out.println("East :"+squares[s.getRow()][s.getColumn()+1]);
    	}
    }
    public void checkSouth(Square s, ArrayList<Square> path, Queue f) {
    	ArrayList<Square> neighbor;
    	if (!s.isDown() && !squares[s.getRow()+1][s.getColumn()].generated) {	// alt tarafında duvar var mı ve alt tarafı frontierda var mı
    		neighbor = new ArrayList<Square>();	// yoksa bütün listeyi buna ekle
    		for (Square sq : path) {
    			neighbor.add(sq);
    		}
    		
    		neighbor.add(squares[s.getRow()+1][s.getColumn()]);		// eski listeye yeni elemanı ekle (sağ taraf)
    		f.add(neighbor);	// frontiera da ekle yeni elemanı
    		squares[s.getRow()+1][s.getColumn()].generated = true;	// frontiera ekledim bilgisi
    		//System.out.println("South :"+squares[s.getRow()+1][s.getColumn()]);
    	}
    }
    public void checkWest(Square s, ArrayList<Square> path, Queue f) {
    	ArrayList<Square> neighbor;
    	if (!s.isLeft() && !squares[s.getRow()][s.getColumn()-1].generated) {	// sol tarafında duvar var mı ve sol tarafı frontierda var mı
    		neighbor = new ArrayList<Square>();	// yoksa bütün listeyi buna ekle
    		for (Square sq : path) {
    			neighbor.add(sq);
    		}
    		
    		neighbor.add(squares[s.getRow()][s.getColumn()-1]);	// eski listeye yeni elemanı ekle (sol taraf)
    		f.add(neighbor);	// frontiera da ekle yeni elemanı
    		squares[s.getRow()][s.getColumn()-1].generated = true;	// frontiera ekledim bilgisi
    		//System.out.println("West :"+squares[s.getRow()][s.getColumn()-1]);
    	}
    }
    public void checkNorth(Square s, ArrayList<Square> path, Queue f) {	
    	ArrayList<Square> neighbor;
    	if (!s.isUp() && !squares[s.getRow()-1][s.getColumn()].generated) {	// üst tarafında duvar var mı ve üst tarafı frontierda var mı
    		neighbor = new ArrayList<Square>();	// yoksa bütün listeyi buna ekle
    		for (Square sq : path) {
    			neighbor.add(sq);
    		}
    		
    		neighbor.add(squares[s.getRow()-1][s.getColumn()]);	// eski listeye yeni elemanı ekle (üst taraf)
    		f.add(neighbor);	// frontiera da ekle yeni elemanı
    		squares[s.getRow()-1][s.getColumn()].generated = true;	// frontiera ekledim bilgisi
    		//System.out.println("North :"+squares[s.getRow()-1][s.getColumn()]);
    	}
    }
    
    public void calculateTotalCost(ArrayList<Square> path) {
    	int sum = -1;	// pathin içerisinde start squarede var. ondada sum artıyor
    	for (Square s: path) {	// bütün stateler için
    		if (s.getSquareType().equals("t"))	sum += 7;	// trap state için 
    		else sum++;	
    	}
    	System.out.print(sum);
    }
        
}
