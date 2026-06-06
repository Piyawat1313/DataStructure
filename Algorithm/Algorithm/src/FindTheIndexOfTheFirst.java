public class FindTheIndexOfTheFirst {
    public int strStr(String haystack, String needle) {
        if (haystack.length() == 0 || needle.length() == 0) {
            return 0;
        }
        int index = haystack.indexOf(needle);   //คำที่ต้องการอยู่ตำแหน่งไหน มันจะหาคำที่เจอตัวแรกเสมอ
        if(index != -1){
            return index;
        }
        return -1; 
    }

    public static void main(String[] args) {
        FindTheIndexOfTheFirst f = new FindTheIndexOfTheFirst();
        System.out.println(f.strStr("sadbutsad", "sad"));
        System.out.println(f.strStr("hello", "ll"));
    }
}
