import java.util.Vector;

public class Board {
	
	public int height;
	public int width;
	public int[] heights;
	public int[] sums;
	public byte[][] cells;
	
	public Board(Board b){
		Board newBoard = new Board(b.height,b.width);
		for(int i=0;i<b.width;i++){
			newBoard.heights[i] = b.heights[i];
		}
		for(int i=0;i<b.height;i++){
			newBoard.sums[i] = b.sums[i];
		}
		for(int i=0;i<b.height;i++){
			for(int j=0;j<b.width;j++){
				newBoard.cells[i][j] = b.cells[i][j];
			}
		}
	}
	
	public Board(int heigth,int width) {
		this.height = heigth;
		this.width = width;
		heights = new int[width];
		for(int i=0;i<width;i++){
			heights[i] = heigth-1;
		}
		sums = new int[heigth];
		for(int i=0;i<width;i++){
			sums[i] = 0;
		}
		cells = new byte[heigth][width];
		for(int i=0;i<heigth;i++)
			for(int j=0;j<width;j++)
				cells[i][j] = 0;
	}
	
	/*
	 * primeste ca parametru tipul piesei
	 * intoarce lista de actiuni valide
	 */
	public Vector<String> validActions(int type){
		Vector<String> validMoves = new Vector<String>();
		//piesele A,E,F se pot afla doar in 2 pozitii diferite in urma rotirilor
		//o rotire cu 180 grade a lui A este egala cu o rotire de 0 grade a lui A(idem pt 90 si 270)
		//piesele B,C,D pot ajunge in 4 pozitii diferite
		//configuratia caramizii G nu se modifica in urma rotirii
		//am ales acest artificiu pentru a mai reduce numarul de stari
		int k=2;
		if(type == 1 || type == 2 || type == 3){
			k = 4;
		}else if(type == 6){
			k = 1;
		}
		for(int rotate=0;rotate<k;rotate++){
			for(int left=0;left<width;left++){
				if(isValid(rotate,left,type)){
					validMoves.add((""+rotate)+left);
				}
			}
		}
	
		return validMoves;
	}
	
	public static Board boardCopy(Board board){
		Board newBoard = new Board(board.height,board.width);
		for(int i=0;i<board.height;i++)
			for(int j=0;j<board.width;j++)
				newBoard.cells[i][j] = board.cells[i][j];
		
		return newBoard;
	}
	
	public int scores(int completedLines){
		
		if(completedLines == 1){
			return 1;
		}
		
		if(completedLines == 2){
			return 3;
		}
		
		if(completedLines == 3){
			return 9;
		}
		
		if(completedLines == 4){
			return 27;
		}
		
		return 0;
	}
	
	public String toString(){
		String board="";
		
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				board+=cells[i][j];
			}
		}
		
		return board;
	}
	
	public boolean isValid(int rotation,int left,int type){
		Brick brick = new Brick(type).rotate(rotation);
		if(brick.width +left <= width){
			return true; 
		}
		
		return false;
	}

	
	public int putBrick(int rotation,int left,int type) {
		  
		  // rotate brick
		Brick brick = new Brick(type).rotate(rotation);
		  
		  // find the line to place the brick
		  int freeLine = height - 1;
		  for (int col = 0; col < brick.width; col++) {
		    int colHeight = heights[col + left];
		    int line  = brick.height - 1;
		    while(brick.cells[line][col] == 0) {
		      line--;
		      colHeight++;
		    }
		    if (colHeight < freeLine) {
		      freeLine = colHeight;
		    }
		  }
		  int startLine = freeLine - brick.height + 1;
		  if (startLine < 0) {
		    return -20;
		  }
		  
		  for (int ln = startLine, bln = 0; bln < brick.height; ++ln, ++bln) {
		    for (int col = left, bcol = 0; bcol < brick.width; ++col, ++bcol) {
		      if (brick.cells[bln][bcol] == 1) {
		        cells[ln][col] = 1;
		        sums[ln]++;
		        if ((ln-1) < heights[col]) {
		          heights[col] = ln-1;
		        }
		      }
		    }
		  }

		  int completedLines = 0;
		  for (int ln = startLine + brick.height - 1; ln >= startLine; --ln) {
		    if (sums[ln] == width) {
		      completedLines++;
		      for (int ln2 = ln; ln2 > 0; --ln2) {
		        cells[ln2] = cells[ln2-1];
		        sums[ln2] = sums[ln2-1];
		      }
		      for(int k=0;k<width;k++)
		    	  cells[0][k] = 0; 
		      sums[0] = 0;
		      ln++;
		    }
		  }

		  for (int col = 0; col < width; ++col) {
		    int h = -1;
		    while (h < (height-1) && cells[h+1][col] == 0) h++;
		    heights[col] = h;
		  }

		  return scores(completedLines);
		}
	
	public void printBoard(){
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				if(cells[i][j] == 1){
					System.out.print("X");
				}else{
					System.out.print("_");
				}
			}
			System.out.println();
		}
	}

}
