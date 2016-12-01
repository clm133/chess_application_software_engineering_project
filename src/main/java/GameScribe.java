//I see this class as handling the PGN and FEN translation, perhaps even the save and load file reading and writing

public class GameScribe
{
	Game game;
	public final boolean BLACK = true;
	public final boolean WHITE = false;
	public final boolean USER = true;
	public final boolean COMP = false;
	
	public GameScribe(Game g)
	{
		game = g;
	}
	
	/* this function helps generateFEN by returning a generated string for field1 of a FEN record */
	private String generateFENF1()
	{
		int file;
		int rank;
		int counter;
		Square src;
		Piece p;
		String str;
		
		//FEN starts at Rank 8, File A (a8)
		str = "";
		for(rank = 7; rank >= 0; rank--){
			counter = 0;//counter for empty spaces 
			for(file = 0; file < 8; file++){
				src = game.getSquareAt(file, rank);
				
				//if the square is occupied
				if(src.isOccupied()){
					if(counter > 0){
						str = str + String.valueOf(counter);
						counter = 0;
					}
					p = src.getPiece();
					switch(p.getPieceType()){
						case PAWN:
							if(p.getColor() == WHITE){
								str = str + "P";
							}
							else{
								str = str + "p";
							}
							break;
						case ROOK:
							if(p.getColor() == WHITE){
								str = str + "R";
							}
							else{
								str = str + "r";
							}
							break;
						case KNIGHT:
							if(p.getColor() == WHITE){
								str = str + "N";
							}
							else{
								str = str + "n";
							}
							break;
						case BISHOP:
							if(p.getColor() == WHITE){
								str = str + "B";
							}
							else{
								str = str + "b";
							}
							break;
						case QUEEN:
							if(p.getColor() == WHITE){
								str = str + "Q";
							}
							else{
								str = str + "q";
							}
							break;
						case KING:
							if(p.getColor() == WHITE){
								str = str + "K";
							}
							else{
								str = str + "k";
							}
							break;
					}
				}
				//if square is unoccupied
				else{
					counter++;//increment counter
				}
				if(file == 7 && counter > 0){
					str = str + String.valueOf(counter);
				}
			}
			
			if(rank != 0){
				str = str + "/"; //the slash denotes a new rank
			}
		}
		
		return str;
	}
	
	/* this function helps generateFEN by returning a generated string for field2 of a FEN record */
	private String generateFENF2()
	{
		if(game.getActiveColor() == WHITE){
			return "w";
		}
		else{
			return "b";
		}
	}
	
	/* this function helps generateFEN by returning a generated string for field3 of a FEN record */
	private String generateFENF3()
	{
		String str;
		
		str = game.fenCastleString();
		if(str.equals("")){
			str = "-";
		}
		return str;
	}
	
	/* this function helps generateFEN by returning a generated string for field4 of a FEN record */
	private String generateFENF4()
	{
		String str;
		Square enPassant;
		
		str = "";
		if(game.isEnPassant()){
			enPassant = game.getEnPassant();
			str = str + enPassant.toString();
		}
		else{
			str = str + "-";
		}
		return str;
	}
	
	/* this function helps generateFEN by returning a generated string for field5 of a FEN record */
	private String generateFENF5()
	{
		String str = String.valueOf(game.getHalfmoveClock());
		return str;
	}
	
	/* this function helps generateFEN by returning a generated string for field6 of a FEN record */
	private String generateFENF6()
	{
		int turn;
		String str;
		
		turn = game.getFullmoveClock();
		str = String.valueOf(turn);
		return str;
	}
	
	
	public String generateFEN()
	{
		/*A FEN record contains six fields sperated by a space */
		String FENrecord;
		
		/*field one is piece placement, which lists pieces starting with rank 8 and moving left to right*/
		String piecePlacement = generateFENF1() + " ";
		
		/*field two is the active color, 'w' means white moves NEXT, 'b' means black moves NEXT */
		String activeColor = generateFENF2() + " ";
		
		/*field three is castling availablility.*/
		String castlingAvailibility =  generateFENF3()+ " ";
		
		/*en passant target square in algebraic notation*/
		String enPassant = generateFENF4() + " ";
		
		/* halfmove clock - number of half moves since the last capture or pawn advance */
		String halfClock = generateFENF5() + " ";
		
		/* fullmove clock*/
		String fullClock = generateFENF6();
		
		FENrecord = piecePlacement + activeColor + castlingAvailibility + enPassant + halfClock + fullClock;
		return FENrecord;
	}
}