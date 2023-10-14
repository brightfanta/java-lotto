package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현

        Integer bonusNumber;
        List<Integer> winningNumbers = new ArrayList<>();
        Integer lotteryCnt;
        List<Integer> matchingNumCnt = new ArrayList<>();
        String budgetInput;
        Integer budget;

        //로또 구입금액 입력
        System.out.println("구입금액을 입력해 주세요.");
        budgetInput = Console.readLine();
        System.out.println();
        budget = Integer.parseInt(budgetInput);
        lotteryCnt = budget / 1000;

        //로또 생성
        System.out.println(lotteryCnt + "개를 구매했습니다.");
        Lotto[] lotteries = new Lotto[lotteryCnt];
        generateLottos(lotteryCnt, lotteries);

        //당첨번호 입력받기
        System.out.println("당첨 번호를 입력해 주세요.");
        getWinningNumbers(winningNumbers);
        /*System.out.println("winningNumbers is " + winningNumbers);//전체 출력 시 toString() 메서드 필요 없음
        for (Integer winningNumber : winningNumbers) {
            System.out.println("winningNumber = " + winningNumber);
        }*/

        //보너스번호 입력받기
        System.out.println("보너스 번호를 입력해 주세요.");
        bonusNumber = getBonusNumber();
        /*System.out.println("bonusNumber = " + bonusNumber);*/


        //로또와 당첨 번호 비교
        getMatchingCnt(winningNumbers, matchingNumCnt, lotteries);
        System.out.println("matchingNumCnt = " + matchingNumCnt);

        //로또와 보너스 번호 비교 후 2, 3등 산출
        for (Integer matchCnt : matchingNumCnt) {
            if (matchCnt == 5) {

                bonusNumMatchingCnt(bonusNumber, matchingNumCnt, lotteries);
            }
        }

        //당첨통계
        System.out.println("당첨 통계");
        System.out.println("---");

        DecimalFormat df = new DecimalFormat("###,###");

        System.out.println("3개 일치 (" + df.format(rank.fifth.prize) +"원) - " + Collections.frequency(matchingNumCnt, 3) + "개");
        System.out.println("4개 일치 (" + df.format(rank.fourth.prize) +"원) - " + Collections.frequency(matchingNumCnt, 4) + "개");
        System.out.println("5개 일치 (" + df.format(rank.third.prize) +"원) - " + Collections.frequency(matchingNumCnt, 5) + "개");
        System.out.println("5개 일치, 보너스볼 일치 (" + df.format(rank.second.prize) +"원) - " + Collections.frequency(matchingNumCnt, 7) + "개");
        System.out.println("5개 일치 (" + df.format(rank.first.prize) +"원) - " + Collections.frequency(matchingNumCnt, 6) + "개");





    }

    private static void getMatchingCnt(List<Integer> winningNumbers, List<Integer> matchingNumCnt, Lotto[] lotteries) {


        for (Lotto lotto : lotteries) {
            System.out.println("lotto = " + lotto.getNumbers());
//            System.out.println("winningNumbers = " + winningNumbers);

//            matchingNumsTemp = winningNumbers; // 주소를 참조하는구나 값 자체가 아니라
            List<Integer> matchingNumsTemp = new ArrayList<>(winningNumbers);
//            System.out.println("matchingNumsTemp injected = " + matchingNumsTemp);
            matchingNumsTemp.retainAll(lotto.getNumbers());
//            System.out.println("after matchingNumsTemp = " + matchingNumsTemp);
            matchingNumCnt.add(matchingNumsTemp.size());

        }
        System.out.println("matchingNumCnt = " + matchingNumCnt);
    }

    private static void bonusNumMatchingCnt(Integer bonusNumber, List<Integer> matchingNumCnt, Lotto[] lotteries) {
        int listCntTemp = 0;
        for (Lotto lottery : lotteries) {

            if (lottery.getNumbers().contains(bonusNumber)) {
                matchingNumCnt.set(listCntTemp, 7);
            }
            listCntTemp++;
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
            System.out.println(lottos[i].getNumbers());
        }
        System.out.println();
    }

    private static Integer getBonusNumber() {
        String bonusNum;
        bonusNum = Console.readLine();
        System.out.println();
        return Integer.parseInt(bonusNum);
    }

    private static void getWinningNumbers(List<Integer> winningNumbers) {
        String winningNum;
        winningNum = Console.readLine();
        for (int i = 0; i < 6; i++) {

            winningNumbers.add(Integer.parseInt(winningNum.split(",")[i]));
        }
        System.out.println();
    }


    public enum rank{
        fifth(5_000)    {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        },
        fourth(50_000)   {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        },
        third(1_500_000)    {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        },
        second(30_000_000)   {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        },
        first(2_000_000_000)    {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        }
        ;

        protected final int prize;

        rank(int prize) {
            this.prize = prize;
        }

        abstract int totalPrize(int lottoMatchedCnt);

    }

}
