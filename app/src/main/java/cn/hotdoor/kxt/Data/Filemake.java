package cn.hotdoor.kxt.Data;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.hotdoor.kxt.R;

/**
 * Created by Administrator on 2015/8/18.
 */
public class Filemake {

    int flag = 0;

    private String FilepathMusic = GlobleData.FilepathMusic;//记得mnt private String FilepathLRC="mnt/sdcard/LCLrc/"; int flag; 
    File pic1 = new File(FilepathMusic + "1");
    File pic2 = new File(FilepathMusic + "2");
    File pic3 = new File(FilepathMusic + "3");
    File pic4 = new File(FilepathMusic + "4");
    File pic5 = new File(FilepathMusic + "5");
    File pic6 = new File(FilepathMusic + "6");


    public Filemake(int flag) {//flag就是主activity传入的misfirst值 
        this.flag = flag;
    }

    //创建文件夹代码int：
    public void makeDir(Context context) throws IOException {
        //Toast.makeText(context, "创建成功0", Toast.LENGTH_SHORT).show();
        if (flag == 0) {//只能是0才做下面的事情。表示首次代开播放器
            // Toast.makeText(context, "创建成功1", Toast.LENGTH_SHORT).show();
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {
// Log.v("ewewewewewewewewew", Environment
// .getExternalStorageState());这里判断Sdcard的状态，一般remove是挂载不到，mounted就说明挂载成功。
                File Musicfoder = new File(FilepathMusic);
                // Toast.makeText(context, "创建成功2", Toast.LENGTH_SHORT).show();
                // File Lrcfoder = new File(FilepathLRC);
                if (!Musicfoder.exists()) {
// boolean a=
                    Musicfoder.mkdirs();
                    // Toast.makeText(context, "创建成功3", Toast.LENGTH_SHORT).show();

                }
                //if (!Lrcfoder.exists()) {
                // Lrcfoder.mkdirs();
                // }
            }
            Inputmusic(context, pic1, R.raw.man_1);//下面的写入携带文件函数，这里是mp3写入
            Inputmusic(context, pic2, R.raw.man_2);
            Inputmusic(context, pic3, R.raw.man_3);
            Inputmusic(context, pic4, R.raw.girl_1);
            Inputmusic(context, pic5, R.raw.girl_2);
            Inputmusic(context, pic6, R.raw.girl_3);

            //Inputmusic(context,lrc,R.raw.misslrc);lrc写入
        }
    }

    //携带文件写入代码
    public void Inputmusic(Context context, File music, int x) throws IOException {//规划了file参数、ID参数，方便多文件写入。
        InputStream in = null;
        OutputStream out = null;
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            if (!music.exists()) {
                music.createNewFile();
            }
            in = context.getResources().openRawResource(x);
            int length = in.available();// 获取文件的字节数
            byte[] buffer = new byte[length];// 创建byte数组
            out = new FileOutputStream(music);// 字节输入流
            bin = new BufferedInputStream(in);// 缓冲输出流
            bout = new BufferedOutputStream(out);// 缓存输入流
            int len = bin.read(buffer);
            while (len != -1) {
                bout.write(buffer, 0, len);
                len = bin.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (bin != null) {
                bin.close();
            }
            if (bout != null) {
                bout.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
