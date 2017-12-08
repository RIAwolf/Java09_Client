package com.company;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final String HOST="127.0.0.1";
    public static final int PORT = 8080;
    public static void main(String[] args) {
	// write your code here
        Scanner skaneris = new Scanner(System.in);
        try{
            Socket kanalas = new Socket(HOST,PORT);
            boolean testi = true;
            ObjectOutputStream isvedimas = new ObjectOutputStream(kanalas.getOutputStream());
            ObjectInputStream ivedimas = new ObjectInputStream(kanalas.getInputStream());
            while (testi){
                String veiksmas ="";
                try {
                    if (ivedimas.available() > 0) {
                        veiksmas = ivedimas.readUTF();
                    }
                    if (veiksmas == null) {
                        kanalas.close();
                        veiksmas = "";
                    }
                } catch (Exception skaitymoKlaida) {
                    skaitymoKlaida.printStackTrace();
                }
                switch(veiksmas){
                    case "pabaiga":
                        testi=false;
                        kanalas.close();
                        System.out.println("Baigem darba");
                        break;
                    case "":
                        System.out.println("Iveskite teksta");
                        String zinute = skaneris.next();
                        isvedimas.writeUTF(zinute);
                        isvedimas.flush();
                        break;
                    default:
                        System.out.println(veiksmas);
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception klaida){
            klaida.printStackTrace();
        }

    }
}
