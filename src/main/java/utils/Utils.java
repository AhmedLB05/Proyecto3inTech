package utils;

import java.util.Scanner;

public class Utils {

    public static final Scanner S = new Scanner(System.in);


    public static void mensajeSaliendo() {
        System.out.print("\n SALIENDO");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void pulsaParaContinuar() {
        System.out.print("\nPulsa para continuar...");
        S.nextLine();
    }


}
