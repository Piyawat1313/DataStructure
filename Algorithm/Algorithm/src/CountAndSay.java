public class CountAndSay {
    public String countAndSay(int n) {
        String current = "1";

        // สร้างค่าจนครบ n ตัว
        for(int i = 1; i < n; i++){
            StringBuilder next = new StringBuilder();
            int count = 1;

            // อ่านค่าจากสตริงปัจจุบัน
            for (int j = 0; j < current.length(); j++) {
                
                // ถ้าเป็นตัวสุดท้าย หรือ ตัวถัดไปไม่เหมือนกัน
                if(j + 1 == current.length() || current.charAt(j) != current.charAt(j + 1)){
                    next.append(count).append(current.charAt(j));
                    count = 1;  //reset ตัวนับ
                }
                else{
                    count++;    //ตัวถัดไปเหมือนกัน ให้นับเพิ่ม
                }
            }
            current = next.toString();
        }
        return current;
    }

    public static void main(String[] args) {
        CountAndSay c = new CountAndSay();
        System.out.println(c.countAndSay(4));
    }
}
