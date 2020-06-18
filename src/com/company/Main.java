package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    final static String workingcopy_filePath = "workingcopy.txt";
    final static String finalcopy_filePath = "Finalcopy.txt";
    final static String scannercopy_filePath = "scannercopy.txt";
    
    final static File workingcopy_file = new File(workingcopy_filePath);
    final static File scannercopy_file = new File(scannercopy_filePath);
    final static File finalcopy_file=new File(finalcopy_filePath);

    public static void main(String[] args) throws IOException {
	// store scanner values into workingcopy.txt file
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw =  new BufferedWriter( new FileWriter(scannercopy_file) );
        try {
            System.out.print("program ");
            String line = br.readLine();
            String[] serverlist = line.split(" ");
            for(String server:serverlist) {
                String[] serverdetails=server.split(":");
                bw.write(serverdetails[0] + ":" + serverdetails[1]);
                bw.newLine();
            }
        bw.flush();
        } catch (Exception e) {
            //TODO: handle exception
            throw new IOException("InValid Input", e);
        }



        // compare both scannercopy.txt and finalcopy.txt once input is scanned. If both files are same
        //then we will not do anything. If both files are different the update Finalcopy.txt and workingcopy.txt
        //to scannercopy.txt

         comparebothfile(finalcopy_file,scannercopy_file);

         //Restore values from workingcopy.txt file inorder to fetch server names
        HashMap<String, Integer> mapFromFile = getHashMapFromTextFile();

        // once server count==0 , we are removing  servers from workingcopy.txt. When entire workingcopy.txt
        //becomes empty then copy Finalcopy.txt to workingcopy.txt and restore hashmap again
        if(mapFromFile.size()==0){

            copyFileUsingStream(finalcopy_file,workingcopy_file);
            mapFromFile = getHashMapFromTextFile();
        }
        //ACTUAL CODE: This is the actual code to fetch server names from  workingcopy.txt file and also reduce count
        //to -1 on every program run.
       checkprogram(mapFromFile);
    }

    public static void comparebothfile(File source, File dest) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(source));
        BufferedReader reader2 = new BufferedReader(new FileReader(dest));
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        boolean areEqual = true;
        while (line1 != null || line2 != null) {
            if (line1 == null || line2 == null) {
                areEqual = false;
                break;
            } else if (!line1.equalsIgnoreCase(line2)) {
                areEqual = false;
                break;
            }
            line1 = reader1.readLine();
            line2 = reader2.readLine();
        }
        if (!areEqual) {
            //copy scannercopy.txt to finalcopy.txt
           // System.out.println("copy scanner txt file to finalcopy txt.");
            copyFileUsingStream(dest,source);
           //copy scannercopy.txt to workingcopy.txt
            copyFileUsingStream(dest,workingcopy_file);
        }
        reader1.close();
        reader2.close();
    }

    public static HashMap<String, Integer> getHashMapFromTextFile(){
        Map<String, Integer> mapFileContents = new HashMap<String, Integer>();
        BufferedReader br = null;
        try{
            File file = new File(workingcopy_filePath);
            br = new BufferedReader( new FileReader(file) );
            String line = null;
            while ( (line = br.readLine()) != null ){
                String[] parts = line.split(":");
               // System.out.println(parts[0]);
                String name = parts[0];
                Integer Noofservers = Integer.parseInt( parts[1].trim() );
                mapFileContents.put(name, Noofservers);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(br != null){
                try {
                    br.close();
                }catch(Exception e){};
            }
        }
        return (HashMap<String, Integer>) mapFileContents;
    }

    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    public static void checkprogram(HashMap<String,Integer> map){
        Object[] crunchifyKeys = map.keySet().toArray();
        Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
        if(map.get(key)>=1){
            System.out.println(key);
        }
        //System.out.println("map size before"+ map.size());
        map.put(String.valueOf(key),map.get(key)-1);
        if(map.get(key)==0){
            map.remove(key);
           // System.out.println(String.valueOf(key));
        }
        BufferedWriter bw = null;
        File file = new File(workingcopy_filePath);
        try{
            bw = new BufferedWriter( new FileWriter(file) );
            for(Map.Entry<String, Integer> entry : map.entrySet()){
                bw.write( entry.getKey() + ":" + entry.getValue() );
                bw.newLine();
            }
            bw.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                bw.close();
            }catch(Exception e){}
        }

    }
}
