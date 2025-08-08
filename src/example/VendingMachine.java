package src.example;

import java.util.Scanner;

/*
 * 1. 음료 재고는 2차원 배열로 관리한다: [음료명][재고]
 * 2. 사용자가 음료를 선택하면:
 *   - 재고가 있으면 음료를 제공하고 재고를 1 감소시킨다.
 *   - 재고가 없으면 "재고 없음" 메시지를 출력한다.
 * 3. 사용자가 3초 이상 아무 것도 고르지 않으면 "대기 종료" 출력
 * 4. 음료명은 "콜라", "사이다", "커피"로 고정
 * 5. 사용자 입력은 sc로 받아도 되고, 랜덤 선택으로 해도 된다.
 */

public class VendingMachine {

  static String[][] drinks = {
    {"콜라", "10"},
    {"사이다", "5"},
    {"커피", "10"}
  };

  public static void main(String[] args) throws InterruptedException {
    Scanner sc = new Scanner(System.in);
    long startTime = System.currentTimeMillis();
    boolean waiting = true;

    while (true) {
      System.out.println("음료를 선택하세요 (1: 콜라, 2: 사이다, 3: 커피, 0: 종료). 3초 안에 입력하세요.");
      long currentTime = System.currentTimeMillis();

      if (currentTime - startTime > 3000) {
        System.out.println("대기 종료");
        break;
      }

      if (sc.hasNextInt()) {
        int choice = sc.nextInt();

        if (choice == 0) {
          System.out.println("시스템 종료");
          break;
        }

        if (choice >= 1 && choice <= 3) {
          String drinkName = drinks[choice - 1][0];
          int stock = Integer.parseInt(drinks[choice - 1][1]);

          if (stock > 0) {
            stock--;
            drinks[choice - 1][1] = String.valueOf(stock);
            System.out.println(drinkName + " 제공. 남은 재고: " + stock);
          } else {
            System.out.println(drinkName + " 재고 없음.");
          }

          startTime = System.currentTimeMillis(); // 입력 성공 시에만 시간 초기화
        } else {
          System.out.println("잘못된 선택입니다. 다시 선택하세요.");
        }
      }

      Thread.sleep(100); // CPU 과다 사용 방지
    }

    sc.close();
  }
}