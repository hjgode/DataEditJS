package hsm.dataeditjs;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class codeIDs {
    static Map<String, String> myMap = new HashMap<String, String>() {{
        put("z", "AZTEC");
        put("a", "CODABAR");
        put("h", "CODE11");
        put("j", "CODE128");
        put("I", "EAN128");
        put("b", "CODE39");
        put("l", "CODE49");
        put("i", "CODE93");
        put("y", "COMPOSITE");
        put("w", "DATAMATRIX");
        put("D", "EAN8");
        put("d", "EAN13");
        put("e", "INT25");
        put("x", "MAXICODE");
        put("R", "MICROPDF");
        put("r", "PDF417");
        put("P", "POSTNET");
        put("O", "OCR");
        put("s", "QR");
        put("y", "RSS");
        put("c", "UPCA");
        put("E", "UPCE");
        put("j", "ISBT");
        put("B", "BPO");
        put("C", "CANPOST");
        put("A", "AUSPOST");
        put("f", "IATA25");
        put("q", "CODABLOCK");
        put("J", "JAPOST");
        put("L", "PLANET");
        put("K", "DUTCHPOST");
        put("g", "MSI");
        put("T", "TLC39");
        put("=", "TRIOPTIC");
        put("<", "CODE32");
        put("f", "STRT25");
        put("m", "MATRIX25");
        put("n", "PLESSEY");
        put("Q", "CHINAPOST");
        put("?", "KOREAPOST");
        put("t", "TELEPEN");
        put("o", "CODE16K");
        put("W", "POSICODE");
        put("c", "COUPONCODE");
        put("M", "USPS4CB");
        put("N", "IDTAG");
        put(">", "LABELIV");
        put(",", "LABELV");
        put("I", "GS1_128");
        put("H", "HANXIN");
        put("x", "GRIDMATRIX");
    }};

    public static String getNameCodeID(String sCodeID){
        return myMap.getOrDefault(sCodeID, "ndef");
    }

}
