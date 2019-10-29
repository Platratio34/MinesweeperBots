package minesweeper;

import java.util.Scanner;

public class Game {	
	private PlaySpace[][] playBoard;
	private boolean dead;
	private Scanner scan;
	private int flaggedBoombs;
	private int boombs;
	
	public Game() {
		scan = new Scanner(System.in);
		boolean go = true;
		while(go) {
			dead = false;
			flaggedBoombs = 0;
			newBoard(10,10,15, false);
			while(dead==false) {
				System.out.println("Input");
				String in=scan.next();
				input(in,false);
			}
			scan.next();
			System.out.println("Repeat? (y/n)");
			String input2 = scan.next();
			if(input2.charAt(0)=='y') {
				go=true;
			} else {
				go=false;
			}
		}
	}
	public Game(int h, int w, int b, boolean bot) {
		scan = new Scanner(System.in);
		boombs = b;
		boolean go = true;
		String in = "";
		char[][] out = null;
		while(go) {
			dead = false;
			flaggedBoombs = 0;
			newBoard(w,h,b,bot);
			while(dead==false) {
				if(!bot) {
					System.out.println("Input");
					in=scan.next();
				} else {
					in=Bot.onTurn(out);
				}
				out = input(in,bot);
			}
			scan.next();
			System.out.println("Repeat? (y/n)");
			String input2 = scan.next();
			if(input2.charAt(0)=='y') {
				go=true;
			} else {
				go=false;
			}
		}
	}
	
