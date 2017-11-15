package com.dp.logcat

import android.os.Parcel
import android.os.Parcelable

private var logCounter = 0

data class Log(val id: Int,
               val date: String,
               val time: String,
               val pid: String,
               val tid: String,
               val priority: String,
               val tag: String,
               val msg: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(date)
        dest.writeString(time)
        dest.writeString(pid)
        dest.writeString(tid)
        dest.writeString(priority)
        dest.writeString(tag)
        dest.writeString(msg)
    }

    override fun describeContents() = 0


    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Log> {
            override fun createFromParcel(source: Parcel): Log {
                return Log(source.readInt(),
                        source.readString(),
                        source.readString(),
                        source.readString(),
                        source.readString(),
                        source.readString(),
                        source.readString(),
                        source.readString())
            }

            override fun newArray(size: Int) = arrayOfNulls<Log>(size)
        }
    }
}

internal object LogFactory {
    fun createNewLog(metadata: String, msg: String): Log {
        val date: String
        val time: String
        val pid: String
        val tid: String
        val priority: String
        val tag: String

        val trimmed = metadata.substring(1, metadata.length - 1).trim()
        var startIndex = 0

        val skipSpaces = {
            while (trimmed[startIndex] == ' ') {
                startIndex++
            }
        }

        var index = trimmed.indexOf(' ', startIndex)
        date = trimmed.substring(startIndex, index)
        startIndex = index + 1

        index = trimmed.indexOf(' ', startIndex)
        time = trimmed.substring(startIndex, index)
        startIndex = index + 1

        skipSpaces()

        index = trimmed.indexOf(':', startIndex)
        pid = trimmed.substring(startIndex, index)
        startIndex = index + 1

        skipSpaces()

        index = trimmed.indexOf(' ', startIndex)
        tid = trimmed.substring(startIndex, index)
        startIndex = index + 1

        index = trimmed.indexOf('/', startIndex)
        priority = trimmed.substring(startIndex, index)
        startIndex = index + 1

        tag = trimmed.substring(startIndex, trimmed.length).trim()

        return Log(logCounter++, date, time, pid, tid, priority, tag, msg)
    }
}