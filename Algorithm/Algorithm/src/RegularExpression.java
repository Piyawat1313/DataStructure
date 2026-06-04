public class RegularExpression {

    // Logic เดิมต่อเช็คเงื่อนไขใหม่ test 299/354
    public boolean isMatch(String s, String p) {
        int count = 0;
        for (int i = 0; i < p.length(); i++) {
            if( i + 1 < p.length() && p.charAt(i + 1) == '*'){
                System.out.println(1);
                char ch = p.charAt(i);
                while (count < s.length() && (s.charAt(count) == ch || ch == '.')) {
                    System.out.println(2);
                    count++;
                }
                i++;
            }
            else if(p.charAt(i) == '.'){
                if(count < s.length()){
                    System.out.println(3);
                    count++;
                }
                else{
                    System.out.println(4);
                    return false;
                }
            }
            else{
                if(count < s.length() && s.charAt(count) == p.charAt(i)){
                    System.out.println(5);
                    count++;
                }
                else{
                    System.out.println(6);
                    return false;
                }
            }
        }
        return count == s.length();
    }

    // AI test 354/354 method: Dynamic Programming
    public boolean testRexgular1(String s, String p){
        int m = s.length();
        int n = p.length();
        // m กับ n ต้องเหมือนกันหรือไม่
        boolean[][] dp = new boolean[m + 1][n + 1];

        dp[0][0] = true;    //สตริงทั้งคู่ว่าง ถือว่าเข้ากันได้

        // จัดการเคสที่ s เป็นสตริงว่าง แต่ p มี * 
        for (int i = 2; i <= n; i++) {
            if(p.charAt(i - 1) == '*'){
                dp[0][i] = dp[0][i - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // ตัวอักษรตรงกัน หรือ แพทเทินเป็น . แทนตัวอะไรก็ได้
                if(p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '.'){
                    dp[i][j] = dp[i - 1][j - 1];
                }

                //เจอเครื่องหมาย *  
                else if(p.charAt(j - 1) == '*'){
                    dp[i][j] = dp[i][j - 2];    //มีตัวอักษรก่อนหน้า 0 ตัว column ให้ข้ามไปสองตำแหน่ง

                    // ถ้าตัวอักษรก่อนหน้ามี * ตรงกับ s ปัจจุบัน หรือ .
                    // ให้ขยับตัวชี้สตริง s ถอยหลังไปเช็คตัวก่อนหน้า
                    if(p.charAt(j - 2) == s.charAt(i - 1) || p.charAt(j - 2) == '.'){
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                }
            }
        }
        return dp[m][n];
    }

    // logic ที่คิดเอง test 234/354
    public boolean testRexgular2(String s, String p){
        int count = 0;
        for (int i = 0; i < p.length(); i++) {
            if(p.charAt(i) == '*'){
                for (int j = 0; j < s.length(); j++) {
                    for (int j2 = j + 1; j2 < s.length(); j2++) {
                        if(s.charAt(j) == s.charAt(j2)){
                            System.out.println(1);
                            return true;
                        }
                    }
                }
            }
            else if(p.charAt(i) == '.'){
                for (int j = 0; j < s.length(); j++) {
                    for (int j2 = j + 1; j2 < s.length(); j2++) {
                        if(s.charAt(j) == s.charAt(j2)){
                            System.out.println(2);
                            count++;
                        }
                        if (count == 1) {
                            System.out.println(3);
                            return true;
                        }
                        if(s.charAt(j) != s.charAt(j2)){
                            System.out.println(4);
                            return true;
                        }
                    }
                }
            }
            else if(p.charAt(i) == '*' && p.charAt(i) == '.'){
                System.out.println(5);
                return true;
            }
            else if(!p.startsWith("*") || !p.startsWith(".")){
                System.out.println(6);
                return false;
            }
            else if(p.endsWith("*")){
                return true;
            }
            else if(p.endsWith(".")){
                return true;
            }
        }
        return false;
    }
    // test 316/354
    public boolean testRexgular3(String s, String p){
        int indexS = 0;
        int indexP = 0;

        int startIdx = -1;
        int sTmpIdx = -1;

        while (indexS < s.length()) {
            
            if(indexP < p.length() && (indexP + 1 < p.length() && p.charAt(indexP + 1) == '*') ){

                startIdx = indexP;
                sTmpIdx = indexS;

                indexP += 2;
            }

            else if(indexP < p.length() && (p.charAt(indexP) == s.charAt(indexS) || p.charAt(indexP) == '.')){
                indexS++;
                indexP++;
            }

            else if(startIdx != -1){
                indexP = startIdx + 2;

                sTmpIdx++;

                if(p.charAt(startIdx) == s.charAt(sTmpIdx - 1) || p.charAt(startIdx) == '.'){
                    indexS = sTmpIdx;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }

        while (indexP + 1 < p.length() && p.charAt(indexP + 1) == '*') {
            indexP += 2;
        }

        return indexP == p.length();
    }

    public static void main(String[] args) {
        RegularExpression r = new RegularExpression();
        System.out.println(r.testRexgular3("aaa", "a*a"));
    }
}
