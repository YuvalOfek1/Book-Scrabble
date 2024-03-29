package SecondMileStone;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

import static java.util.Objects.hash;

public class BloomFilter {
	public BitSet bitSet;
    int size;
    public String[] hashFuncs;
    BigInteger bigInt;
    MessageDigest md;
    public BloomFilter(int bits, String... algs){
        bitSet = new BitSet(bits);
        size = bits;
        hashFuncs = new String[algs.length];
        System.arraycopy(algs, 0, hashFuncs, 0, algs.length);
    }

    public void add(String word) {
        // TODO Auto-generated method stub
        byte[] bytes = null;
        for(String s: hashFuncs){
            /*try {
                md = MessageDigest.getInstance(s);
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            byte[] bts = md.digest(word.getBytes());
            bigInt = new BigInteger(bts);
            int bigVal = bigInt.intValue();
            bitSet.set(Math.abs(bigVal)%256);*/
            try{
                md = MessageDigest.getInstance(s);
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            bytes = md.digest(word.getBytes());
            bigInt = new BigInteger(bytes);
            int bigVal = bigInt.intValue();
            bitSet.set(Math.abs(bigVal)%size);

        }

    }
    public boolean contains(String word){
        byte[] bytes = null;
        for(String s: hashFuncs){
            try{
                md = MessageDigest.getInstance(s);
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            bytes = md.digest(word.getBytes());
            bigInt = new BigInteger(bytes);
            int bigVal = bigInt.intValue();
            if(!bitSet.get(Math.abs(bigVal)%256)){
                return false;
            }
        }
        return true;
    }




    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder(bitSet.length());
        for(int i = 0; i < bitSet.length(); i++){
            if(bitSet.get(i)){
                sb.append("1");
            }
            else{
                sb.append("0");
            }
        }
        return sb.toString();
    }


}
