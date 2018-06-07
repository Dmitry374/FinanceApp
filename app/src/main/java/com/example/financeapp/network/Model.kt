package com.example.financeapp.network

object Model {

    data class User(var name: String, var surname: String, var email: String, var photourl: String,
                    var password: String, var gender: String, var datebirth: String, var synchronise: Int)

    data class Bill(var name: String, var amount: String, var currency: String, var color: Int,
                    var colorPosition: Int, var synchronise: Int)

    data class Category(val name: String, val icon: Int)

    data class Record(val name: String, val sumOp: String, val type: String, val bill: String, val color: Int, val icon: Int, val date: String)

    data class Currency(val Date: String, val PreviousDate: String, val PreviousURL: String, val Timestamp: String,
                        var Valute: Valute)

    data class Valute(val AUD: AUD,
                      val AZN: AZN,
                      val GBP: GBP,
                      val AMD: AMD,
                      val BYN: BYN,
                      val BGN: BGN,
                      val BRL: BRL,
                      val HUF: HUF,
                      val HKD: HKD,
                      val DKK: DKK,
                      val USD: USD,
                      val EUR: EUR,
                      val INR: INR,
                      val KZT: KZT,
                      val CAD: CAD,
                      val KGS: KGS,
                      val CNY: CNY,
                      val MDL: MDL,
                      val NOK: NOK,
                      val PLN: PLN,
                      val RON: RON,
                      val XDR: XDR,
                      val SGD: SGD,
                      val TJS: TJS,
                      val TRY: TRY,
                      val TMT: TMT,
                      val UZS: UZS,
                      val UAH: UAH,
                      val CZK: CZK,
                      val SEK: SEK,
                      val CHF: CHF,
                      val ZAR: ZAR,
                      val KRW: KRW,
                      val JPY: JPY)

//    enum class Valute {
//        AUD,
//        AZN,
//        GBP,
//        AMD,
//        BYN,
//        BGN,
//        BRL,
//        HUF,
//        HKD,
//        DKK,
//        USD,
//        EUR,
//        INR,
//        KZT,
//        CAD,
//        KGS,
//        CNY,
//        MDL,
//        NOK,
//        PLN,
//        RON,
//        XDR,
//        SGD,
//        TJS,
//        TRY,
//        TMT,
//        UZS,
//        UAH,
//        CZK,
//        SEK,
//        CHF,
//        ZAR,
//        KRW,
//        JPY
//    }

    data class AUD (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class AZN (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class GBP (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class AMD (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class BYN (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class BGN (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class BRL (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class HUF (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class HKD (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class DKK (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class USD (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class EUR (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class INR (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class KZT (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class CAD (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class KGS (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class CNY (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class MDL (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class NOK (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class PLN (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class RON (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class XDR (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class SGD (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class TJS (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class TRY (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class TMT (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class UZS (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class UAH (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class CZK (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class SEK (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class CHF (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class ZAR (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class KRW (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)
    data class JPY (val ID: String, val NumCode: String, val CharCode: String, val Nominal: String, val Name: String, val Value: String, val Previous: String)

}