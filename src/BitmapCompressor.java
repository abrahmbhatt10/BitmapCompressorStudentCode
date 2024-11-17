/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Agastya Brahmbhatt
 */
public class BitmapCompressor {
    /*
        max Count will be 15, an arbitrary number, using 4 bits.
     */
    private static int countSize = 4;
    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {
        /*
            Below code inspired by Sedgewick & Wayne's Algorithms 4th edition
         */
        boolean isZero, previous = false;
        char count = 0;
        while(!BinaryStdIn.isEmpty()){
            isZero = BinaryStdIn.readBoolean();
            if(isZero!= previous){
                BinaryStdOut.write(count, countSize);
                count = 0;
                previous = !previous;
            }
            else{
                if(count == 16){
                    BinaryStdOut.write(count, countSize);
                    count = 0;
                    BinaryStdOut.write(count, countSize);
                }
            }
            count++;
        }
        BinaryStdOut.write(count, countSize);
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {
        /*
            Below code inspired by Sedgewick & Wayne's Algorithms 4th edition
         */
        boolean isZero = false;
        while(!BinaryStdIn.isEmpty()){
            char count = BinaryStdIn.readChar(countSize);
            for(int i = 0; i < count; i++){
                BinaryStdOut.write(isZero);
            }
            isZero = !isZero;
        }
        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}