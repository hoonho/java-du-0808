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
