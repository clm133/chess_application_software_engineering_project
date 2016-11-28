import java.util.*;
public class Pawn extends Piece
{
	/* CONSTANTS */
	public static final boolean BLACK = true;
	public static final boolean WHITE = false;
	public static final boolean USER = true;
	public static final boolean COMP = false;
	
	/* VARIABLES */
	
	/*CONSTRUCTORS*/

	public Pawn (Player owner, boolean gameColor, Square position)
	{
		super(owner, "Pawn", gameColor, position);
	}
	
	public Pawn (boolean gameColor, Square position)
	{
		super("Pawn", gameColor, position);
	}
	
	public boolean movePiece(Square destination)
	{
		return true;
	}
	
	public boolean move(Board board, Square dest) throws MoveException
	{
		Square src;
		int sf, sr, df, dr, rise, run;
		MoveTemplate mt;
		MoveTemplate.MovePattern pattern;
		
		src = this.getPosition(); //source square
		sf = src.getFile(); //source file
		sr = src.getRank(); //source rank
		df = dest.getFile(); //destination file
		dr = dest.getRank(); //destination rank
		
		//first, briefly check that pawn is not moving backward
		if(this.getColor() == BLACK){
			if(dr > sr){
				throw new MoveException("Illegal pawn Move");
			}
		}
		else{
			if(dr < sr){
				throw new MoveException("Illegal pawn Move");
			}
		}
		
		//then get rise-over-run to determine if move is diagonal or vertical (if horizontal, we will throw an exception)
		rise = dr-sr;
		run = df-sf;
		
		//if move is horizontal...
		if(run != 0 && rise == 0){
			throw new MoveException("Pawn's can't move horizontally");
		}
		
		//if move is diagonal
		else if(run != 0 && rise != 0){
			pattern = MoveTemplate.MovePattern.DIAGONAL;
		}
		
		//if move is orthogonally vertical
		else{
			pattern = MoveTemplate.MovePattern.ORTHOGONAL;
		}
		
		//now we will try to make the movetemplate
		try{
			mt = new MoveTemplate(pattern, src, dest);
			//next we will make sure movelen is no more than two
			if(mt.getMoveLen() > 2){
				throw new MoveException("Pawn cannot move that many squares.");
			}
			//if the pawn moved two squares, it must not have moved previously
			else if(mt.getMoveLen() == 2 && pattern != MoveTemplate.MovePattern.DIAGONAL){
				if(this.hasMoved){
					throw new MoveException("Pawn cannot move that many squares.");
				}
			}
			//the pawn cannot move more than one square diagonally
			else if(mt.getMoveLen() == 2 && pattern == MoveTemplate.MovePattern.DIAGONAL){
				throw new MoveException("Pawn cannot move that many squares.");
			}
			//if moving diagonnally, pawn must have a legal capture target
			else if(pattern == MoveTemplate.MovePattern.DIAGONAL && dest.isOccupied() == false){
				throw new MoveException("Pawn cannot move diagonally without capture target.");
			}
			//finally, we need to check if pawn's path is obstructed and if it is capturing something
			if(pathObstructed(board, mt)){
				throw new MoveException("Pawn's movement is obstructed.");
			}
			//pawns require an additional check to make sure they can't capture or move into anything directly in front of them
			else if(pattern == MoveTemplate.MovePattern.ORTHOGONAL && dest.isOccupied()){
				throw new MoveException("Pawn's movement is obstructed.");
			}
			else{
				return dest.isOccupied();
			}
		}
		//if the movetemplate constructor has issues, move is illegal
		catch(MoveTemplateException e){
			throw new MoveException("Illegal pawn Move");
		}
	}
}
