package com.richard.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DraftTest {

    String matrix[][];

    private  String [][] createMatrix() {
        String[][] matrix = new String [3][3];

        matrix[0][2] = "7";
        matrix[0][1] = "4";
        matrix[0][0] = "1";

        matrix[1][2] = "8";
        matrix[1][1] = "5";
        matrix[1][0] = "2";

        matrix[2][2] = "9";
        matrix[2][1] = "6";
        matrix[2][0] = "3";

        descriptionGame(matrix);

        return matrix;
    }

    private void descriptionGame(String[][] matrix) {
        StringBuilder description = new StringBuilder()
            .append("\n" + matrix[0][2]  + "\t" + "|" + "\t" + matrix[1][2]  + "\t" + "|" + "\t" + matrix[2][2])
            .append("\n" + "----|-------|----")
            .append("\n" + matrix[0][1]  + "\t" + "|" + "\t" + matrix[1][1]  + "\t" + "|" + "\t" + matrix[2][1])
            .append("\n" + "----|-------|----")
            .append("\n" + matrix[0][0]  + "\t" + "|" + "\t" + matrix[1][0]  + "\t" + "|" + "\t" + matrix[2][0]);
        System.out.println(description.toString());
    }

    @Before
    public void setup() {
        this.matrix = createMatrix();
    }

    @Test
    public void firstGame() {
        this.matrix[0][1] = "X";
        descriptionGame(this.matrix);
    }


}
