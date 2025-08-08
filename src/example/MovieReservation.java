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
