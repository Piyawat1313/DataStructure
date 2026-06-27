public class WildcardMatching {
    
    public boolean isMatch(String s, String p) {
        // ? ต้องตรงกันทั้งหมด
        // * ตรงกับอักขระตัวใดก็ได้
        if(s.length() == 0 || p.length() == 0){
            return false;
        }

        int l1 = s.length();
        if(p == "*" && s.length() != 0){
            return true;
        }

        else if(p == "?"){
            for (int i = 0; i < l1; i++) {
                if(i + 1 < p.length() && s.charAt(i) != p.charAt(i + 1)){
                    return false;
                }
            }
            return true;
        }
        for (int i = 0; i < l1; i++) {
            if(i + 1 < p.length() && s.charAt(i) == p.charAt(i + 1)){
                return true;
            }
        }
        return false;
    }


    public boolean isMatch2(String s, String p){
        int M = s.length();
        int N = p.length();
        boolean[][] dp = new boolean[M + 1][N + 1]; //สร้างตาราง
        
        dp[0][0] = true;    //สตริงว่างคู่กับแพทเทินว่างถือว่า Match กัน

        // จัดการแถวแรกเมื่อ s เป็นสตริงว่าง แต่ p มีเครื่องหมาย *
        for (int j = 1; j <= N; j++) {
            if(p.charAt(j - 1) == '*'){
                dp[0][j] = dp[0][j - 1];
            }
        }

        // ลูปไล่เติมตาราง DP ให้ครบทุกช่อง
        for(int i = 1; i <= M; i++){
            for(int j = 1; j <= N; j++){
                // ถ้าเจอ ? หรือเจออักษรตรงกัน
                if(p.charAt(j - 1) == '?' || s.charAt(i - 1 ) == p.charAt(j - 1)){
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // ถ้าเจอ * 
                else if(p.charAt(j - 1) == '*'){
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }

        return dp[M][N];

    }

    public static void main(String[] args) {
        WildcardMatching c = new WildcardMatching();
        System.out.println(c.isMatch2("cb", "?a"));
    }
}
