package hsm.dataeditjs;

public class Const {

    /**
     * Key for a {@link ResultReceiver}
     */
    public static final String KEY_RESULT_RECEIVER = "resultReceiver";
    /**
     * Key for an array of permissions
     */
    public static final String KEY_PERMISSIONS = "permissions";

    /**
     * Key for an array of permission grant results
     */
    public static final String KEY_GRANT_RESULTS = "grantResults";

    /**
     * Key for a request code
     */
    public static final String KEY_REQUEST_CODE = "requestCode";

    public static final String JS_FILE_NAME = "dataedit.js";

    public  static final String EXTRA_DOLOG = "EXTRA_DOLOG";

    public static final String EXTRA_MY_INTENT = "hdm.dataeditjs.log";


    //from ScanDemo
    public static String getSymbologyNamebyID(String id) {
        int length = Const.SYMBOLOTY_NAME_TABLE.length;
        for (int i = 0; i < length; i++) {
            if (Const.SYMBOLOTY_NAME_TABLE[i][1].equals(id)) {
                return Const.SYMBOLOTY_NAME_TABLE[i][0];
            }
        }
        return null;
    }

    public static String[][] SYMBOLOTY_NAME_TABLE;
    static {
        String[][] r0 = new String[87][];
        r0[0] = new String[]{"2D PQA", ">", "0x3E"};
        r0[1] = new String[]{"Australian Post", "A", "0x41"};
        r0[2] = new String[]{"Auxiliary Port Input 1", "3", "0x33"};
        r0[3] = new String[]{"Auxiliary Port Input 2", "4", "0x34"};
        r0[4] = new String[]{"Aztec Code", "z", "0x7A"};
        r0[5] = new String[]{"Aztec Mesas", "Z", "0x5A"};
        r0[6] = new String[]{"BC412", "G", "0x47"};
        r0[7] = new String[]{"British Post", "B", "0x42"};
        r0[8] = new String[]{"Canadian Post", "C", "0x43"};
        r0[9] = new String[]{"Channel Code", "p", "0x70"};
        r0[10] = new String[]{"China Post", "Q", "0x51"};
        r0[11] = new String[]{"Chinese Sensible Code (Han Xin Code)", "H", "0x48"};
        r0[12] = new String[]{"Codabar", "a", "0x61"};
        r0[13] = new String[]{"Codablock A", "V", "0x56"};
        r0[14] = new String[]{"Codablock F", "q", "0x71"};
        r0[15] = new String[]{"Code 11", "h", "0x68"};
        r0[16] = new String[]{"Code 128", "j", "0x6A"};
        r0[17] = new String[]{"GS1-128 (Formerly UCC/EAN-128)", "I", "0x49"};
        r0[18] = new String[]{"ISBT 128", "j", "0x6A"};
        r0[19] = new String[]{"Code 16K", "o", "0x6F"};
        r0[20] = new String[]{"Code 32 Pharmaceutical (PARAF)", "<", "0x3C"};
        r0[21] = new String[]{"Code 39 (Supports Full ASCII mode)", "b", "0x62"};
        r0[22] = new String[]{"Code 49", "l", "0x6C"};
        r0[23] = new String[]{"Code 93 and 93i", "i", "0x69"};
        r0[24] = new String[]{"Code One", "1", "0x31"};
        r0[25] = new String[]{"Code Z", "u", "0x75"};
        r0[26] = new String[]{"Data Matrix", "w", "0x77"};
        r0[27] = new String[]{"DotCode", ".", "0x2E"};
        r0[28] = new String[]{"EAN-13 (Including Bookland EAN)", "d", "0x64"};
        r0[29] = new String[]{"EAN-8", "D", "0x44"};
        r0[30] = new String[]{"Grid Matrix Code", "X", "0x58"};
        r0[31] = new String[]{"GS1 DataMatrix (with ECI)", "y", "0x79"};
        r0[32] = new String[]{"GS1 Composite (formerly EAN.UCC Composite Symbology)", "y", "0x79"};
        r0[33] = new String[]{"GS1 DataBar (formerly Reduced Space Symbology) Limited", "{", "0x7B"};
        r0[34] = new String[]{"GS1 DataBar (formerly Reduced Space Symbology) Expanded", "}", "0x7D"};
        r0[35] = new String[]{"Host Command Response", "7", "0x37"};
        r0[36] = new String[]{"Host Input", "8", "0x38"};
        r0[37] = new String[]{"InfoMail", ",", "0x2c"};
        r0[38] = new String[]{"Intelligent Mail Barcode (4-State Customer Barcode)", "M", "0x4D"};
        r0[39] = new String[]{"Interleaved 2 of 5", "e", "0x65"};
        r0[40] = new String[]{"Japanese Post", "J", "0x4A"};
        r0[41] = new String[]{"Keyboard", "k", "0x6B"};
        r0[42] = new String[]{"KIX (Netherlands) Post", "K", "0x4B"};
        r0[43] = new String[]{"Korea Post", "?", "0x3F"};
        r0[44] = new String[]{"Label Code", "F", "0x46"};
        r0[45] = new String[]{"Matrix 2 of 5", "m", "0x6D"};
        r0[46] = new String[]{"MaxiCode", "x", "0x78"};
        r0[47] = new String[]{"Menu Command Response", "6", "0x36"};
        r0[48] = new String[]{"Merged Coupon Code", ";", "0x3B"};
        r0[49] = new String[]{"MICR CMC 7 (check reader)", "!", "0x21"};
        r0[50] = new String[]{"MICR E 13 B (check reader)", "\"", "0x22"};
        r0[51] = new String[]{"MicroPDF417", "R", "0x52"};
        r0[52] = new String[]{"MSI", "g", "0x67"};
        r0[53] = new String[]{"MSR AAMVA Track 1", "#", "0x23"};
        r0[54] = new String[]{"MSR AAMVA Track 2", "$", "0x24"};
        r0[55] = new String[]{"MSR AAMVA Track 3", "%", "0x25"};
        r0[56] = new String[]{"MSR California Track 1", "&", "0x26"};
        r0[57] = new String[]{"MSR California Track 2", "'", "0x27"};
        r0[58] = new String[]{"MSR California Track 3", "(", "0x28"};
        r0[59] = new String[]{"MSR Track 1", ")", "0x29"};
        r0[60] = new String[]{"MSR Track 2", "*", "0x2A"};
        r0[61] = new String[]{"MSR Track 3", "+", "0x2B"};
        r0[62] = new String[]{"NEC 2 of 5", "Y", "0x59"};
        r0[63] = new String[]{"OCR MICR (E 13 B)", "O", "0x4F"};
        r0[64] = new String[]{"OCR SEMI Font", "O", "0x4F"};
        r0[65] = new String[]{"OCR US Money Font", "O", "0x4F"};
        r0[66] = new String[]{"OCR-A", "O", "0x4F"};
        r0[67] = new String[]{"OCR-B", "O", "0x4F"};
        r0[68] = new String[]{"PDF417", "r", "0x72"};
        r0[69] = new String[]{"Planet Code", "L", "0x4C"};
        r0[70] = new String[]{"Plessey Code", "n", "0x6E"};
        r0[71] = new String[]{"PosiCode", "W", "0x57"};
        r0[72] = new String[]{"Postal-4i (ID-tag, UPU 4-State)", "N", "0x4E"};
        r0[73] = new String[]{"Postnet", "P", "0x50"};
        r0[74] = new String[]{"QR Code and Micro QR Code", "s", "0x73"};
        r0[75] = new String[]{"Micro QR (separate Code ID enabled)", "-", "0x2D"};
        r0[76] = new String[]{"Quantity", "5", "0x35"};
        r0[77] = new String[]{"SecureCode", "S", "0x53"};
        r0[78] = new String[]{"Send From Script", "9", "0x39"};
        r0[79] = new String[]{"Straight 2 of 5", "f", "0x66"};
        r0[80] = new String[]{"TCIF Linked Code 39 (TLC39)", "T", "0x54"};
        r0[81] = new String[]{"Telepen", "t", "0x74"};
        r0[82] = new String[]{"Trioptic Code", "=", "0x3D"};
        r0[83] = new String[]{"Ultracode", "U", "0x55"};
        r0[84] = new String[]{"UPC-A", "c", "0x63"};
        r0[85] = new String[]{"UPC-E", "E", "0x45"};
        r0[86] = new String[]{"Vericode", "v", "0x76"};
        SYMBOLOTY_NAME_TABLE = r0;
    }
}
