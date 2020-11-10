package com.vwwsapp.helpers;


import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import android.os.Environment;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.Matrix;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.Layout;
import android.graphics.Paint;
import android.text.TextPaint;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import java.io.UnsupportedEncodingException;

public class Other
{
    public byte[] buf;
    public int index;
    private static final int WIDTH_80 = 576;
    private static final int WIDTH_58 = 384;
    private static int[] p0;
    private static int[] p1;
    private static int[] p2;
    private static int[] p3;
    private static int[] p4;
    private static int[] p5;
    private static int[] p6;
    private static final byte[] chartobyte;

    static {
        Other.p0 = new int[] { 0, 128 };
        Other.p1 = new int[] { 0, 64 };
        Other.p2 = new int[] { 0, 32 };
        Other.p3 = new int[] { 0, 16 };
        Other.p4 = new int[] { 0, 8 };
        Other.p5 = new int[] { 0, 4 };
        Other.p6 = new int[] { 0, 2 };
        chartobyte = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0, 0, 0, 0, 10, 11, 12, 13, 14, 15 };
    }

    public Other(final int length) {
        this.buf = new byte[length];
        this.index = 0;
    }

    public static StringBuilder RemoveChar(final String str, final char c) {
        final StringBuilder sb = new StringBuilder();
        for (int length = str.length(), i = 0; i < length; ++i) {
            final char tmp = str.charAt(i);
            if (tmp != c) {
                sb.append(tmp);
            }
        }
        return sb;
    }

    public static boolean IsHexChar(final char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
    }

    public static byte HexCharsToByte(final char ch, final char cl) {
        final byte b = (byte)((Other.chartobyte[ch - '0'] << 4 & 0xF0) | (Other.chartobyte[cl - '0'] & 0xF));
        return b;
    }

    public static byte[] HexStringToBytes(final String str) {
        final int count = str.length();
        byte[] data = null;
        if (count % 2 == 0) {
            data = new byte[count / 2];
            for (int i = 0; i < count; i += 2) {
                char ch = str.charAt(i);
                char cl = str.charAt(i + 1);
                if (!IsHexChar(ch) || !IsHexChar(cl)) {
                    data = null;
                    break;
                }
                if (ch >= 'a') {
                    ch -= ' ';
                }
                if (cl >= 'a') {
                    cl -= ' ';
                }
                data[i / 2] = HexCharsToByte(ch, cl);
            }
        }
        return data;
    }

    public void UTF8ToGBK(final String Data) {
        try {
            final byte[] bs = Data.getBytes("GBK");
            for (int DataLength = bs.length, i = 0; i < DataLength; ++i) {
                this.buf[this.index++] = bs[i];
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static byte[] StringTOGBK(final String data) {
        byte[] buffer = null;
        try {
            buffer = data.getBytes("GBK");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static Bitmap createAppIconText(final Bitmap icon, final String txt, final float size, final boolean is58mm, final int hight) {
        if (is58mm) {
            final Bitmap canvasBitmap = Bitmap.createBitmap(384, hight, Bitmap.Config.ARGB_8888);
            final int width = canvasBitmap.getWidth();
            final Canvas canvas = new Canvas(canvasBitmap);
            canvas.setBitmap(canvasBitmap);
            canvas.drawColor(-1);
            final TextPaint paint = new TextPaint();
            paint.setColor(-16777216);
            paint.setTextSize(size);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setFakeBoldText(false);
            final StaticLayout layout = new StaticLayout((CharSequence)txt, 0, txt.length(), paint, width, Layout.Alignment.ALIGN_NORMAL, 1.1f, 0.0f, true, TextUtils.TruncateAt.END, width);
            canvas.translate(0.0f, 5.0f);
            layout.draw(canvas);
            canvas.save();
            canvas.restore();
            return canvasBitmap;
        }
        final Bitmap canvasBitmap = Bitmap.createBitmap(576, hight, Bitmap.Config.ARGB_8888);
        final int width = canvasBitmap.getWidth();
        final Canvas canvas = new Canvas(canvasBitmap);
        canvas.setBitmap(canvasBitmap);
        canvas.drawColor(-1);
        final TextPaint paint = new TextPaint();
        paint.setColor(-16777216);
        paint.setTextSize(size);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setFakeBoldText(false);
        final StaticLayout layout = new StaticLayout((CharSequence)txt, 0, txt.length(), paint, width, Layout.Alignment.ALIGN_NORMAL, 1.1f, 0.0f, true, TextUtils.TruncateAt.END, width);
        canvas.translate(0.0f, 5.0f);
        layout.draw(canvas);
        canvas.save(); //31
        canvas.restore();
        return canvasBitmap;
    }

    public static byte[] byteArraysToBytes(final byte[][] data) {
        int length = 0;
        for (int i = 0; i < data.length; ++i) {
            length += data[i].length;
        }
        final byte[] send = new byte[length];
        int k = 0;
        for (int j = 0; j < data.length; ++j) {
            for (int l = 0; l < data[j].length; ++l) {
                send[k++] = data[j][l];
            }
        }
        return send;
    }

    public static Bitmap resizeImage(final Bitmap bitmap, final int w, final int h) {
        final Bitmap BitmapOrg = bitmap;
        final int width = BitmapOrg.getWidth();
        final int height = BitmapOrg.getHeight();
        final int newWidth = w;
        final int newHeight = h;
        final float scaleWidth = newWidth / (float)width;
        final float scaleHeight = newHeight / (float)height;
        final Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        final Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    public static Bitmap toGrayscale(final Bitmap bmpOriginal) {
        final int height = bmpOriginal.getHeight();
        final int width = bmpOriginal.getWidth();
        final Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas c = new Canvas(bmpGrayscale);
        final Paint paint = new Paint();
        final ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.0f);
        final ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter((ColorFilter)f);
        c.drawBitmap(bmpOriginal, 0.0f, 0.0f, paint);
        return bmpGrayscale;
    }

    public static void saveMyBitmap(final Bitmap mBitmap, final String name) {
        final File f = new File(Environment.getExternalStorageDirectory().getPath(), name);
        try {
            f.createNewFile();
        }
        catch (IOException ex) {}
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fOut);
            fOut.flush();
            fOut.close();
        }
        catch (FileNotFoundException ex2) {}
        catch (IOException ex3) {}
    }

    public static byte[] thresholdToBWPic(final Bitmap mBitmap) {
        final int[] pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()];
        final byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight()];
        mBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        format_K_threshold(pixels, mBitmap.getWidth(), mBitmap.getHeight(), data);
        return data;
    }

    private static void format_K_threshold(final int[] orgpixels, final int xsize, final int ysize, final byte[] despixels) {
        int graytotal = 0;
        int grayave = 128;
        int k = 0;
        for (int i = 0; i < ysize; ++i) {
            for (int j = 0; j < xsize; ++j) {
                final int gray = orgpixels[k] & 0xFF;
                graytotal += gray;
                ++k;
            }
        }
        grayave = graytotal / ysize / xsize;
        k = 0;
        for (int i = 0; i < ysize; ++i) {
            for (int j = 0; j < xsize; ++j) {
                final int gray = orgpixels[k] & 0xFF;
                if (gray > grayave) {
                    despixels[k] = 0;
                }
                else {
                    despixels[k] = 1;
                }
                ++k;
            }
        }
    }

    public static void overWriteBitmap(final Bitmap mBitmap, final byte[] dithered) {
        final int ysize = mBitmap.getHeight();
        final int xsize = mBitmap.getWidth();
        int k = 0;
        for (int i = 0; i < ysize; ++i) {
            for (int j = 0; j < xsize; ++j) {
                if (dithered[k] == 0) {
                    mBitmap.setPixel(j, i, -1);
                }
                else {
                    mBitmap.setPixel(j, i, -16777216);
                }
                ++k;
            }
        }
    }

    public static byte[] eachLinePixToCmd(final byte[] src, final int nWidth, final int nMode) {
        final int nHeight = src.length / nWidth;
        final int nBytesPerLine = nWidth / 8;
        final byte[] data = new byte[nHeight * (8 + nBytesPerLine)];
        int offset = 0;
        int k = 0;
        for (int i = 0; i < nHeight; ++i) {
            offset = i * (8 + nBytesPerLine);
            data[offset + 0] = 29;
            data[offset + 1] = 118;
            data[offset + 2] = 48;
            data[offset + 3] = (byte)(nMode & 0x1);
            data[offset + 4] = (byte)(nBytesPerLine % 256);
            data[offset + 5] = (byte)(nBytesPerLine / 256);
            data[offset + 6] = 1;
            data[offset + 7] = 0;
            for (int j = 0; j < nBytesPerLine; ++j) {
                data[offset + 8 + j] = (byte)(Other.p0[src[k]] + Other.p1[src[k + 1]] + Other.p2[src[k + 2]] + Other.p3[src[k + 3]] + Other.p4[src[k + 4]] + Other.p5[src[k + 5]] + Other.p6[src[k + 6]] + src[k + 7]);
                k += 8;
            }
        }
        return data;
    }
}