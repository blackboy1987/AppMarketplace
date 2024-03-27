package com.bootx.yysc.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.FileUtils
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import com.bootx.app.service.UploadService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

/**
 * 文件上传
 */
object UploadUtils {

    suspend fun uploadImage(token: String,file: File): String {
        val uploadService = UploadService.instance();
        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull());

        builder.addFormDataPart("name", file.getName());
        builder.addFormDataPart("size", "" + file.length());

        builder.addFormDataPart("file", file.getName(), requestBody);
        val body = builder.build();
        val upload = uploadService.upload(token,body)
        return upload.data.url
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun uri2File(uri: Uri, context:Context): File? {
        var file: File? = null
        if(uri.scheme.equals(ContentResolver.SCHEME_FILE)){
            return uri.path?.let { File(it) }
        }
        val contentResolver = context.contentResolver
        val displayName = System.currentTimeMillis().toString()+Math.round((Math.random()+1)*1000).toString()+"."+MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri))
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val cache = File(context.cacheDir.absolutePath,displayName)
            val fos = withContext(Dispatchers.IO) {
                FileOutputStream(cache)
            }
            if (inputStream != null) {
                FileUtils.copy(inputStream,fos)
            }
            file = cache
            withContext(Dispatchers.IO) {
                fos.close()
            }
            withContext(Dispatchers.IO) {
                inputStream?.close()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return file
    }

}