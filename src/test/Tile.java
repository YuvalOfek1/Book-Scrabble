package test;
import java.util.Random;
public class Tile {
    public final char letter;
    public final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Tile tile = (Tile) object;
        return letter == tile.letter && score == tile.score;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), letter, score);
    }

    public static class Bag {
        public int[] letters;
        public int[] originalAmountOfLetters;
        public int[] scores;
        public Tile[] tiles;
        public int totalTiles = 98;

        private static Bag bag = null;

        private Bag() {
            this.letters = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
            this.originalAmountOfLetters = new int[]{1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
            this.scores = new int[] {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            this.tiles = new Tile[26];
            int i=0;
            for(char c = 'A'; c<='Z'; c++, i++) {
                this.tiles[i] = new Tile(c, this.scores[i]);
            }
        }

        public Tile getRand(){
            if(this.totalTiles==0){
                return null;
            }
            Random random = new Random();
            while(true) {
                int x = random.nextInt(25);
                if(this.letters[x]>0) {
                    totalTiles--;
                    return this.tiles[x];
                }
            }
        }
        public Tile getTile(char c) {
            if (this.totalTiles != 0){
                return null;
            }
            int indexchar = c-'A';
            if(this.letters[indexchar]==0)
                return null;
            totalTiles--;
            return this.tiles[indexchar];
        }
        public void put(Tile t){
            if(this.originalAmountOfLetters[t.letter-'A']<this.originalAmountOfLetters[t.letter-'A']){
                this.letters[t.letter-'A']++;
                this.totalTiles++;
            }
        }
        public int size(){
            return this.totalTiles;
        }
        public int[] getQuantities(){
            return this.letters;
        }

        public static Bag getBag(){
            if(this.bag == null){
                bag = new Bag();
            }
            return bag;
        }


    }
}






