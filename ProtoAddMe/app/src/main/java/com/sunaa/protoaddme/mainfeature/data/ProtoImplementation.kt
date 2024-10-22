package com.sunaa.protoaddme.mainfeature.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.sunaa.protoaddme.UserInfo

import java.io.InputStream
import java.io.OutputStream

class ProtoImplementation : Serializer<UserInfo.UserList> {

    override val defaultValue: UserInfo.UserList
        get() = UserInfo.UserList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserInfo.UserList {
        try {
            return UserInfo.UserList.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }
    }

    override suspend fun writeTo(t: UserInfo.UserList, output: OutputStream) {
        t.writeTo(output)
    }
}