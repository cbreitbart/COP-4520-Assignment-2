import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class problem1 {
    private static final int NUM_GUESTS = 10;
    private static boolean[] guestEaten = new boolean[NUM_GUESTS]; // Tracks if a guest has eaten
    private static Lock lock = new ReentrantLock(); // Lock object for thread synchronization
    private static boolean isCupcakeAvailable = true;
    private static int count = 0;
    private static int activeThreadIndex;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[NUM_GUESTS];

        threads[0] = new Thread(problem1::checkCupcake);
        threads[0].start();

        for (int i = 1; i < NUM_GUESTS; i++) {
            int index = i;
            threads[i] = new Thread(() -> enterLabyrinth(index));
            threads[i].start();
        }

        while (count < NUM_GUESTS) {
            activeThreadIndex = randomNum(0, NUM_GUESTS - 1); // Selecting which thread to enter labyrinth
        }

        Arrays.stream(threads).forEach(thread -> { // Wait for all threads to finish execution
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.currentTimeMillis();
        System.out.println("All " + count + " guests have entered the labyrinth.");
        System.out.println("Runtime: " + (end - start) * 0.001 + " seconds");
    }
    
    // Method executed by the first thread to check cupcake status
    private static void checkCupcake() { 
        while (count < NUM_GUESTS) {
            lock.lock();
            if (activeThreadIndex == 0) {
                if (!isCupcakeAvailable) { // If cupcake is missing and needs to be replaced.
                    count++;
                    isCupcakeAvailable = true;
                }
                if (isCupcakeAvailable && !guestEaten[0]) { // If first guest (counter) hasn't eaten the cupcake yet.
                    count++;
                    isCupcakeAvailable = true;
                    guestEaten[0] = true;
                }
            }
            lock.unlock();
        }
    }

    // Method executed by all other threads than the first to enter the labyrinth
    private static void enterLabyrinth(int threadIdx) {
        while (count < NUM_GUESTS) {
            lock.lock();
            if (activeThreadIndex == threadIdx && isCupcakeAvailable && !guestEaten[threadIdx]) {
                isCupcakeAvailable = false;
                guestEaten[threadIdx] = true;
                System.out.println("Guest #" + (threadIdx) + " ate the cupcake!");
            }
            lock.unlock();
        }
    }

    private static int randomNum(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
