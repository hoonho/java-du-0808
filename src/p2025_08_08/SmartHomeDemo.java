package src.p2025_08_08;

import java.util.ArrayList;
import java.util.List;

// 부모 클래스: 스마트홈 기기
abstract class Device {
    protected String name;

    public Device(String name) {
        this.name = name;
    }

    public abstract void operate();

    public void status() {
        System.out.println(name + "의 상태를 확인합니다.");
    }
}

// 자식 클래스 1: 전등
class Light extends Device {
    public Light(String name) {
        super(name);
    }

    @Override
    public void operate() {
        System.out.println(name + "을(를) 켭니다.");
    }

    public void dim() {
        System.out.println(name + "의 밝기를 조절합니다.");
    }
}

// 자식 클래스 2: 에어컨
class AirConditioner extends Device {
    public AirConditioner(String name) {
        super(name);
    }

    @Override
    public void operate() {
        System.out.println(name + "을(를) 작동시킵니다.");
    }

    public void setTemperature(int temp) {
        System.out.println(name + "의 온도를 " + temp + "도로 설정합니다.");
    }
}

// 스마트홈 클래스
class SmartHome {
    private List<Device> devices = new ArrayList<>();

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void operateAllDevices() {
        for (Device device : devices) {
            device.operate();
            device.status();
            if (device instanceof Light) {
                ((Light) device).dim();
            } else if (device instanceof AirConditioner) {
                ((AirConditioner) device).setTemperature(24);
            }
            System.out.println();
        }
    }
}

// 실행 예제
public class SmartHomeDemo {
    public static void main(String[] args) {
        SmartHome home = new SmartHome();
        home.addDevice(new Light("거실 전등"));
        home.addDevice(new AirConditioner("안방 에어컨"));

        home.operateAllDevices();
    }
}