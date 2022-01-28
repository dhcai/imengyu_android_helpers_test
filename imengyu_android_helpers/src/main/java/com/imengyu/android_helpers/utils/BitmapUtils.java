package com.imengyu.android_helpers.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 位图工具
 */
public class BitmapUtils {
    /*
     * bitmap转base64
     * */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static String checkBitmapTempExists(Context context, String name) {
        String path = context.getCacheDir() + "/video-picker/cache/" + name + ".jpg";
        if(new File(path).exists())
            return path;
        return null;
    }
    /**
     * 保存文件至
     * @param bitmap 图像
     * @return
     */
    public static String saveBitmapToTemp(Context context, Bitmap bitmap, String name) {
        try {
            String dirPath = context.getCacheDir() + "/video-picker/cache/";
            File dir = new File(dirPath);
            if(!dir.exists())
                if(!dir.mkdirs())
                    return "";

            File file = new File(dirPath + name + ".jpg");
            file.deleteOnExit();

            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
            out.flush();
            out.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 选择图片处理缩放
     * @param bmp 需要压缩的bitmap图片对象
     * @param quality 属性0-100，来实现压缩（因为png是无损压缩，所以该属性对png是无效的）
     * @param width 强制缩放宽
     * @param height 强制缩放高
     * @param orientation 旋转度数
     * @param file 压缩后图片保存的位置
     */
    public static void imageFixCompress(Bitmap bmp, int quality, int width, int height, int orientation, File file) {

        //如果指定了长宽其中某个值，则会按比例为你缩小图片。
        //如果指定了长宽其两个值，则会按直接拉伸图片。
        int resultWidth = 0;
        int resultHeight = 0;
        if(width != 0 && height != 0) {
            resultWidth = width;
            resultHeight = height;
        } else if(width == 0 && height != 0) {
            resultWidth = (int)((height / (float)bmp.getHeight()) * bmp.getWidth());
            resultHeight = height;
        } else if(width != 0) {
            resultWidth = width;
            resultHeight = (int)((width / (float)bmp.getWidth()) * bmp.getHeight());
        }


        Bitmap newBitmap = Bitmap.createBitmap(resultWidth, resultHeight, bmp.getConfig());
        Canvas canvas = new Canvas(newBitmap);

        float scaleX = resultWidth / (float)bmp.getWidth();
        float scaleY = resultHeight / (float)bmp.getHeight();
        //旋转图片
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        matrix.postRotate(orientation, (bmp.getWidth() / 2.0f) * scaleX, (bmp.getHeight() / 2.0f) * scaleY);
        //

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(bmp, matrix, paint);

        //保存新图片
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(outputStream.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            newBitmap.recycle();
        }

    }

    public static int getImageExifOrientation(final String path) {
        ExifInterface exif;
        int degree = 0;

        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            exif = null;
        }

        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;


            }
        }
        return degree;
    }
}
