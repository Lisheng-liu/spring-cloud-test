package com.lls;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class FileTest {

    public static void main(String[] args) {
//        createDir();
//        printFileName();

        cmd();
    }

    public static void createDir(){
        String dir = "/home/liulisheng/data/a/b/c";
        File file = new File(dir);
        if(!file.exists()){
            file.mkdirs();
        }

    }

    public static void printFileName(){
        String dir = "/home/liulisheng/data/a/b/c/aa.jar";
        File file = new File(dir);
        System.out.println(file.getName());

    }


    public static void cmd()  {
        String ls = "  cd /home/ &&  dir ";
        Process process = null;
        String cmd = getOsCmd()+ ls;
        try {
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(new String(line.getBytes(),"GBK"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            process.destroy();
        }
    }

    public static String getOsCmd(){
        Properties props=System.getProperties(); //获得系统属性集
        String osName = props.getProperty("os.name"); //操作系统名称
        if(osName.toLowerCase().contains("linux")){
            return "/bin/sh -c";
        }else if(osName.toLowerCase().contains("windows")){
            return "cmd /c";
        }else{
            throw new RuntimeException("服务器不是linux|windows操作系统");
        }
    }




}
