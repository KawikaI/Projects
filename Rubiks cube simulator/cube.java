import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

// cube is as follows: front(g) up(w) left(o) right(r) bottom(y) back(b)
// optimized and normal solution works 
// gives working options for custom or random scramble
// I think overall time complexity of O(1) and space of O(n) for the scramble 
// all moved are programmed to take action with cube orintated as such (above)

public class RubiksCubeSimulator {
    // initlaize map for cube
    private Map<Character, char[][]> faces = new HashMap<>();
    // initialize stack for moves
    private Stack<String> movesMade = new Stack<>();

    // Constructor to initialize cube
    public RubiksCubeSimulator() {
        initializeCube();
    }
  
    private void initializeCube() {
        faces.put('r', new char[][] { { 'r', 'r', 'r' }, { 'r', 'r', 'r' }, { 'r', 'r', 'r' } });
        faces.put('b', new char[][] { { 'b', 'b', 'b' }, { 'b', 'b', 'b' }, { 'b', 'b', 'b' } });
        faces.put('o', new char[][] { { 'o', 'o', 'o' }, { 'o', 'o', 'o' }, { 'o', 'o', 'o' } });
        faces.put('g', new char[][] { { 'g', 'g', 'g' }, { 'g', 'g', 'g' }, { 'g', 'g', 'g' } });
        faces.put('y', new char[][] { { 'y', 'y', 'y' }, { 'y', 'y', 'y' }, { 'y', 'y', 'y' } });
        faces.put('w', new char[][] { { 'w', 'w', 'w' }, { 'w', 'w', 'w' }, { 'w', 'w', 'w' } });

    }  
     /* 
    private void initializeCube() {
        faces.put('r', new char[][]{{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h','i'}});
        faces.put('b', new char[][]{{'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q','r'}});
        faces.put('o', new char[][]{{'s', 't', 'u'}, {'v', 'w', 'x'}, {'y', 'z','A'}});
        faces.put('g', new char[][]{{'B', 'C', 'D'}, {'E', 'F', 'G'}, {'H', 'I','J'}});
        faces.put('y', new char[][]{{'K', 'L', 'M'}, {'N', 'O', 'P'}, {'Q', 'R','S'}});
        faces.put('w', new char[][]{{'T', 'U', 'V'}, {'W', 'X', 'Y'}, {'Z', '1','2'}});
       }  */
     

