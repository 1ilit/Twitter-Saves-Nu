import java.util.Collections;
import java.util.Stack;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class Maze {
    /**
     * A class to store vertices
     */
    private class V2{
        int x;
        int y;
        V2 next;

        public V2(int x, int y){
            this.x=x;
            this.y=y;
        }
    }

    private final int n;
    private String[][] maze;
    private char facingDir;
    private V2 currentPos;

    /**
     * @param n number of cells in a maze
     */
    public Maze(int n) {
        this.n = n;

        String[][] grid = generateGrid();
        maze = generateMaze(grid);

        facingDir='S';
        currentPos=new V2(1, 0);
        maze[0][1]="👾";
    }

    /**
     * Generates a 2d grid of specified number of cells,
     * with walls being black squares,
     * start and finish tiles as yellow squares,
     * cell tiles as light purple squares
     *
     * @return a 2d array representation of the grid
     */
    private String[][] generateGrid(){
        int dim=2*n+1;
        String[][] grid=new String[dim][dim];
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){

                if(i==0 && j==1)
                    grid[i][j]="\uD83D\uDFE8";

                else if(i==dim-1 && j==dim-2)
                    grid[i][j]="\uD83D\uDFE8";

                else if(i%2==0){
                    grid[i][j]="⬛";
                }
                else{
                    if(j%2==0)
                        grid[i][j]="⬛";
                    else
                        grid[i][j]="\uD83D\uDFEA";
                }
            }
        }
        return grid;
    }

    /**
     * @param grid the grid generated in generateGrid()
     *             uses DFS to create a maze by "breaking" walls and replacing with purple tiles
     * @return a 2d array representation of the maze
     */
    private String[][] generateMaze(String[][] grid){
        Stack<V2> stack=new Stack<>();
        int size= (grid.length-1)/2;
        int nV=size*size;
        int visited=1;
        V2 current=new V2(0, 0);

        while(visited<nV){
            ArrayList<String> dirs=new ArrayList<>();
            dirs.add("east");
            dirs.add("west");
            dirs.add("north");
            dirs.add("south");
            Collections.shuffle(dirs);
            String random=getDir(grid, current, dirs);

            if(random.equals("backtrack")){
                current=stack.pop();
                continue;
            }

            current=move(grid, current, random);
            visited++;
            stack.push(current);
        }
        return grid;
    }

    /**
     * A helper method to perform DFS that randomly selects a direction to move
     * @param grid the grid passed to generateMaze()
     * @param current the current position of where we have reached in breaking the wall
     * @param dirs an arraylist of strings containing 4 directions
     * @return a direction out of the 4 in the arraylist
     */
    private String getDir(String[][] grid, V2 current, ArrayList<String> dirs){
        int size=(grid.length-1)/2;

        int x=2*current.x+1;
        int y=2*current.y+1;

        if(dirs.size()==0){
            return "backtrack";
        }

        String r=dirs.remove(0);
        switch (r) {
            case "north" -> {
                if (current.y - 1 < 0) {
                    return getDir(grid, current, dirs);
                }
                if ((grid[y - 3][x].equals("\uD83D\uDFEA") || grid[y - 1][x].equals("\uD83D\uDFEA"))
                        || (grid[y - 2][x - 1].equals("\uD83D\uDFEA") || grid[y - 2][x + 1].equals("\uD83D\uDFEA"))) {
                    return getDir(grid, current, dirs);
                }
            }
            case "south" -> {
                if (current.y + 1 >= size) {
                    return getDir(grid, current, dirs);
                }
                if ((grid[y + 1][x].equals("\uD83D\uDFEA") || grid[y + 3][x].equals("\uD83D\uDFEA"))
                        || (grid[y + 2][x - 1].equals("\uD83D\uDFEA") || grid[y + 2][x + 1].equals("\uD83D\uDFEA"))) {
                    return getDir(grid, current, dirs);
                }
            }
            case "east" -> {
                if (current.x + 1 >= size) {
                    return getDir(grid, current, dirs);
                }
                if ((grid[y + 1][x + 2].equals("\uD83D\uDFEA")|| grid[y - 1][x + 2].equals("\uD83D\uDFEA"))
                        || (grid[y][x + 1].equals("\uD83D\uDFEA") || grid[y][x + 3].equals("\uD83D\uDFEA"))) {
                    return getDir(grid, current, dirs);
                }
            }
            case "west" -> {
                if (current.x - 1 < 0) {
                    return getDir(grid, current, dirs);
                }
                if ((grid[y - 1][x - 2].equals("\uD83D\uDFEA") || grid[y + 1][x - 2].equals("\uD83D\uDFEA"))
                        || (grid[y][x - 3].equals("\uD83D\uDFEA") || grid[y][x - 1].equals("\uD83D\uDFEA"))) {
                    return getDir(grid, current, dirs);
                }
            }
        }
        return r;
    }


    /**
     * @param grid the grid in generateMaze()
     * @param current current position
     * @param random a random direction
     * @return the current (already moved) position
     */
    private V2 move(String[][] grid, V2 current, String random){
        grid[1][1]="\uD83D\uDFEA";
        if(random.equals("north")){
            current.next=new V2(current.x, current.y-1);
            current=current.next;
            grid[2*current.y+2][2*current.x+1]="\uD83D\uDFEA";
            grid[2*current.y+1][2*current.x+1]="\uD83D\uDFEA";
        }
        if(random.equals("east")){
            current.next=new V2(current.x+1, current.y);
            current=current.next;
            grid[2*current.y+1][2*current.x]="\uD83D\uDFEA";
            grid[2*current.y+1][2*current.x+1]="\uD83D\uDFEA";
        }
        if(random.equals("south")){
            current.next=new V2(current.x, current.y+1);
            current=current.next;
            grid[2*current.y][2*current.x+1]="\uD83D\uDFEA";
            grid[2*current.y+1][2*current.x+1]="\uD83D\uDFEA";
        }
        if(random.equals("west")){
            current.next=new V2(current.x-1, current.y);
            current=current.next;
            grid[2*current.y+1][2*current.x+2]="\uD83D\uDFEA";
            grid[2*current.y+1][2*current.x+1]="\uD83D\uDFEA";
        }
        return current;
    }

    /**
     * @return a string representation of the maze
     */
    public String toString(){
        StringBuilder retVal=new StringBuilder();
        for (String[] i : maze) {
            for (String j : i) {
                retVal.append(j);
            }
            retVal.append("\n");
        }
        return retVal.toString();
    }

    /**
     * @return the direction Nu is facing
     */
    public char getFacingDir(){
        return facingDir;
    }

    /**
     * Moves Nu 90 degrees clockwise or counter-clockwise
     * @param action [like (l), retweet(r), or same(s)]
     */
    public void update(char action){
        if(!maze[0][1].equals("👾")){
            maze[0][1]="\uD83D\uDFE8";
        }
        if(action=='l'){ //move 90 degrees counter-clockwise
            //N->W
            if(facingDir=='N'){
                if (!maze[currentPos.y][currentPos.x - 1].equals("⬛")
                        && withinBounds(currentPos.x - 1, currentPos.y)) {

                    maze[currentPos.y][currentPos.x - 1] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.x = currentPos.x - 1;
                    facingDir='W';
                }
            }
            //E->N
            else if(facingDir=='E'){
                if (!maze[currentPos.y - 1][currentPos.x].equals("⬛") &&
                        withinBounds(currentPos.x, currentPos.y-1)) {

                    maze[currentPos.y - 1][currentPos.x] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.y = currentPos.y - 1;
                    facingDir='N';
                }
            }
            //W->S
            else if(facingDir=='W'){
                if (!maze[currentPos.y + 1][currentPos.x].equals("⬛") &&
                        withinBounds(currentPos.x, currentPos.y+1)) {

                    maze[currentPos.y + 1][currentPos.x] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.y = currentPos.y + 1;
                    facingDir='S';
                }
            }
            //S->E
            else if(facingDir=='S'){
                if (!maze[currentPos.y][currentPos.x + 1].equals("⬛") &&
                        withinBounds(currentPos.x + 1, currentPos.y)) {

                    maze[currentPos.y][currentPos.x + 1] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.x = currentPos.x + 1;
                    facingDir='E';
                }
            }
        }
        else if(action=='r'){//move 90 degrees clockwise
            //S->W
            if(facingDir=='S'){
                if (!maze[currentPos.y][currentPos.x - 1].equals("⬛")
                        && withinBounds(currentPos.x - 1, currentPos.y)) {

                    maze[currentPos.y][currentPos.x - 1] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.x = currentPos.x - 1;
                    facingDir='W';
                }
            }
            //W->N
            else if(facingDir=='W'){
                if (!maze[currentPos.y - 1][currentPos.x].equals("⬛")&&
                        withinBounds(currentPos.x, currentPos.y-1)) {

                    maze[currentPos.y - 1][currentPos.x] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.y = currentPos.y - 1;
                    facingDir='N';
                }
            }
            //E->S
            else if(facingDir=='E'){
                if (!maze[currentPos.y + 1][currentPos.x].equals("⬛")&&
                        withinBounds(currentPos.x, currentPos.y+1)) {

                    maze[currentPos.y + 1][currentPos.x] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.y = currentPos.y + 1;
                    facingDir='S';
                }
            }
            //N->E
            else if(facingDir=='N'){
                if (!maze[currentPos.y][currentPos.x + 1].equals("⬛") &&
                        withinBounds(currentPos.x + 1, currentPos.y)) {

                    maze[currentPos.y][currentPos.x + 1] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.x = currentPos.x + 1;
                    facingDir='E';
                }
            }
        }
        else if(action=='s'){//move Nu in the direction he's facing
            //W->->
            if(facingDir=='W'){
                if (!maze[currentPos.y][currentPos.x - 1].equals("⬛")
                        && withinBounds(currentPos.x - 1, currentPos.y)) {

                    maze[currentPos.y][currentPos.x - 1] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.x = currentPos.x - 1;
                    //facingDir='W';
                }
            }
            //N->->
            else if(facingDir=='N'){
                if (!maze[currentPos.y - 1][currentPos.x].equals("⬛") &&
                        withinBounds(currentPos.x, currentPos.y-1)) {

                    maze[currentPos.y - 1][currentPos.x] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.y = currentPos.y - 1;
                    //facingDir='N';
                }
            }
            //S->->
            else if(facingDir=='S'){
                if (!maze[currentPos.y + 1][currentPos.x].equals("⬛") &&
                        withinBounds(currentPos.x, currentPos.y+1)) {

                    maze[currentPos.y + 1][currentPos.x] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.y = currentPos.y + 1;
                    //facingDir='S';
                }
            }
            //E->->
            else if(facingDir=='E'){
                if (!maze[currentPos.y][currentPos.x + 1].equals("⬛")&&
                        withinBounds(currentPos.x + 1, currentPos.y)) {

                    maze[currentPos.y][currentPos.x + 1] = "👾";
                    maze[currentPos.y][currentPos.x] = "\uD83D\uDFEA";
                    currentPos.x = currentPos.x + 1;
                    //facingDir='E';
                }
            }
        }
    }

    /**
     * @param x x component of the vertex
     * @param y y component of the vertex
     * @return whether the vertex is within the bounds of the grid
     */
    private boolean withinBounds(int x, int y){
        return x<2*n && y<2*n && x>=1 && y>=1;
    }
}

