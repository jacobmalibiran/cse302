package turtle;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TurtleInterpreter {

    private DrawableTurtle turtle = new DrawableTurtle();
    private static Scanner scanner;

    // program ::= block "programEnd"
    void program() {
        block();
        String token = scanner.next();
        if (!token.equals("programEnd")) {
            throw new RuntimeException("Got '" + token + "' Not programEnd");
        }
    }

    // block ::= "begin" statementList "end"
    void block() {
        String token = scanner.next();
        if (!token.equals("begin")) {
            throw new RuntimeException("Got '" + token + "' not begin");
        }
        statementList();
    }

    // statementList ::= statement | statement statementList
    void statementList() {
        String token = scanner.next();
        while (!token.equals("end")) {
            statement(token);
            token = scanner.next();
        }
    }

    // statement ::= command
    void statement(String token) {
        command(token);
    }

    // command ::= "forward" distance | "turn" angle
    void command(String token) {
        if (token.equals("forward")) {
            int turtle_distance = distance();
            turtle.forward(turtle_distance);

        } else if (token.equals("turn")) {
            int turtle_angle = angle();
            turtle.turn(turtle_angle);

        } else {
            throw new RuntimeException("Forward or turn expected but got '" + token + "'");
        }
    }

    // distance ::= NUMBER
    int distance() {
        String turtle_distance = scanner.next();
        if (!turtle_distance.matches("[0-9]+")) {
            throw new RuntimeException("Not NUMBER for distance. '" + turtle_distance + "'");
        }
        return Integer.parseInt(turtle_distance);
    }

    // angle ::= NUMBER
    int angle() {
        String angle = scanner.next();
        if (!angle.matches("[0-9]+")) {
            throw new RuntimeException("Not a NUMBER for angle.'" + angle + "' gotten instead");
        }
        return Integer.parseInt(angle);
    }

    public static void main(String[] args) {
        try {
            scanner = new Scanner(new File("testProgramStep1.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        TurtleInterpreter interpreter = new TurtleInterpreter();
        interpreter.program();
    }
}