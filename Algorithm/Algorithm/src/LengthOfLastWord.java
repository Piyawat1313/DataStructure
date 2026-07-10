public class LengthOfLastWord {
     public int lengthOfLastWord(String s) {
        int result = 0;
        s = s.trim();   //ตัดช่องว่าง
        s = s.substring(s.lastIndexOf(" ") + 1);  //ดึงข้อความตัวสุดท้าย  
        result = s.length();
        return result;
    }

    public static void main(String[] args) {
        LengthOfLastWord l = new LengthOfLastWord();
        System.out.println(l.lengthOfLastWord("Hello World"));
        System.out.println(l.lengthOfLastWord("   fly me   to   the moon  "));
    }
}
