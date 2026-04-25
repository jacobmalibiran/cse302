package turtle;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.junit.Test;

public class TurtleInterpreterTester {

    private File makeFile(String contents) throws IOException {
        File file = File.createTempFile("turtle-test", ".txt");
        file.deleteOnExit();

        FileWriter writer = new FileWriter(file);
        writer.write(contents);
        writer.close();

        return file;
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, Integer> getVariables(TurtleInterpreter interpreter) throws Exception {
        Field field = TurtleInterpreter.class.getDeclaredField("variables");
        field.setAccessible(true);
        return (HashMap<String, Integer>) field.get(interpreter);
    }

    private TurtleInterpreter makeInterpreter(String program) throws IOException {
        return new TurtleInterpreter(makeFile(program));
    }

    // -------------------------
    // Valid full programs
    // -------------------------

    @Test
    public void emptyProgram() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin end programEnd"
        );

        interpreter.program();
    }

    @Test
    public void forwardProgram() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin forward 10 end programEnd"
        );

        interpreter.program();
    }

    @Test
    public void turnProgram() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin turn 90 end programEnd"
        );

        interpreter.program();
    }

    @Test
    public void forwardAndTurn() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin forward 10 turn 90 forward 5 end programEnd"
        );

        interpreter.program();
    }

    @Test
    public void assignmentProgram() throws Exception {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin x = 25 end programEnd"
        );

        interpreter.program();

        HashMap<String, Integer> variables = getVariables(interpreter);
        assertEquals(Integer.valueOf(25), variables.get("x"));
    }

    @Test
    public void reassignmentProgram() throws Exception {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin x = 10 x = 20 end programEnd"
        );

        interpreter.program();

        HashMap<String, Integer> variables = getVariables(interpreter);
        assertEquals(Integer.valueOf(20), variables.get("x"));
    }

    @Test
    public void variableDistance() throws Exception {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin d = 15 forward d end programEnd"
        );

        interpreter.program();

        HashMap<String, Integer> variables = getVariables(interpreter);
        assertEquals(Integer.valueOf(15), variables.get("d"));
    }

    @Test
    public void variableAngle() throws Exception {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin a = 90 turn a end programEnd"
        );

        interpreter.program();

        HashMap<String, Integer> variables = getVariables(interpreter);
        assertEquals(Integer.valueOf(90), variables.get("a"));
    }

    @Test
    public void variableLoopCount() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin n = 3 loop n begin forward 10 end end programEnd"
        );

        interpreter.program();
    }

    @Test
    public void zeroLoop() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin loop 0 begin forward 10 turn 90 end end programEnd"
        );

        interpreter.program();
    }

    @Test
    public void oneLoop() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin loop 1 begin forward 10 end end programEnd"
        );

        interpreter.program();
    }

    @Test
    public void manyLoops() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin loop 5 begin forward 10 turn 90 end end programEnd"
        );

        interpreter.program();
    }

    @Test
    public void loopUsesVariable() throws Exception {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin d = 12 loop 3 begin forward d end end programEnd"
        );

        interpreter.program();

        HashMap<String, Integer> variables = getVariables(interpreter);
        assertEquals(Integer.valueOf(12), variables.get("d"));
    }

    @Test
    public void loopCanAssign() throws Exception {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin loop 3 begin x = 7 end end programEnd"
        );

        interpreter.program();

        HashMap<String, Integer> variables = getVariables(interpreter);
        assertEquals(Integer.valueOf(7), variables.get("x"));
    }

    @Test
    public void loopCanReassign() throws Exception {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin x = 1 loop 3 begin x = 9 end end programEnd"
        );

        interpreter.program();

        HashMap<String, Integer> variables = getVariables(interpreter);
        assertEquals(Integer.valueOf(9), variables.get("x"));
    }

    @Test
    public void nestedLoops() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin loop 2 begin loop 3 begin forward 5 end end end programEnd"
        );

        interpreter.program();
    }

    @Test
    public void mixedProgram() throws Exception {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin " +
                        "distance = 20 " +
                        "angle = 90 " +
                        "forward distance " +
                        "turn angle " +
                        "loop 4 begin forward distance turn angle end " +
                        "end programEnd"
        );

        interpreter.program();

        HashMap<String, Integer> variables = getVariables(interpreter);
        assertEquals(Integer.valueOf(20), variables.get("distance"));
        assertEquals(Integer.valueOf(90), variables.get("angle"));
    }

    // -------------------------
    // Individual helper methods
    // -------------------------

    @Test
    public void expectMatches() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin"
        );

        interpreter.expect("begin");
    }

    @Test
    public void handleLiteralNumber() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin end programEnd"
        );

        assertEquals(123, interpreter.handleNum("123"));
    }

    @Test
    public void countLiteral() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "42"
        );

        assertEquals(42, interpreter.count());
    }

    @Test
    public void angleLiteral() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "180"
        );

        assertEquals(180, interpreter.angle());
    }

    @Test
    public void distanceLiteral() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "30"
        );

        assertEquals(30, interpreter.distance());
    }

    @Test
    public void getBlockTokensSimple() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin forward 10 end"
        );

        assertEquals(
                "[begin, forward, 10, end]",
                interpreter.getBlockTokens().toString()
        );
    }

    @Test
    public void getBlockTokensWithLoop() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin loop 2 begin forward 10 end end"
        );

        assertEquals(
                "[begin, loop, 2, begin, forward, 10, end, end]",
                interpreter.getBlockTokens().toString()
        );
    }

    // -------------------------
    // Invalid syntax
    // -------------------------

    @Test(expected = RuntimeException.class)
    public void missingBegin() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "forward 10 end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void missingProgramEnd() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin forward 10 end"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void wrongProgramEnd() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin forward 10 end stop"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void unknownCommand() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin jump 10 end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void badAssignment() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin x 10 end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void assignmentMissingValue() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin x = end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void assignmentNonNumber() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin x = hello end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void forwardMissingDistance() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin forward end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void turnMissingAngle() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin turn end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void undefinedForwardVariable() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin forward x end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void undefinedTurnVariable() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin turn angle end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void undefinedLoopVariable() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin loop n begin forward 10 end end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void loopMissingBegin() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin loop 3 forward 10 end programEnd"
        );

        interpreter.program();
    }

    @Test(expected = RuntimeException.class)
    public void expectMismatch() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "hello"
        );

        interpreter.expect("begin");
    }

    @Test(expected = RuntimeException.class)
    public void handleUndefinedVariable() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "begin end programEnd"
        );

        interpreter.handleNum("missing");
    }

    @Test(expected = RuntimeException.class)
    public void getBlockTokensMissingBegin() throws IOException {
        TurtleInterpreter interpreter = makeInterpreter(
                "forward 10 end"
        );

        interpreter.getBlockTokens();
    }
}