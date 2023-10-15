package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.text.DecimalFormat;
import java.util.*;


public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현

        Integer bonusNumber;
        List<Integer> winningNumbers = new ArrayList<>();
        Integer lotteryCnt;
        List<Integer> matchingNumCnt = new ArrayList<>();
        String budgetInput;
        Integer budget;
        double earningsPerBudgetRound = 0.0;
        boolean exceptionCheck = true;

        //로또 구입금액 입력
        System.out.println("구입금액을 입력해 주세요.");
        budgetInput = Console.readLine();
        System.out.println();
        budget = Integer.parseInt(budgetInput);

        //예외처리(1000으로 나누어 떨어지지 않은 금액 입력 시)
        exceptionCheck = InputBudgetExceptionCheck(budget);
        budgetException(exceptionCheck);

        //구입 금액으로 로또 개수 계산 및 로또 생성
        lotteryCnt = budget / 1000;
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

        //예외처리(1~45의 범위를 벗어난 당첨번호)
        exceptionCheck = InputWinningNumbersExceptionCheck(winningNumbers);
        numbersException(exceptionCheck);

        //보너스번호 입력받기
        System.out.println("보너스 번호를 입력해 주세요.");
        bonusNumber = getBonusNumber();
        /*System.out.println("bonusNumber = " + bonusNumber);*/

        //예외처리(1~45의 범위를 벗어난 보너스번호)
        exceptionCheck = InputBonusNumberExceptionCheck(bonusNumber);
        numbersException(exceptionCheck);

        //로또와 당첨 번호 비교
        getMatchingCnt(winningNumbers, matchingNumCnt, lotteries);
//        System.out.println("matchingNumCnt = " + matchingNumCnt);

        //로또와 보너스 번호 비교 후 2, 3등 산출
        getSecondNThirdRank(bonusNumber, matchingNumCnt, lotteries);

        //당첨통계
        System.out.println("당첨 통계");
        System.out.println("---");

        //당첨금액 ,로 자리수 표기
        DecimalFormat df = new DecimalFormat("###,###");

        //수익률 계산
        earningsPerBudgetRound = getEarningsPerBudget(matchingNumCnt, budget);

        //통계 출력
        printStatistics(matchingNumCnt, df, earningsPerBudgetRound);

    }

    private static void getSecondNThirdRank(Integer bonusNumber, List<Integer> matchingNumCnt, Lotto[] lotteries) {
        for (Integer matchCnt : matchingNumCnt) {
            if (matchCnt == 5) {
                bonusNumMatchingCnt(bonusNumber, matchingNumCnt, lotteries);
            }
        }
    }

    private static double getEarningsPerBudget(List<Integer> matchingNumCnt, Integer budget) {
        Integer earnings = 0;
        int idxTemp = 0;

        for (Rank rank : Rank.values()) {

            earnings += rank.totalPrize(Collections.frequency(matchingNumCnt, Integer.valueOf(idxTemp + 3)));
            idxTemp++;
        }

        return (double)(earnings)/ budget * 100.0;
    }

    private static void printStatistics(List<Integer> matchingNumCnt, DecimalFormat df, double earningsPerBudgetRound) {
        for (Rank rank : Rank.values()) {

            if (rank.id < 3) {

                System.out.println((rank.id + 3) + "개 일치 (" + df.format(rank.prize) + "원) - " + Collections.frequency(matchingNumCnt, (rank.id + 3)) + "개");
            }

            if (rank.id > 3) {

                System.out.println((rank.id + 2) + "개 일치 (" + df.format(rank.prize) + "원) - " + Collections.frequency(matchingNumCnt, (rank.id + 3)) + "개");
            }

            if (rank.id == 3) {

                System.out.println((rank.id + 2) + "개 일치, 보너스 볼 일치 (" + df.format(rank.prize) + "원) - " + Collections.frequency(matchingNumCnt, (rank.id + 3)) + "개");
            }
        }

        System.out.println("총 수익률은 " + String.format("%.1f", earningsPerBudgetRound) + "%입니다.");
    }

    private static void getMatchingCnt(List<Integer> winningNumbers, List<Integer> matchingNumCnt, Lotto[] lotteries) {

        for (Lotto lotto : lotteries) {
//            System.out.println("lotto = " + lotto.getNumbers());
//            System.out.println("winningNumbers = " + winningNumbers);

//            matchingNumsTemp = winningNumbers; // 주소를 참조하는구나 값 자체가 아니라
            List<Integer> matchingNumsTemp = new ArrayList<>(winningNumbers);
//            System.out.println("matchingNumsTemp injected = " + matchingNumsTemp);
            matchingNumsTemp.retainAll(lotto.getNumbers());
//            System.out.println("after matchingNumsTemp = " + matchingNumsTemp);

            //2등 count를 위해 한 자리 만듦(1등을 7로 옮김)
            if (matchingNumsTemp.size() == 6) {
                matchingNumCnt.add(matchingNumsTemp.size() + 1);
            }

            if (matchingNumsTemp.size() != 6) {
                matchingNumCnt.add(matchingNumsTemp.size());
            }
            //matchingNumCnt는 0~5, 7의 값을 갖는다.
        }
    }

    private static void bonusNumMatchingCnt(Integer bonusNumber, List<Integer> matchingNumCnt, Lotto[] lotteries) {

        int listCntTemp = 0;

        for (Lotto lottery : lotteries) {
            if (lottery.getNumbers().contains(bonusNumber) && matchingNumCnt.get(listCntTemp) == 5) {
                matchingNumCnt.set(listCntTemp, 6);
            }
            listCntTemp++;
        }
    }

    private static void generateLottos(Integer lotteryCnt, Lotto[] lottos) {
        for (int i = 0; i < lotteryCnt; i++) {
            //로또에 입력할 1~45 숫자 6개 성성
            List<Integer> numbers = new ArrayList<>(Randoms.pickUniqueNumbersInRange(1, 45, 6));

            //6개의 숫자 오름차순 정렬
            numbers.sort(null);

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
            winningNumbers.sort(Comparator.naturalOrder());
        }
        System.out.println();
    }


    public enum Rank {
        fifth(5_000, 0)    {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        },
        fourth(50_000, 1)   {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        },
        third(1_500_000, 2)    {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        },
        second(30_000_000, 3)   {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        },
        first(2_000_000_000, 4)    {
            @Override
            int totalPrize(int lottoMatchedCnt) {
                return prize * lottoMatchedCnt;
            }
        }
        ;

        protected final int prize;
        protected final int id;

        Rank(int prize, int id) {
            this.prize = prize;
            this.id = id;
        }

        abstract int totalPrize(int lottoMatchedCnt);

    }

    private static void budgetException(boolean priceInputCheck) throws IllegalArgumentException {
        if (!priceInputCheck) {
            throw new IllegalArgumentException("[ERROR] 1,000원으로 나누어 떨어지지 않는 금액입니다.");
        }
    }

    private static void numbersException(boolean numbersInputCheck) throws IllegalArgumentException {
        if (!numbersInputCheck) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private static boolean InputBudgetExceptionCheck(Integer budget) {
        if (budget % 1000 != 0) {
            return false;
        }

        return true;
    }

    private static boolean InputWinningNumbersExceptionCheck(List<Integer> winningNumbers) {
        for (Integer winningNumber : winningNumbers) {
            if (winningNumber < 1 || winningNumber > 45) {
                return false;
            }
        }
        return true;
    }

    private static boolean InputBonusNumberExceptionCheck(Integer bonusNumber) {
        if (bonusNumber < 1 || bonusNumber > 45) {
            return false;
        }

        return true;
    }
}
