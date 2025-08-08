package src.p2025_08_08;

import java.util.ArrayList;
import java.util.List;

// 부모 클래스
abstract class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void makeSound();

    public void eat() {
        System.out.println(name + "이(가) 먹이를 먹습니다.");
    }
}

// 자식 클래스 1
class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + "이(가) 야옹~ 하고 웁니다.");
    }

    public void climb() {
        System.out.println(name + "이(가) 나무를 탑니다.");
    }
}

// 자식 클래스 2
class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + "이(가) 멍멍! 하고 웁니다.");
    }

    public void fetch() {
        System.out.println(name + "이(가) 공을 물어옵니다.");
    }
}

// 동물원 클래스
class Zoo {
    private List<Animal> animals = new ArrayList<>();

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void showAllAnimals() {
        for (Animal animal : animals) {
            animal.makeSound();
            animal.eat();
            // 타입에 따라 추가 행동
            if (animal instanceof Cat) {
                ((Cat) animal).climb();
            } else if (animal instanceof Dog) {
                ((Dog) animal).fetch();
            }
            System.out.println();
        }
    }
}

// 실행 예제
public class ZooDemo {
    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        zoo.addAnimal(new Cat("나비"));
        zoo.addAnimal(new Dog("초코"));

        zoo.showAllAnimals();
    }
}