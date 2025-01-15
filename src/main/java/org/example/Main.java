package org.example;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.util.*;
import java.time.LocalDate;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List <MyData> data = getFromJsonFile();
        for (MyData entry: data) {
            System.out.println(entry);
        }

        printDataWithInitial(data);

        //printSortedStarkStudents(data);


        writeHouseResultToFile(data, "results.txt");
    }

    public static List<MyData> getFromJsonFile() {
        Gson gson = new Gson();
        List<MyData> data = new ArrayList<>();

        try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/data/" + "evenimente.json")) {
            MyData[] myData = gson.fromJson(reader, MyData[].class);

            return Arrays.stream(myData).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void printDataWithInitial(List<MyData> data){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter initial: ");
        char initial = scanner.next().charAt(0);
        data.stream().filter(s-> s.getMitgliedsname().charAt(0) == initial)
                .distinct()
                .forEach(d-> System.out.println(d.getMitgliedsname()));
    }

    public static void printSortedStarkStudents(List<MyData> data) {
        List<MyData> filteredData = data
                .stream()
                .filter(s-> s.Haus == Haus.Stark)
                .toList();

        List<String> names = new ArrayList<>();

        for (MyData data1 : filteredData) {
            names.add(data1.getMitgliedsname());
        }

        names
                .stream()
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    public static Result getResultForHouse(Haus house, List<MyData> data) {
        List<MyData> filteredData = data
                .stream()
                .filter(s -> s.getHaus() == house)
                .toList();

        int points = filteredData.size();

        String houseName = house.name();

        return new Result(houseName, points);
    }

    public static void writeHouseResultToFile(List<MyData> data, String filename) {
        List<Result> results = getOrderedHouses(data);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "/data/" + filename));
            for (Result result : results) {
                String line = result.getName() + '#' + result.getScore();
                writer.write(line);
                writer.newLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Result> getOrderedHouses(List<MyData> students) {
        List<Result> results = new ArrayList<>();
        for (Haus house : Haus.values()) {
            results.add(getResultForHouse(house, students));
        }

        return results
                .stream()
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    public static enum Haus {
        Stark,
        Lannister,
        Targaryen,
        Baratheon,
        GreyJoy,
        Martell,
        Tyrell,
    }

    public static class MyData {
        private int Id;
        private String Mitgliedsname;
        private Haus Haus;
        private String Ereignis;
        private Date Datum;


        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getMitgliedsname() {
            return Mitgliedsname;
        }

        public void setMitgliedsname(String mitgliedsname) {
            Mitgliedsname = mitgliedsname;
        }

        public Haus getHaus() {
            return this.Haus;
        }

        public void setHaus(Haus haus) {
            this.Haus = haus;
        }

        public String getEreignis() {
            return Ereignis;
        }

        public void setEreignis(String ereignis) {
            Ereignis = ereignis;
        }

        public Date getDatum() {
            return Datum;
        }

        public void setDatum(Date datum) {
            this.Datum = datum;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            MyData data = (MyData) o;
            return Objects.equals(Mitgliedsname, data.Mitgliedsname);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(Mitgliedsname);
        }

        @Override
        public String toString() {
            return "MyData{" +
                    "Id=" + Id +
                    ", Mitgliedsname='" + Mitgliedsname + '\'' +
                    ", Haus='" + Haus + '\'' +
                    ", Ereignis='" + Ereignis + '\'' +
                    ", Datum='" + Datum + '\'' +
                    '}';
        }
    }

    public static class Result implements Comparable<Result> {
        private String name;
        private int score;

        public Result(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(Result o) {
            return Integer.compare(this.score, o.score);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Result result = (Result) o;
            return score == result.score;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(score);
        }

        @Override
        public String toString() {
            return "Result{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }
    }
}