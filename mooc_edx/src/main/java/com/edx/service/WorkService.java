package com.edx.service;

import com.edx.dao.UserDao;
import com.edx.dao.WorkDao;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.sun.javafx.binding.DoubleConstant;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2018/5/17.
 */
public class WorkService {


    private final static WorkDao workDao = new WorkDao();


//    /**
//     * 柳青代码模块！
//     * 返回一级菜单的权重，直接查询
//     * 查询的数据表为 liuqing_menu
//     *
//     * 实现思路:
//     * 1.从liuqing_menu表中查询到相应的一级菜单权重，存储到变量名为menu  ok
//     * 的double数组中。
//     * 2.分别查询对应的menu1，menu2，menu3的指标名称及权重，然后返回map  ok
//     * 分为3个方法分别进行运算。（删）
//     * 改成写一个方法，之后返回处理好的对应的值
//     * menu[0],menu[1],menu[2] 为一级菜单获取数据库的权重
//     * str1 str4 str7 对应的二级菜单的名字
//     * str2 str5 str8 对应的二级菜单的值，1或者2 （0代表未使用，1代表使用，2代表只判断是否大于0）
//     * str3 str6 str9 对应的二级菜单的权重，小数（0.3）
//     * 3.最后进行统一的返回，一级菜单权重，一级菜单名字，二级菜单名字及权重
//     *
//     * @return
//     */
//    public Map<String, Object> getMenuWeight(String id) throws SQLException {
//
//        HashMap<String,String> menuMap = new HashMap();
//        menuMap =workDao.getMenuWeight();
//        double[] menu = new double[3];
//
//        /**
//         * menu[0-3]指一级指标的权重，分别存储为0，1，2
//         */
//        menu[0]=Double.parseDouble(menuMap.get("menu1"));
//        menu[1]= Double.parseDouble(menuMap.get("menu2"));
//        menu[2]= Double.parseDouble(menuMap.get("menu3"));
//
//        /**
//         * 注意这里查询之后有空值null
//         * 之后需要进行处理
//         */
//        Map map = workDao.getMenu1Weight();
//        String[] str1 = (String[]) map.get("str1");
//        String[] str2 = (String[]) map.get("str2");
//        String[] str3 = (String[]) map.get("str3");
//
//        /**
//         * 第二个二级菜单
//         */
//        map = null;
//        map = workDao.getMenu2Weight();
//        String[] str4 = (String[]) map.get("str1");
//        String[] str5 = (String[]) map.get("str2");
//        String[] str6 = (String[]) map.get("str3");
//        /**
//         * 第三个二级菜单
//         */
//
//        map = null;
//        map = workDao.getMenu3Weight();
//        String[] str7 = (String[]) map.get("str1");
//        String[] str8 = (String[]) map.get("str2");
//        String[] str9 = (String[]) map.get("str3");
//
//        str2 = getMenuValue(str1,str2,id);
//        str5 = getMenuValue(str4,str5,id);
//        str8 = getMenuValue(str7,str8,id);
//
//        /**
//         * 这里交换之后，str1，4，7 是二级菜单名字
//         * str2，5，8 是最后得到的满分是100分的值
//         * str3，6，9 是二级菜单的权重
//         */
//
//        //TODO
//        str1 = changeString(str1);
//        str2 = changeString(str2);
//        str3 = changeString(str3);
//        str4 = changeString(str4);
//        str5 = changeString(str5);
//        str6 = changeString(str6);
//        str7 = changeString(str7);
//        str8 = changeString(str8);
//        str9 = changeString(str9);
//        Map<String,Object> resMap = new HashMap<>();
//        double resdouble =0;
//        String[] oneValue = new String[3];
//        for(int i=0;i<3;i++){
//            for(int j=0;j<str1.length;j++){
//                resdouble=resdouble+Double.valueOf(str2[j])* Double.valueOf(str3[j]);
//            }
//            oneValue[i]=String.valueOf((int)resdouble);
//            resdouble = 0.0;
//        }
//        resMap.put("oneWeight",menu);
//        resMap.put("oneValue",oneValue);
//        resMap.put("menu1",str1);
//        resMap.put("menu2",str4);
//        resMap.put("menu3",str7);
//        resMap.put("value1",str2);
//        resMap.put("value2",str5);
//        resMap.put("value3",str8);
//        resMap.put("weight1",str3);
//        resMap.put("weight2",str6);
//        resMap.put("weight3",str9);
//        return resMap;
//        /**
//         *
//         * oneMenu=["您在学习过程与方法中表现为不及格！","您在学习态度与情感评价中表现为优秀，请继续保持！","您在学习效果与能力评价中表现为良好，请继续加油！","您再本门课的综合表现为优秀，请继续保持！"]
//         * oneValue=["30","23","21"]
//         */
//
//    }


