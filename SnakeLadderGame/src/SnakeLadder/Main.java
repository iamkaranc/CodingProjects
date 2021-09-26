package SnakeLadder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter number of Snakes: ");
        List<Snake> snakeList = new ArrayList<>();
        List<Ladder> ladderList = new ArrayList<>();
        List<Player> playerList = new ArrayList<>();
        int numberOfSnakes = sc.nextInt();
        for(int i = 0; i < numberOfSnakes; ++i) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            Snake snake = new Snake();
            snake.setStart(a);
            snake.setEnd(b);
            snakeList.add(snake);
        }
        System.out.print("Enter number of Ladders: ");
        int numberOfLadders = sc.nextInt();
        for(int i = 0; i < numberOfLadders; ++i) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            Ladder ladder = new Ladder();
            ladder.setStart(a);
            ladder.setEnd(b);
            ladderList.add(ladder);
        }
        System.out.print("Enter number of Players: ");
        int numberOfPlayers = sc.nextInt();
        for(int i = 0; i < numberOfPlayers; ++i) {
            String name = sc.next();
            Player player = new Player();
            player.setName(name);
            playerList.add(player);
        }
        playGame(snakeList, ladderList, playerList);
    }
    public static void playGame(List<Snake> snakeList, List<Ladder> ladderList, List<Player> playerList) {
        while(true) {
            int dice = 0;
            for (Player player : playerList) {
                Random random = new Random();
                dice = random.nextInt(6) + 1; //Karan current value = 0 + 5 = 5 -> temp Hritam current = 0
                int temp = player.getCurrent() + dice;
                boolean gotLadder = false;
                if(temp < 100) {
                    for(Ladder ladder : ladderList) {
                        if(temp == ladder.getStart()) {
                            temp = ladder.getEnd();
                            gotLadder = true;
                            break;
                        }
                    }
                    if(!gotLadder) {
                        for (Snake snake : snakeList) {
                            if(temp == snake.getStart()) {
                                temp = snake.getEnd();
                                break;
                            }
                        }
                    }
                    if(temp == 100) {
                        //System.out.println(player.getName() + " rolled a " + dice + " and moved from " + player.getCurrent() + " to " + temp + "\n");
                        System.out.println(player.getName() + " rolled a " + dice + " and wins the game! Congratulations!!! \n");
                        return;
                    }
                    System.out.println(player.getName() + " rolled a " + dice + " and moved from " + player.getCurrent() + " to " + temp + "\n");
                    if(temp < 100)
                        player.setCurrent(temp);
                }
            }
        }
    }
}
