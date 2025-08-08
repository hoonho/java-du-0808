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

    // 1. 랜덤 우편번호 생성 (10000~99999)
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