    /**
     //     * 柳青代码模块！
     //     * 返回一级菜单的权重，直接查询
     //     * 查询的数据表为 liuqing_menu
     //     *
     //     * 实现思路:
     //     * 1.从liuqing_menu表中查询到相应的一级菜单权重，存储到变量名为menu  ok
     //     * 的double数组中。
     //     * 2.分别查询对应的menu1，menu2，menu3的指标名称及权重，然后返回map  ok
     //     * 分为3个方法分别进行运算。（删）
     //     * 改成写一个方法，之后返回处理好的对应的值
     //     * menu[0],menu[1],menu[2] 为一级菜单获取数据库的权重
     //     * str1 str4 str7 对应的二级菜单的名字
     //     * str2 str5 str8 对应的二级菜单的值，1或者2 （0代表未使用，1代表使用，2代表只判断是否大于0）
     //     * str3 str6 str9 对应的二级菜单的权重，小数（0.3）
     //     * 3.最后进行统一的返回，一级菜单权重，一级菜单名字，二级菜单名字及权重
     //     *
     //     * @return
     //     */
    public Map<String, Object> getMenuWeight(String id) throws SQLException {

        HashMap<String,String> menuMap = new HashMap();
        menuMap =workDao.getMenuWeight();
        double[] menu = new double[3];

        /**
         * menu[0-3]指一级指标的权重，分别存储为0，1，2
         */
        menu[0]=Double.parseDouble(menuMap.get("menu1"));
        menu[1]= Double.parseDouble(menuMap.get("menu2"));
        menu[2]= Double.parseDouble(menuMap.get("menu3"));

        /**
         * 注意这里查询之后有空值null
         * 之后需要进行处理
         */
        Map map = workDao.getMenu1Weight();
        String[] str1 = (String[]) map.get("str1");
        String[] str2 = (String[]) map.get("str2");
        String[] str3 = (String[]) map.get("str3");

        /**
         * 第二个二级菜单
         */
        map = null;
        map = workDao.getMenu2Weight();
        String[] str4 = (String[]) map.get("str1");
        String[] str5 = (String[]) map.get("str2");
        String[] str6 = (String[]) map.get("str3");
        /**
         * 第三个二级菜单
         */

        map = null;
        map = workDao.getMenu3Weight();
        String[] str7 = (String[]) map.get("str1");
        String[] str8 = (String[]) map.get("str2");
        String[] str9 = (String[]) map.get("str3");

        str2 = getMenuValue(str1,str2,id);
        str5 = getMenuValue(str4,str5,id);
        str8 = getMenuValue(str7,str8,id);

        /**
         * 这里交换之后，str1，4，7 是二级菜单名字
         * str2，5，8 是最后得到的满分是100分的值
         * str3，6，9 是二级菜单的权重
         */

        //TODO
        str1 = changeString(str1);
        str2 = changeString(str2);
        str3 = changeString(str3);
        str4 = changeString(str4);
        str5 = changeString(str5);
        str6 = changeString(str6);
        str7 = changeString(str7);
        str8 = changeString(str8);
        str9 = changeString(str9);
        Map<String,Object> resMap = new HashMap<>();
        double resdouble =0;
        /**
         * 下面把string转换成double进行计算
         * 一级评价指标模型——学习过程于方法
         */
        int[] value = new int[4];
            for(int j=0;j<str1.length;j++){
                resdouble=resdouble+Double.valueOf(str2[j])* Double.valueOf(str3[j]);
            }
            value[0]=(int)resdouble;
            resdouble = 0.0;
            for(int j=0;j<str5.length;j++){
                resdouble=resdouble+Double.valueOf(str5[j])* Double.valueOf(str6[j]);
            }
            value[1]=(int)resdouble;
            resdouble = 0.0;
            for(int j=0;j<str8.length;j++){
                resdouble=resdouble+Double.valueOf(str8[j])* Double.valueOf(str9[j]);
            }
            value[2]=(int)resdouble;
            resdouble = 0.0;

        for(int i=0;i<3;i++){
            resdouble=resdouble+menu[i]*value[i];
        }
        value[3]=(int)resdouble;


        /**
         * 返回接口的三个一级指标的名字！ 接口名字为：Name
         */
        String[] string1 = new String[4];
        string1[0]="学习过程与方法";
        string1[1]="学习态度与情感";
        string1[2]="学习效果与能力";
        string1[3]="学习效果综合评价";

        /**
         * 返回对应分数的评价反馈
         */
        String[] returnStr = getAsses(value);
        resMap.put("Name",string1);
        resMap.put("Value",value);
        resMap.put("asses",returnStr);
        System.out.println(resMap);
        return resMap;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 得到学生成绩反馈的接口 getRank
     * @param id
     * @return
     * @throws SQLException
     *     * str1 str4 str7 对应的二级菜单的名字
    //     * str2 str5 str8 对应的二级菜单的值，1或者2 （0代表未使用，1代表使用，2代表只判断是否大于0）
    //     * str3 str6 str9 对应的二级菜单的权重，小数（0.3）
    //     * 3.最后进行统一的返回，一级菜单权重，一级菜单名字，二级菜单名字及权重
     */
    public Map<String, Object> getRank(String id) throws SQLException {
        /**
         * 注意这里查询之后有空值null
         * 之后需要进行处理
         */
        Map map = workDao.getMenu1Weight();
        String[] str1 = (String[]) map.get("str1");
        String[] str2 = (String[]) map.get("str2");
        String[] str3 = (String[]) map.get("str3");

        /**
         * 第二个二级菜单
         */
        map = null;
        map = workDao.getMenu2Weight();
        String[] str4 = (String[]) map.get("str1");
        String[] str5 = (String[]) map.get("str2");
        String[] str6 = (String[]) map.get("str3");
        /**
         * 第三个二级菜单
         */

        map = null;
        map = workDao.getMenu3Weight();
        String[] str7 = (String[]) map.get("str1");
        String[] str8 = (String[]) map.get("str2");
        String[] str9 = (String[]) map.get("str3");

        //新加入中文字符串判断

        //这里进行操作之后可以得到值，声明返回字符串数组
        String[] resName1 = getMenuName(str1,str2,id);
        String[] resName2 = getMenuName(str4,str5,id);
        String[] resName3 = getMenuName(str7,str8,id);

        //这里进行操作，对各个二级指标的成绩进行查询 且把str2 str5 str8重新覆盖
        str2 = getMenuValue(str1,str2,id);
        str5 = getMenuValue(str4,str5,id);
        str8 = getMenuValue(str7,str8,id);

        //这里获取Rank排名的值
        String[] resRank1 = getRankValue(str1,id);
        String[] resRank2 = getRankValue(str4,id);
        String[] resRank3 = getRankValue(str7,id);

        /**
         * 这里交换之后，str1，4，7 是二级菜单名字
         * str2，5，8 是最后得到的满分是100分的值
         * str3，6，9 是二级菜单的权重
         */


        str1 = changeString(str1);
        str2 = changeString(str2);
        str3 = changeString(str3);
        str4 = changeString(str4);
        str5 = changeString(str5);
        str6 = changeString(str6);
        str7 = changeString(str7);
        str8 = changeString(str8);
        str9 = changeString(str9);
        resName1 = changeString(resName1);
        resName2 = changeString(resName2);
        resName3 = changeString(resName3);
        resRank1 = changeString(resRank1);
        resRank2 = changeString(resRank2);
        resRank3 = changeString(resRank3);

        Map<String,Object> resMap = new HashMap<>();

        /**
         * 返回对应分数的评价反馈
         */
        double[] resDouble1 = changeStringToDouble(str2);
        double[] resDouble2 = changeStringToDouble(str5);
        double[] resDouble3 = changeStringToDouble(str8);

        double[] douRank1 = changeStringToDouble(resRank1);
        double[] douRank2 = changeStringToDouble(resRank2);
        double[] douRank3 = changeStringToDouble(resRank3);


        double[] resultValue = doubleAdd(resDouble1,resDouble2,resDouble3);
        double[] resultRank = doubleAdd(douRank1,douRank2,douRank3);
        String[] resultName = StringAdd(resName1,resName2,resName3);
        resMap.put("Name",resultName);
        resMap.put("Value",resultValue);
        resMap.put("Rank",resultRank);
        return resMap;


    }

    //字符串数组拼接函数功能
    public String[] StringAdd(String[] string1,String[] string2,String[] string3){
        String[] strings = new String[string1.length+string2.length+string3.length];
        int i=0;
        for(int j=0;j<string1.length;i++,j++){
            strings[i]=string1[j];
        }
        for(int j=0;j<string2.length;i++,j++){
            strings[i]=string2[j];
        }
        for(int j=0;j<string3.length;i++,j++){
            strings[i]=string3[j];
        }
        return strings;
    }
    //double数组拼接功能
    public double[] doubleAdd(double[] string1,double[] string2,double[] string3){
        double[] strings = new double[string1.length+string2.length+string3.length];
        int i=0;
        for(int j=0;j<string1.length;i++,j++){
            strings[i]=string1[j];
        }
        for(int j=0;j<string2.length;i++,j++){
            strings[i]=string2[j];
        }
        for(int j=0;j<string3.length;i++,j++){
            strings[i]=string3[j];
        }
        return strings;
    }

    /**
     * 得到学生成绩反馈的接口 liuqingGradeAll
     * @param id
     * @return
     * @throws SQLException
     *      //     * str1 str4 str7 对应的二级菜单的名字
    //     * str2 str5 str8 对应的二级菜单的值，1或者2 （0代表未使用，1代表使用，2代表只判断是否大于0）
    //     * str3 str6 str9 对应的二级菜单的权重，小数（0.3）
    //     * 3.最后进行统一的返回，一级菜单权重，一级菜单名字，二级菜单名字及权重
     */
    public Map<String, Object> getGradeAll(String id) throws SQLException {
        /**
         * 注意这里查询之后有空值null
         * 之后需要进行处理
         */
        Map map = workDao.getMenu1Weight();
        String[] str1 = (String[]) map.get("str1");
        String[] str2 = (String[]) map.get("str2");
        String[] str3 = (String[]) map.get("str3");

        /**
         * 第二个二级菜单
         */
        map = null;
        map = workDao.getMenu2Weight();
        String[] str4 = (String[]) map.get("str1");
        String[] str5 = (String[]) map.get("str2");
        String[] str6 = (String[]) map.get("str3");
        /**
         * 第三个二级菜单
         */

        map = null;
        map = workDao.getMenu3Weight();
        String[] str7 = (String[]) map.get("str1");
        String[] str8 = (String[]) map.get("str2");
        String[] str9 = (String[]) map.get("str3");

        //新加入中文字符串判断

        //这里进行操作之后可以得到值，声明返回字符串数组
        String[] resName1 = getMenuName(str1,str2,id);
        String[] resName2 = getMenuName(str4,str5,id);
        String[] resName3 = getMenuName(str7,str8,id);

        //这里进行操作，对各个二级指标的成绩进行查询 且把str2 str5 str8重新覆盖
        str2 = getMenuValue(str1,str2,id);
        str5 = getMenuValue(str4,str5,id);
        str8 = getMenuValue(str7,str8,id);

        /**
         * 这里交换之后，str1，4，7 是二级菜单名字
         * str2，5，8 是最后得到的满分是100分的值
         * str3，6，9 是二级菜单的权重
         */


        str1 = changeString(str1);
        str2 = changeString(str2);
        str3 = changeString(str3);
        str4 = changeString(str4);
        str5 = changeString(str5);
        str6 = changeString(str6);
        str7 = changeString(str7);
        str8 = changeString(str8);
        str9 = changeString(str9);
        resName1 = changeString(resName1);
        resName2 = changeString(resName2);
        resName3 = changeString(resName3);
        Map<String,Object> resMap = new HashMap<>();
        double resdouble =0;
        /**
         * 下面把string转换成double进行计算
         *  三个一级指标的结果
         */
        int[] value = new int[3];
        for(int j=0;j<str1.length;j++){
            resdouble=resdouble+Double.valueOf(str2[j])* Double.valueOf(str3[j]);
        }
        value[0]=(int)resdouble;
        resdouble = 0.0;
        for(int j=0;j<str5.length;j++){
            resdouble=resdouble+Double.valueOf(str5[j])* Double.valueOf(str6[j]);
        }
        value[1]=(int)resdouble;
        resdouble = 0.0;
        for(int j=0;j<str8.length;j++){
            resdouble=resdouble+Double.valueOf(str8[j])* Double.valueOf(str9[j]);
        }
        value[2]=(int)resdouble;
        resdouble = 0.0;



        /**
         * 返回接口的三个一级指标的名字！ 接口名字为：Name
         */
        String[] string1 = new String[3];
        string1[0]="学习过程与方法";
        string1[1]="学习态度与情感";
        string1[2]="学习效果与能力";

        /**
         * 返回对应分数的评价反馈
         */
        double[] resDouble1 = changeStringToDouble(str2);
        double[] resDouble2 = changeStringToDouble(str5);
        double[] resDouble3 = changeStringToDouble(str8);
        String[] returnStr = getAsses(value);
        resMap.put("name1",resName1);
        resMap.put("name2",resName2);
        resMap.put("name3",resName3);
        resMap.put("Value1",resDouble1);
        resMap.put("Value2",resDouble2);
        resMap.put("Value3",resDouble3);
        resMap.put("Name",string1);
        resMap.put("asses",returnStr);
        return resMap;


    }


    /**
     * 用于判断得分的高低反馈情况，根据得到的分值来进行反馈
     * @param
     * @return
     */
    public String[] getAsses(int[] doubles){
        String[] string = new String[doubles.length];
        for(int i=0;i<doubles.length;i++){
            if(doubles[i]<60)
                string[i]="您在这方面评价为不及格，请加油！";
            else if(doubles[i]<70)
                string[i]="您在这方面评价为及格，仍须努力！";
            else if(doubles[i]<85)
                string[i]="您在这方面评价为良好，表现不错！";
            else
                string[i]="您在这方面评价为优秀，继续保持！";
        }
        return string;
    }

    /**
     * 这里的设计逻辑是二级指标传入看需要那个，然后依次寻找
     * 然后这个判断2是因为需要判断有的属性是看是否大于0，如果大于0就是100，否则就是0分
     * 每个属性依次进行判断，除此之外我们在进行模型值的确定
     * @param str1
     * @param str2
     * @param id
     * @return
     * @throws SQLException
     */
    public String[] getMenuValue(String[] str1,String[] str2,String id) throws SQLException{
        String[] resStr = new String[10];
        int i=0;
        for(;str1[i]!="null"&&str1[i]!=""&&str1[i]!="\n";i++){
            String limitStr = workDao.getMenuValue(str1[i],id);
            switch (str1[i]){
                case "viewed":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {

                        resStr[i]=getViewed(id);
                    }
                    continue;
                case "explored":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {
                        resStr[i]=getExplored(id);
                    }
                    continue;
                case "certified":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {
                        resStr[i]=getCertified(id);
                    }
                    continue;
                case "loe_di":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {
                        resStr[i]=getLoe(id);
                    }
                    continue;
                case "nevents":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {
                        resStr[i]=getNevents(id);
                    }
                    continue;
                case "ndays_act":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {
                        resStr[i]=getNdaysAct(id);
                    }
                    continue;
                case "nplay_video":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {
                        resStr[i]=getNplayVideo(id);
                    }
                    continue;
                case "nchapters":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {
                        resStr[i]=getNchapters(id);
                    }
                    continue;
                case "nforum_posts":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {
                        resStr[i]=getNforumPost(id);
                    }
                    continue;
                case "grade":
                    if(str2[i].equals("2")){
                        if(!limitStr.equals("0"))
                        {
                            resStr[i]="100";
                        }else{
                            resStr[i]="0";
                        }
                    }else {
                        resStr[i]=getGrade(id);
                    }
                    continue;
                default:
                    System.out.println("循环结束，这里是workService的getMenuValue");
                    break;
            }
        }
        resStr[i] = "null";
        return resStr;
    }


    /**
     * 处理返回的中文名，比如说1 和 2 分别进行判断，返回不同的中文名称
     * @param str1
     * @param str2
     * @param id
     * @return
     * @throws SQLException
     */
    public String[] getMenuName(String[] str1,String[] str2,String id) throws SQLException{
        String[] resStr = new String[10];
        int i=0;
        for(;str1[i]!="null"&&str1[i]!=""&&str1[i]!="\n";i++){

            switch (str1[i]){
                case "viewed":
                    if(str2[i].equals("2")){
                        resStr[i]="是否查看了课外资料";
                    }else {
                        resStr[i]="是否查看了课外资料";
                    }
                    continue;
                case "explored":
                    if(str2[i].equals("2")){
                        resStr[i]="是否完成章节一半学习";
                    }else {
                        resStr[i]="是否完成章节一半学习";
                    }
                    continue;
                case "certified":
                    if(str2[i].equals("2")){
                        resStr[i]="是否完成获得证书";
                    }else {
                        resStr[i]="是否完成获得证书";
                    }
                    continue;
                case "loe_di":
                    if(str2[i].equals("2")){
                        resStr[i]="完成最高级别的教育";
                    }else {
                        resStr[i]="完成最高级别的教育";
                    }
                    continue;
                case "nevents":
                    if(str2[i].equals("2")){
                        resStr[i]="是否于课程进行交互";
                    }else {
                        resStr[i]="与课程进行交互次数";
                    }
                    continue;
                case "ndays_act":
                    if(str2[i].equals("2")){
                        resStr[i]="学生交互天数是否大于一";
                    }else {
                        resStr[i]="学生与课程交互天数";
                    }
                    continue;
                case "nplay_video":
                    if(str2[i].equals("2")){
                        resStr[i]="学生是否观看过学习视频";
                    }else {
                        resStr[i]="学生观看学习视频数量";
                    }
                    continue;
                case "nchapters":
                    if(str2[i].equals("2")){
                        resStr[i]="学生是否与章节交互过";
                    }else {
                        resStr[i]="学生与之交互的章节数";
                    }
                    continue;
                case "nforum_posts":
                    if(str2[i].equals("2")){
                        resStr[i]="学生是否参与过讨论区";
                    }else {
                        resStr[i]="学生讨论区发帖数量";
                    }
                    continue;
                case "grade":
                    if(str2[i].equals("2")){
                        resStr[i]="学生最终获得成绩";
                    }else {
                        resStr[i]="学生最终获得成绩";
                    }
                    continue;
                default:
                    System.out.println("循环结束，这里是workService的getMenuValue");
                    break;
            }
        }
        resStr[i] = "null";
        return resStr;
    }


    /**
     *
     * @param str1
     * @param str2
     * @param id
     * @return  // 排名之后统一处理
     * @throws SQLException
     */
//    public String[] getRankValue(String[] str1,String id) throws SQLException{
//        String[] resStr = new String[10];
//        int i=0;
//        for(;str1[i]!="null"&&str1[i]!=""&&str1[i]!="\n";i++){
//            String resDouble = workDao.getValueRank(str1[i],id);
//            switch (str1[i]){
//                case "viewed":
//                    resStr[i]=resDouble;
//                    continue;
//                case "explored":
//                    resStr[i]=resDouble;
//                    continue;
//                case "certified":
//                    resStr[i]=resDouble;
//                    continue;
//                case "loe_di":
//                    resStr[i]=getLoeRank(id);
//                    continue;
//                case "nevents":
//                    resStr[i]=resDouble;
//                    continue;
//                case "ndays_act":
//                    resStr[i]=resDouble;
//                    continue;
//                case "nplay_video":
//                    resStr[i]=resDouble;
//                    continue;
//                case "nchapters":
//                    resStr[i]=resDouble;
//                    continue;
//                case "nforum_posts":
//                    resStr[i]=resDouble;
//                    continue;
//                case "grade":
//                    resStr[i]=resDouble;
//                    continue;
//                default:
//                    System.out.println("循环结束，这里是workService的getMenuValue");
//                    break;
//            }
//        }
//        resStr[i] = "null";
//        return resStr;
//    }

    public String[] getRankValue(String[] str1,String id) throws SQLException{
        String[] resStr = new String[10];
        int i=0;
        for(;str1[i]!="null"&&str1[i]!=""&&str1[i]!="\n";i++){
            String resDouble = workDao.getValueRank(str1[i],id);
            resStr[i]=resDouble;
        }
        resStr[i] = "null";
        return resStr;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 之后这里对应七个模型处理方法
     */

    /**
     * Viewed模型算法
     * 第一个方法，判断是否查看了课件以外的资料
     * 函数模型处理
     * @param
     * @return
     */
    public String getViewed(String id){
        String string = workDao.getViewed(id);
        if(string.equals("1"))
            return "100";
        else
            return "0";
    }

    /**
     * Grade模型算法
     * 第二个方法，对成绩进行处理判断
     * @param
     * @return
     */
    public String getGrade(String id){
        String string = workDao.getGrade(id);
        if(string!=null||string!=""){
            double dou = Double.valueOf(string);
            dou = dou * 100;
            return Integer.toString((int)dou);
        }else
            return "0";
    }


    /**
     * 第三个方法
     * explored模型算法
     * @param
     * @return
     */
    public String getExplored(String id){
        String string = workDao.getExplored(id);
        if(string.equals("1"))
            return "100";
        else
            return "0";
    }


    /** OK！！！
     * 第四个方法
     * nchapters 模型处理方法
     * 这个方法在Dao层里面进行了计算，把查询出来的结果除以最大值作为返回结果
     *
     * @param id
     * @return
     */
    public String getNchapters(String id){

        return String.valueOf(workDao.getNchapters(id));
    }


    /** OK！！！！
     * 第五个方法
     * loe_di
     * Loe_Di模型算法，根据学历计算出相应的学习能力
     * 院士100，硕士95，学士85，中学70，中学及以下60
     * 未填写数据处理按照中学及以下 默认60

     * @param id
     * @return
     */
    public String getLoe(String id){
        String str = workDao.getLoe(id);
        if(str.equals("0")) {
            return "0";
        }else {
            if(str.equals(""))
                return "60";
            else if(str.equals("Less than Secondary"))
                return "60";
            else if(str.equals("Secondary"))
                return "70";
            else if(str.equals("Bachelor's"))
                return "85";
            else if(str.equals("Master's"))
                return "90";
            else if(str.equals("Doctorate"))
                return "95";
            else
                return "100";
        }
    }

    /** OK！！！
     * 第六个方法
     * 观看视频数量模型算法  nplay_video
     * @param id
     * @return
     */
    public String getNplayVideo(String id){

        Double userVideo = Double.parseDouble(workDao.getNplayVideo(id));
        Double maxVideo = Double.parseDouble(workDao.getAllNplayVideo(id));
        String str = String.valueOf((int)(userVideo*100/maxVideo));

        return str;
//        return String.valueOf(userVideo*100/maxVideo);

    }


    /** OK!!!!!
     * 第七个方法
     * nevents
     * @param id
     * @return
     */
    public String getNevents(String id){
        Double userNevent = Double.parseDouble(workDao.getUserNevent(id));
        Double maxNevent = Double.parseDouble(workDao.getMaxNevents(id));
        String str = String.valueOf((int)(userNevent*100/maxNevent));

        return str;
    }

    /** Ok!!!
     * 第八个方法
     * nforum_posts
     * @param id
     * @return
     */
    public String getNforumPost(String id){
        Double userPost = Double.parseDouble(workDao.getUserNforumPosts(id));
        Double maxPost = Double.parseDouble(workDao.getMaxNforumPosts(id));
        String str = String.valueOf((int)(userPost*100/maxPost));

        return str;
    }


    /** OK！！！
     * 第九个方法
     * ndays_act
     * @param id
     * @return
     */
    public String getNdaysAct(String id){
        Double userNday = Double.parseDouble(workDao.getStudentNdays(id));
        Double allNday = Double.parseDouble(workDao.getAllNdaysAct(id));
        String str = String.valueOf((int)(userNday*100/allNday));

        return str;
    }


    /**OK！！！
     * 第十个方法
     * 这里是是否获得证书
     * 不需要这个方法了！！！！！
     * @param id
     * @return
     */
    public String getCertified(String id) {

        return "100";
    }

    /**
     * 返回的数据处理一下，把后面多余定义的数组给取消
     * 之前所有的比方说声明的10然后最后加了null字符串，
     * 这里进行统一的一个判断，然后返回处理之后的结果（字符串数组）
     * @param str
     * @return
     */
    public String[] changeString(String[] str){
        int i=0;
        for(;str[i]!="null";i++) {
        }
        String[] resStr = new String[i];
        for(int j=0;j<i;j++){
            resStr[j]=str[j];
        }
        return resStr;

    }


    /**
     * 把字符串数组转换成double数组
     * 方便与前端进行直接展示
     * @param strings
     * @return
     */
    public double[] changeStringToDouble(String[] strings){
        double[] doubles = new double[strings.length];
        for(int i=0;i<strings.length;i++){
            doubles[i]=Double.valueOf(strings[i]);
        }
        return doubles;
    }



/*
     *
     * @return
     */
    public String getLoeRank(String id){
        String str = workDao.getLoe(id);
        if(str.equals("0")) {
            return "0";
        }else {
            if(str.equals(""))
                return "0.2";
            else if(str.equals("Less than Secondary"))
                return "0.5";
            else if(str.equals("Secondary"))
                return "0.6";
            else if(str.equals("Bachelor's"))
                return "0.7";
            else if(str.equals("Master's"))
                return "0.8";
            else if(str.equals("Doctorate"))
                return "0.9";
            else
                return "0.95";
        }
    }

    //////////////////////////////////////////////////////////////////////////////


    /**
     * 下面的方法全部舍弃，暂时保留，接口调试固定写死代码
     * @param edx_id
     * @return
     */


    public Object liuqingGradeAll(String edx_id) {
        Map map = new HashMap<>();
        /**
         * 四个标签的名字
         */
        String[] string1 = new String[3];
        string1[0]="学习过程与方法";
        string1[1]="学习态度与情感";
        string1[2]="学习效果与能力";


        /**
         * 四个标签的值
         */
        int[] string2 = new int[4];
        string2[0]=64;
        string2[1]=48;
        string2[2]=78;
        string2[3]=47;

        int[] string3 = new int[4];
        string3[0]=44;
        string3[1]=49;
        string3[2]=78;
        string3[3]=67;

        int[] string4 = new int[4];
        string4[0]=94;
        string4[1]=100;
        string4[2]=48;
        string4[3]=38;

        /**
         * 四个标签的评价
         */
        String[] string5 = new String[3];
        string5[0]="您在这方面评价为优秀，继续保持！";
        string5[1]="您在这方面评价为良好，仍需努力！";
        string5[2]="您在这方面评价为不及格，请加油！";
//        string5[3]="您的学习效果综合评价为优秀，继续保持！";

        /**
         * 第一个模型返回的名字
         */
        String[] string6 = new String[4];
        string6[0]="观看视频得分:";
        string6[1]="论坛回复得分:";
        string6[2]="互动得分:";
        string6[3]="课程得分:";

        /**
         * 第二个模型返回的名字
         */
        String[] string7 = new String[4];
        string7[0]="章节得分:";
        string7[1]="发帖得分:";
        string7[2]="互动得分:";
        string7[3]="天数得分:";

//        jsonpCallback({
//                "asses": ["您在这方面评价为优秀，继续保持！", "您在这方面评价为良好，仍需努力！", "您在这方面评价为不及格，请加油！"],
//        "name3": ["教育得分:", "成绩得分:", "发帖得分:", "互动得分"],
//        "Value3": ["94", "100", "48", "38"],
//        "Value1": ["64", "48", "78", "47"],
//        "Value2": ["44", "49", "78", "67"],
//        "name2": ["章节得分:", "发帖得分:", "互动得分:", "天数得分:"],
//        "name1": ["观看视频得分:", "论坛回复得分:", "互动得分:", "课程得分:"],
//        "Name": ["学习过程与方法", "学习态度与情感", "学习效果与能力"]
//        })
        /**
         *
         */
        String[] string8 = new String[4];
        string8[0]="教育得分:";
        string8[1]="成绩得分:";
        string8[2]="发帖得分:";
        string8[3]="互动得分";

        map.put("Name",string1);
        map.put("Value1",string2);
        map.put("Value2",string3);
        map.put("Value3",string4);
        map.put("asses",string5);
        map.put("name1",string6);
        map.put("name2",string7);
        map.put("name3",string8);
        return map;
    }


    public Object liuqingRank(String edx_id) {
        Map map = new HashMap<>();
        /**
         *  10个属性名字
         */
        String[] string1 = new String[10];
        string1[0]="学习";
        string1[1]="态度";
        string1[2]="效力";
        string1[3]="能力";
        string1[4]="效力";
        string1[5]="学与";
        string1[6]="与能";
        string1[7]="果与";
        string1[8]="能力";
        string1[9]="效能";


        /**
         * 10个属性的值
         * 后续自定义更改长度
         */
        int[] string2 = new int[10];
        string2[0]=64;
        string2[1]=48;
        string2[2]=78;
        string2[3]=47;
        string2[4]=64;
        string2[5]=48;
        string2[6]=78;
        string2[7]=47;
        string2[8]=78;
        string2[9]=47;


/**
 * 10个属性的排名
 * 后续自定义更改长度
 */
        int[] string3 = new int[10];
        string3[0]=64;
        string3[1]=48;
        string3[2]=78;
        string3[3]=47;
        string3[4]=64;
        string3[5]=48;
        string3[6]=78;
        string3[7]=47;
        string3[8]=78;
        string3[9]=47;

        map.put("Name",string1);
        map.put("Value",string2);
        map.put("Rank",string3);
//        map.put("Value3",string4);
//        map.put("asses",string5);
        return map;

    }

}