    // method to configure the cube
    public void configCube(String input) {
        if ("r".equals(input)) {
            // Temporarily store the column that will be replaced
            rotateFaceClockwise('r');

            char[] tempUp = new char[3];
            for (int i = 0; i < 3; i++) {
                tempUp[i] = faces.get('w')[i][2]; // Right column of the up w face
            }

            // Move right g col to right w col
            for (int i = 0; i < 3; i++) {
                faces.get('w')[i][2] = faces.get('g')[i][2];
            }

            // Move right y col to right g col
            for (int i = 0; i < 3; i++) {
                faces.get('g')[i][2] = faces.get('y')[i][2];
            }
            /*
             * // Move right b col to right y col
             * for (int i = 0; i < 3; i++) {
             * //faces.get('y')[i][2] = faces.get('b')[2-i][0]; // Note: Inverting back (b)
             * column
             * faces.get('y')[i][2] = faces.get('b')[i][2];
             * }
             */

            for (int i = 0; i < 3; i++) {
                faces.get('y')[i][2] = faces.get('b')[2 - i][0];
            }

            // Restore up (w) column to back (b) face (inverted)
            for (int i = 0; i < 3; i++) {
                faces.get('b')[2 - i][0] = tempUp[i]; // Note: Inverting column when moving to back
            }

        } else if ("l".equals(input)) {
            // Temporarily store column that will be replaced
            rotateFaceClockwise('o');
            char[] tempUp = new char[3];
            for (int i = 0; i < 3; i++) {
                tempUp[i] = faces.get('w')[i][0]; // Left column of up (w) face
            }
            // Move back (b) column to up (w) face
            for (int i = 0; i < 3; i++) {
                faces.get('w')[i][0] = faces.get('b')[2 - i][2]; // Note: Inverting back (b) column
            }

            // Move down (y) column to back (b) face (inverted)
            for (int i = 0; i < 3; i++) {
                faces.get('b')[2 - i][2] = faces.get('y')[i][0]; // Note: Inverting when moving to back
            }

            // Move front (g) column to down (y) face
            for (int i = 0; i < 3; i++) {
                faces.get('y')[i][0] = faces.get('g')[i][0];
            }

            // Restore up (w) column to front (g) face
            for (int i = 0; i < 3; i++) {
                faces.get('g')[i][0] = tempUp[i];
            }

            // Rotate left (o) face itself clockwise

        } else if ("u'".equals(input)) {
            rotateFaceCounterClockwise('w');
            // Temporarily store top row that will be replaced
            char[] tempFront = new char[3];
            for (int i = 0; i < 3; i++) {
                tempFront[i] = faces.get('g')[0][i]; // Top row of front (g) face
            }

            // Move left (o) top row to front (g) top row
            for (int i = 0; i < 3; i++) {
                faces.get('g')[0][i] = faces.get('o')[0][i];
            }

            // Move back (b) top row to left (o) top row
            for (int i = 0; i < 3; i++) {
                faces.get('o')[0][i] = faces.get('b')[0][i];
            }

            // Move right (r) top row to back (b) top row
            for (int i = 0; i < 3; i++) {
                faces.get('b')[0][i] = faces.get('r')[0][i];
            }

            // Restore front (g) top row to right (r) top row
            for (int i = 0; i < 3; i++) {
                faces.get('r')[0][i] = tempFront[i];
            }

            // Rotate upper (w) face itself clockwise

        } else if ("d".equals(input)) {
            // Temporarily store bottom row of front (g) face
            rotateFaceClockwise('y');
            char[] tempFront = new char[3];
            for (int i = 0; i < 3; i++) {
                tempFront[i] = faces.get('g')[2][i]; // Bottom row of front (g) face
            }

            // Move bottom row of left (o) face to bottom row of front (g) face
            for (int i = 0; i < 3; i++) {
                faces.get('g')[2][i] = faces.get('o')[2][i];
            }

            // Move bottom row of back (b) face to bottom row of left (o) face
            for (int i = 0; i < 3; i++) {
                faces.get('o')[2][i] = faces.get('b')[2][i];
            }

            // Move bottom row of right (r) face to bottom row of back (b) face
            for (int i = 0; i < 3; i++) {
                faces.get('b')[2][i] = faces.get('r')[2][i];
            }

            // Restore bottom row of front (g) face to bottom row of right (r) face
            for (int i = 0; i < 3; i++) {
                faces.get('r')[2][i] = tempFront[i];
            }

        } else if ("d'".equals(input)) {
            rotateFaceCounterClockwise('y');
            char[] tempBottom = new char[3];

            for (int i = 0; i < 3; i++) {
                tempBottom[i] = faces.get('g')[2][i]; // Bottom row of front (g) face
            }

            for (int i = 0; i < 3; i++) {
                // move bottom red row to bottom green
                faces.get('g')[2][i] = faces.get('r')[2][i];
            }

            for (int i = 0; i < 3; i++) {
                // move bottom red row to bottom green
                faces.get('r')[2][i] = faces.get('b')[2][i];
            }

            for (int i = 0; i < 3; i++) {
                // move blue to red
                faces.get('b')[2][i] = faces.get('o')[2][i];
            }

            for (int i = 0; i < 3; i++) {
                // move blue to red
                faces.get('o')[2][i] = tempBottom[i];
            }

        } else if ("f".equals(input)) {
            rotateFaceClockwise('g');
            // Temporarily store bottom row of up (w) face
            char[] tempUp = new char[3];
            for (int i = 0; i < 3; i++) {
                tempUp[i] = faces.get('w')[2][i]; // Bottom row of up (w) face
            }

            // Move right column of left (o) face to bottom row of up (w) face
            for (int i = 0; i < 3; i++) {
                faces.get('w')[2][i] = faces.get('o')[2 - i][2]; // Inverting column
            }

            // wip
            // Move top row of down (y) face to right column of left (o) face (in reverse
            // order)
            for (int i = 0; i < 3; i++) {
                faces.get('o')[i][2] = faces.get('y')[0][i];
            }

            // Move left column of right (r) face to top row of down (y) face (in reverse
            // order)
            for (int i = 0; i < 3; i++) {
                faces.get('y')[0][i] = faces.get('r')[2 - i][0];
            }

            // Restore bottom row of up (w) face to left column of right (r) face
            for (int i = 0; i < 3; i++) {
                faces.get('r')[i][0] = tempUp[i];
            }

            // Rotate front (g) face itself clockwise

        } else if ("l'".equals(input)) {
            // Rotate left (l) face itself counter-clockwise
            rotateFaceCounterClockwise('o'); // Assuming 'o' represents left face

            // Temporarily store column that will be moved from front (g) to up (w) face
            char[] tempFront = new char[3];
            for (int i = 0; i < 3; i++) {
                tempFront[i] = faces.get('g')[i][0]; // Left column of front (g) face
            }

            // Move down (y) column to front (g) face
            for (int i = 0; i < 3; i++) {
                faces.get('g')[i][0] = faces.get('y')[i][0];
            }

            // wip
            // Move back (b) column (inverted) to down (y) face
            for (int i = 0; i < 3; i++) {
                // 'i' iterates normally for 'y', but inversely for 'b'
                // 'b' starts from the bottom (index 2) and moves upwards (to 0)
                faces.get('y')[i][0] = faces.get('b')[2 - i][2]; // Correcting the mapping
            }

            /*
             * original
             * for (int i = 0; i < 3; i++) {
             * faces.get('y')[i][0] = faces.get('b')[i][2]; // Inverting column when moving
             * }
             */

            // Move up (w) column to back (b) face (inverted)
            for (int i = 0; i < 3; i++) {
                faces.get('b')[i][2] = faces.get('w')[2 - i][0]; // Note: Inverting column when restoring
            }

            // Restore front (g) column to up (w) face
            for (int i = 0; i < 3; i++) {
                faces.get('w')[i][0] = tempFront[i];
            }
        } else if ("f'".equals(input)) {
            rotateFaceCounterClockwise('g'); // Assuming 'g' represents front face

            char[] tempUp = new char[3];
            for (int i = 0; i < 3; i++) {
                tempUp[i] = faces.get('w')[2][i];
            }

            for (int i = 0; i < 3; i++) {
                faces.get('w')[2][i] = faces.get('r')[i][0];
            }

            for (int i = 0; i < 3; i++) {
                faces.get('r')[i][0] = faces.get('y')[0][2 - i];
            }

            for (int i = 0; i < 3; i++) {
                faces.get('y')[0][i] = faces.get('o')[i][2];
            }

            // wip
            for (int i = 0; i < 3; i++) {
                faces.get('o')[2 - i][2] = tempUp[i];
            }

            // problem u with white face
        } else if ("u".equals(input)) {
            // Rotate up (u) face itself counter-clockwise
            rotateFaceClockwise('w'); // Assuming 'w' represents up face

            // Temporarily store top row of front face to move it later to left
            char[] tempFront = new char[3];
            for (int i = 0; i < 3; i++) {
                tempFront[i] = faces.get('g')[0][i];
            }

            // Move top row of right (r) face to top row of front (g) face
            for (int i = 0; i < 3; i++) {
                faces.get('g')[0][i] = faces.get('r')[0][i];
            }

            // Move top row of back (b) face to top row of right (r) face
            for (int i = 0; i < 3; i++) {
                faces.get('r')[0][i] = faces.get('b')[0][i];
            }

            // Move top row of left (o) face to top row of back (b) face
            for (int i = 0; i < 3; i++) {
                faces.get('b')[0][i] = faces.get('o')[0][i];
            }

            // Restore top row of front (g) face to top row of left (o) face
            for (int i = 0; i < 3; i++) {
                faces.get('o')[0][i] = tempFront[i];
            }
        } else if ("b".equals(input)) {
            rotateFaceClockwise('b');
            // Temporarily store top row of up (w) face
            char[] tempUp = new char[3];
            for (int i = 0; i < 3; i++) {
                tempUp[i] = faces.get('w')[0][2 - i]; // Top row of up (w) face
            }

            // Move left column of right (r) face to top row of up (w) face
            for (int i = 0; i < 3; i++) {
                faces.get('w')[0][i] = faces.get('r')[i][2];
            }

            // Move bottom row of down (y) face to left column of right (r) face
            for (int i = 0; i < 3; i++) {
                faces.get('r')[i][2] = faces.get('y')[2][2 - i];
            }

            // Move right column of left (o) face to bottom row of down (y) face
            for (int i = 0; i < 3; i++) {
                faces.get('y')[2][i] = faces.get('o')[i][0];
            }

            // wip
            // Restore top row of up (w) face to right column of left (o) face
            for (int i = 0; i < 3; i++) {
                faces.get('o')[i][0] = tempUp[i];

            }
            // Rotate back (b) face itself clockwise

            // problem with b face
        } else if ("r'".equals(input)) {
            // Rotate right (r) face itself counter-clockwise
            rotateFaceCounterClockwise('r');

            // Temporarily store right column of white (w) face
            char[] tempWhiteRightColumn = new char[3];
            for (int i = 0; i < 3; i++) {
                tempWhiteRightColumn[i] = faces.get('w')[i][2];
            }

            for (int i = 0; i < 3; i++) {
                // For the white face, it remains the same, iterating normally
                // For the blue face, reverse the iteration by subtracting the current index
                // from 2 (i.e., 2, 1, 0)
                faces.get('w')[i][2] = faces.get('b')[2 - i][0];
            }

            // left column of blue (b) face is replaced by right column of yellow (y) face
            for (int i = 0; i < 3; i++) {
                faces.get('b')[i][0] = faces.get('y')[2 - i][2];
            }

            // right column of yellow (y) face is replaced by right column of green (g) face
            for (int i = 0; i < 3; i++) {
                faces.get('y')[i][2] = faces.get('g')[i][2];
            }

            // Finally, right column of green (g) face is replaced by temporarily stored
            // right column of white (w) face
            for (int i = 0; i < 3; i++) {
                faces.get('g')[i][2] = tempWhiteRightColumn[i];
            }

        } else if ("b'".equals(input)) {
            // Rotate the back (b) face itself counter-clockwise
            rotateFaceCounterClockwise('b');

            // Temporarily store the right column of the red (r) face
            char[] tempRedRightColumn = new char[3];
            for (int i = 0; i < 3; i++) {
                tempRedRightColumn[i] = faces.get('r')[i][2];
            }

            // Move the top row of the white (w) face to the right column of the red (r)
            // face
            for (int i = 0; i < 3; i++) {
                faces.get('r')[i][2] = faces.get('w')[0][i];
            }

            // Move the left column of the orange (o) face to the top row of the white (w)
            // face
            for (int i = 0; i < 3; i++) {
                faces.get('w')[0][i] = faces.get('o')[2 - i][0];
            }

            // Move the bottom row of the yellow (y) face to the left column of the orange
            // (o) face, in reverse order
            for (int i = 0; i < 3; i++) {
                faces.get('o')[i][0] = faces.get('y')[2][i];
            }

            // Restore the temporarily stored right column of the red (r) face to the bottom
            // row of the yellow (y) face, in correct order
            for (int i = 0; i < 3; i++) {
                faces.get('y')[2][i] = tempRedRightColumn[2 - i];
            }

            // wip
            // Assuming 'M' move is the input

        } else if ("m'".equals(input)) {
            // rotate o clockwise
            rotateFaceClockwise('o');
            // roatete red counter clockwise
            rotateFaceCounterClockwise('r');
            // create temp arrays for l and r cols for w b y g

            char[] tempWLeft = new char[3];
            char[] tempWRight = new char[3];
            char[] tempGLeft = new char[3];
            char[] tempGRight = new char[3];
            char[] tempBLeft = new char[3];
            char[] tempBRight = new char[3];
            char[] tempYLeft = new char[3];
            char[] tempYRight = new char[3];

            // temp for w left col
            for (int i = 0; i < 3; i++) {
                tempWLeft[i] = faces.get('w')[i][0];
            }

            // temp for w right col
            for (int i = 0; i < 3; i++) {
                tempWRight[i] = faces.get('w')[i][2];
            }

            // temp for g left col
            for (int i = 0; i < 3; i++) {
                tempGLeft[i] = faces.get('g')[i][0];
            }

            // temp for g right col
            for (int i = 0; i < 3; i++) {
                tempGRight[i] = faces.get('g')[i][2];
            }

            // temp for b left col
            for (int i = 0; i < 3; i++) {
                tempBLeft[i] = faces.get('b')[i][0];
            }

            // temp for b right col
            for (int i = 0; i < 3; i++) {
                tempBRight[i] = faces.get('b')[i][2];
            }

            // temp for y left col
            for (int i = 0; i < 3; i++) {
                tempYLeft[i] = faces.get('y')[i][0];
            }

            // temp for y right col
            for (int i = 0; i < 3; i++) {
                tempYRight[i] = faces.get('y')[i][2];
            }
            // Temporarily store the column from the up (U) face

            // w left col = b right col
            for (int i = 0; i < 3; i++) {
                faces.get('w')[i][0] = tempBRight[2 - i];
            }

            // w right col = b left col
            for (int i = 0; i < 3; i++) {
                faces.get('w')[i][2] = tempBLeft[2 - i];
            }

            // g left col = w left col
            for (int i = 0; i < 3; i++) {
                faces.get('g')[i][0] = tempWLeft[i];
            }

            // g right col = w right col
            for (int i = 0; i < 3; i++) {
                faces.get('g')[i][2] = tempWRight[i];
            }

            // y left col = g left col
            for (int i = 0; i < 3; i++) {
                faces.get('y')[i][0] = tempGLeft[i];
            }

            // y right col = g right col
            for (int i = 0; i < 3; i++) {
                faces.get('y')[i][2] = tempGRight[i];
            }

            // b left col = y left col
            for (int i = 0; i < 3; i++) {
                faces.get('b')[i][0] = tempYRight[2 - i];
            }

            // b right col = y right col
            for (int i = 0; i < 3; i++) {
                faces.get('b')[i][2] = tempYLeft[2 - i];
            }

        } else if ("m".equals(input)) {
            // rotate o clockwise
            rotateFaceCounterClockwise('o');
            // roatete red counter clockwise
            rotateFaceClockwise('r');
            // create temp arrays for l and r cols for w b y g

            char[] tempmWLeft = new char[3];
            char[] tempmWRight = new char[3];
            char[] tempmGLeft = new char[3];
            char[] tempmGRight = new char[3];
            char[] tempmBLeft = new char[3];
            char[] tempmBRight = new char[3];
            char[] tempmYLeft = new char[3];
            char[] tempmYRight = new char[3];

            // temp for w left col
            for (int i = 0; i < 3; i++) {
                tempmWLeft[i] = faces.get('w')[i][0];
            }

            // temp for w right col
            for (int i = 0; i < 3; i++) {
                tempmWRight[i] = faces.get('w')[i][2];
            }

            // temp for g left col
            for (int i = 0; i < 3; i++) {
                tempmGLeft[i] = faces.get('g')[i][0];
            }

            // temp for g right col
            for (int i = 0; i < 3; i++) {
                tempmGRight[i] = faces.get('g')[i][2];
            }

            // temp for b left col
            for (int i = 0; i < 3; i++) {
                tempmBLeft[i] = faces.get('b')[i][0];
            }

            // temp for b right col
            for (int i = 0; i < 3; i++) {
                tempmBRight[i] = faces.get('b')[i][2];
            }

            // temp for y left col
            for (int i = 0; i < 3; i++) {
                tempmYLeft[i] = faces.get('y')[i][0];
            }

            // temp for y right col
            for (int i = 0; i < 3; i++) {
                tempmYRight[i] = faces.get('y')[i][2];
            }
            // Temporarily store the column from the up (U) face

            // w left col = g left col
            for (int i = 0; i < 3; i++) {
                faces.get('w')[i][0] = tempmGLeft[i];
            }

            // w right col = g right col
            for (int i = 0; i < 3; i++) {
                faces.get('w')[i][2] = tempmGRight[i];
            }

            // g left col = y left col
            for (int i = 0; i < 3; i++) {
                faces.get('g')[i][0] = tempmYLeft[i];
            }

            // g right col = y right col
            for (int i = 0; i < 3; i++) {
                faces.get('g')[i][2] = tempmYRight[i];
            }

            // y left col = b right col Rev
            for (int i = 0; i < 3; i++) {
                faces.get('y')[i][0] = tempmBRight[2 - i];
            }

            // y right col = b left col rev
            for (int i = 0; i < 3; i++) {
                faces.get('y')[i][2] = tempmBLeft[2 - i];
            }

            // b left col = w right col rev
            for (int i = 0; i < 3; i++) {
                faces.get('b')[i][0] = tempmWRight[2 - i];
            }

            // b right col = w left col rev
            for (int i = 0; i < 3; i++) {
                faces.get('b')[i][2] = tempmWLeft[2 - i];
            }

        } else if ("e".equals(input)) {
            // rotate faces y and w
            rotateFaceClockwise('y');
            rotateFaceCounterClockwise('w');

            // create temp arrays for top and bottom rows of g r b o
            char[] tempeGTop = new char[3];
            char[] tempeGBottom = new char[3];
            char[] tempeRTop = new char[3];
            char[] tempeRBottom = new char[3];
            char[] tempeBTop = new char[3];
            char[] tempeBBottom = new char[3];
            char[] tempeOTop = new char[3];
            char[] tempeOBottom = new char[3];

            // temp for red top
            for (int i = 0; i < 3; i++) {
                tempeRTop[i] = faces.get('r')[0][i];
            }

            // temp for red bottom
            for (int i = 0; i < 3; i++) {
                tempeRBottom[i] = faces.get('r')[2][i];
            }

            // temp for blue top
            for (int i = 0; i < 3; i++) {
                tempeBTop[i] = faces.get('b')[0][i];
            }

            // temp for blue bottom
            for (int i = 0; i < 3; i++) {
                tempeBBottom[i] = faces.get('b')[2][i];
            }

            // temp for o top
            for (int i = 0; i < 3; i++) {
                tempeOTop[i] = faces.get('o')[0][i];
            }

            // temp for o bottom
            for (int i = 0; i < 3; i++) {
                tempeOBottom[i] = faces.get('o')[2][i];
            }

            // temp for g top
            for (int i = 0; i < 3; i++) {
                tempeGTop[i] = faces.get('g')[0][i];
            }

            // temp for g bottom
            for (int i = 0; i < 3; i++) {
                tempeGBottom[i] = faces.get('g')[2][i];
            }

            // g top = r top
            for (int i = 0; i < 3; i++) {
                faces.get('g')[0][i] = tempeRTop[i];
            }

            // g bot = r bot
            for (int i = 0; i < 3; i++) {
                faces.get('g')[2][i] = tempeRBottom[i];
            }

            // r top = b top
            for (int i = 0; i < 3; i++) {
                faces.get('r')[0][i] = tempeBTop[i];
            }

            // r bot = b bot
            for (int i = 0; i < 3; i++) {
                faces.get('r')[2][i] = tempeBBottom[i];
            }

            // b top = o top
            for (int i = 0; i < 3; i++) {
                faces.get('b')[0][i] = tempeOTop[i];
            }

            // b bot = o bot
            for (int i = 0; i < 3; i++) {
                faces.get('b')[2][i] = tempeOBottom[i];
            }

            // o top = g top
            for (int i = 0; i < 3; i++) {
                faces.get('o')[0][i] = tempeGTop[i];
            }

            // o bot = g bot
            for (int i = 0; i < 3; i++) {
                faces.get('o')[2][i] = tempeGBottom[i];
            }

        } else if ("e'".equals(input)) {
            rotateFaceClockwise('w');
            rotateFaceCounterClockwise('y');
            char[] tempERTop = new char[3];
            char[] tempERBottom = new char[3];
            char[] tempEBTop = new char[3];
            char[] tempEBBottom = new char[3];
            char[] tempEOTop = new char[3];
            char[] tempEOBottom = new char[3];
            char[] tempEGTop = new char[3];
            char[] tempEGBottom = new char[3];

            // temp for red top
            for (int i = 0; i < 3; i++) {
                tempERTop[i] = faces.get('r')[0][i];
            }

            // temp for red bottom
            for (int i = 0; i < 3; i++) {
                tempERBottom[i] = faces.get('r')[2][i];
            }

            // temp for blue top
            for (int i = 0; i < 3; i++) {
                tempEBTop[i] = faces.get('b')[0][i];
            }

            // temp for blue bottom
            for (int i = 0; i < 3; i++) {
                tempEBBottom[i] = faces.get('b')[2][i];
            }

            // temp for o top
            for (int i = 0; i < 3; i++) {
                tempEOTop[i] = faces.get('o')[0][i];
            }

            // temp for o bottom
            for (int i = 0; i < 3; i++) {
                tempEOBottom[i] = faces.get('o')[2][i];
            }

            // temp for g top
            for (int i = 0; i < 3; i++) {
                tempEGTop[i] = faces.get('g')[0][i];
            }

            // temp for g bottom
            for (int i = 0; i < 3; i++) {
                tempEGBottom[i] = faces.get('g')[2][i];
            }

            // g top = o top
            for (int i = 0; i < 3; i++) {
                faces.get('g')[0][i] = tempEOTop[i];
            }

            // g bot = o bot
            for (int i = 0; i < 3; i++) {
                faces.get('g')[2][i] = tempEOBottom[i];
            }

            // r top = g top
            for (int i = 0; i < 3; i++) {
                faces.get('r')[0][i] = tempEGTop[i];
            }

            // r bot = g bot
            for (int i = 0; i < 3; i++) {
                faces.get('r')[2][i] = tempEGBottom[i];
            }

            // b top = r top
            for (int i = 0; i < 3; i++) {
                faces.get('b')[0][i] = tempERTop[i];
            }

            // b bot = r bot
            for (int i = 0; i < 3; i++) {
                faces.get('b')[2][i] = tempERBottom[i];
            }

            // o top = b top
            for (int i = 0; i < 3; i++) {
                faces.get('o')[0][i] = tempEBTop[i];
            }

            // o bot = b bot
            for (int i = 0; i < 3; i++) {
                faces.get('o')[2][i] = tempEBBottom[i];
            }

        } else if ("s".equals(input)) {
            rotateFaceClockwise('b');
            rotateFaceCounterClockwise('g');
            char[] tempsRleft = new char[3];
            char[] tempsRRight = new char[3];
            char[] tempsYTop = new char[3];
            char[] tempsYbottom = new char[3];
            char[] tempsOLeft = new char[3];
            char[] tempsORight = new char[3];
            char[] tempsWTop = new char[3];
            char[] tempsWBottom = new char[3];

            // temp for w top
            for (int i = 0; i < 3; i++) {
                tempsWTop[i] = faces.get('w')[0][i];
            }

            // temp for w bot
            for (int i = 0; i < 3; i++) {
                tempsWBottom[i] = faces.get('w')[2][i];
            }

            // temp for r left
            for (int i = 0; i < 3; i++) {
                tempsRleft[i] = faces.get('r')[i][0];
            }

            // temp for r right
            for (int i = 0; i < 3; i++) {
                tempsRRight[i] = faces.get('r')[i][2];
            }

            // temp for y top
            for (int i = 0; i < 3; i++) {
                tempsYTop[i] = faces.get('y')[0][i];
            }

            // temp for y Bottom
            for (int i = 0; i < 3; i++) {
                tempsYbottom[i] = faces.get('y')[2][i];
            }

            // temp for o left
            for (int i = 0; i < 3; i++) {
                tempsOLeft[i] = faces.get('o')[i][0];
            }

            // temp for o right
            for (int i = 0; i < 3; i++) {
                tempsORight[i] = faces.get('o')[i][2];
            }

            // w top = r right
            for (int i = 0; i < 3; i++) {
                faces.get('w')[0][i] = tempsRRight[i];
            }

            // w bot = r left
            for (int i = 0; i < 3; i++) {
                faces.get('w')[2][i] = tempsRleft[i];
            }

            // r left = y top
            for (int i = 0; i < 3; i++) {
                faces.get('r')[i][0] = tempsYTop[2-i];
            }

            // r right = y bot
            for (int i = 0; i < 3; i++) {
                faces.get('r')[i][2] = tempsYbottom[2-i];
            }

            // y top = o right
            for (int i = 0; i < 3; i++) {
                faces.get('y')[0][i] = tempsORight[i];
            }

            // y bottom = o left
            for (int i = 0; i < 3; i++) {
                faces.get('y')[2][i] = tempsOLeft[i];
            }

            // o left = w top
            for (int i = 0; i < 3; i++) {
                faces.get('o')[i][0] = tempsWTop[2-i];
            }

            // o right = w bot
            for (int i = 0; i < 3; i++) {
                faces.get('o')[i][2] = tempsWBottom[2-i];
            }

        } else if ("s'".equals(input)) {
            rotateFaceClockwise('g');
            rotateFaceCounterClockwise('b');
            char[] tempSRleft = new char[3];
            char[] tempSRRight = new char[3];
            char[] tempSYTop = new char[3];
            char[] tempSYbottom = new char[3];
            char[] tempSOLeft = new char[3];
            char[] tempSORight = new char[3];
            char[] tempSWTop = new char[3];
            char[] tempSWBottom = new char[3];

            // temp for w top
            for (int i = 0; i < 3; i++) {
                tempSWTop[i] = faces.get('w')[0][i];
            }

            // temp for w bot
            for (int i = 0; i < 3; i++) {
                tempSWBottom[i] = faces.get('w')[2][i];
            }

            // temp for r left
            for (int i = 0; i < 3; i++) {
                tempSRleft[i] = faces.get('r')[i][0];
            }

            // temp for r right
            for (int i = 0; i < 3; i++) {
                tempSRRight[i] = faces.get('r')[i][2];
            }

            // temp for y top
            for (int i = 0; i < 3; i++) {
                tempSYTop[i] = faces.get('y')[0][i];
            }

            // temp for y Bottom
            for (int i = 0; i < 3; i++) {
                tempSYbottom[i] = faces.get('y')[2][i];
            }

            // temp for o left
            for (int i = 0; i < 3; i++) {
                tempSOLeft[i] = faces.get('o')[i][0];
            }

            // temp for o right
            for (int i = 0; i < 3; i++) {
                tempSORight[i] = faces.get('o')[i][2];
            }

            // w top = o left
            for (int i = 0; i < 3; i++) {
                faces.get('w')[0][i] = tempSOLeft[2 - i];
            }

            // w bottom = o right
            for (int i = 0; i < 3; i++) {
                faces.get('w')[2][i] = tempSORight[2-i];
            }

            // o left = y bottom
            for (int i = 0; i < 3; i++) {
                faces.get('o')[i][0] = tempSYbottom[i];
            }

            // o right = y top
            for (int i = 0; i < 3; i++) {
                faces.get('o')[i][2] = tempSYTop[i];
            }

            // r left = w bottom
            for (int i = 0; i < 3; i++) {
                faces.get('r')[i][0] = tempSWBottom[i];
            }

            // r right = w top
            for (int i = 0; i < 3; i++) {
                faces.get('r')[i][2] = tempSWTop[i];
            }

            // y top = r left
            for (int i = 0; i < 3; i++) {
                faces.get('y')[0][i] = tempSRleft[2 - i];
            }

            // y bottom = r right
            for (int i = 0; i < 3; i++) {
                faces.get('y')[2][i] = tempSRRight[2 - i];
            }

        }
    }

