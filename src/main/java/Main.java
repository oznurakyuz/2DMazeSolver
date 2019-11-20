public class Main {
    public static void main(String [] args){
        MazeReader mazeReader = new MazeReader();
        mazeReader.readMazeFile("src/input/maze.txt");
        Square[][] squares=mazeReader.createMazeSquares();
    }
}
