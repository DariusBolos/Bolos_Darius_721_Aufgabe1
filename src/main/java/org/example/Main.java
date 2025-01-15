package org.example;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(System.getProperty("user.dir") + "/data/" + "evenimente.json")) {
            MyData[] myData = gson.fromJson(reader, MyData[].class);

            for(MyData data: myData) {
                System.out.println(data.Mitgliedsname);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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