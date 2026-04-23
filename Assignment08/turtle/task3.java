package turtle;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class TurtleInterpreter {

    private HashMap<String, Integer> map = new HashMap<>();
    private ArrayList<String> action = new ArrayList<String>();
    private DrawableTurtle turtle = new DrawableTurtle();
    private static Scanner scanner;

    void expect(String expectedToken) {
        String actual = scanner.next();
        if (!actual.equals(expectedToken)) {
            throw new RuntimeException("Expected '" + expectedToken + "' but got '" + actual + "'");
        }
    }

    // program ::= block “programEnd”
    void program() {
        block();
        expect("programEnd");
    }

    // block ::= “begin” statementList “end”
    void block() {
        expect("begin");
        String token = statementList();

        if (!token.equals("end")) {
            throw new RuntimeException("Expected 'end' but got '" + token + "'");
        }

    }

    // statementList ::= statement | statement statementList
    String statementList() {
        String token = scanner.next();
        while (!token.equals("end")) {
            statement(token);
            token = scanner.next();
        }
        return token;
    }

    // statement ::= loop | command
    void statement(String token) {
        if (token.equals("loop")) {
            loop();
        } else {
            command(token);
        }
    }

    // loop ::= “loop” count block
    void loop() {
        int count = count();
        for (int i = 0; i < count; i++) {
            block();
        }
    }

    // command :== "forward" distance | "turn" angle | assignment
    void command(String token) {
        if (token.equals("forward")) {
            int distance = distance();
            turtle.forward(distance);

        } else if (token.equals("turn")) {
            int angle = angle();
            turtle.turn(angle);

        } else {
            assignment(token);
        }
    }

    // assignment :== variable "=" NUMBER
    void assignment(String token) {
        map.put(token, Integer.parseInt(scanner.next()));
    }

    // count ::= NUMBER | variable
    int count() {
        String count = scanner.next();

        if (count.matches("[0-9]+")) {
            return Integer.parseInt(count);
        } else {
            return map.get(count);
        }
    }

    // angle ::= NUMBER | variable
    int angle() {
        String angle = scanner.next();

        if (angle.matches("[0-9]+")) {
            return Integer.parseInt(angle);
        } else {
            return map.get(angle);
        }
    }

    // ::= NUMBER | variable
    int distance() {
        String distance = scanner.next();

        if (distance.matches("[0-9]+")) {
            return Integer.parseInt(distance);
        } else {
            return map.get(distance);
        }
    }


    public static void main(String[] args) {
        try {
            scanner = new Scanner(new File("testProgramStep3.txt"));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found");
        }
        TurtleInterpreter interpreter = new TurtleInterpreter();

        interpreter.program();
    }



}