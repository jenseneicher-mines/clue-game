/* Clue Game Part 1
 * Kai Mizuno and Jensen Eicher
 */

package clueGame;

public class BadConfigFormatException extends Exception {
    public BadConfigFormatException(String errorMessage) {
        System.out.println(errorMessage);
    }
}
