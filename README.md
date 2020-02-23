# 2D Maze Solver
#### _CSE4082 Artificial Intelligence Course Project (Marmara University)_
This program solves 2D maze using various uninformed and informed (heuristic) search strategies. It takes the maze structure as input file and performs selected search algorithm. The cost of the solution found, the solution path itself and the list of expanded nodes are given as output. Thus, the results and differences of algorithms can be compared. Also, a GUI is implemented that visualizes the search steps.

## Details of the Program
### - Input File
The first line of the input file consists of 2 numbers, that are mapped to row and column indexes, representing the dimensions of the maze. The remaining lines consist of 1 letter and 2 numbers. The letter indicates the expression in the line(the type of wall or square), while the numbers indicate the row and column indexes, respectively.


Maze Structure             |  Input Letter
:-------------------------:|:-------------------------:
<img src="https://i.ibb.co/QktYcrZ/Screenshot-7.png" height="253" width="254"/>|  **v:** vertical walls <br /> **h:** horizontal walls <br />**s:** the starting square <br />**t:** trap squares <br />**g:** goal squares


### - Execution
![start_screen](https://i.ibb.co/xLXrXQJ/Screenshot-5.png) <br />
When the program is run, the algorithm must be selected. The program consists of following algorithms:
1. Depth First Search
2. Breadth First Search
3. Iterative Deepening
4. Uniform Cost Search
5. Greedy Best First Search
6. A* Heuristic Search

For Greedy Best First Search and A* Heuristic Search,city block distance (Manhattan distance) is used as an admissible heuristic.

### - Example Output
<img src="https://i.ibb.co/dMWmh7X/output.png" />
