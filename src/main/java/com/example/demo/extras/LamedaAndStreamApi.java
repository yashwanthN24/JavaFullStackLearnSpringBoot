package com.example.demo.extras;

import java.util.List;
import java.util.stream.Stream;

public class LamedaAndStreamApi {

//    lameda  is a way of creating annonmous instances for functional interfaces (Interfaces having onlu one abstract method)
public static void main(String[] args) {
    Walker w = new Walker();
    System.out.println(w.walk(33));

    Walkable w1 = new Walkable() {
        @Override
        public int walk(int steps) {
            return 2*steps + 1 ;
        }
    };
    System.out.println(w1.walk(12));

    Walkable w2 = steps -> 2*steps + 2;
    System.out.println(w2.walk(13));


//    Streams heavily uses lameda's
//    - stream is like just like water stream once it flows it in one direction cant process again

    List<String> fruits = List.of("Apple" , "Banana" , "Carrot");

    Stream<String> stream = fruits.stream(); // or use parallelStream() for a bigger list and it run on multiple threads and faster than jnst stream for heavy lists

    stream.forEach(fruit -> System.out.print(fruit + " "));

//    you cant work with this stream again its closed

}




}

@FunctionalInterface
interface Walkable {
    int walk(int steps);
}

class Walker implements Walkable{

    @Override
    public int walk(int steps) {
        return 2*steps;
    }
}
