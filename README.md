# 15일차

<br/>

## 자판기 시스템
```java
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
```

<br/><br/>

## 영화 예매 시스템
```java
package src.example;

/*
 * 영화 예매 시스템
 * 
 * 1. 좌석은 5행 5열의 2차원 배열로 구성: 0 = 빈 좌석, 1 = 예약된 좌석
 * 2. 랜덤으로 1좌석 예약 시도
 * 3. 예약 성공 시 좌석 상태 출력, 실패하면 "이미 예약된 좌석입니다." 출력
 * 4. 모든 좌석이 예약되면 "매진" 출력 후 종료
 * 5. 무한 루프 돌며 예약 시도 (성공할 때까지 또는 매진될 때까지)
 */

public class MovieReservation {
    private int[][] seats = new int[5][5]; // 0: 빈 좌석, 1: 예약된 좌석

    public void reserveSeat() {
        while (true) {
            int row = (int) (Math.random() * 5);
            int col = (int) (Math.random() * 5);

            if (seats[row][col] == 0) { // 빈 좌석인 경우
                seats[row][col] = 1; // 좌석 예약
                System.out.println("좌석 예약 성공: " + (row + 1) + "행 " + (col + 1) + "열");
                printSeats();
                break;
            } else {
                System.out.println("이미 예약된 좌석입니다: " + (row + 1) + "행 " + (col + 1) + "열");
            }

            if (isFull()) {
                System.out.println("매진되었습니다.");
                break;
            }
        }
    }

    private boolean isFull() {
        for (int[] row : seats) {
            for (int seat : row) {
                if (seat == 0) return false; // 빈 좌석이 있으면 false
            }
        }
        return true; // 모든 좌석이 예약되면 true
    }

    private void printSeats() {
        System.out.println("현재 좌석 상태:");
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
    MovieReservation reservation = new MovieReservation();
    
    while (!reservation.isFull()) {
        reservation.reserveSeat();

        try {
            Thread.sleep(500); // 0.5초 대기 (시뮬레이션용)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    System.out.println("모든 좌석 예약 완료.");
  }
}
```

<br/><br/>

## 우편 배달 시스템
```java
package src.example;

import java.util.Scanner;

/*
 * 우편 배달 시스템
 * 
 * 1. 우편물은 1차원 배열에 저장: [우편번호]
 * 2. 랜덤으로 우편번호 5개 생성 (10000~99999)
 * 3. 사용자가 배송할 구역(우편번호 앞 두 자리)을 입력하면:
 *   - 해당 구역 우편만 "배송 시작" 출력
 *   - 나머지는 "다른 지역"으로 출력
 * 4. 앞 두 자리 비교는 문자열 또는 정수 연산으로 처리 가능
 */

public class PostDeliverySystem {
    private int[] postalCodes = new int[5]; // 우편번호 배열

    public PostDeliverySystem() {
        generateRandomPostalCodes();
    }

    private void generateRandomPostalCodes() {
        for (int i = 0; i < postalCodes.length; i++) {
            postalCodes[i] = (int) (Math.random() * 90000) + 10000;
        }
    }

    // 2. 배송 시작 로직 (입력받은 앞 두 자리와 비교)
    public void startDelivery(String areaCode) {
        System.out.println("\n📦 배송 결과 (" + areaCode + "번 지역):");
        for (int i = 0; i < postalCodes.length; i++) {
            int code = postalCodes[i];
            String codeStr = String.valueOf(code);

            if (codeStr.startsWith(areaCode)) {
                System.out.println((i + 1) + ". 우편번호: " + code + " → 배송 시작");
            } else {
                System.out.println((i + 1) + ". 우편번호: " + code + " → 다른 지역");
            }
        }
    }

    // 3. 메인 메서드: 사용자 입력 받고 반복 처리
    public static void main(String[] args) {
        PostDeliverySystem system = new PostDeliverySystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== 우편 배달 시스템 ===");
        System.out.println("현재 5개의 우편물이 준비되어 있습니다.");

        while (true) {
            System.out.print("\n배송할 구역 앞 두 자리(예: 12) 또는 'exit' 입력: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("시스템 종료. 감사합니다.");
                break;
            }

            // 입력 유효성 검사: 두 자리 숫자인지 확인
            if (!input.matches("\\d{2}")) {
                System.out.println("⚠️ 잘못된 입력입니다. 숫자 두 자리(예: 12)를 입력해주세요.");
                continue;
            }

            // 배송 시작
            system.startDelivery(input);
        }

        scanner.close();
    }
}
```