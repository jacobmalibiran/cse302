/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

import java.util.Random;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {

        /**
         * Determine inside angles of a regular polygon.
         *
         * There is a simple formula for calculating the inside angles of a polygon;
         * you should derive it and use it here.
         *
         * @param sides number of sides, where sides must be > 2
         * @return angle in degrees, where 0 <= angle < 360
         */
        final int degrees = 90;
        for (int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(degrees);
        }

    }



    /**
     * Determine inside angles of a regular polygon.
     *
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     *
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        final double MAX_SIDES_WITHOUT_POLYGON = 2;
        final double TRIANGLES_IN_POLYGON = sides - MAX_SIDES_WITHOUT_POLYGON;
        final double DEGREES_IN_TRIANGLE = 180.0;

        assertTrue(sides > MAX_SIDES_WITHOUT_POLYGON);

        final double DEGREES_IN_POLYGON = DEGREES_IN_TRIANGLE  * TRIANGLES_IN_POLYGON;

        return DEGREES_IN_POLYGON / (double)sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        final double DEGREES_IN_TRIANGLE = 180.0;
        final double ANGLE_IN_TRIANGLE = 60.0;
        final double DEGREES_IN_CIRCLE = 360.0;

        assertTrue(angle < DEGREES_IN_TRIANGLE);
        assertTrue(angle >= ANGLE_IN_TRIANGLE);

        final double EXTERIOR_ANGLE = DEGREES_IN_TRIANGLE - angle;
        return Math.toIntExact(Math.round(DEGREES_IN_CIRCLE / EXTERIOR_ANGLE));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        final double MAX_SIDES_WITHOUT_POLYGON = 2;

        assertTrue(sides > MAX_SIDES_WITHOUT_POLYGON);
        assertTrue(sideLength > 0);

        final double DEGREES_PER_ANGLE = 180.0 - calculateRegularPolygonAngle(sides);

        for (int i = 0; i < sides; i++) {
            turtle.turn(DEGREES_PER_ANGLE);
            turtle.forward(sideLength);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX currentY current location
     * @param targetX targetY target point
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360.
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        //throw new RuntimeException("implement me!");
        int x_value = targetX - currentX;
        int y_value = targetY - currentY;

        double atan_angle = Math.toDegrees(Math.atan2(x_value, y_value));

        if (atan_angle < 0) {
            atan_angle += 360;
        }

        double heading = atan_angle - currentHeading;

        if (heading < 0) {
            heading += 360;
        }

        return heading;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size (# of points) - 1.
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        //throw new RuntimeException("implement me!");
        List<Double> headings = new ArrayList<>();

        double current = 0.0;

        for (int i = 0; i < xCoords.size() - 1; i++) {
            double turn = calculateHeadingToPoint(
                    current,
                    xCoords.get(i), yCoords.get(i),
                    xCoords.get(i + 1), yCoords.get(i + 1)
            );

            headings.add(turn);

            current = (current + turn) % 360;
        }

        return headings;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * We'll be peer-voting on the different images, and the highest-rated one will win a prize. 
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {

        /**
         * Main method.
         *
         * This is the method that runs when you run "java TurtleSoup".
         */

        PenColor[] colors = {PenColor.RED, PenColor.PINK, PenColor.ORANGE, PenColor.YELLOW, PenColor.GREEN, PenColor.CYAN, PenColor.BLUE, PenColor.MAGENTA};
        for (int i = 1; i < 99999; i++) {
            Random rand = new Random();
            turtle.color(   colors[rand.nextInt(colors.length)]    );
            drawRegularPolygon(turtle, 15, rand.nextInt(1, 100));
            turtle.turn(144);
        }
    }



    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);
        //drawSquare(turtle, 200);
        drawPersonalArt(turtle);


        // draw the window
        turtle.draw();
    }

}