    // method that manages stack history and simplified moves
    private void addMove(String move) {
        // Check if last three moves are same as curr move
        if (movesMade.size() >= 2 && movesMade.peek().equals(move)
                && movesMade.get(movesMade.size() - 2).equals(move)) {
            // Remove last two moves, leaving one for simpl solution
            movesMade.pop();
            movesMade.pop();

            // init simplified move to the inverse of a move (ex. r' = r and l = l')
            String simplifiedMove = move.endsWith("'") ? move.substring(0, move.length() - 1) : move + "'";
            // If the simplified move is the same as the top of the stack remove it
            if (!movesMade.isEmpty() && movesMade.peek().equals(simplifiedMove)) {
                movesMade.pop();
            } else {
                // add the simplified move
                movesMade.push(simplifiedMove);
            }
        } else {
            // add unique move to stack
            movesMade.push(move);
        }
    }

    // returns the inverse of the move
    private String getInverseMove(String move) {
        return move.endsWith("'") ? move.substring(0, move.length() - 1) : move + "'";
    }

    // method that prints cube
    public void printCube() {
        // iterate through the faces and print them
        char[] order = { 'w', 'r', 'y', 'b', 'g', 'o' };
        for (char faceKey : order) {
            char[][] face = faces.get(faceKey);
            System.out.println("Face: " + faceKey);
            for (char[] row : face) {
                for (char cell : row) {
                    System.out.print(cell + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    // method to print the solution commamds
    public void printSolutionCommands() {
        System.out.println("Solution commands:");
        // iterate through the moves made and print the inverse of the moves
        while (!movesMade.isEmpty()) {
            String move = movesMade.pop();
            System.out.print(getInverseMove(move) + " ");
        }
        System.out.println();
    }

    // method to rotate face clockwise
    private void rotateFaceClockwise(char face) {
        // store face var in originalFace
        char[][] originalFace = faces.get(face);
        // new arr for rotated face
        char[][] rotatedFace = new char[3][3];
        // rotate face
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotatedFace[j][2 - i] = originalFace[i][j];
            }
        }
        // put face in map
        faces.put(face, rotatedFace);
    }

    // method to rotate face counter clockwise
    private void rotateFaceCounterClockwise(char face) {
        // store face var in originalFace
        char[][] originalFace = faces.get(face);
        // new arr for rotated face
        char[][] rotatedFace = new char[3][3];
        // rotate face
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rotatedFace[2 - j][i] = originalFace[i][j];
            }
        }
        // put face in map
        faces.put(face, rotatedFace);
    }

    // method to gernerate random scramble
    private String generateRandomScramble(int length) {
        // possible moves
        // add "M","M'", "E", "E'", "S", "S'" when ready
        String[] possibleMoves = { "R", "R'", "L", "L'", "U", "U'", "D", "D'", "F", "F'", "B", "B'", "M", "M'", "E",
                "E'", "S", "S'" };
        //String[] possibleMoves = { "R", "R'", "L", "L'", "U", "U'", "D", "D'", "F", "F'", "B", "B'", "M", "M'" };

        
        // create instance of string builder
        StringBuilder scramble = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // grab random index
            int moveIndex = (int) (Math.random() * possibleMoves.length);
            // appends move to scramble
            scramble.append(possibleMoves[moveIndex]).append(" ");
        }
        // return random scramble in a trimmed str
        return scramble.toString().trim();
    }

