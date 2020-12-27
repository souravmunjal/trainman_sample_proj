package com.example.trainmanscreeningproject.core.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import static com.example.trainmanscreeningproject.core.Constants.Constants.FILE_PROVIDER_AUTHORITY;

public class ImageUtils {

    public static File getImagefile(Context context,String name) throws IOException {
        File storageDir;
        String imageFileName = name;
        storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,
                ".gif",
                storageDir
        );
    }




}
