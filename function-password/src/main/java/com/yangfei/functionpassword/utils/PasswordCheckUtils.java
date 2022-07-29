package com.yangfei.functionpassword.utils;

import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * <p>
 * 多规则校验密码合法性
 * </p>
 *
 * @author yangfei
 * @since 2022/6/15 17:29
 */
public class PasswordCheckUtils {
    /**
     * 密码口令检测对应系统等级
     */
    public static String SYSTEM_GRADE;

    /**
     * 是否检测密码口令长度标识
     */
    public static String CHECK_PASSWORD_LENGTH;
    /**
     * 密码最小长度，默认为8
     */
    public static String MIN_LENGTH;
    /**
     * 密码最大长度，默认为20
     */
    public static String MAX_LENGTH;

    /**
     * 是否包含数字
     */
    public static String CHECK_CONTAIN_DIGIT;
    /**
     * 是否区分大小写
     */
    public static String CHECK_DISTINGGUISH_CASE;
    /**
     * 是否包含小写字母
     */
    public static String CHECK_LOWER_CASE;
    /**
     * 是否包含大写字母
     */
    public static String CHECK_UPPER_CASE;
    /**
     * 是否包含特殊符号
     */
    public static String CHECK_CONTAIN_SPECIAL_CHAR;
    /**
     * 特殊符号集合
     */
    public static String DEFAULT_SPECIAL_CHAR="!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static String SPECIAL_CHAR;

    /**
     * 是否检测键盘按键横向连续
     */
    public static String CHECK_HORIZONTAL_KEY_SEQUENTIAL;
    /**
     * 键盘物理位置横向不允许最小的连续个数
     */
    public static String LIMIT_HORIZONTAL_NUM_KEY;
    /**
     * 是否检测键盘按键斜向连续
     */
    public static String CHECK_SLOPE_KEY_SEQUENTIAL;
    /**
     * 键盘物理位置斜向不允许最小的连续个数
     */
    public static String LIMIT_SLOPE_NUM_KEY;

    /**
     * 是否检测逻辑位置连续
     */
    public static String CHECK_LOGIC_SEQUENTIAL;
    /**
     * 密码口令中字符在逻辑位置上不允许最小的连续个数
     */
    public static String LIMIT_LOGIC_NUM_CHAR;

    /**
     * 是否检测连续字符相同
     */
    public static String CHECK_SEQUENTIAL_CHAR_SAME;
    /**
     * 密码口令中相同字符不允许最小的连续个数
     */
    public static String LIMIT_NUM_SAME_CHAR;

