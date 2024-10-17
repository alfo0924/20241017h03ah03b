package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @AfterAll
    static void tearDownAfterClass(){
        System.out.println("@AfterAll tearDownAfterClass");
    }

    @Test
    void add() {
        Main main = new Main();
        int expected = 13;
        int actual = main.add(1, 2);
        assertEquals(expected, actual);

    }
    @Test
    void b_add(){
        Main m =new Main();
        int expected = 19;
        int actual = m.add(1,3);
        assertEquals(expected,actual);
    }
//    @Test
    void c_add(){
        Main m2=new Main();
        int expected =25;
        int actual =m2.add(1,4);
    }
    @Test
    void d_add(){
        Main m3=new Main();
        int expected =33;
        int actual = m3.add(3,6);
    }
    @BeforeAll
    static void setUpBeforeClass()throws Exception{
    System.out.println("@BeforeAll  setUpBeforeClass");
    }
    @AfterEach
    void tearDown(){
    System.out.println("@AfterEach tearDown");
    }



}