package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void add() {
        Main main = new Main();
        int expected = 13;
        int actual = main.add(1, 2);
        assertEquals(expected, actual);

    }
    void b_add(){
        Main m =new Main();
        int expected =20;
        int actual =m.add(1,3);
        assertEquals(expected,actual);
    }
}