    /**
     * 键盘横向方向规则
     */
    public static String[] KEYBOARD_HORIZONTAL_ARR = {
            "01234567890",
            "qwertyuiop",
            "asdfghjkl",
            "zxcvbnm",
    };
    /**
     * 键盘斜线方向规则
     */
    public static String[] KEYBOARD_SLOPE_ARR = {
            "1qaz",
            "2wsx",
            "3edc",
            "4rfv",
            "5tgb",
            "6yhn",
            "7ujm",
            "8ik,",
            "9ol.",
            "0p;/",
            "=[;.",
            "-pl,",
            "0okm",
            "9ijn",
            "8uhb",
            "7ygv",
            "6tfc",
            "5rdx",
            "4esz"
    };
    static {
        Properties prop = new Properties();

        try{
            //读取属性文件enc.properties
            //InputStream in = new BufferedInputStream(new FileInputStream("password.properties"));
            ResourceBundle password = ResourceBundle.getBundle("password");
            Iterator<String> it = password.keySet().iterator();
            //prop.load(in);
            //Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()) {
                String key = it.next();

                if (key.equals("systemGrade")) {
                    SYSTEM_GRADE = password.getString(key);
                }

                if (key.equals("checkPasswordLength")) {
                    CHECK_PASSWORD_LENGTH = password.getString(key);
                }
                if (key.equals("limitPassMinLength")) {
                    MIN_LENGTH = password.getString(key);
                }
                if (key.equals("limitPassMaxLength")) {
                    MAX_LENGTH = password.getString(key);
                }

                if (key.equals("checkContainDigit")) {
                    CHECK_CONTAIN_DIGIT = password.getString(key);
                }
                if (key.equals("checkContainUpperLowerCase")) {
                    CHECK_DISTINGGUISH_CASE = password.getString(key);
                }
                if (key.equals("checkContainLowerCase")) {
                    CHECK_LOWER_CASE = password.getString(key);
                }
                if (key.equals("checkContainUpperCase")) {
                    CHECK_UPPER_CASE = password.getString(key);
                }
                if (key.equals("checkContainSpecialChar")) {
                    CHECK_CONTAIN_SPECIAL_CHAR = password.getString(key);
                }
                if (key.equals("specialCharSet")) {
                    SPECIAL_CHAR = password.getString(key);
                }

                if (key.equals("checkHorizontalKeySequential")) {
                    CHECK_HORIZONTAL_KEY_SEQUENTIAL = password.getString(key);
                }
                if (key.equals("horizontalKeyLimitNum")) {
                    LIMIT_HORIZONTAL_NUM_KEY = password.getString(key);
                }
                if (key.equals("checkSlopeKeySequential")) {
                    CHECK_SLOPE_KEY_SEQUENTIAL = password.getString(key);
                }
                if (key.equals("slopeKeyLimitNum")) {
                    LIMIT_SLOPE_NUM_KEY = password.getString(key);
                }

                if (key.equals("checkLogicSequential")) {
                    CHECK_LOGIC_SEQUENTIAL = password.getString(key);
                }
                if (key.equals("logicLimitNum")) {
                    LIMIT_LOGIC_NUM_CHAR = password.getString(key);
                }

                if (key.equals("checkSequentialCharSame")) {
                    CHECK_SEQUENTIAL_CHAR_SAME = password.getString(key);
                }
                if (key.equals("sequentialCharNum")) {
                    LIMIT_NUM_SAME_CHAR = password.getString(key);
                }

            }
            //in.close();
            if("2".equals(SYSTEM_GRADE) || "3".equals(SYSTEM_GRADE) ) {

                if("".equals(CHECK_PASSWORD_LENGTH)){
                    CHECK_PASSWORD_LENGTH = "enable";
                    MIN_LENGTH = "8";
                    MAX_LENGTH = "20";
                }
                if("".equals(CHECK_CONTAIN_DIGIT)) {
                    CHECK_CONTAIN_DIGIT = "enable";
                }
                if("".equals(CHECK_DISTINGGUISH_CASE)) {
                    CHECK_DISTINGGUISH_CASE = "disable";
                }
                if("".equals(CHECK_LOWER_CASE)) {
                    CHECK_LOWER_CASE = "enable";
                }
                if("".equals(CHECK_UPPER_CASE)) {
                    CHECK_UPPER_CASE = "enable";
                }
                if("".equals(CHECK_CONTAIN_SPECIAL_CHAR)) {
                    if("2".equals(SYSTEM_GRADE)) {
                        CHECK_CONTAIN_SPECIAL_CHAR = "disable";
                    }else{
                        CHECK_CONTAIN_SPECIAL_CHAR = "enable";
                        if("".equals(SPECIAL_CHAR)) {
                            SPECIAL_CHAR = DEFAULT_SPECIAL_CHAR;
                        }
                    }
                }

                if("".equals(CHECK_HORIZONTAL_KEY_SEQUENTIAL)) {
                    CHECK_HORIZONTAL_KEY_SEQUENTIAL = "enable";
                    if("2".equals(SYSTEM_GRADE)) {
                        LIMIT_HORIZONTAL_NUM_KEY = "4";
                    }else{
                        LIMIT_HORIZONTAL_NUM_KEY = "3";
                    }
                }

                if("".equals(CHECK_SLOPE_KEY_SEQUENTIAL)) {
                    CHECK_SLOPE_KEY_SEQUENTIAL = "enable";
                    if("2".equals(SYSTEM_GRADE)) {
                        LIMIT_SLOPE_NUM_KEY = "4";
                    }else{
                        LIMIT_SLOPE_NUM_KEY = "3";
                    }
                }

                if("".equals(CHECK_LOGIC_SEQUENTIAL)) {
                    CHECK_LOGIC_SEQUENTIAL = "enable";
                    if("2".equals(SYSTEM_GRADE)) {
                        LIMIT_LOGIC_NUM_CHAR = "4";
                    }else{
                        LIMIT_LOGIC_NUM_CHAR = "3";
                    }

                }
                if("".equals(CHECK_SEQUENTIAL_CHAR_SAME)) {
                    CHECK_SEQUENTIAL_CHAR_SAME = "enable";
                    if("2".equals(SYSTEM_GRADE)) {
                        LIMIT_NUM_SAME_CHAR = "4";
                    }else{
                        LIMIT_NUM_SAME_CHAR = "3";
                    }
                }
            }else{
                SYSTEM_GRADE = "3";
                CHECK_PASSWORD_LENGTH = "enable";
                MIN_LENGTH = "8";
                MAX_LENGTH = "20";
                CHECK_CONTAIN_DIGIT = "enable";
                CHECK_LOWER_CASE = "enable";
                CHECK_UPPER_CASE = "enable";
                CHECK_CONTAIN_SPECIAL_CHAR = "enable";
                CHECK_HORIZONTAL_KEY_SEQUENTIAL = "enable";
                LIMIT_HORIZONTAL_NUM_KEY = "3";
                CHECK_SLOPE_KEY_SEQUENTIAL = "enable";
                LIMIT_SLOPE_NUM_KEY = "3";
                CHECK_LOGIC_SEQUENTIAL = "enable";
                LIMIT_LOGIC_NUM_CHAR = "3";
                CHECK_SEQUENTIAL_CHAR_SAME = "enable";
                LIMIT_NUM_SAME_CHAR = "3";
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    /**
     *  检测密码中字符长度
     * @param[in] password            密码字符串
     * @return  符合长度要求 返回true
     */
    public static boolean checkPasswordLength(String password) {
        boolean flag =false;

        if("".equals(MAX_LENGTH)) {
            if (password.length() >= Integer.parseInt(MIN_LENGTH)) {
                flag = true;
            }
        }else{
            if (password.length() >= Integer.parseInt(MIN_LENGTH) &&
                    password.length() <= Integer.parseInt(MAX_LENGTH)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     *  检测密码中是否包含数字
     * @param[in] password            密码字符串
     * @return  包含数字 返回true
     */
    public static boolean checkContainDigit(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int num_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isDigit(chPass[i])) {
                num_count++;
            }
        }

        if (num_count >= 1){
            flag = true;
        }
        return flag;
    }

    /**
     *  检测密码中是否包含字母（不区分大小写）
     * @param[in] password            密码字符串
     * @return  包含字母 返回true
     */
    public static boolean checkContainCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int char_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isLetter(chPass[i])) {
                char_count++;
            }
        }

        if (char_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     *  检测密码中是否包含小写字母
     * @param[in] password            密码字符串
     * @return  包含小写字母 返回true
     */
    public static boolean checkContainLowerCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int char_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isLowerCase(chPass[i])) {
                char_count++;
            }
        }

