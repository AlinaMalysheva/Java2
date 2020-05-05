package Lesson5;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final int SIZE = 8;
        final int HALF = SIZE / 2;
        float[] arr_1 = new float[SIZE];
        float[] arr_2 = new float[SIZE];

        method_1(arr_1);
        method_2(arr_2, HALF);

        System.out.print("Массивы одинаковы? " + Arrays.equals(arr_1,arr_2));
    }

    private static void method_1(float[] _arr) {
        for (int i=0;i< _arr.length;i++) {
            _arr[i] = 1;
        }

       long a = System.currentTimeMillis();

        for (int i=0;i< _arr.length;i++) {
            _arr[i] = (float) (_arr[i] * Math.sin(0.2f + i  / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        for (int i=0;i< 4;i++) {
            System.out.print(_arr[i] + "; ");
        }
        System.out.println("\nИз середины массива: ");
        for (int i=0,j=(_arr.length/2);i< 4;i++,j++) {
            System.out.print(_arr[j] + "; ");
        }

        long result = System.currentTimeMillis() - a;
        System.out.println ("\nВремя работы первого метода " + result + " миллисекунд.\n");

    }

    private static void method_2(float[] _arr, int _HALF) throws InterruptedException {
        for (int i = 0; i < _arr.length; i++) {
            _arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        float[] arr21 = new float[_HALF];
        float[] arr22 = new float[_HALF];
        System.arraycopy(_arr, 0,arr21, 0, _HALF);
        System.arraycopy(_arr, _HALF, arr22, 0, _HALF);

        Thread thread1 = new Thread((new changeArray(arr21, 0)));
        Thread thread2= new Thread((new changeArray(arr22, _HALF)));
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }catch (InterruptedException e){
            System.out.println("Поймали исключение");
        }

        System.arraycopy(arr21, 0, _arr, 0, _HALF);
        System.arraycopy(arr22, 0, _arr, _HALF, _HALF);

        for (int i=0;i< 4;i++) {
            System.out.print(_arr[i] + "; ");
        }
        System.out.println("\nИз середины массива: ");
        for (int i=0,j=(_arr.length/2);i< 4;i++,j++) {
            System.out.print(_arr[j] + "; ");
        }

        long result = System.currentTimeMillis() - a;
        System.out.println ("\nВремя работы второго метода " + result + " миллисекунд.");
    }

}