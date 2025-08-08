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
