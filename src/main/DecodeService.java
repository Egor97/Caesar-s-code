package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class DecodeService {
    private ArrayList<Character> alp = setAlp();
    private ArrayList<Character> smAlp =setSmAlp();

    private String getInputMessage() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (FileReader fileReader = new FileReader(new File("src" + File.separator + "Repository" + File.separator + "InputFile" + File.separator + "InputFile.txt"))) {
            int i;

            while((i = fileReader.read()) != -1) {
                stringBuilder.append((char) i);
            }
        }

        return stringBuilder.toString();
    }

    private void getDecodeMessages() throws IOException {
        File dir = new File("src" + File.separator + "Repository" + File.separator + "OutputFile");

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();

            if (files != null) {
                String message = getInputMessage();
                int count = 1;
                for (int i = 0; i < files.length; i++) {
                    try (FileWriter fileWriter = new FileWriter(files[i])) {
                        for (Character element : message.toCharArray()) {
                            if (element == ' ') {
                                fileWriter.write(' ');
                            } else if (element == '.') {
                                fileWriter.write('.');
                            } else if (element == ',') {
                                fileWriter.write(',');
                            } else if (element == '\n') {
                                fileWriter.write('\n');
                            } else if (element == Character.toUpperCase(element)) {
                                ArrayList<Character> result;

                                // Создание нового алфавита для работы с count
                                if (alp.contains(element)) {
                                    ArrayList<Character> cloneList = new ArrayList<>(alp);

                                    if (alp.indexOf(element) != 0) {
                                        List<Character> first = cloneList.subList(0, alp.indexOf(element));
                                        List<Character> second = cloneList.subList(alp.indexOf(element), alp.size());

                                        result = new ArrayList<>(first.size() + second.size());
                                        result.addAll(second);
                                        result.addAll(first);

                                        // Запись изменненого элемента в файл
                                        fileWriter.write(result.get(count));
                                    } else {
                                        fileWriter.write(cloneList.get(count));
                                    }
                                }
                            } else if (element == Character.toLowerCase(element)) {
                                ArrayList<Character> result;

                                if (smAlp.contains(element)) {
                                    ArrayList<Character> cloneList = new ArrayList<>(smAlp);

                                    if (smAlp.indexOf(element) != 0) {
                                        List<Character> first = cloneList.subList(0, smAlp.indexOf(element));
                                        List<Character> second = cloneList.subList(smAlp.indexOf(element), smAlp.size());

                                        result = new ArrayList<>(first.size() + second.size());
                                        result.addAll(second);
                                        result.addAll(first);

                                        fileWriter.write(result.get(count));
                                    } else {
                                        fileWriter.write(cloneList.get(count));
                                    }
                                }
                            }
                        }

                        count++;
                    }
                }
            }
        }
    }

    public void getTextFromFilesInConsole() {
        String value = "";

        try {
            getDecodeMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File dir = new File("src" + File.separator + "Repository" + File.separator + "OutputFile");
        File[] files = dir.listFiles();

        if (files != null) {
            for (int i = 0; i <files.length; i++) {
                try (FileReader fileReader = new FileReader(files[i])) {
                    StringBuilder stringBuilder = new StringBuilder();
                    int j;

                    while ((j = fileReader.read()) != -1) {
                        stringBuilder.append((char) j);
                    }

                    String[] dict = new String[]{"я", "мы", "ты", "вы", "он", "она", "они", "оно", "себя", "собой", "её", "его", "мой", "наш", "твой", "ваш",
                            "свой", "кто", "что", "какой", "каков", "который", "чей", "сколько", "этот", "тот", "такой", "таков", "столько", "сам",
                            "самый", "весь", "всякий", "каждый", "любой", "иной", "никто", "ничто", "никакой", "ничей", "никоторый", "некого", "нечего",
                            "некто", "нечто", "некоторый", "некий", "несколько", "кто-то", "что-то", "сколько-нибудь", "какой-либо", "кое-что"};

                    int matches = 0;

                    for (String element : dict) {
                        Pattern pattern = Pattern.compile("\\s" + element + "\\s");
                        Matcher matcher = pattern.matcher(stringBuilder.toString());

                        if (matcher.find()) {
                            matches++;
                        }
                    }

                    if (matches > 2) {
                        value = stringBuilder.toString();
                    }

                    out.println(stringBuilder.toString() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        out.println("Корректный вариант рассшифровки: \n \n" + value);
    }

    public List getAlp() {
        return alp;
    }

    private ArrayList<Character> setAlp() {
        alp = new ArrayList<>();

        for (char i = '\u0410'; i <= '\u042F'; i++) {
            alp.add(i);
        }

        alp.add(6, '\u0401');

        return alp;
    }

    public List getSmAlp() {
        return smAlp;
    }


    private ArrayList<Character> setSmAlp() {
        smAlp = new ArrayList<>();

        for (char i = '\u0430'; i <= '\u044F'; i++) {
            smAlp.add(i);
        }

        smAlp.add(6, '\u0451');

        return smAlp;
    }
}
