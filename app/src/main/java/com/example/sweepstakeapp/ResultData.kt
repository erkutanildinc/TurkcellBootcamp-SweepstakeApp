package com.example.sweepstakeapp

import com.example.sweepstakeapp.models.SweepstakeModel
import com.example.sweepstakeapp.room.DrawDao
import com.example.sweepstakeapp.room.DrawDatabase
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import kotlin.math.min

class ResultData {

    var baseURL = "https://www.kimkazandi.com"

    fun cekilisler() : MutableList<SweepstakeModel>{
        val list = mutableListOf<SweepstakeModel>()
        val url = "/cekilisler"
        val doc: Document = Jsoup.connect(baseURL+url).timeout(15000).get()
        val elements: Elements = doc.getElementsByClass("col-sm-3 col-lg-3 item")
        for (element in elements){
            val imgTag = element.getElementsByTag("img")
            if(!imgTag.isEmpty()){
                val imgURL = baseURL+ imgTag.attr("src") //img
                val srcTAG = element.getElementsByTag("a")
                val src = srcTAG.attr("abs:href")
                val dateAndMoney = element.getElementsByClass("date d-flex")
                val deadline = dateAndMoney.get(0).text()
                val money = dateAndMoney.get(1).text()
                val condition = element.getElementsByClass("date kosul_fiyat d-flex").text()
                val name = imgTag.attr("alt")
                var sweepstakeModel = SweepstakeModel(name,imgURL,src,deadline,money,condition)
                list.add(sweepstakeModel)
            }
        }
        return list
    }


    fun getSectionData(section : String) : MutableList<SweepstakeModel>{
        val list = mutableListOf<SweepstakeModel>()
        val url = section
        val doc: Document = Jsoup.connect(baseURL+url).timeout(15000).get()
        val elements: Elements = doc.getElementsByClass("col-sm-3 col-lg-3 item")
        for (element in elements){
            val imgTag = element.getElementsByTag("img")
            if(!imgTag.isEmpty()){
                val imgURL = baseURL+ imgTag.attr("src") //img
                val srcTAG = element.getElementsByTag("a")
                val src = srcTAG.attr("abs:href")
                val dateAndMoney = element.getElementsByClass("date d-flex")
                val deadline = dateAndMoney.get(0).text()
                val money = dateAndMoney.get(1).text()
                val condition = element.getElementsByClass("date kosul_fiyat d-flex").text()
                val name = imgTag.attr("alt")
                val sweepstakeModel = SweepstakeModel(name,imgURL,src,deadline,money,condition)
                list.add(sweepstakeModel)
            }
        }
        return list
    }

    fun getTableDetails( url : String) : MutableList<String>{
        var list = mutableListOf<String>()
        val doc: Document = Jsoup.connect(url).timeout(15000).get()
        val elements: Elements = doc.getElementsByClass("brandDesc")
        for( element in elements){
            var baslangic = element.getElementsByClass("kalanSure").get(0).text()
            var son = element.getElementsByClass("kalanSure").get(1).text()
            var cekilis = element.getElementsByClass("kalanSure").get(2).text()
            var ilan = element.getElementsByClass("kalanSure").get(3).text()
            var minHarcama = element.getElementsByClass("kalanSure").get(4).text()
            var toplamHediyeTutar = element.getElementsByClass("kalanSure").get(5).text()
            var toplamHediyeSayısı = element.getElementsByClass("kalanSure").get(6).text()
            list.add(baslangic)
            list.add(son)
            list.add(cekilis)
            list.add(ilan)
            list.add(minHarcama)
            list.add(toplamHediyeTutar)
            list.add(toplamHediyeSayısı)
        }
        return list
    }
}