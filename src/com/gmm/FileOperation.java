package com.gmm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * 文件相关操作
 */
public class FileOperation {
    //读取文件内容 放到arr数组中
    public static boolean readFile(String filename, ArrayList<String> words){
        if(filename==null||words==null){
            System.out.println("filename is null or words is null");
            return false;
        }
        Scanner scanner = null;

        try {
            File file = new File(filename);
            if(file.exists()){
                FileInputStream fis = new FileInputStream(file);
                //使用管道进行包装
                scanner = new Scanner(new BufferedInputStream(fis),"UTF-8");
                scanner.useLocale(Locale.ENGLISH);
                //使用本地化语言读取
                //三要素 文件流 编码设置 本地化设置
            }else{
                return false;
            }
        }
        catch (IOException ioerror){
            System.out.println("read file error"+filename);
            System.out.println(ioerror);
            return false;
        }

        //简单分词 不考虑时态问题 以及其他国家字符编码问题
        if(scanner.hasNextLine()){
            //以下method不理解
            String contents = scanner.useDelimiter("\\A").next();


            int start = firstCharacterIndex(contents,0);
            for(int i = start+1;i<=contents.length();){
                if(i==contents.length()||!Character.isLetter(contents.charAt(i))){
                    String word = contents.substring(start,i).toLowerCase();
                    words.add(word);
                    start = firstCharacterIndex(contents,i);
                    i = start + 1;
                }
                else
                    i++;
            }
        }
        return true;
    }

    private static int firstCharacterIndex(String s,int start){
        for(int i=start;i<s.length();i++){
            if(Character.isLetter(s.charAt(i)))
                return i;
        }
        return s.length();
    }
}
