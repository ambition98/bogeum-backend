package xyz.bogeum.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FileUtil {
    companion object {
        fun get(path: String): String {
            val sb = StringBuilder()
            val file = File(path)
            val input = FileInputStream(file)
            var i: Int
            while (input.read().also { i = it } != -1) {
                sb.append(i.toChar())
            }
            input.close()
            return sb.toString()
        }

        fun put(path: String, data: ByteArray) {
            val file = File(path)
            val output = FileOutputStream(file)
            output.write(data)
            output.flush()
            output.close()
        }
    }
}
