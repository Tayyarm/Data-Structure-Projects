package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        // WRITE YOUR CODE HERE
        StdIn.setFile(file);
        int r =StdIn.readInt();
        int c = StdIn.readInt();
        grid = new boolean[r][c];
        totalAliveCells=0;
        for(r=0;r<grid.length;r++)
        {
          for(c=0;c<grid[r].length;c++)
          {
             boolean x = StdIn.readBoolean();
             if(x==true)
             {
                grid[r][c]=ALIVE;
               totalAliveCells ++;
             }
          }
        }       
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        // WRITE YOUR CODE HERE
        if(grid[row][col]==true)
        {
            grid[row][col]=ALIVE;
        }           
        if(grid[row][col]==false)
        {
            grid[row][col]=DEAD;
        }
        return grid[row][col]; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        // WRITE YOUR CODE HERE
       /* boolean result=false;
       for(int i=0; i<grid.length; i++)
       {
         for(int j =0; j<grid[i].length;j++)
         {
            if(grid[i][j]==ALIVE)
            {
                result = true;
                break;
            }
         }
         if(result==true)
            break;
       } 
         return result;// update this line, provided so that code compiles */
      
         if(totalAliveCells>0)
            return true;
         return false;
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {

        int AliveNeighbors=0;
        int rowDir[]={-1,1,0,0,-1,-1,1,1};
        int colDir[]={0,0,-1,1,1,-1,1,-1};
        int totalRow=grid.length;
        int totalCol=grid[0].length;
        for(int i=0;i<rowDir.length;i++)
        {  
            /*int nr = row + rowDir[i];
            int nc = col + colDir[i];
            if(nr==-1)
            nr=grid.length-1;
            else if(nr>grid.length)
            nr=0;
            if(nc==-1)
            nc=grid[i].length-1;
            else if(nc>grid.length)
            nc=0;*/

            
            int nr = (row+rowDir[i]+totalRow)%totalRow;
            int nc = (col+colDir[i]+totalCol)%totalCol;

            if(grid[nr][nc]==ALIVE)  
            AliveNeighbors++;
        } 
        // WRITE YOUR CODE HERE
        return AliveNeighbors; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
     public boolean[][] computeNewGrid () 
    {
       boolean[][] Ngrid=new boolean[grid.length][grid[0].length];
        for(int r =0; r<grid.length;r++)
        {
             for(int c=0;c<grid[r].length;c++)
            {
                Ngrid[r][c]=grid[r][c];
                int AliveNeighbors = numOfAliveNeighbors(r,c);

             if(grid[r][c]==ALIVE && (AliveNeighbors==1||AliveNeighbors==0)) // 1
                Ngrid[r][c]=DEAD;
              else if(grid[r][c]==DEAD && AliveNeighbors==3) // 2
                Ngrid[r][c]=ALIVE;
             else if(grid[r][c]==ALIVE && (AliveNeighbors==2||AliveNeighbors==3))
                Ngrid[r][c]=ALIVE;     
            else if (grid[r][c]==ALIVE && AliveNeighbors>=4)
                Ngrid[r][c]=DEAD;
            }
        } 
        // WRITE YOUR CODE HERE
        return Ngrid;// update this line, provided so that code compiles
    }  

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {

        // WRITE YOUR CODE HERE
        boolean [][] Ngrid = computeNewGrid();
        totalAliveCells=0;
        for(int i=0;i<Ngrid.length;i++)
        {
            for(int j=0; j<Ngrid[i].length;j++)
            {
                grid[i][j]=Ngrid[i][j];
                if(grid[i][j]==ALIVE)
                 totalAliveCells++;               
            }
        }

    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        // WRITE YOUR CODE HERE
        for(int i=0;i<n;i++)
        {
            nextGeneration();
        }
    }
    private void ConnectCells(int r, int c, WeightedQuickUnionUF f, boolean [][]visited)
    {
        if(grid[r][c]==DEAD || visited[r][c]==true)
        {
            return;
        }
        visited[r][c]=true;
        int rowDir[]={-1,1,0,0,-1,-1,1,1};
        int colDir[]={0,0,-1,1,1,-1,1,-1};
        int totalRow=grid.length;
        int totalCol=grid[0].length;
        for(int i =0; i<rowDir.length; i++)
        {
            int nr = (r+rowDir[i]+totalRow)%totalRow;
            int nc = (c+colDir[i]+totalCol)%totalCol;
            if(grid[nr][nc]==ALIVE)
            {
                f.union(r, c, nr, nc);
                ConnectCells(nr, nc, f, visited);
            }
        }

    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {
         
        // WRITE YOUR CODE HERE
        System.out.println("-------------------------begin");
        boolean[][]visited= new boolean[grid.length][grid[0].length];
        int count = 0;
        int[] roots= new int [grid.length*grid[0].length];
        for(int i=0; i<roots.length; i++)
        {
          roots[i]=-1;
        }
        WeightedQuickUnionUF f = new WeightedQuickUnionUF(grid.length, grid[0].length);
       /*  int rowDir[]={-1,1,0,0,-1,-1,1,1};
        int colDir[]={0,0,-1,1,1,-1,1,-1};
        int totalRow=grid.length;
        int totalCol=grid[0].length; */
        for(int i = 0; i<grid.length; i++) 
        {
            for(int j =0; j<grid[i].length; j++)
            {
                if(grid[i][j]==DEAD)
                    continue;
                ConnectCells(i, j, f,visited);  
             /*    boolean LoneCell= true;    
             for(int k=0;k<rowDir.length;k++)
             {                
                int nr = (i+rowDir[k]+totalRow)%totalRow;
                int nc = (j+colDir[k]+totalCol)%totalCol;

                   if(grid[nr][nc]==ALIVE)
                   {
                        LoneCell=false;
                        f.union(i, j, nr, nc);
                        int root = f.find(i, j);
                        boolean found = false;
                        System.out.println("i=" +i+", j=" +j+", nr="+nr+", nc="+nc+", root=" + root);
                        for(int l=0;l<count;l++)
                        {
                          if(roots[l]==root)
                          {
                            found=true;
                            break;
                          }
                        }
                        if(found==false)
                        {
                          roots[count]=root;
                          count++;
                        }
                   }
             }   
               if(LoneCell==true)
               {
                  int root = f.find(i, j);
                  roots[count]=root;
                  count++;
               }          
             */
            }
        }
        for(int i=0;i<grid.length;i++)
        {
            for(int j=0;j<grid[i].length;j++)
            {
                if(grid[i][j]==DEAD)
                 continue;
                int root=f.find(i, j);
                boolean found=false;
              for(int k=0; k<count; k++)
              {
                if(roots[k]==root)
                {
                    found=true;
                    break;
                }

              }  
              if(found==false)
              {
                roots[count]=root;
                count++;
              }                 
            }           
        }
         System.out.println("-------------------------end");
        return count; // update this line, provided so that code compiles
    }
}