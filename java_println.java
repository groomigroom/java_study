class SecondConsole {
    public static void main(String[] args) {
        System.out.println("구름구름");
        System.out.println("구름구름세상이에요");
    }
}

class ThirdConsole {
    public static void main(String[] args) {
        int age = 9;
        double number = 3.14;
        String name = "김구름";
        System.out.println("age: " +age+" number: "+number+" name: "+name);
    }
}

/*
Java 파일 컴파일

(파일이 있는 폴더에 그대로 있을 때)

예: 파일이 Example.java라면

javac Example.java


성공하면 .class 파일이 생성됨:

SecondConsole.class

ThirdConsole.class

✅ 5. 클래스 실행

main()이 들어 있는 클래스를 클래스명으로 실행해야 함.

▶ SecondConsole 실행
java SecondConsole

▶ ThirdConsole 실행
java ThirdConsole
*/
