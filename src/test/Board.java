package test;


import java.util.ArrayList;

public class Board {

    private static final int size = 15;
    private BoardSquare[][] gameBoard;
    private static Board single_Board = null;


    //Gives a reference to the board - or creates it - Single Tone
    public static Board getBoard() {
        if (single_Board == null) {
            single_Board = new Board();
        }
        return single_Board;
    }

    //Builds the board
    public Board() {
        gameBoard = new BoardSquare[size][size];
        buildBoard();
    }

    //Assigning all the squares according to the given template.
    public void buildBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameBoard[i][j] = new BoardSquare();
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
    public Tile[][] getTiles() {
        Tile[][] theTiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (gameBoard[i][j].tile != null) {
                    theTiles[i][j] = gameBoard[i][j].getTile();
                } else {
                    theTiles[i][j] = null;
                }
            }
        }
        return theTiles;

    }

    //checks if the word assignment on the board is legal
    public boolean boardLegal(Word word) {
        int col = word.getCol();
        int row = word.getRow();
        int len = word.getTiles().length;
        if (word.getTiles().length < 2)
            return false;
        //If the center square is null - so it's the first turn -
        // we must check that the word pass in the Star square
        if (!isWordInBorders(word)) {
            return false;
        }
        if (gameBoard[7][7].getTile() == null) {
            return isWordOnStarSquare(word);
        }

        //not the first word on the boarder
        return (isLeaning(word) && donotReplaceLetter(word));
        /*if(!donotReplaceLetter(word)){//changes the word
            return false;
        }
        if(!isLeaning(word){
            return false;
        }
        return true;*/
    }

    private int getScore(Word word) {
        int sum=0;
        int mul=1;
        int col = word.getCol();
        int row = word.getRow();
        int len = word.getTiles().length;
        for(int i=0;i<len;i++){
            switch (gameBoard[row][col].color){
                case 1:
                    sum+=word.getTiles()[i].getScore()*2;
                    break;
                case 2:
                    sum+=word.getTiles()[i].getScore()*3;
                    break;
                case 3:
                    mul*=2;
                    sum+=word.getTiles()[i].getScore();
                    break;
                case 4:
                    mul*=3;
                    sum+=word.getTiles()[i].getScore();
                    break;
                case 5:
                    sum+=word.getTiles()[i].getScore();
                    if(gameBoard[7][7].getTile()==null){
                        mul*=2;
                    }
                    break;
                default:
                    sum+=word.getTiles()[i].getScore();
            }
            if(word.isVertical())
                row++;
            else
                col++;
        }
        return sum*mul;
    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    private ArrayList<Word> getWords(Word word) {
        ArrayList<Word> createdWords = new ArrayList<Word>();
        int col = word.getCol();
        int row = word.getRow();
        for (int i = 0; i < word.getTiles().length; i++) {
            if (word.getTiles()[i] != null) {
                if(word.isVertical()) {
                    if (col - 1 >= 0 && gameBoard[row][col - 1].getTile() != null) { //left
                        createdWords.add(leftWord(row, col));
                    } else {
                        if (col + 1 < size && gameBoard[row][col + 1].getTile() != null) { // right
                            createdWords.add(rightWord(row, col));
                        }
                    }
                }
                else {
                    if (row - 1 >= 0 && gameBoard[row - 1][col].getTile() != null) { //top
                        createdWords.add(topWord(row, col));
                    }
                    else {
                        if (row + 1 < size && gameBoard[row + 1][col].getTile() != null) { //bottom
                            createdWords.add(bottomWord(row, col));
                        }
                    }
                }

            }
            if(word.isVertical())
                row++;
            else{
                col++;
            }

        }
        createdWords.add(fullWord(word));
        return createdWords;
    }

    public int tryPlaceWord(Word word){
        ArrayList<Word> createdWords = new ArrayList<Word>();
        int sum=0;
        if(boardLegal(word)){
            createdWords = getWords(word);
            for(Word w:createdWords){
                if(!dictionaryLegal(w)){
                    return 0;
                }
                else{
                    sum+=getScore(w);
                }
            }
        }
        else{
            return 0;
        }
        for(int i=0;i<createdWords.size();i++){
            wordPlacing(createdWords.get(i));
        }
        return sum;
    }

    private void wordPlacing(Word word){
        int row = word.getRow();
        int col = word.getCol();
        int len = word.getTiles().length;
        for(int i=0;i<len;i++){
            if(gameBoard[row][col].getTile()==null){
                gameBoard[row][col].setTile(word.getTiles()[i]);
            }
            if(word.isVertical())
                row++;
            else{
                col++;
            }
        }
    }

    private Word leftWord(int row, int col) {
        Word toReturn;
        Tile[] wordTiles;
        int startCol;
        int len;

        while (col - 1 >= 0 && gameBoard[row][col - 1] != null)
            col--;
        startCol = col;
        while (col + 1 < size && gameBoard[row][startCol + 1] != null)
            col++;
        len = col - startCol + 1;
        wordTiles = new Tile[len];
        for (int i = 0; i < len; i++) {
            wordTiles[i] = gameBoard[row][startCol + i].getTile();
        }
        toReturn = new Word(wordTiles, row, startCol, false);
        return toReturn;
    }

    private Word rightWord(int row, int col) {
        Word toReturn;
        Tile[] wordTiles;
        int startCol = col;
        int len;
        while (col + 1 < size && gameBoard[row][col + 1] != null)
            col++;
        len = col - startCol + 1;
        wordTiles = new Tile[len];
        for (int i = 0; i < len; i++) {
            wordTiles[i] = gameBoard[row][col + i].getTile();
        }
        toReturn = new Word(wordTiles, row, startCol, false);
        return toReturn;
    }

    private Word topWord(int row, int col) {
        Word toReturn;
        Tile[] wordTiles;
        int startRow;
        int len;
        while (row - 1 >= 0 && gameBoard[row - 1][col].getTile() != null)
            row--;
        startRow = row;
        while (row + 1 < size && gameBoard[row + 1][col].getTile() != null)
            row++;
        len = row - startRow + 1;
        wordTiles = new Tile[len];
        for (int i = 0; i < len; i++) {
            wordTiles[i] = gameBoard[startRow + i][col].getTile();
        }
        toReturn = new Word(wordTiles, startRow, col, true);
        return toReturn;
    }

    private Word bottomWord(int row, int col) {
        Word toReturn;
        Tile[] wordTiles;
        int startRow = row;
        int len;
        while (row + 11 < size && gameBoard[row + 1][col] != null)
            row++;
        len = row - startRow + 1;
        wordTiles = new Tile[len];
        for (int i = 0; i < len; i++) {
            wordTiles[i] = gameBoard[startRow + i][col].getTile();
        }
        toReturn = new Word(wordTiles, startRow, col, true);
        return toReturn;
    }

    //Checks if the whole word is in the board
    private boolean isWordInBorders(Word word) {
        int col = word.getCol();
        int row = word.getRow();
        int len = word.getTiles().length;
        if (col < 0 || col > 14 || row < 0 || row > 14) {
            return false;
        }
        if (word.isVertical() && len > size - row) {
            return false;
        }
        if ((!word.isVertical()) && len > size - col) {
            return false;
        }
        return true;
    }

    private boolean isWordOnStarSquare(Word word) {
        int col = word.getCol();
        int row = word.getRow();
        int len = word.getTiles().length;

        if (word.isVertical()) {
            if (col != 7 || row > 7 || row + size - 1 < 7) {
                return false;
            }
        } else {
            if (row != 7 || col > 7 || col + size - 1 < 7) {
                return false;
            }
        }
        return true;
    }

    /*public boolean legalOverride(Word word){
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
    }*/
    //checks that the word doesnt "overrides" any letters
    private boolean donotReplaceLetter(Word word) {
        if (word.isVertical()) {
            for (int i = 0; i < word.getTiles().length; i++) {
                if (word.getTiles()[i] != null && gameBoard[word.getRow() + i][word.getCol()].getTile() != null)
                    return false;
                if (word.getTiles()[i] == null && gameBoard[word.getRow() + i][word.getCol()].getTile() == null)
                    return false;
            }
        } else {
            for (int i = 0; i < word.getTiles().length; i++) {
                if (word.getTiles()[i] != null && gameBoard[word.getRow()][word.getCol() + i].getTile() != null)
                    return false;
                if (word.getTiles()[i] == null && gameBoard[word.getRow()][word.getCol() + i].getTile() == null)
                    return false;
            }
        }
        return true;
    }

    private boolean isLeaning(Word word) {
        int row = word.getRow();
        int col = word.getCol();
        for (int i = 0; i < word.getTiles().length; i++) {
            if (row - 1 >= 0 && gameBoard[row - 1][col].getTile() != null)
                return true;
            if (row + 1 >= 0 && gameBoard[row + 1][col].getTile() != null)
                return true;
            if (col - 1 >= 0 && gameBoard[row][col - 1].getTile() != null)
                return true;
            if (col + 1 >= 0 && gameBoard[row][col + 1].getTile() != null)
                return true;

            if (word.isVertical()) {
                row += 1;
            } else {
                col += 1;
            }
        }
        return false;
    }

    private Word fullWord(Word word) {
        int len = word.getTiles().length;
        int col = word.getCol();
        int row = word.getRow();
        Word fullWord;
        Tile[] fullWordTiles = new Tile[len];
        for (int i = 0; i < len; i++) {
            if (word.getTiles()[i] != null) {
                fullWordTiles[i] = word.getTiles()[i];
            }
            else {
                if (word.isVertical()) {
                    fullWordTiles[i] = gameBoard[row + i][col].getTile();
                } else {
                    fullWordTiles[i] = gameBoard[row][col + i].getTile();
                }

            }
        }
        fullWord = new Word(fullWordTiles,row,col,word.isVertical());
        return fullWord;
    }

    //This class represents every square in the board - it has a Tile and a color
    public static class BoardSquare {
        private int color;
        private Tile tile;

        public BoardSquare() {
            color = 0;
            tile = null;
        }

        // Green = 0
        // Cyan - 1
        //Blue - 2
        //Yellow - 3
        //Red - 4
        //Star - 5
        public void setColor(int color) {
            if (color >= 1 && color <= 5)
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
