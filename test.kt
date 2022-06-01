
import java.io.File
import java.io.InputStream
import java.util.BitSet
import kotlin.system.exitProcess

fun main() {

	val begin = System.nanoTime() // starting estimating time
    val uniq = BitSet() 
    val uniq_ng = BitSet() // initializeing bitsets for positive and negative bimapped ips 
    var ucounter : Int = 0
	var counter : Int = 0

    val inputStream: InputStream = File("ip_list.txt").inputStream()
    inputStream.bufferedReader().useLines { 
        lines -> lines.forEach { // opening file and looping rows in lazy generator mode

                if (it=="") { return@forEach }	
                if (it.length>15) { return@forEach } // skipping wrong ips
                
                val ip_splt : List<String> = it.split(".")
                var d : Int = ip_splt[0].toInt()
                d = (d shl 8) or ip_splt[1].toInt()
                d = (d shl 8) or ip_splt[2].toInt()
                d = (d shl 8) or ip_splt[3].toInt() // bitmapping ip

                if ( d>0 ) {
                    if ( !uniq.get(d) ) {
                       ucounter++
                       uniq.set(d, true)
                    } 
                } 
                else 
                {
                    d=2147483648.toInt()+d
                    if ( !uniq_ng.get(d) ) {
                       ucounter++
                       uniq_ng.set(d, true)
                    } 
                } // checking if ip in either positive or negative bitset
                counter++
        }            
    } 

    println("uniq.size: ${ucounter}") 
    val end = System.nanoTime()
    println("Elapsed time in seconds: ${(end-begin)/1000000000}")
	
}

