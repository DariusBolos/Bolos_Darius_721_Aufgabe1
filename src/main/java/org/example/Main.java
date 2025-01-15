package org.example;

import com.google.gson.Gson;

import java.util.*;

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

    public static class MyData {
        private int Id;
        private String Mitgliedsname;
        private String Haus;
        private String Ereignis;
        private String Datum;


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

        public String getHaus() {
            return Haus;
        }

        public void setHaus(String haus) {
            Haus = haus;
        }

        public String getEreignis() {
            return Ereignis;
        }

        public void setEreignis(String ereignis) {
            Ereignis = ereignis;
        }

        public String getDatum() {
            return Datum;
        }

        public void setDatum(String datum) {
            Datum = datum;
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
}