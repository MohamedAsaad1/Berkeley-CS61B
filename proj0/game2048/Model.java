package game2048;

import java.util.Formatter;
import java.util.Map;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {

    /** Current contents of the board. */
    private final Board _board;
    /** Current score. */
    private int _score;
    /** Maximum score so far.  Updated when game ends. */
    private int _maxScore;
    /** True iff game is ended. */
    private boolean _gameOver;


    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        _board = new Board(size);
        _score = _maxScore = 0;
        _gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        _board = new Board(rawValues);
        this._score = score;
        this._maxScore = maxScore;
        this._gameOver = gameOver;
    }

    /** Same as above, but gameOver is false. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore) {
        this(rawValues, score, maxScore, false);
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     * */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return _board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /** Return the current score. */
    public int score() {
        return _score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return _maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /** Allow initial game board to announce a hot start to the GUI. */
    public void hotStartAnnounce() {
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }
    /** Tilt the board toward SIDE.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void tilt(Side side) {
        _board.setViewingPerspective(side);
        for (int c = 0; c < _board.size(); c ++) {
            int temp = 0;
            /**this for check if tile from down to up null or not if null move this tile like this
            * |1 |2 |         1 2
            * |null |4 |   => 5  4
            * |5 |6 |         null 6(for explain this will be in real programs random number )
            */
            for (int r = _board.size() - 1; r >= 0; r--) {
                Tile tile = _board.tile(c, r);
                temp = check(tile,c,r);
                if (temp!=0) {
                    _board.move(c, temp, tile);
                }
            }
            //this for merage
            for (int r = _board.size() - 2; r >= 0; r --) {
                Tile tile = _board.tile(c, r);
//               int temp_r = check(tile,c,r);
                if (tile!=null) {
                    int rd = r;
                    boolean check_merge = false;
//                    if(temp_r!=0){
//                        check_merge = true;
//                        rd = temp_r;
//                    }
                    while (rd < _board.size() - 1 && _board.tile(c, rd + 1) == null) {
                        rd ++;
                        check_merge = true;
                    }
                        while (rd < _board.size() - 1 && _board.tile(c, rd + 1).value() == tile.value() && check_merge == false) {
                            rd++;
                            _score += 2 * tile.value();
                        }
                        _board.move(c, rd, tile);
                }
            }
        }
        _board.setViewingPerspective(Side.NORTH);
        checkGameOver();

    }


    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     */

    public static boolean emptySpaceExists(Board b) {
        boolean indicator = false;
    // this is one approach take (n*2)
        for(int i = 0;i < 4;i++){
            for (int j = 0;j < 4;j++){

                if (b.tile(j,i)== null){
                    indicator = true;
                    break;
                }
            }
        }


    return indicator;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int MAX_PIECE = 2048;
        int  current_price = 0;
        for(int i = 0;i < 4;i++){
            for (int j = 0;j < 4;j++){

                if (b.tile(j,i)== null){
                    continue;
                }
                else{
                   int  valueOfTiles = b.tile(j,i).value();
                    if ( valueOfTiles > current_price){
                        current_price = valueOfTiles;
                    }
                }
            }
        }
    return MAX_PIECE == current_price;

    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        boolean emptyspaceexists = emptySpaceExists(b);
        boolean max_exist = maxTileExists(b);
        boolean indicator = false;
        for(int i =0 ;i < 4;i++) {
            if (indicator){break;}
            for (int j = 1; j < 4; j++) {
                Tile current = b.tile(j, i);
                if ( max_exist || emptyspaceexists){indicator=true; break;}

                else{
                    if (current.value() == b.tile(j-1, i).value()) {
                            indicator = true;
                            break;
                    }
                    if (i < 3) {
                        if (current.value() == b.tile(j, i+1).value()) {
                            indicator= true;
                            break;
                        }
                    }

                    }
            }
        }
    return indicator;
    }

    /** Returns the model as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

private  int check(Tile tile , int c , int r){
    if (tile != null) {
        while (r < _board.size() - 1 && _board.tile(c, r + 1) == null) {
            r++;
        }

        return r;
    }
    return 0;

}
    /** Returns whether two models are equal. */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /** Returns hash code of Model’s string. */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

// draft
//	         0,3 | 1,3 | 2,3 | 3,3
//           0,2 | 1,2 | 2,2 | 3,2
//           0,1 | 1,1 | 2,1 | 3,1
//           0,0 | 1,0 | 2,0 | 3,0
//        final  int size = _board.size();
//        _board.setViewingPerspective(side);
//        for(int c =0;c < size;c++){

//            for(int r = size-1; r >= 0;r--){
//                Tile tile = _board.tile(c,r);
//                if (tile != null){
//                    int temp_r = r;
//                    //to access the empty site
//
//                    while((temp_r < size - 1) && (_board.tile(c,r+1) == null)){
//                        temp_r++;
//                    }
//                    _board.move(c,temp_r,tile);
//                }
//            }
//            //this for merage
//            //{2,2,2,x} {4,2,x,x}
//            for(int merage_r = size - 2; merage_r >= 0; merage_r--) {
//                boolean check_merage = false;
//                Tile tile = _board.tile(c, merage_r);
//
//                Tile top_tile;
//                if (tile != null) {
//                    int r_m = merage_r;
//                    while (r_m <_board.size()-1 && _board.tile(c,r_m+1) == null){
//                        r_m++;
//                        check_merage = true;
//                    }
//                    while (r_m < size - 1 && tile.value() == _board.tile(c, r_m).value() && check_merage == false) {
//                        r_m++;
//                        _score += 2*tile.value();
//                    }
//
//                    _board.move(c, r_m, tile);
//                }
//            }
//        }
//        _board.setViewingPerspective(Side.NORTH);