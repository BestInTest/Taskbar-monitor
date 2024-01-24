package com.github.bestintest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            /*
             Sprawdzanie czy program został uruchomiony
             przez podwójne kliknięcie, aby otworzyć okno konsoli
            */
            if (!Utils.isCli(args)) {
                File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                        .toURI().getPath());
                String launchArg = "cmd.exe /c start java -jar \"" + jarFile.getAbsolutePath() + "\" -cli";
                System.out.println(launchArg);
                Runtime.getRuntime().exec(launchArg);
                return;
            }

            List<String> appsOnTaskbar = new ArrayList<>();
            while (true) {
                ProcessBuilder processBuilder = new ProcessBuilder("powershell", "Get-Process | Where-Object {$_.MainWindowTitle -ne ''} | Select-Object MainWindowTitle");
                Process process = processBuilder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                List<String> tempList = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    //System.out.println("Aktualnie otwarta aplikacja: " + line.trim());
                    tempList.add(line.trim());
                }

                if (!Utils.areListsEqualUnordered(appsOnTaskbar, tempList)) {
                    System.out.println(Utils.getCurrentTime() + " | Lista aplikacji zmieniła się");
                    appsOnTaskbar = tempList;

                    for (String app : tempList) {
                        System.out.println(app);
                    }
                }

                process.waitFor();
                Thread.sleep(300);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}