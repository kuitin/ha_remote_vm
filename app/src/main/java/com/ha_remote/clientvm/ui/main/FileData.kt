package com.ha_remote.clientvm.ui.main

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.DateFormat
import java.util.Date
import java.util.logging.Level.parse
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.time.ZoneId
import java.text.SimpleDateFormat;
object DateSerializer : KSerializer<Date> {
    override val descriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)
//    override fun serialize(encoder: Encoder, value: Date) {
//        val result = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//        encoder.encodeString(result)
//    }
    override fun serialize(encoder: Encoder, value: Date) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): Date {
        var temp = decoder.decodeString()
        var local_datef = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val d = local_datef.parse(temp)
        return d
    }
}

@Serializable
data  class FileData (
    val name : String,
    val state : String,
    @Serializable(DateSerializer::class)
    val last_changed : Date) {

}