# COP-4520-Assignment-2

1. To compile from the command line, type the following:

javac problem1.java (for problem 1)

javac problem2.java (for problem 2)

2. After compiling successfully, to run from the command line type the following:

java problem1 (for problem 1)

java problem2 (for problem 2)

Problem 1:
Calculating the efficiency of this problem is difficult since it relies on randomness so it is subject to change based on the randomness for each run.

This solution is as follows, as there are N guests (threads) and they cannot communicate between eachother, the strategy used to track when all guests have gone is to have the first guest keep a count and be the only guest allowed to replace the cupcake. Since he knows how many other guests there are, (N-1), which will only eat a cupcake once. He will know when each guest has vistied the labyrinth as he will increment his count each time he replaces the cupcake.

Problem 2:

This solution (strategy 1) allows multiple guest threads to access a room containing a crystal vase one at a time. Each guest tries to enter the room. If the room is available, the guest enters, views the vase for a random duration, then exits, making the room available again. Access to the room is synchronized using a lock to prevent multiple guests from entering simultaneously.

Since each guest thread runs independently and there is no interaction or dependency between threads (except for synchronization), the runtime complexity can be considered linear with respect to the number of guests. O(n)

Strategy 1:
Advantages
- Guests can attempt to enter the showroom at any time without waiting.
Disadvantages:
- No guarantee that a guest will be able to enter the showroom when desired.

Strategy 2:
Advantages
- Guests can easily see if the showroom is available or busy by checking the sign on the door.
Disadvantages:
- Guests may still need to wait if the showroom is busy, without any defined order of access.

Strategy 3:
Advantages
- Provides a systematic approach for guests to access the vase, reducing conflicts and frustration.
Disadvantages:
- If a guest exits the showroom without notifying the next guest in the queue promptly, it may disrupt the order.
