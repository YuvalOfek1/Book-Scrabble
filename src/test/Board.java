package test;


public class Board {

    private static final int size = 15;
    private boardSquare[][] gameBoard;
    private static Board single_Board = null;

    //Builds the board
    public Board(){
        gameBoard = new boardSquare[size][size];
        buildBoard();
    }

    //Gives a reference to the board - or creates it
    public static Board getBoard(){
        if(single_Board==null){
            single_Board = new Board();
        }
        return single_Board;
    }

    //Assigning all the squares according to the given template.
    public void buildBoard(){
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = new boardSquare();
            }
        }


        //Setting Start square - Star
        gameBoard[7][7].setColor(5);

        //Setting Red squares - color 4 - Triple Word Score
        gameBoard[0][0].setColor(4);
        gameBoard[0][7].setColor(4);
        gameBoard[0][14].setColor(4);
        gameBoard[7][0].setColor(4);
        gameBoard[7][14].setColor(4);
        gameBoard[14][0].setColor(4);
        gameBoard[14][7].setColor(4);
        gameBoard[14][14].setColor(4);

        //Setting Yellow squares - color 3 - Double Word Score
        gameBoard[1][1].setColor(3);
        gameBoard[2][2].setColor(3);
        gameBoard[3][3].setColor(3);
        gameBoard[4][4].setColor(3);
        gameBoard[1][13].setColor(3);
        gameBoard[2][12].setColor(3);
        gameBoard[3][11].setColor(3);
        gameBoard[4][10].setColor(3);
        gameBoard[13][13].setColor(3);
        gameBoard[12][12].setColor(3);
        gameBoard[11][11].setColor(3);
        gameBoard[10][10].setColor(3);
        gameBoard[10][4].setColor(3);
        gameBoard[11][3].setColor(3);
        gameBoard[12][2].setColor(3);
        gameBoard[13][1].setColor(3);

        //Setting Blue squares - color 2 - Triple Letter Score
        gameBoard[1][5].setColor(2);
        gameBoard[1][9].setColor(2);
        gameBoard[5][1].setColor(2);
        gameBoard[5][5].setColor(2);
        gameBoard[5][9].setColor(2);
        gameBoard[5][13].setColor(2);
        gameBoard[9][1].setColor(2);
        gameBoard[9][5].setColor(2);
        gameBoard[9][9].setColor(2);
        gameBoard[9][13].setColor(2);
        gameBoard[13][5].setColor(2);
        gameBoard[13][9].setColor(2);

        //Setting Cyan squares - color 1 - Double Letter Score
        gameBoard[0][3].setColor(1);
        gameBoard[0][11].setColor(1);
        gameBoard[2][6].setColor(1);
        gameBoard[2][8].setColor(1);
        gameBoard[3][0].setColor(1);
        gameBoard[3][7].setColor(1);
        gameBoard[3][14].setColor(1);
        gameBoard[6][2].setColor(1);
        gameBoard[6][6].setColor(1);
        gameBoard[6][8].setColor(1);
        gameBoard[6][12].setColor(1);
        gameBoard[7][3].setColor(1);
        gameBoard[7][11].setColor(1);
        gameBoard[8][2].setColor(1);
        gameBoard[8][6].setColor(1);
        gameBoard[8][8].setColor(1);
        gameBoard[8][12].setColor(1);
        gameBoard[11][0].setColor(1);
        gameBoard[11][7].setColor(1);
        gameBoard[11][14].setColor(1);
        gameBoard[12][6].setColor(1);
        gameBoard[12][8].setColor(1);
        gameBoard[14][3].setColor(1);
        gameBoard[14][11].setColor(1);

    }

    //Creates a 2-D array of tiles and copy the status of the tiles in the board;
    public Tile[][] getTiles(){
        Tile[][] theTiles = new Tile[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(gameBoard[i][j].tile!=null){
                    theTiles[i][j] = gameBoard[i][j].getTile();
                }
                else{
                    theTiles[i][j] = null;
                }
            }
        }
        return theTiles;

    }

    public boolean boardLegal(Word word){
        int col = word.getCol();
        int row = word.getRow();
        int len = word.getTiles().length;
        if(word.getTiles().length<2)
            return false;
        //If the center square is null - so it's the first turn -
        // we must check that the word pass in the Star square
        if(!isWordInBorders(word)){
            return false;
        }
        if(gameBoard[7][7].getTile()==null && !isWordOnStarSquare(word)){
            return false;
        }


        //check if the word is "leaning" on another tile.
        
    }
    //Checks if the whole word is in the board
    public boolean isWordInBorders(Word word){
        int col = word.getCol();
        int row = word.getRow();
        int len = word.getTiles().length;
        if(col<0 || col > 14 || row<0 || row>14){
            return false;
        }
        if(word.isVertical()&&len>size-row){
            return false;
        }
        if((!word.isVertical())&&len>size-col){
            return false;
        }
        return true;
    }
    public boolean isWordOnStarSquare(Word word){
        int col = word.getCol();
        int row = word.getRow();
        int len = word.getTiles().length;

        if(word.isVertical()){
            if(col!=7 || row > 7 || row+size-1 <7) {
                return false;
            }
        }
        else{
            if(row != 7 || col > 7 || col + size - 1 < 7){
                return false;
            }
        }
        return true;
    }
    public boolean legalOverride(Word word){
        int col = word.getCol();
        int row = word.getRow();
        int len = word.getTiles().length;


        boolean flag = false;
        if(word.isVertical()) {
            for (int i = 0; i < len; i++) {
                if(gameBoard[i+row][col].tile!=null){
                    flag = true;
                    if(gameBoard[i+row][col].tile.letter!=word.getTiles()[i].letter) {
                        return false;
                    }
                }
            }
        }
        else {
            for (int i = 0; i < len; i++) {
                if(gameBoard[row][i+col].tile!=null){
                    flag = true;
                    if(gameBoard[row][i+col].tile.letter!=word.getTiles()[i].letter) {
                        return false;
                    }
                }
            }
        }
        if(flag){
            return true;
        }
        //Now we know that the whole word has placed on null squares, we have to check if it leans on other tile(s)
    }

    //This class represents every square in the board - it has a Tile and a color
    public static class boardSquare{
        private int color;
        private Tile tile;
        public boardSquare(){
            color = 0;
            tile=null;
        }
        // Cyan - 1
        //Blue - 2
        //Yellow - 3
        //Red - 4
        //Star - 5
        public void setColor(int color){
            if(color>=1 && color<=5)
                this.color = color;
        }
        public void setTile(Tile tile) {
            this.tile = tile;
        }
        public int getColor() {
            return color;
        }
        public Tile getTile() {
            return tile;
        }
    }

}
