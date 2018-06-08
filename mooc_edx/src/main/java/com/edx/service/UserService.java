package com.edx.service;

import com.edx.dao.UserDao;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2018/5/17.
 */
@Service
public class UserService {

    private final static UserDao userDao = new UserDao();

    /**
     * 用户登录后台判断
     * @param username
     * @param password
     * @return
     */
    public HashMap<String, String> login(String username, String password) {

        return userDao.login(username,password);
    }


    /**
     * 得到用户的十项个人信息展示
     * @param
     * @param
     * @return
     */
    public Map getStudent(String id) {
        String[] values = userDao.getStudent(id);
        String[] names = new String[12];
        names[0]="您最终所获得的成绩为：";
        names[1]="您观看本课程视频数量：";
        names[2]="您与本课程交互章节数：";
        names[3]="您在讨论区发帖的数量：";
        names[4]="您所获得最高的学历为：";
        names[5]="您所开始学习的时间为：";
        names[6]="您所最后学习的时间为：";
        names[7]="您与本课程交互的次数：";
        names[8]="您与本课程交互的天数：";
        names[9]="您是否通过考试获得了证书：";
        names[10]="您是否查看了课件以外资料：";
        names[11]="您是否完成章节一半的学习：";
        Map map = new HashMap<>();
        map.put("names",names);
        map.put("values",values);
        return map;
    }

    /**
     * 柳青学习效果综合评价展示
     * @param userId
     * @return
     */
    public Object liuqingOverShow(String userId) {
        Map map = new HashMap<>();
        /**
         * 四个标签的名字
         */
        String[] string1 = new String[4];
        string1[0]="学习过程与方法";
        string1[1]="学习态度与情感";
        string1[2]="学习效果与能力";
        string1[3]="学习效果综合评价";

        /**
         * 四个标签的值
         */
        int[] string2 = new int[4];
        string2[0]=33;
        string2[1]=25;
        string2[2]=30;
        string2[3]=88;

        /**
         * 四个标签的评价
         */
        String[] string3 = new String[4];
        string3[0]="您在这方面评价为优秀，继续保持！";
        string3[1]="您在这方面评价为良好，仍需努力！";
        string3[2]="您在这方面评价为不及格，请加油！";
        string3[3]="您的学习效果综合评价为优秀，继续保持！";

        map.put("Name",string1);
        map.put("Value",string2);
        map.put("asses",string3);
        return map;
    }

    public Object liuqingTeacherShow(String userId) {
        Map map = new HashMap<>();
        /**
         * 四个评价的名字
         */
        String[] string1 = new String[4];
        string1[0]="优秀人数及占比";
        string1[1]="良好人数及占比";
        string1[2]="及格人数及占比";
        string1[3]="不及格人数及占比";

        /**
         * 综合评价值
         */
        int[] string2 = new int[4];
        string2[0]=3485;
        string2[1]=1547;
        string2[2]=1547;
        string2[3]=2650;


        /**
         * 学习过程与方法排名
         */
        double[] string3 = new double[4];
        string3[0]=0.10;
        string3[1]=0.21;
        string3[2]=0.34;
        string3[3]=0.18;

        /**
         * 学习态度与情感排名
         */
        double[] string4 = new double[4];
        string4[0]=0.16;
        string4[1]=0.20;
        string4[2]=0.21;
        string4[3]=0.36;

        /**
         * 学习效果与能力排名
         */
        double[] string5 = new double[4];
        string5[0]=0.15;
        string5[1]=0.30;
        string5[2]=0.20;
        string5[3]=0.30;

        map.put("name",string1);
        map.put("value",string2);
        map.put("rank1",string3);
        map.put("rank2",string4);
        map.put("rank3",string5);
        return map;
    }

    public Map<String,String> changeToChinese(Map map){
        return null;
    }



}