	public char[][] input(String in, Boolean bot) {
		char[][] out = null;
		if(!dead) {
			String[] input = PeterLogic.VParse(in, ',');
			try {
				char in1 = input[0].charAt(0);
				int X = Integer.parseInt(input[2]);
				int Y = Integer.parseInt(input[1]);
				if(in1=='R' || in1=='r') {
					click(X, Y, PlaySpaceState.REVALED);
				}
				if(in1=='F' || in1=='f') {
					click(X, Y, PlaySpaceState.FLAGGED);
				}
				if(!bot) {
					displayBoard();
				} else {
					out = packageBoard();
				}
				//peterLibrary.Arrays.printArray(pakageBoard());
				checkWin();
			} catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Error: Improper Input: Out of bounds");
			} catch(NumberFormatException e) {
				System.out.println("Error: Improper Input: Format");
			}
		}
		return out;
	}
	
	public char[][] newBoard(int width, int height, int boombs, boolean bot) {
		playBoard = new PlaySpace[width][];
		for(int i=0;i<width;i++) {
			playBoard[i] = new PlaySpace[height];
			for(int j=0;j<height;j++) {
				playBoard[i][j] = new PlaySpace();
				playBoard[i][j].setNumber(0);
			}
		}
		
		for(int i=0;i<boombs;i++) {
			int tx = (int) (Math.random() * (width-1));
			int ty = (int) (Math.random() * (height-1));
			if(playBoard[tx][ty].isBoomb()) {
				i--;
			} else {
				playBoard[tx][ty].setBoomb(true);
				playBoard[tx][ty].setNumber(-10);
				
				if(inBounds(tx-1,ty)) {playBoard[tx-1][ty].setNumber(playBoard[tx-1][ty].getNumber()+1);}
				if(inBounds(tx-1,ty+1)) {playBoard[tx-1][ty+1].setNumber(playBoard[tx-1][ty+1].getNumber()+1);}
				if(inBounds(tx-1,ty-1)) {playBoard[tx-1][ty-1].setNumber(playBoard[tx-1][ty-1].getNumber()+1);}
				
				if(inBounds(tx+1,ty)) {playBoard[tx+1][ty].setNumber(playBoard[tx+1][ty].getNumber()+1);}
				if(inBounds(tx+1,ty+1)) {playBoard[tx+1][ty+1].setNumber(playBoard[tx+1][ty+1].getNumber()+1);}
				if(inBounds(tx+1,ty-1)) {playBoard[tx+1][ty-1].setNumber(playBoard[tx+1][ty-1].getNumber()+1);}
				
				if(inBounds(tx,ty+1)) {playBoard[tx][ty+1].setNumber(playBoard[tx][ty+1].getNumber()+1);}
				if(inBounds(tx,ty-1)) {playBoard[tx][ty-1].setNumber(playBoard[tx][ty-1].getNumber()+1);}
			}
		}
		if(!bot) {
			displayBoard();
			return null;
		} else {
			return packageBoard();
		}
	}
	
	public String displayBoard() {
		String outputStream = "";
		int w = playBoard.length;
		int h = playBoard[1].length;
		outputStream+=h+1+"\n";
		outputStream+=flaggedBoombs + "/" + boombs + "\n";
		System.out.println(flaggedBoombs + "/" + boombs);
		for(int x=-2;x<w;x++) {
			if(x>=0) {
				if(x<10) {
					System.out.print("0"+x+" |");
				} else {
					System.out.print(x+" |");
				}
			} else {
				System.out.print("   ");
			}
			for(int y=0;y<h;y++) {
				if(x==-1) {
					System.out.print("--");
				} else if(x==-2) {
					System.out.print(" "+y);
				} else if(x>=0) {
					System.out.print(" ");
					System.out.print(test(x,y));
					outputStream+=test(x,y);
				}
			}
			System.out.print("\n");
			if(x>=0) {
				outputStream+="\n";
			}
		}
		System.out.print("\n");
		//System.out.println(outputStream);
		return outputStream;
	}
	
	public char[][] packageBoard() {
		int w = playBoard.length;
		int h = playBoard[1].length;
		char[][] output = new char[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				output[x][y]=test(x,y);
			}
		}
		return output;
	}
	
	public char test(int x, int y) {
		PlaySpace state = playBoard[x][y];
		char out = '~';
		if(state.getState()==PlaySpaceState.FLAGGED) {
			out = '+';
		} else if(state.getState()==PlaySpaceState.HIDDEN) {
			out = '=';
		} else if(state.getState()==PlaySpaceState.REVALED) {
			String out2 = ""+playBoard[x][y].getNumber();
			if(playBoard[x][y].getNumber()==0) {
				out = '.';
			} else {
				out = out2.charAt(0);
			}
		}
		if(playBoard[x][y].isBoomb() && dead && !playBoard[x][y].isFlagged()) {
			out = '#';
		}
		return out;
	}
	
	public void click(int x, int y, PlaySpaceState request) {
		//PlaySpaceState state = playBoard[x][y].getState();
		if(request==PlaySpaceState.HIDDEN) {
			System.out.println("You Can't Do That");
		} else if(request==PlaySpaceState.REVALED) {
			revealSpace(x,y);
			if(playBoard[x][y].getState()==PlaySpaceState.REVALED) {
				int boombsA = 0;
				if(inBounds(x-1,y+1)) {if(playBoard[x-1][y+1].isFlagged()) {boombsA++;}}
				if(inBounds(x,y+1)) {if(playBoard[x][y+1].isFlagged()) {boombsA++;}}
				if(inBounds(x+1,y+1)) {if(playBoard[x+1][y+1].isFlagged()) {boombsA++;}}
				if(inBounds(x-1,y)) {if(playBoard[x-1][y].isFlagged()) {boombsA++;}}
				if(inBounds(x+1,y)) {if(playBoard[x+1][y].isFlagged()) {boombsA++;}}
				if(inBounds(x-1,y-1)) {if(playBoard[x-1][y-1].isFlagged()) {boombsA++;}}
				if(inBounds(x,y-1)) {if(playBoard[x][y-1].isFlagged()) {boombsA++;}}
				if(inBounds(x+1,y-1)) {if(playBoard[x+1][y-1].isFlagged()) {boombsA++;}}
				if(boombsA==playBoard[x][y].getNumber()) {
					if(inBounds(x-1,y+1)) {revealSpace(x-1,y+1);}
					if(inBounds(x,y+1)) {revealSpace(x,y+1);}
					if(inBounds(x+1,y+1)) {revealSpace(x+1,y+1);}
					if(inBounds(x-1,y)) {revealSpace(x-1,y);}
					if(inBounds(x+1,y)) {revealSpace(x+1,y);}
					if(inBounds(x-1,y-1)) {revealSpace(x-1,y-1);}
					if(inBounds(x,y-1)) {revealSpace(x,y-1);}
					if(inBounds(x+1,y-1)) {revealSpace(x+1,y-1);}
				}
			}
		} else if(playBoard[x][y].getState()==PlaySpaceState.REVALED) {
			System.out.println("You Can't Do That");
		} else if(playBoard[x][y].getState()==PlaySpaceState.FLAGGED) {
			playBoard[x][y].setState(PlaySpaceState.HIDDEN);
			flaggedBoombs--;
		} else {
			playBoard[x][y].setState(PlaySpaceState.FLAGGED);
			flaggedBoombs++;
		}
	}
	
	public void revealSpace(int x, int y) {
		if(playBoard[x][y].isFlagged()) {
			
		} else if(playBoard[x][y].isBoomb()) {
			dead = true;
			System.out.println("BOOMB!");
		} else if(playBoard[x][y].getState()==PlaySpaceState.HIDDEN) {
			playBoard[x][y].setState(PlaySpaceState.REVALED);
			if(playBoard[x][y].getNumber()==0) {
				if(inBounds(x-1,y+1)) {revealSpace(x-1,y+1);}
				if(inBounds(x,y+1)) {revealSpace(x,y+1);}
				if(inBounds(x+1,y+1)) {revealSpace(x+1,y+1);}
				if(inBounds(x-1,y)) {revealSpace(x-1,y);}
				if(inBounds(x+1,y)) {revealSpace(x+1,y);}
				if(inBounds(x-1,y-1)) {revealSpace(x-1,y-1);}
				if(inBounds(x,y-1)) {revealSpace(x,y-1);}
				if(inBounds(x+1,y-1)) {revealSpace(x+1,y-1);}
			}
		}
	} 
	
	public void checkWin() {
		int w = playBoard.length;
		int h = playBoard[1].length;
		boolean win = true;
		for(int i=0;i<w;i++) {
			for(int j=0;j<h;j++) {
				if(playBoard[i][j].isFlagged()) {
					if(playBoard[i][j].isBoomb()) {
						
					} else {
						win = false;
					}
				}
				if(playBoard[i][j].getState()==PlaySpaceState.HIDDEN && !playBoard[i][j].isBoomb()) {
					win = false;
				}
				
			}
		}
		if(win) {
			dead = true;
			System.out.println("You Win!");
		}
	}
	
	public boolean inBounds(int x, int y) {
		boolean outX = false;
		boolean outY = false;
		int w = playBoard.length;
		int h = playBoard[1].length;
		if(x>=0 && x<w) {
			outX = true;
		}
		if(y>=0 && y<h) {
			outY = true;
		}
		if(outX && outY) {
			return true;
		} else {
			return false;
		}
	}
}
