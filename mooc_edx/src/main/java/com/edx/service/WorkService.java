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


    /**
     * 柳青代码模块！
     * 返回一级菜单的权重，直接查询
     * 查询的数据表为 liuqing_menu
     *
     * 实现思路:
     * 1.从liuqing_menu表中查询到相应的一级菜单权重，存储到变量名为menu  ok
     * 的double数组中。
     * 2.分别查询对应的menu1，menu2，menu3的指标名称及权重，然后返回map  ok
     * 分为3个方法分别进行运算。（删）
     * 改成写一个方法，之后返回处理好的对应的值
     * menu[0],menu[1],menu[2] 为一级菜单获取数据库的权重
     * str1 str4 str7 对应的二级菜单的名字
     * str2 str5 str8 对应的二级菜单的值，1或者2 （0代表未使用，1代表使用，2代表只判断是否大于0）
     * str3 str6 str9 对应的二级菜单的权重，小数（0.3）
     * 3.最后进行统一的返回，一级菜单权重，一级菜单名字，二级菜单名字及权重
     *
     * @return
     */
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
        String[] oneValue = new String[3];
        for(int i=0;i<3;i++){
            for(int j=0;j<str1.length;j++){
                resdouble=resdouble+Double.valueOf(str2[j])* Double.valueOf(str3[j]);
            }
            oneValue[i]=String.valueOf(resdouble);
            resdouble = 0.0;
        }
        resMap.put("oneWeight",menu);
        resMap.put("oneValue",oneValue);
        resMap.put("menu1",str1);
        resMap.put("menu2",str4);
        resMap.put("menu3",str7);
        resMap.put("value1",str2);
        resMap.put("value2",str5);
        resMap.put("value3",str8);
        resMap.put("weight1",str3);
        resMap.put("weight2",str6);
        resMap.put("weight3",str9);
        return resMap;

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
                        //TODO 这里得查值！
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
            return Double.toString(dou);
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


    /**
     * 第四个方法
     * nchapters 模型处理方法
     *
     * @param id
     * @return
     */
    public String getNchapters(String id){
        return String.valueOf(workDao.getNchapters(id));
    }


    /**
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

    /**
     * 第六个方法
     * 观看视频数量模型算法
     * @param id
     * @return
     */
    public String getNplayVideo(String id){

        return "60";

    }


    /**
     * 第七个方法
     * nevents
     * @param id
     * @return
     */
    public String getNevents(String id){

        return "70";
    }

    /**
     * 第八个方法
     * nforum_posts
     * @param id
     * @return
     */
    public String getNforumPost(String id){

        return "80";
    }


    /**
     * 第九个方法
     * ndays_act
     * @param id
     * @return
     */
    public String getNdaysAct(String id){

        return "90";
    }


    /**
     * 第十个方法
     * 这里是是否获得证书
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

}
