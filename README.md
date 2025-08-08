# 15일차

<br/>

## 목차
1. [자판기 시스템](#자판기-시스템)
2. [영화 예매 시스템](#영화-예매-시스템)
3. [우편 배달 시스템](#우편-배달-시스템)
4. [로그인 시스템 01](#로그인-시스템-01-사용자-회원-가입-정보-저장)
5. [로그인 시스템 02](#로그인-시스템-02)

<br/>

### 자판기 시스템
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

### 영화 예매 시스템
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

### 우편 배달 시스템
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

<br/><br/>

### 로그인 시스템 01 (사용자 회원 가입 정보 저장)
```java
package src.example;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/*
 * 로그인 시스템 예제
 * 
 * 사용자 데이터는 "users.txt" 파일에 저장됩니다.
 * 사용자는 아이디와 비밀번호를 입력하여 로그인하거나 회원 가입할 수 있습니다.
 * 비밀번호는 SHA-256 해시 알고리즘을 사용하여 저장됩니다.
 * 사용자 데이터 파일 형식: "아이디,해시된 비밀번호"
 * 예시: "user1,5e884898da28047151d0e56f8dc6292773603d0d4c2f6b8c7a9b1c5f2a3b4c5"
 * 사용자가 로그인할 때 입력한 비밀번호는 해시된 후 저장된 해시와 비교됩니다.
 * 회원 가입 시 아이디 중복 체크를 수행합니다.
 * 시스템은 콘솔에서 사용자 입력을 받으며, 잘못된 입력에 대한 예외 처리를 포함합니다.
 * 사용자가 로그인에 성공하면 "로그인 성공!" 메시지를 출력하고, 실패하면 "로그인 실패!" 메시지를 출력합니다.
 * 회원 가입이 성공하면 "회원 가입이 완료되었습니다." 메시지를 출력합니다.
 * 시스템은 사용자가 종료를 선택할 때까지 계속 실행됩니다.
 * 이 코드는 Java의 기본 입출력, 파일 처리, 예외 처리 및 암호화 기능을 사용하여 구현되었습니다.
 */

public class LoginSystem_01 {
    private static final String USER_DATA_FILE = "users.txt";
    private Scanner scanner;

    public LoginSystem_01() {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        LoginSystem_01 system = new LoginSystem_01();
        system.run();
    }

    public void run() {
        while (true) {
            System.out.println("=== 로그인 시스템 ===");
            System.out.println("1. 로그인");
            System.out.println("2. 회원 가입");
            System.out.println("3. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            if (choice == 1) {
                login();
            } else if (choice == 2) {
                register();
            } else if (choice == 3) {
                System.out.println("시스템을 종료합니다.");
                break;
            } else {
                System.out.println("잘못된 선택입니다.");
            }
        }
    }

    private void login() {
        System.out.print("아이디 입력: ");
        String id = scanner.nextLine();
        System.out.print("비밀번호 입력: ");
        String password = scanner.nextLine();

        if (authenticate(id, password)) {
            System.out.println("로그인 성공!");
        } else {
            System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

    private boolean authenticate(String id, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                String storedId = user[0];
                String storedPassword = user[1];

                if (id.equals(storedId) && storedPassword.equals(hashPassword(password))) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("사용자 데이터를 읽는 도중 오류가 발생했습니다.");
        }
        return false;
    }

    private void register() {
        System.out.print("아이디 입력: ");
        String id = scanner.nextLine();
        System.out.print("비밀번호 입력: ");
        String password = scanner.nextLine();

        if (isUserExists(id)) {
            System.out.println("이미 존재하는 아이디입니다.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
                writer.write(id + "," + hashPassword(password));
                writer.newLine();
                System.out.println("회원 가입이 완료되었습니다.");
            } catch (IOException e) {
                System.out.println("회원 가입 중 오류가 발생했습니다.");
            }
        }
    }

    private boolean isUserExists(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (id.equals(user[0])) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("사용자 데이터를 읽는 도중 오류가 발생했습니다.");
        }
        return false;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("암호화 알고리즘을 찾을 수 없습니다.");
            return null;
        }
    }
}
```

<br/><br/>

### 로그인 시스템 02

<br/>

로그인 시스템 01과 다른점
* 회원 가입 시 비밀번호 8자리 이상 영문, 숫자, 특수문자를 포함해야 한다.
* 비밀번호 복구 기능 (실제 이메일 전송 API를 추가하지 않음)
* 관리자 메뉴 추가

<br/>

```java
package src.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class LoginSystem_02 {
  
  private static final String USER_DATA_FILE = "users.txt"; // 사용자 데이터 파일
  private Scanner sc;
  private String currentSessionUser = null; // 로그인한 사용자의 세션을 관리
  
  public LoginSystem_02() {
    sc = new Scanner(System.in);
  }

  public static void main(String[] args) {
    LoginSystem_02 system = new LoginSystem_02();
    system.run();
  }

  public void run() {
    while (true) {
      // 로그인, 회원 가입, 종료 메뉴
      System.out.println("=== 로그인 시스템 ===");
      System.out.println("1. 로그인");
      System.out.println("2. 회원 가입");
      System.out.println("3. 비밀번호 복구");
      System.out.println("4. 관리자 메뉴");
      System.out.println("5. 종료");
      System.out.print("선택: ");
      int choice = sc.nextInt();
      sc.nextLine(); // 버퍼 지우기

      if (choice == 1) {
        login();
      } else if (choice == 2) {
        register();
      } else if (choice == 3) {
        passwordRecovery();
      } else if (choice == 4) {
        adminMenu();
      } else if (choice == 5) {
        System.out.println("시스템을 종료합니다.");
        break;
      } else {
        System.out.println("잘못된 선택입니다.");
      }
    }
  }

  // 로그인 처리
  private void login() {
    System.out.print("아이디 입력: ");
    String id = sc.nextLine();
    System.out.print("비밀번호 입력: ");
    String password = sc.nextLine();

    if (authenticate(id, password)) {
      currentSessionUser = id; // 로그인 성공 시 세션 관리
      System.out.println("로그인 성공");
    } else {
      System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
    }
  }

  // 비밀번호 복구 기능
  private void passwordRecovery() {
    System.out.print("이메일을 입력해주세요: ");
    String email = sc.nextLine();

    if (recoverPassword(email)) {
      System.out.println("비밀번호 복구 링크가 " + email + "로 전송되었습니다.");
    } else {
      System.out.println("이메일을 찾을 수 없습니다.");
    }
  }

  // 관리자 메뉴 (사용자 관리)
  private void adminMenu() {
    if (currentSessionUser == null || !currentSessionUser.equals("admin")) {
      System.out.println("관리자만 접근 가능합니다.");
      return;
    }

    System.out.println("=== 관리자 메뉴 ===");
    System.out.println("1. 사용자 정보 삭제");
    System.out.println("2. 시용자 정보 수정");
    System.out.print("선택: ");
    int choice = sc.nextInt();
    sc.nextLine(); // 버퍼 비우기

    if (choice == 1) {
      deleteUser();
    } else if (choice == 2) {
      updateUser();
    } else {
      System.out.println("잘못된 선택입니다.");
    }
  }

  // 사용자 인증
  private boolean authenticate(String id, String password) {
    try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
      String line;

      while ((line = reader.readLine()) != null) {
        String[] user = line.split(",");
        String storedId = user[0];
        String storedPassword = user[1];

        if (id.equals(storedId) && storedPassword.equals(hashPassword(password))) {
          return true;
        }
      }
    } catch (IOException e) {
      System.out.println("사용자 데이터를 읽는 도중 오류가 발생했습니다.");
    }
    return false;
  }

  // 사용자 가입
  private void register() {
    System.out.print("아이디 입력: ");
    String id = sc.nextLine();
    System.out.print("비밀번호 입력: ");
    String password = sc.nextLine();

    if (isUserExists(id)) {
      System.out.println("이미 존재하는 아이디입니다.");
    } else {
      if (isPasswordStrong(password)) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
          writer.write(id + "," + hashPassword(password));
          writer.newLine();
          System.out.println("회원 가입이 완료되었습니다.");
        } catch (IOException e) {
          System.out.println("회원 가입 중 오류가 발생했습니다.");
        }
      } else {
        System.out.println("비밀번호가 너무 약합니다. 영문, 숫자, 특수문자를 포함하여 8자 이상으로 설정해주세요.");
      }
    }
  }

  // 비밀번호 강도 검사
  private boolean isPasswordStrong(String password) {
    return password.length() >= 8 &&
            password.matches(".*[A-Za-z].*") &&
            password.matches(".*[0-9].*") &&
            password.matches(".*[!@#$%^&*].*");
  }

  // 사용자 아이디 중복 검사
  private boolean isUserExists(String id) {
    try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
      String line;

      while ((line = reader.readLine()) != null) {
        String[] user = line.split(",");
        if (id.equals(user[0])) {
          return true;
        }
      }
    } catch (IOException e) {
      System.out.println("사용자 데이터를 읽는 도중 오류가 발생했습니다.");
    }
    return false;
  }

  // 비밀번호 해싱
  private String hashPassword(String password) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hashBytes = md.digest(password.getBytes());
      StringBuilder hexString = new StringBuilder();
      for (byte b : hashBytes) {
        hexString.append(String.format("%02x", b));
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      System.out.println("암호화 알고리즘을 찾을 수 없습니다.");
      return null;
    }
  }

  // 비밀번호 복구 (이메일에 복구 링크를 보내는 기능)
  private boolean recoverPassword(String email) {
    // 실제 복구 기능은 이메일 발송 API 필요
    return email.contains("@");
  }

  // 사용자 정보 삭제
  private void deleteUser() {
    System.out.print("삭제할 사용자 아이디 입력: ");
    String id = sc.nextLine();

    File tempFile = new File("temp.txt");
    File inputFile = new File(USER_DATA_FILE);

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
      
        String line;
        boolean userDeleted = false;

        while ((line = reader.readLine()) != null) {
          String[] user = line.split(",");
          if (!user[0].equals(id)) {
            writer.write(line);
            writer.newLine();
          } else {
            userDeleted = true;
          }
        }

        if (userDeleted) {
          tempFile.renameTo(inputFile); // 삭제 후 파일 덮어쓰기
          System.out.println("사용자가 삭제되었습니다.");
        } else {
          System.out.println("해당 사용자가 존재하지 않습니다.");
        }
    } catch (IOException e) {
      System.out.println("사용자 삭제 중 오류가 발생했습니다.");
    }
  }

  // 사용자 정보 수정
  private void updateUser() {
    System.out.print("수정할 사용자 아이디 입력: ");
    String id = sc.nextLine();

    File tempFile = new File("temp.txt");
    File inputFile = new File(USER_DATA_FILE);

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        String line;
        boolean userUpdated = false;
        while ((line = reader.readLine()) != null) {
            String[] user = line.split(",");
            if (user[0].equals(id)) {
                System.out.print("새로운 비밀번호 입력: ");
                String newPassword = sc.nextLine();
                if (isPasswordStrong(newPassword)) {
                    writer.write(user[0] + "," + hashPassword(newPassword));
                    writer.newLine();
                    userUpdated = true;
                } else {
                    System.out.println("비밀번호가 너무 약합니다.");
                }
            } else {
                writer.write(line);
                writer.newLine();
            }
        }

        if (userUpdated) {
            tempFile.renameTo(inputFile); // 수정 후 파일 덮어쓰기
            System.out.println("사용자 정보가 수정되었습니다.");
        } else {
            System.out.println("해당 사용자가 존재하지 않습니다.");
        }
    } catch (IOException e) {
      System.out.println("사용자 수정 중 오류가 발생했습니다.");
    }
  }
}
```

<br/><br/>

[맨 위로 가기](#15일차)