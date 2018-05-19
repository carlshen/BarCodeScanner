package cn.carl.demo;


public class Constants {

    // 网址
    public static String URL = "";
    public final static String BAR_TYPE = "bar_type";
    public final static String BAR_RESULT = "bar_type";


    private Constants() {
    }

    static Constants instatce = new Constants();

    public static Constants getInstance() {
        return instatce;
    }

}
