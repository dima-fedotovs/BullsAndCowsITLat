package sample;

import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class Controller {
    public static final int NUM_COUNT = 4;
    public static final int NUM_BOUND = 10;
    public Spinner<Integer> userNum1;
    public Spinner<Integer> userNum2;
    public Spinner<Integer> userNum3;
    public Spinner<Integer> userNum4;
    public TableView<Turn> turnList;
    private int turnCounter;
    private List<Integer> myNums = new ArrayList<>();

    public void initialize() {
        var random = new Random();
        while (myNums.size() < NUM_COUNT) {
            var num = random.nextInt(NUM_BOUND);
            System.out.print(num);
            if (!myNums.contains(num)) {
                myNums.add(num);
            }
        }
        System.out.println();
        System.out.println(myNums);
    }

    public int countBulls(List<Integer> userNums) {
        int result = 0;
        for (int i = 0; i < NUM_COUNT; i++) {
            int myNum = myNums.get(i);
            int userNum = userNums.get(i);
            if (myNum == userNum) {
                result++;
            }
        }
        return result;
    }

    public int countCows(List<Integer> userNums) {
        int result = 0;
        for (int i = 0; i < NUM_COUNT; i++) {
            int myNum = myNums.get(i);
            for (int j = 0; j < NUM_COUNT; j++) {
                int userNum = userNums.get(j);
                if (myNum == userNum && i != j) {
                    result++;
                }
            }
        }
        return result;
    }

    public void countBullsAndCows(List<Integer> userNums,
                                  BiConsumer<Integer, Integer> resultConsumer) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < NUM_COUNT; i++) {
            int myNum = myNums.get(i);
            for (int j = 0; j < NUM_COUNT; j++) {
                int userNum = userNums.get(j);
                if (myNum == userNum) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }
        resultConsumer.accept(bulls, cows);
    }

    public void doTurn() {
        turnCounter++;

        int n1 = userNum1.getValue();
        int n2 = userNum2.getValue();
        int n3 = userNum3.getValue();
        int n4 = userNum4.getValue();
        var guess = String.format("%d %d %d %d", n1, n2, n3, n4);

        var userNums = List.of(n1, n2, n3, n4);

        var turn = new Turn();
        turn.setUserGuess(guess);
        turn.setTurnNr(turnCounter);
        countBullsAndCows(userNums, (b, c) -> {
            turn.setBulls(b);
            turn.setCows(c);
        });

        turnList.getItems().add(0, turn);
        turnList.sort();
    }


}
