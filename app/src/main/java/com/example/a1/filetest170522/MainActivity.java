package com.example.a1.filetest170522;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/*
*
* 1: getDataDirectory() 获取到Android中的data数据目录（sd卡中的data文件夹）
    2:getDownloadCacheDirectory() 获取到下载的缓存目录（sd卡中的download文件夹）
    3:getExternalStorageDirectory() 获取到外部存储的目录 一般指SDcard（/storage/sdcard0）
    4:getExternalStorageState() 获取外部设置的当前状态 一般指SDcard，比较常用的应该是 MEDIA_MOUNTED（SDcard存在并且可以进行读写）还有其他的一些状态，可以在文档中进行查找。
    5:getRootDirectory()  获取到Android Root路径
*
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSdCardPath();
//        String defaultFilePath = getDefaultFilePath();
//        System.out.println("aaa----->>>defaultFilePath= "+defaultFilePath);
//        String s = readFile();
//        System.out.println("aaa----->>>>s= "+s.toString());
//        System.out.println("----------------------------------------");
//        String s1 = readFileByBuffer();
//        System.out.println("aaa----------->>> s1= "+s1.toString());
//        writeToFile();
        writeByBuf();
        System.out.println("--------------------------------------------");
    }
    //判断SD卡是否存在
    public static boolean isSdcardExist(){
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED
        );
    }
    //获取SD卡根目录
    public static String getSdCardPath(){
        boolean exist=isSdcardExist();
        System.out.println("aaa---->>>ex= "+exist);
        String sdpath="";
        if(exist){
            sdpath=Environment.getExternalStorageDirectory().getAbsolutePath();
            System.out.println("aaa------>>>sdpath= "+sdpath);
        }else{
            sdpath="不适用";
        }
        return sdpath;
    }
    //获取默认的文件路径
    public static String getDefaultFilePath(){
        String filepath="";
        File file = new File(Environment.getExternalStorageDirectory(), "cc.txt");
        if(file.exists()){
            filepath=file.getAbsolutePath();
        }else{
            filepath="不适用";
        }
        return  filepath;
    }
    //使用FileInputStream读取文件
    public String  readFile(){
        String result=null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "t.txt");
            FileInputStream is = new FileInputStream(file);
            byte[] b = new byte[is.available()];
            is.read(b);
            result = new String(b);
            System.out.println("aaa--->>读取成功 result= "+result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }
    //使用BufferReader读取文件
    public String readFileByBuffer(){
        String s=null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "t.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readline="";
            StringBuffer sb = new StringBuffer();
            while((readline=br.readLine())!=null){
//                System.out.println("aaa----->>>readline= "+readline);
                sb.append(readline);
            }
            br.close();
            System.out.println("aaa--->>> 读取成功：sb===="+sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    //httpConnection读取流保存成String数据
    public void getNetStream(){
        try {
            URL url = new URL("");
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            //1种方式
//            InputStream is = conn.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb = new StringBuilder();
//            String readline=null;
//            while((readline=br.readLine())!=null){
//                sb.append(readline);
//            }
            //第二种
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len=-1;
            while((len=is.read(buffer))!=-1){
                bos.write(buffer,0,len);
            }
            is.close();
            bos.close();
            String sb = new String(bos.toByteArray());
            System.out.println("aaa----->>>sb= "+sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //使用FIleOutputStream写入文件
    public void writeToFile(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "360.txt");
            FileOutputStream fos = new FileOutputStream(file);
            String info="I am a chinese!";
            fos.write(info.getBytes());
            fos.close();
            System.out.println("aaa--->>>写入成功：");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//使用BufferedWriter写入文件
    public void writeByBuf(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "360.txt");
            //第二个参数表示是否以append方式添加内容
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            String info="hey, i am a good boy";
            bw.newLine();
            bw.write("\n");
            bw.write(info);
            bw.flush();
            System.out.println("aaa---->>>success");
        }catch (Exception e){
                e.printStackTrace();
        }
    }
}
