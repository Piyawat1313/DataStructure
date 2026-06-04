public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        int L = strs[0].length();
        for (int i = 0; i < L; i++) {
            char c = strs[0].charAt(i); //เก็บตัวอักษรปัจจุบันที่จะเช็ค

            for (int j = 1; j < strs.length; j++) {
                // ถ้าตำแหน่งมันเกินขอบเขตความยาวของคำนั้น หรือ ตัวอักษรในตำแหน่งที่ i เริ่มไม่ตรงกัน
                if(i == strs[j].length() || strs[j].charAt(i) != c){
                    return strs[0].substring(0, i); //ตัดตั้งแต่ตัวแรกจนถึงตัวปุจจุบัน
                }
            }
        }
        return strs[0];
    }
    public static void main(String[] args) {
        LongestCommonPrefix l = new LongestCommonPrefix();
        System.out.println(l.longestCommonPrefix(new String[]{"flower","flow","flight"}));
    }
}
