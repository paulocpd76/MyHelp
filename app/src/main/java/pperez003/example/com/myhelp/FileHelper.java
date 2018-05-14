package pperez003.example.com.myhelp;

/**
 * Created by pperez003 on 14/05/2018.
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class FileHelper {
    final static String fileName = "becon.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/readtxtfile" ;
    final static String TAG = FileHelper.class.getName();
    public static  String ReadFile(Tab1Register context){
        String line = null;
        try {
            FileInputStream fileInputStream = new FileInputStream (new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
                //System.out.println("******************"+line+"***"+System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return line;
    }

    public static boolean saveToFile( String data){
       // System.out.println("entro");
        try {
            new File(path  ).mkdir();
            File file = new File(path+ fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            if (checkUser(data)){
                FileOutputStream fileOutputStream = new FileOutputStream(file,true);
                fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());
                return true;
            };
        }  catch(FileNotFoundException ex) {
           // System.out.println("entro**3");
            Log.d(TAG, ex.getMessage());
        }  catch(IOException ex) {
            //System.out.println("entro**4");
            Log.d(TAG, ex.getMessage());
        }
        return  false;


    }

    public static boolean checkUser(String data){
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream (new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<String> list=new ArrayList<String>();
            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
                list.add(line);

            }
            fileInputStream.close();
            line = stringBuilder.toString();
            bufferedReader.close();
            for (String oneList : list) {
                //  System.out.print(oneList + "************* ");
                if (oneList.equals(data)) {
                    System.out.println("Created before =" +oneList );
                    return false;
                }

            }
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Output : C

        return true;
    }

    public static boolean getFile(){

        new File(path  ).mkdir();
        File file = new File(path+ fileName);
        if (!file.exists()) {
            return false;
        }
        return  true;
    }




}