        if (char_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     *  检测密码中是否包含大写字母
     * @param[in] password            密码字符串
     * @return  包含大写字母 返回true
     */
    public static boolean checkContainUpperCase(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int char_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (Character.isUpperCase(chPass[i])) {
                char_count++;
            }
        }

        if (char_count >= 1) {
            flag = true;
        }
        return flag;
    }

    /**
     *  检测密码中是否包含特殊符号
     * @param[in] password            密码字符串
     * @return  包含特殊符号 返回true
     */
    public static boolean checkContainSpecialChar(String password) {
        char[] chPass = password.toCharArray();
        boolean flag = false;
        int special_count = 0;

        for (int i = 0; i < chPass.length; i++) {
            if (SPECIAL_CHAR.indexOf(chPass[i]) != -1) {
                special_count++;
            }
        }

        if (special_count >= 1){
            flag = true;
        }
        return flag;
    }

    /**
     *  键盘规则匹配器 横向连续检测
     * @param[in] password            密码字符串
     * @return  含有横向连续字符串 返回true
     */
    public static boolean checkLateralKeyboardSite(String password) {
        String t_password = new String(password);
        //将所有输入字符转为小写
        t_password = t_password.toLowerCase();
        int n = t_password.length();
        /**
         * 键盘横向规则检测
         */
        boolean flag = false;
        int arrLen = KEYBOARD_HORIZONTAL_ARR.length;
        int limit_num = Integer.parseInt(LIMIT_HORIZONTAL_NUM_KEY) ;

        for(int i=0; i+limit_num<=n; i++) {
            String str = t_password.substring(i, i+limit_num);
            String distinguishStr = password.substring(i, i+limit_num);

            for(int j=0; j<arrLen; j++) {
                String configStr = KEYBOARD_HORIZONTAL_ARR[j];
                String revOrderStr = new StringBuffer(KEYBOARD_HORIZONTAL_ARR[j]).reverse().toString();

                //检测包含字母(区分大小写)
                if ("enable".equals(CHECK_DISTINGGUISH_CASE)) {
                    //考虑 大写键盘匹配的情况
                    String UpperStr = KEYBOARD_HORIZONTAL_ARR[j].toUpperCase();
                    if((configStr.indexOf(distinguishStr) != -1) || (UpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                    //考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(UpperStr).reverse().toString();
                    if((revOrderStr.indexOf(distinguishStr) != -1) || (revUpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                }else {
                    if(configStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                    //考虑逆序输入情况下 连续输入
                    if(revOrderStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                }
            }
        }
        return flag;
    }

    /**
     *  键盘规则匹配器 斜向规则检测
     * @param[in] password            密码字符串
     * @return  含有斜向连续字符串 返回true
     */
    public static boolean checkKeyboardSlantSite(String password) {
        String t_password = new String(password);
        t_password = t_password.toLowerCase();
        int n = t_password.length();
        /**
         * 键盘斜线方向规则检测
         */
        boolean flag = false;
        int arrLen = KEYBOARD_SLOPE_ARR.length;
        int limit_num = Integer.parseInt(LIMIT_SLOPE_NUM_KEY);

        for(int i=0; i+limit_num<=n; i++) {
            String str = t_password.substring(i, i+limit_num);
            String distinguishStr = password.substring(i, i+limit_num);
            for(int j=0; j<arrLen; j++) {
                String configStr = KEYBOARD_SLOPE_ARR[j];
                String revOrderStr = new StringBuffer(KEYBOARD_SLOPE_ARR[j]).reverse().toString();
                //检测包含字母(区分大小写)
                if ("enable".equals(CHECK_DISTINGGUISH_CASE)) {

                    //考虑 大写键盘匹配的情况
                    String UpperStr = KEYBOARD_SLOPE_ARR[j].toUpperCase();
                    if((configStr.indexOf(distinguishStr) != -1) || (UpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                    //考虑逆序输入情况下 连续输入
                    String revUpperStr = new StringBuffer(UpperStr).reverse().toString();
                    if((revOrderStr.indexOf(distinguishStr) != -1) || (revUpperStr.indexOf(distinguishStr) != -1)) {
                        flag = true;
                        return flag;
                    }
                }else {
                    if(configStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                    //考虑逆序输入情况下 连续输入
                    if(revOrderStr.indexOf(str) != -1) {
                        flag = true;
                        return flag;
                    }
                }
            }
        }
        return flag;
    }

    /**
     *  评估a-z,z-a这样的连续字符
     * @param[in] password            密码字符串
     * @return  含有a-z,z-a连续字符串 返回true
     */
    public static boolean checkSequentialChars(String password) {
        String t_password = new String(password);
        boolean flag = false;
        int limit_num = Integer.parseInt(LIMIT_LOGIC_NUM_CHAR);
        int normal_count = 0;
        int reversed_count = 0;

        //检测包含字母(区分大小写)
        if ("enable".equals(CHECK_DISTINGGUISH_CASE)) {

        }else{
            t_password = t_password.toLowerCase();
        }
        int n = t_password.length();
        char[] pwdCharArr = t_password.toCharArray();

        for (int i=0; i+limit_num<=n; i++) {
            normal_count = 0;
            reversed_count = 0;
            for (int j=0; j<limit_num-1; j++) {
                if (pwdCharArr[i+j+1]-pwdCharArr[i+j]==1) {
                    normal_count++;
                    if(normal_count == limit_num -1){
                        return true;
                    }
                }

                if (pwdCharArr[i+j]-pwdCharArr[i+j+1]==1) {
                    reversed_count++;
                    if(reversed_count == limit_num -1){
                        return true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     *  评估aaaa,1111这样的相同连续字符
     * @param[in] password            密码字符串
     * @return  含有aaaa,1111等连续字符串 返回true
     */
    public static boolean checkSequentialSameChars(String password) {
        String t_password = new String(password);
        int n = t_password.length();
        char[] pwdCharArr = t_password.toCharArray();
        boolean flag = false;
        int limit_num = Integer.parseInt(LIMIT_NUM_SAME_CHAR);
        int count = 0;
        for (int i=0; i+limit_num<=n; i++) {
            count=0;
            for (int j=0; j<limit_num-1; j++) {
                if(pwdCharArr[i+j] == pwdCharArr[i+j+1]) {
                    count++;
                    if (count == limit_num -1){
                        return true;
                    }
                }
            }
        }
        return flag;
    }

    /**
     *  评估密码中包含的字符类型是否符合要求
     * @param[in] password            密码字符串
     * @return  符合要求 返回true
     */
    public static String EvalPWD(String password) {
        if (password == null || "".equals(password)) {
            return "密码不能为空";
        }
        boolean flag = false;

        /**
         * 检测长度
         */
        if ("enable".equals(CHECK_PASSWORD_LENGTH)){
            flag = checkPasswordLength(password);
            if (!flag) {
                return "密码长度为8-20位，密码可设置字母、数字、特殊符号（至少要包含数字和字母）";
            }
        }

        /**
         * 检测包含数字
         */
        if ("enable".equals(CHECK_CONTAIN_DIGIT)){
            flag = checkContainDigit(password);
            if (!flag) {
                return "密码长度为8-20位，密码可设置字母、数字、特殊符号（至少要包含数字和字母）";
            }
        }

        /**
         * 检测包含字母(区分大小写)
         */
        if ("enable".equals(CHECK_DISTINGGUISH_CASE)){
            //检测包含小写字母
            if ("enable".equals(CHECK_LOWER_CASE)){
                flag = checkContainLowerCase(password);
                if (!flag) {
                    return "密码必须包含小写字母";
                }
            }

            //检测包含大写字母
            if ("enable".equals(CHECK_UPPER_CASE)){
                flag = checkContainUpperCase(password);
                if (!flag) {
                    return "密码必须包含大写字母";
                }
            }
        }else {
            flag = checkContainCase(password);
            if (!flag) {
                return "密码长度为8-20位，密码可设置字母、数字、特殊符号（至少要包含数字和字母）";
            }
        }

        /**
         * 检测包含特殊符号
         */
        if ("enable".equals(CHECK_CONTAIN_SPECIAL_CHAR)){
            flag = checkContainSpecialChar(password);
            if (!flag) {
                return "密码必须包含特殊符号";
            }
        }

        /**
         * 检测键盘横向连续
         */
        if ("enable".equals(CHECK_HORIZONTAL_KEY_SEQUENTIAL)){
            flag = checkLateralKeyboardSite(password);
            if (flag) {
                return "字母，数字连续字符不可超过三位";
            }
        }

        /**
         * 检测键盘斜向连续
         */
        if ("enable".equals(CHECK_SLOPE_KEY_SEQUENTIAL)){
            flag = checkKeyboardSlantSite(password);
            if (flag) {
                return "字母，数字连续字符不可超过三位";
            }
        }

        /**
         * 检测逻辑位置连续
         */
        if ("enable".equals(CHECK_LOGIC_SEQUENTIAL)){
            flag = checkSequentialChars(password);
            if (flag) {
                return "字母，数字连续字符不可超过三位";
            }
        }

        /**
         * 检测相邻字符是否相同
         */
        if ("enable".equals(CHECK_SEQUENTIAL_CHAR_SAME)){
            flag = checkSequentialSameChars(password);
            if (flag) {
                return "字母，数字相同字符最多可设置三位";
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String flag = null;
        String[] testPass = {
                "aigjfe1234",
                "aigjfea123",
                "aigjfea12",
                "fjodjfip12345",
                "fjodjfipqWe",
                "fjod1jfipqwe"
        };
        for (int i = 0; i < testPass.length; i++) {
            System.out.printf("testpass[%d] = %s\n", i,testPass[i]);
            flag = PasswordCheckUtils.EvalPWD(testPass[i]);
            if (flag == null) {
                System.out.println( flag + " secret pass.\n +");
            } else {
                System.out.println(flag + " secret failed.\n");
            }
        }
    }
}
