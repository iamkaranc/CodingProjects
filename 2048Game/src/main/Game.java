package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Game {

    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            try {
                File file = new File("C:\\Users\\Karan Chawda\\IdeaProjects\\2048 Game\\src\\main\\base.properties");
                FileInputStream fileInputStream = new FileInputStream(file);
                properties.load(fileInputStream);
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("Could not find the properties file" + fileNotFoundException);
            } catch (Exception exception) {
                System.out.println("Could not load properties file" + exception.toString());
            }
            Tile tile = new Tile();
            tile.setSize(Integer.parseInt(properties.getProperty("size")));
            tile = startGame(tile);
            playGame(tile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Tile startGame(Tile tile) {
        int size = tile.getSize();
        String[][] matrix = new String[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                matrix[i][j] = "_";
            }
        }
        tile.setMatrix(matrix);
        tile = generateRandom(tile);
        tile = generateRandom(tile);
        printMatrix(tile);
        return tile;
    }

    /*
     * Moves
     * 0 -> Left
     * 1 -> Right
     * 2 -> Top
     * 3 -> Bottom
     */

    public static void playGame(Tile tile) {
        int pass;
        while (true) {
            int size = tile.getSize();
            String[][] matrix = tile.getMatrix();
            String[][] matrix1 = new String[size][size];
            String[][] matrix2 = new String[size][size];
            List<String> temp = new ArrayList<>();
            pass = checkValidity(size, matrix);
            if (pass == 1) {
                System.out.println("Congratulations!!! You Win!");
                break;
            }
            if (pass == 3) {
                System.out.println("Game Over!!!");
                break;
            }
            Scanner sc = new Scanner(System.in);
            int move = sc.nextInt();
            if (move > 3) {
                System.out.println("Please enter a valid move");
            }
            List<Integer> numList = new ArrayList<>();
            List<Integer> finalList = new ArrayList<>();
            int over = 1;
            if (move == 0) {
                //Left case
                for (int i = 0; i < size; ++i) {
                    for (int j = size - 1; j >= 0; --j) {
                        //Row
                        if (!matrix[i][j].equals("_"))
                            numList.add(Integer.parseInt(matrix[i][j]));
                        else
                            numList.add(0);
                    }
                    finalList = shuffle(numList, finalList, size);
                    for (int l = 0; l < finalList.size(); ++l) {
                        if (finalList.get(l).equals(0))
                            matrix[i][l] = "_";
                        else
                            matrix[i][l] = finalList.get(l).toString();
                    }
                    finalList.clear();
                    numList.clear();
                }
            }
            else if (move == 1) {
                //Right case
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        //Row
                        if (!matrix[i][j].equals("_"))
                            numList.add(Integer.parseInt(matrix[i][j]));
                        else
                            numList.add(0);
                    }
                    finalList = shuffle(numList, finalList, size);
                    Collections.reverse(finalList);
                    for (int l = 0; l < finalList.size(); ++l) {
                        if (finalList.get(l).equals(0))
                            matrix[i][l] = "_";
                        else
                            matrix[i][l] = finalList.get(l).toString();
                    }
                    finalList.clear();
                    numList.clear();
                }

            } else if (move == 3) {
                //Bottom case
                //Make like right
                int base = 0;
                for (int i = size - 1; i >= 0; --i) {
                    for (int j = 0; j < size; ++j) {
                        temp.add(matrix[j][i]);
                    }
                    for (int b = 0; b < size; ++b) {
                        matrix1[base][b] = temp.get(b);
                    }
                    base++;
                    temp.clear();
                }
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        //Row
                        if (!matrix1[i][j].equals("_"))
                            numList.add(Integer.parseInt(matrix1[i][j]));
                        else
                            numList.add(0);
                    }
                    finalList = shuffle(numList, finalList, size);
                    Collections.reverse(finalList);
                    for (int l = 0; l < finalList.size(); ++l) {
                        if (finalList.get(l).equals(0))
                            matrix1[i][l] = "_";
                        else
                            matrix1[i][l] = finalList.get(l).toString();
                    }
                    finalList.clear();
                    numList.clear();
                }
                base = 0;
                for (int i = size - 1; i >= 0; --i) {
                    for (int j = size - 1; j >= 0; --j) {
                        temp.add(matrix1[i][j]);
                    }
                    Collections.reverse(temp);
                    for (int b = 0; b < size; ++b) {
                        matrix[b][base] = temp.get(b);
                    }
                    base++;
                    temp.clear();
                }
            }
            else if (move == 2) {
                //Top case
                //Use left
                int base = 0;
                for (int i = size - 1; i >= 0; --i) {
                    for (int j = 0; j < size; ++j) {
                        temp.add(matrix[j][i]);
                    }
                    for (int b = 0; b < size; ++b) {
                        matrix2[base][b] = temp.get(b);
                    }
                    base++;
                    temp.clear();
                }
                for (int i = 0; i < size; ++i) {
                    for (int j = size - 1; j >= 0; --j) {
                        //Row
                        if (!matrix2[i][j].equals("_"))
                            numList.add(Integer.parseInt(matrix2[i][j]));
                        else
                            numList.add(0);
                    }
                    finalList = shuffle(numList, finalList, size);
                    Collections.reverse(finalList);
                    for (int l = 0; l < finalList.size(); ++l) {
                        if (finalList.get(l).equals(0))
                            matrix2[i][l] = "_";
                        else
                            matrix2[i][l] = finalList.get(l).toString();
                    }
                    finalList.clear();
                    numList.clear();
                }
                base = 0;
                for (int i = size - 1; i >= 0; --i) {
                    for (int j = size - 1; j >= 0; --j) {
                        temp.add(matrix2[i][j]);
                    }
                    for (int b = 0; b < size; ++b) {
                        matrix[b][base] = temp.get(b);
                    }
                    base++;
                    temp.clear();
                }
            }
            tile.setMatrix(matrix);
            tile = generateRandom(tile);
            printMatrix(tile);
        }
    }

    public static List<Integer> shuffle(List<Integer> numList, List<Integer> finalList, int size) {
        int over = numList.size() - 2;
        int p;
        for (p = numList.size() - 1; p > 0 && over >= 0; ) {
            if (!numList.get(p).equals(0) && !numList.get(p - (p - over)).equals(0)) {
                if (numList.get(p).equals(numList.get(p - (p - over)))) {
                    finalList.add(numList.get(p) + numList.get(p - (p - over)));
                    p -= 2;
                    over -= 2;
                } else {
                    finalList.add(numList.get(p));
                    if (over == 0) {
                        if (!numList.get(over).equals(0))
                            finalList.add(numList.get(over));
                    }
                    p = over;
                    over--;
                }

            } else if (!numList.get(p).equals(0) && numList.get(p - (p - over)).equals(0)) {
                if (over == 0) {
                    finalList.add(numList.get(p));
                    if (!numList.get(over).equals(0))
                        finalList.add(numList.get(over));
                }
                over--;
            } else if (numList.get(p).equals(0) && !numList.get(p - (p - over)).equals(0)) {
                if (over == 0) {
                    finalList.add(numList.get(over));
                }
                p--;
                over--;
            } else {
                p -= 1;
                over -= 1;
            }
        }
        if (finalList.size() < size) {
            for (int k = finalList.size(); k < size; ++k) {
                finalList.add(0);
            }
        }
        return finalList;
    }

    public static int checkValidity(int size, String[][] matrix) {
        int pass;
        //check for 2048 or full matrix
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (matrix[i][j].equals("2048")) {
                    pass = 1;
                    return pass;
                }
            }
        }
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (matrix[i][j].equals("_")) {
                    pass = 2;
                    return pass;
                }
            }
        }
        return 3;
    }

    public static void printMatrix(Tile tile) {
        int size = tile.getSize();
        String[][] matrix = tile.getMatrix();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Tile generateRandom(Tile tile) {
        int size = tile.getSize();
        String[][] matrix = tile.getMatrix();
        while (true) {
            Random random = new Random();
            int firstIndex = random.nextInt(size);
            int secondIndex = random.nextInt(size);
            if (matrix[firstIndex][secondIndex].equals("_")) {
                matrix[firstIndex][secondIndex] = "2";
                break;
            }
        }
        tile.setMatrix(matrix);
        return tile;
    }
}
