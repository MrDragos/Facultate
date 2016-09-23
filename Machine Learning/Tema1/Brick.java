import java.util.Vector;


public class Brick {
	
	
	/*
	 * A 0
	 * B 1
	 * C 2
	 * D 3
	 * E 4
	 * F 5
	 * G 6
	 */
	
	public int height;
	public int width;
	public byte[][] cells;
	
	public Brick(int heigth,int width,byte[][] cells){
		this.height = heigth;
		this.width = width;
		this.cells = cells;
	}
	
	public Brick(int type) {
		if(type == 0){
			this.height = 1;
			this.width = 4;
			byte[][] b = {{1,1,1,1}};
			this.cells = b;
		}
		
		if(type == 1){
			this.height = 2;
			this.width = 3;
			byte[][] b = {{1, 0, 0}, {1, 1, 1}};
			this.cells = b;
		}
		
		if(type == 2){
			this.height = 2;
			this.width = 3;
			byte[][] b = {{0, 0, 1}, {1, 1, 1}};
			this.cells = b;
		}
		
		if(type == 3){
			this.height = 2;
			this.width = 3;
			byte[][] b = {{0, 1, 0}, {1, 1, 1}};
			this.cells = b;
		}
		
		if(type == 4){
			this.height = 2;
			this.width = 3;
			byte[][] b = {{0, 1, 1}, {1, 1, 0}};
			this.cells = b;
		}
		
		if(type == 5){
			this.height = 2;
			this.width = 3;
			byte[][] b =  {{1, 1, 0}, {0, 1, 1}};
			this.cells = b;
		}
		
		if(type == 6){
			this.height = 2;
			this.width = 2;
			byte[][] b = {{1, 1}, {1, 1}};
			this.cells = b;
		}
	}
	
	/*
	 * 1 = 90 degrees
	 * 2 = 180 degrees
	 * 3 = 270 degrees
	 */
	
	public int f(boolean left,int i,int len){
		return (left ? i : (len - i - 1));
	}
	
	public Brick rotate(int n){
		n = n & 3;
		
		int newHeight = ((n % 2) == 0) ? height : width;
		int newWidth = ((n % 2) == 0) ? width : height;
		
		byte[][] rotatedCells = new byte[newHeight][newWidth];
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int newRow = f(n < 2, (n & 1) == 0 ? row : col, newHeight);
			    int newCol = f((n % 2) == (n / 2), (n % 2) == 0 ? col : row, newWidth);
			    rotatedCells[newRow][newCol] = cells[row][col];
			}
		}		
		
		return new Brick(newHeight, newWidth,rotatedCells);
	}
/*
 * pentru debug
 */
	public void printBrick(){
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				if(cells[i][j] == 1){
					System.out.print("X");
				}else{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	

}

