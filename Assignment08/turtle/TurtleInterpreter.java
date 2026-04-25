package turtle;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class TurtleInterpreter {

    private final HashMap<String, Integer> variables = new HashMap<>();
    private final DrawableTurtle turtle = new DrawableTurtle();
    private Scanner scanner;

    // Constructor for the class to allow for passing a file to the scanner
    TurtleInterpreter(File file) {
        try {
            this.scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // throws an error if the expected file pattern is not followed
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

    // statement ::= loop | command
    void statement(String token) {
        if (token.equals("loop")) {
            loop();
        } else {
            command(token);
        }
    }

    // Helper to loop()
    // returns block tokens for the given loop
    ArrayList<String> getBlockTokens() {
        ArrayList<String> tokens = new ArrayList<>();

        String token = scanner.next();

        if (!token.equals("begin")) {
            throw new RuntimeException("Expected 'begin' but got '" + token + "'");
        }

        int depth = 1;
        tokens.add(token);

        while (depth > 0) {
            if (!scanner.hasNext()) {
                throw new RuntimeException("Missing 'end' for loop block");
            }

            token = scanner.next();
            tokens.add(token);

            if (token.equals("begin")) depth++;
            else if (token.equals("end")) depth--;
        }
        return tokens;
    }

    // Helper to loop()
    // Executes the block of tokens without removing them from the original scanner
    void runBlockTokens(ArrayList<String> blockTokens) {
        Scanner original = scanner;
        scanner = new Scanner(String.join(" ", blockTokens));
        block();

        scanner.close();
        scanner = original;
    }

    // loop ::= “loop” count block
    void loop() {
        int count = count();
        ArrayList<String> blockTokens = getBlockTokens();

        for (int i = 0; i < count; i++) {
            runBlockTokens(blockTokens);
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

        } else if (scanner.hasNext("=")) {
            assignment(token);
        } else {
            throw new RuntimeException("Unknown command or invalid assignment: " + token);
        }
    }

    // assignment :== variable "=" NUMBER
    void assignment(String token) {
        expect("=");
        int value = Integer.parseInt(scanner.next());
        variables.put(token, value);
    }

    int handleNum(String numType) {
        if (numType.matches("[0-9]+")) {
            return Integer.parseInt(numType);
        } else {
            if (!variables.containsKey(numType)) {
                throw new RuntimeException("Undefined variable: " + numType);
            }
            return variables.get(numType);
        }
    }

    // count ::= NUMBER | variable
    int count() {
        String count = scanner.next();
        return handleNum(count);
    }

    // angle ::= NUMBER | variable
    int angle() {
        String angle = scanner.next();
        return handleNum(angle);
    }

    // ::= NUMBER | variable
    int distance() {
        String distance = scanner.next();
        return handleNum(distance);
    }


    static void main() {
        File file = new File("/home/westley/Documents/classes/spring_2026/software_construction/assignments/cse302/Assignment08/turtle/testProgramStep3.txt");
        TurtleInterpreter interpreter = new TurtleInterpreter(file);

        interpreter.program();
        interpreter.turtle.draw();
    }
}