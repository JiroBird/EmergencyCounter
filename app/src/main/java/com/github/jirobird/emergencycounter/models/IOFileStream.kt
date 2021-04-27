package com.github.jirobird.emergencycounter.models

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.*

class IOFileStream(private val context: Context) {
    init {
        checkExternalMedia()
    }

    private var mExternalStorageAvailable = false
    private var mExternalStorageWriteable = false

    private fun checkExternalMedia(){
        when (Environment.getExternalStorageState()) {
            Environment.MEDIA_MOUNTED -> { // Can read and write the media
                mExternalStorageAvailable = true
                mExternalStorageWriteable = true
            }
            Environment.MEDIA_MOUNTED_READ_ONLY -> {
                // Can only read the media
                mExternalStorageAvailable = true
                mExternalStorageWriteable = false
            }
            else -> { // Can't read or write
                mExternalStorageAvailable = false
                mExternalStorageWriteable = false
            }
        }
    }

    private val appFolderPath = "${context.filesDir.absolutePath}/rintd"

    fun createFileOnCard(fileName: String):String? {
        val dir = File( appFolderPath)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "$fileName.txt")
        try {
            val f = FileOutputStream(file)
            val pw = PrintWriter(f)
            pw.flush()
            pw.close()
            f.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        return file.absolutePath
    }

    fun appendToFileData(fileName:String, value: String) {
        val file = File("$fileName")

        if (file.exists()) {
            try {
                val fw = FileWriter(file.absolutePath, true)
                val bw = BufferedWriter(fw)
                val pw = PrintWriter(bw)
                pw.println("$value")
                pw.flush()
                pw.close()
                bw.close()
                fw.close()
            } catch (e: IOException) {
                //exception handling left as an exercise for the reader
            }
        } else {
            Log.d("","")
        }
    }

    fun getFiles(): List<String>?  {
        val dir = File(appFolderPath)
        if (!dir.exists()) dir.mkdirs()

        if (dir.isDirectory) {
            val arrayList = ArrayList<String>()
            dir.list()?.iterator()?.forEach {
                arrayList.add(it)
            }
            return arrayList
        }
        return null
    }
}