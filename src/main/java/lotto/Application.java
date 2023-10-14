package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        String winningNum; // int, Integer, String
        String bonusNum;
        Integer bonusNumber;
        List<Integer> winningNumbers = new ArrayList<>();
        Integer lotteryCnt = 0;

        //당첨번호 입력받기
        getWinnigNumbers(winningNumbers);
        /*System.out.println("winningNumbers is " + winningNumbers);//전체 출력 시 toString() 메서드 필요 없음
        for (Integer winningNumber : winningNumbers) {
            System.out.println("winningNumber = " + winningNumber);
        }*/

        //보너스번호 입력받기
        bonusNumber = getBonusNumber();
        /*System.out.println("bonusNumber = " + bonusNumber);*/

//        lotteryCnt = 8;
        //로또 생성
        Lotto[] lottos = new Lotto[lotteryCnt];
        generateLottos(lotteryCnt, lottos);

        for (Lotto lotto : lottos) {
            System.out.println("lotto = " + lotto.getNumbers());
        }
    }

    private static void generateLottos(Integer lotteryCnt, Lotto[] lottos) {
        for (int i = 0; i < lotteryCnt; i++) {
            //로또에 입력할 1~45 숫자 6개 성성
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);

            //6개의 숫자 오름차순 정렬
            numbers.sort(Comparator.naturalOrder());

            //오름차순으로 정렬된 숫자로 Lotto 객체에 입력
            lottos[i] = new Lotto(numbers);
        }
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
