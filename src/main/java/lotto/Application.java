package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        String winningNum; // int, Integer, String
        String bonusNum;
        Integer bonusNumber;
        List<Integer> winningNumbers = new ArrayList<>();

        //당첨번호 입력받기
        getWinnigNumbers(winningNumbers);
        System.out.println("winningNumbers is " + winningNumbers);


        //보너스번호 입력받기
        bonusNumber = getBonusNumber();
        System.out.println("bonusNumber = " + bonusNumber);
    }

    private static Integer getBonusNumber() {
        String bonusNum;
        bonusNum = Console.readLine();
        return Integer.parseInt(bonusNum);
    }

    private static void getWinnigNumbers(List<Integer> winningNumbers) {
        String winningNum;
        winningNum = Console.readLine();
        for (int i = 0; i < 6; i++) {

            winningNumbers.add(Integer.parseInt(winningNum.split(",")[i]));
        }
    }
}
