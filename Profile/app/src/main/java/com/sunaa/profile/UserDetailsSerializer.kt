package com.sunaa.profile

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserDetailsSerializer : Serializer<UserDetails> {
    override val defaultValue: UserDetails
        get() = UserDetails.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserDetails {
        try {
            return UserDetails.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", e)
        }
    }

    override suspend fun writeTo(t: UserDetails, output: OutputStream) {
        t.writeTo(output)
    }
}