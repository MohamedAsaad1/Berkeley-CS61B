package gh2;
import deque.Deque;
import deque.ArrayDeque;
//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
       int capacity =  (int) Math.round(SR / frequency);
        buffer = new ArrayDeque();
        while(capacity > 0){
            buffer.addLast(0.0);
            capacity--;

        }
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your should initially fill your buffer array with zeros.
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        int counter = 0;
        while(buffer.size() > counter){
            buffer.removeFirst();
            double random_double = Math.random() - 0.5;
            buffer.addLast(random_double);
            counter++;
        }

    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        //       the average of the two multiplied by the DECAY factor.
        //       **Do not call StdAudio.play().**
        double firstNode = buffer.removeFirst();
        double newNode = ((buffer.get(0) + firstNode) / 2) * DECAY;
        buffer.addLast(newNode);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
