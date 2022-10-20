package com.project105.chatapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DataMemory {

    public static void saveData(String data, Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("datata.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void savePesanTerkirim(String data,String chatId, Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(chatId+".txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveNama(String data, Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("nama.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getData(Context context)  {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("datata.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
                    while ((line = bufferedReader.readLine()) !=null){
                        sb.append(line);
                    }
                    data = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static String getNama(Context context)  {
        String data = "";
        try {
            FileInputStream fis = context.openFileInput("nama.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) !=null){
                sb.append(line);
            }
            data = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static String getPesanTerkirim(Context context, String chatId)  {
        String data = "0";
        try {
            FileInputStream fis = context.openFileInput(chatId+".txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) !=null){
                sb.append(line);
            }
            data = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
