import java.util.Random;

public class problem2 {
    private static final int NUM_GUESTS = 10;
    private static boolean isRoomAvailable = true;
    private static Object lock = new Object(); // Lock object for synchronization

    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_GUESTS];

        for (int i = 0; i < NUM_GUESTS; i++) {
            final int guestNum = i;
            threads[i] = new Thread(() -> viewVase(guestNum));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All guests have viewed the vase.");
    }

    private static void viewVase(int guestNum) {
        while (true) {
            if (canEnterRoom()) { // Attempt to enter the room
                System.out.println("Guest #" + guestNum + " is viewing the crystal vase.");
                
                try {
                    Thread.sleep(new Random().nextInt(2000) + 1000); // View for 1-3 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Guest #" + guestNum + " exits the room.");
                releaseRoom(); // Reset room availability
                break;
            }
        }
    }

    private static boolean canEnterRoom() {
        synchronized (lock) { // Synchronize access to the shared variable
            if (isRoomAvailable) {
                isRoomAvailable = false; 
                return true; // Guest can enter the room
            } else {
                return false; // Room is busy, guest cannot enter
            }
        }
    }

    private static void releaseRoom() {
        synchronized (lock) { // Synchronize access to the shared variable
            isRoomAvailable = true;
        }
    }
}
