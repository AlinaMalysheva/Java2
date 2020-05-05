package Lesson5;

public class changeArray implements Runnable {

    private float[] arr;
    private int plusIndex;

    public  changeArray(float[] _arr, int _half){
        arr = _arr;
        plusIndex = _half;
    }

    public void run () {
        for (int i=0;i< arr.length;i++) {
                            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i+plusIndex)  / 5) * Math.cos(0.2f + (i+plusIndex) / 5) * Math.cos(0.4f + (i+plusIndex) / 2));

        }
    }

}