    // method that applies move to cube and stack
    public void move(String command) {
        System.out.println("Applying move: " + command);
        System.out.println(" ");
        // apply move to cube
        configCube(command);
        // add move to stack
        addMove(command);
    }

    // method that checks for solved cube
    public boolean isSolved() {
        // iterate through map
        for (Map.Entry<Character, char[][]> entry : faces.entrySet()) {
            // get face
            char[][] face = entry.getValue();
            // get first square color for comparison
            char firstSquare = face[0][0];

            // iterate through face and check if all squares are the same
            for (char[] row : face) {
                for (char square : row) {
                    if (square != firstSquare) {
                        // not solved
                        return false;
                    }
                }
            }
        }
        // All faces are solved
        return true;
    }

    public void advancedMoves() {
        System.out.println(
                "Here are the moves including the advanced moves (r, r', l, l', u, u', d, d', f, f', b, b') Advanced moves: M, M', E, E', S, S' (Middle, Equator, Standing) or 'solve' to end:");
        System.out.println(
                "Warning! Since the advanced moves move the centers of the cube it is important to always reposition such that green is the front and white is the top before and after any moves");
    }

    // main
    public static void main(String[] args) {
        // Create instance
        RubiksCubeSimulator simulator = new RubiksCubeSimulator();
        // Init scanner
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Enter 'random' for a random scramble or 'custom' to enter your own moves:");
        // User's choice for scramble type
        String choice = scanner.nextLine().trim().toLowerCase();
    
        // Flow for random scramble
        if ("random".equals(choice)) {
            String randomScramble = simulator.generateRandomScramble(10);
            System.out.println("Random scramble: " + randomScramble);
    
            String[] moves = randomScramble.split(" ");
            for (String move : moves) {
                simulator.configCube(move.toLowerCase());
            }
            simulator.printCube();
        }
        // Flow for custom scramble
        else if ("custom".equals(choice)) {
            System.out.println("Would you like to access advanced moves? (yes/no)");
            String advancedChoice = scanner.nextLine().trim().toLowerCase();
        
            if ("yes".equals(advancedChoice)) {
                System.out.println("Enter advanced moves (e.g., M, M', E, E', S, S', etc.) or 'solve' to finish:");
                while (true) {
                    String move = scanner.nextLine().trim().toLowerCase();
                    if ("solve".equals(move)) {
                        if (simulator.isSolved()) {
                            System.out.println("Congratulations! The cube is solved.");
                        } else {
                            System.out.println("The cube is not solved. Here's the solution:");
                            simulator.printSolutionCommands();
                        }
                        break;
                    } else if (!move.isEmpty()) {
                        System.out.println("You have selected " + move);
                        simulator.move(move);
                        //simulator.configCube(move);
                        simulator.printCube();
                        System.out.println("Enter next move or 'solve' to end:");
                    }
                }
            } else {
                System.out.println("Enter moves (r, r', l, l', u, u', d, d', f, f', b, b') One character at a time");
                while (true) {
                    String move = scanner.nextLine().trim().toLowerCase();
                    if ("solve".equals(move)) {
                        if (simulator.isSolved()) {
                            System.out.println("Congratulations! The cube is solved.");
                        } else {
                            System.out.println("The cube is not solved. Here's the solution:");
                            simulator.printSolutionCommands();
                        }
                        break;
                    } else if (!move.isEmpty()) {
                        System.out.println("You have selected " + move);
                        simulator.move(move);
                        //simulator.configCube(move);
                        simulator.printCube();
                        System.out.println("Enter next move or 'solve' to end:");
                    }
                }
            }
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
        
        scanner.close();
    }
}
