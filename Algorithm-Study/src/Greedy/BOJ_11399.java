package Greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_11399 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);
        int[] wait = new int[n];
        wait[0] = arr[0];
        for (int i = 1; i < n; i++) {
            wait[i] = arr[i] + wait[i-1];
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum+=wait[i];
        }
        System.out.println(sum);
    }
}
