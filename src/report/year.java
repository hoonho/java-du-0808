package src.report;

public class year {
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("연도를 입력하세요: ");
        int year = sc.nextInt();

        if (isLeapYear(year)) {
            System.out.println(year + "년은 윤년입니다.");
        } else {
            System.out.println(year + "년은 평년입니다.");
        }
    }

    public static boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            }
            return true;
        }
        return false;
    }
}
