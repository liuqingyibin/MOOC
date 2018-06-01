package com.wangsizhuo.algorithm;

import com.wangsizhuo.model.Data;
import com.wangsizhuo.util.MyFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Kfolder {
    ArrayList<Data> data;
    //测试集
    ArrayList<Data> test = new ArrayList<>();
    //训练集
    ArrayList<Data> train = new ArrayList<>();
    //标签集
    ArrayList<String> attrNames;
    //属性值集
    ArrayList<ArrayList<String>> attrValues;
    private String decisionName = "certified";                      //决策属性
    private BufferedOutputStream outputStream;
    private Node node;
    private String cid;                                              //课程号


    public Kfolder(String path, String cid) {
        this.cid = cid;
        MyFile myFile = new MyFile(path);
        data = myFile.myFileReader();
        Map<Integer, List<Data>> allData = randomData();
        int flag = 0;
        for (int i = 0; i < allData.size(); i++) {
            if (i == flag) {
                test.addAll(allData.get(i));
            } else {
                train.addAll(allData.get(i));
            }
        }
        //处理时间跨度
        dealTimeSpan();
        attrNames = getAttrNames();
        attrValues = getAttrValues();

    }

    public Kfolder(String path) {
        MyFile myFile = new MyFile(path);
        data = myFile.myFileReader();

        //处理时间跨度
        dealTimeSpan();
        attrNames = getAttrNames();
        attrValues = getAttrValues();

        Map<Integer, List<Data>> allData = randomData();
        int flag = 0;
        for (int i = 0; i < allData.size(); i++) {
            if (i == flag) {
                test.addAll(allData.get(i));
            } else {
                train.addAll(allData.get(i));
            }
        }
    }

    private void dealTimeSpan() {

        for (int i = 0; i < train.size(); i++) {
            double span;
            String startTime = train.get(i).getStartTime();
            String lastTime = train.get(i).getLastEventTime();
            if ((startTime.equals("null") || (lastTime.equals("null")))) {
                span = 0;
            } else {
                String[] begin = startTime.split("/");
                String[] last = lastTime.split("/");
                LocalDate ld = LocalDate.of(Integer.parseInt(begin[0]), Integer.parseInt(begin[1]), Integer.parseInt(begin[2]));
                LocalDate ld2 = LocalDate.of(Integer.parseInt(last[0]), Integer.parseInt(last[1]), Integer.parseInt(last[2]));
                span = ChronoUnit.DAYS.between(ld, ld2);
                span++;
                if (span < 0) {
                    span = 0;
                }
            }
            double ndays = train.get(i).getNdays();
            if (span == 0) {
                train.get(i).setNdays("0");
            } else {
                train.get(i).setNdays(String.valueOf(ndays / span));
            }
        }

    }

    //将原数据随机
    private List<Data> getRandomData() {
        //数据随机起来
        Random r = new Random();
        List<Data> testdata = data;
        List<Data> temp = new ArrayList<>();
        int seed;
        while (testdata.size() > 0) {
            seed = r.nextInt(testdata.size());
            temp.add(testdata.get(seed));
            testdata.remove(seed);
        }
        return temp;
    }

    //构建训练集和测试集总集
    private Map<Integer, List<Data>> randomData() {
        List<Data> testData = getRandomData();
        //分成10组
        int step = testData.size() / 10 + 1;    //每组数据的大小
        Map<Integer, List<Data>> map = new HashMap<>();
        int index = 0;
        while (index < 9) {
            List<Data> list = new ArrayList<>();
            for (int i = 0; i < step; i++) {
                list.add(testData.get(i + index * step));
            }
            map.put(index, list);
            index++;
        }
        List<Data> ten = new ArrayList<>();
        for (int i = 0; i < testData.size() - 9 * step; i++) {
            ten.add(testData.get(i));
        }
        map.put(index, ten);
        return map;
    }

    //获取属性集
    private ArrayList<String> getAttrNames() {
        ArrayList<String> attrNames = new ArrayList<>();
        attrNames.add("identify");
        attrNames.add("certified");
        attrNames.add("location");
        attrNames.add("level");
        attrNames.add("age");
        attrNames.add("gender");
        attrNames.add("grade");
        attrNames.add("nevents");
        attrNames.add("ndays_frequnecy");
        attrNames.add("nplayVideos");
        attrNames.add("nchapters");
        attrNames.add("nforumPosts");
        return attrNames;
    }

    //获取属性值集
    private ArrayList<ArrayList<String>> getAttrValues() {
        attrValues = new ArrayList<>();
        Set<String> identifySet = new TreeSet<>();
        Set<String> certfiedSet = new TreeSet<>();
        Set<String> locationSet = new TreeSet<>();
        Set<String> levelSet = new TreeSet<>();
        Set<String> ageSet = new TreeSet<>();
        Set<String> genderSet = new TreeSet<>();
        Set<String> gradeSet = new TreeSet<>();
        Set<Double> neventsSet = new TreeSet<>();
        Set<Double> nDaysFrequencySet = new TreeSet<>();
        Set<Double> nPlayVideosSet = new TreeSet<>();
        Set<Double> nchaptersSet = new TreeSet<>();
        Set<Double> nforumPosts = new TreeSet<>();

        for (int i = 0; i < data.size(); i++) {
            identifySet.add(data.get(i).getIdentify());
            certfiedSet.add(data.get(i).getCertified());
            locationSet.add(data.get(i).getLocation());
            levelSet.add(data.get(i).getLevel());
            ageSet.add(data.get(i).getAge());
            genderSet.add(data.get(i).getGender());
            gradeSet.add(data.get(i).getGrade());
            neventsSet.add(data.get(i).getNevents());
            nDaysFrequencySet.add(data.get(i).getNdays());
            nPlayVideosSet.add(data.get(i).getNplayVideos());
            nchaptersSet.add(data.get(i).getNchapters());
            nforumPosts.add(data.get(i).getNforumPosts());
        }

        attrValues.add(toArrayList(identifySet));
        attrValues.add(toArrayList(certfiedSet));
        attrValues.add(toArrayList(locationSet));
        attrValues.add(toArrayList(levelSet));
        attrValues.add(toArrayList(ageSet));
        attrValues.add(toArrayList(genderSet));
        attrValues.add(toArrayList(smooth(gradeSet)));
        //离散化
        attrValues.add(lisanhua(neventsSet));
        attrValues.add(lisanhua(nDaysFrequencySet));
        attrValues.add(lisanhua(nPlayVideosSet));
        attrValues.add(lisanhua(nchaptersSet));
        attrValues.add(lisanhua(nforumPosts));
        return attrValues;
    }


    //将集合转换成List
    private ArrayList<String> toArrayList(Set set) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }

    private ArrayList<String> lisanhua(Set set) {
        ArrayList<Double> temp = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        temp.addAll(set);
        double min = temp.get(0);
        double max = temp.get(temp.size() - 1);
        Set tempSet = new TreeSet();
        for (int i = 0; i < temp.size(); i++) {
            double res = 0;
            if (max != min) {
                res = (temp.get(i) - min) / (max - min);
            }
            tempSet.add(smooth(res));
        }
        list.addAll(tempSet);
        return list;

    }

    private Set smooth(Set<String> gradeSet) {
        Set set = new TreeSet();
        Iterator iterator = gradeSet.iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (key.equals("null")) {
                set.add("-1");
            } else {
                set.add(smooth(Double.parseDouble(key)));
            }
        }
        return set;
    }

    //对于交互信息归一化处理后的数据，向上取整，光滑数据
    private String smooth(double d) {
        double result;
        if (d == 0) {
            result = 0;
        } else if (d > 0 && d <= 0.1) {
            result = 0.1;
        } else if (d <= 0.2) {
            result = 0.2;
        } else if (d <= 0.3) {
            result = 0.3;
        } else if (d <= 0.4) {
            result = 0.4;
        } else if (d <= 0.5) {
            result = 0.5;
        } else if (d <= 0.6) {
            result = 0.6;
        } else if (d <= 0.7) {
            result = 0.7;
        } else if (d <= 0.8) {
            result = 0.8;
        } else if (d <= 0.9) {
            result = 0.9;
        } else if (d <= 1) {
            result = 1;
        } else {
            result = 0;
        }
        return String.valueOf(result);
    }

    private ArrayList<ArrayList<String>> transfer(ArrayList<Data> temp) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            ArrayList<String> itemList = new ArrayList<>();
            itemList.add(temp.get(i).getIdentify());
            itemList.add(temp.get(i).getCertified());
            itemList.add(temp.get(i).getLocation());
            itemList.add(temp.get(i).getLevel());
            itemList.add(temp.get(i).getAge());
            itemList.add(temp.get(i).getGender());
            if (temp.get(i).getGrade().equals("null")) {
                itemList.add("-1");
            } else {
                itemList.add(smooth(Double.parseDouble(temp.get(i).getGrade())));
            }
            itemList.add(smooth(temp.get(i).getNevents()));
            itemList.add(smooth(temp.get(i).getNdays()));
            itemList.add(smooth(temp.get(i).getNplayVideos()));
            itemList.add(smooth(temp.get(i).getNchapters()));
            itemList.add(smooth(temp.get(i).getNforumPosts()));
            list.add(itemList);
        }
        return list;
    }

    //将决策树的内容写进文件
    public void C45Tree() {
        node = new Node();
        node.setValue("root");
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream("out.txt"));
            //构建决策树
            buildTree(transfer(train), attrNames, attrValues, node);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assess(attrNames, transfer(test));
    }

//    public Map<String,ArrayList<String>> prediction(String path) {
//
//    }


    //构建决策树
    private void buildTree(List<ArrayList<String>> datalist, List<String> attrNames, List<ArrayList<String>> attrValues, Node fatherRoot) {
        //获取最大信息增益
        String attr = getMaxGainRatioAttr(datalist, attrNames, attrValues);
        //
        Map<String, List<ArrayList<String>>> cutData = getCutData(attr, datalist, attrNames, attrValues);
        //遍历cutData的每一个值
        for (Map.Entry<String, List<ArrayList<String>>> entry : cutData.entrySet()) {

            String space = "";
            Node childNode = new Node();
            fatherRoot.addChildNode(childNode);
            try {
                for (int i = 0; i < ((attrNames.size()) - entry.getValue().get(0).size() - 1); i++) {
                    space += "\t";
                }

                childNode.setValue(attr);
                childNode.setDisvisionValue(entry.getKey());

                outputStream.write((space + attr + ":" + entry.getKey() + "\n").getBytes());

                int index = attrNames.indexOf(attr);
                ArrayList<String> names = new ArrayList<>();
                ArrayList<ArrayList<String>> values = new ArrayList<>();
                getCutNames(attrNames, names, attrValues, values, index);
                if ((!isDecisionValuesSettled(entry.getValue(), names)) && (names.size() > 1)) {
                    buildTree(entry.getValue(), names, values, childNode);
                } else {
                    childNode.setDecision(entry.getValue().get(0).get(names.indexOf(decisionName)));
                    outputStream.write(("\t" + space + "leafNode: " + entry.getValue().get(0).get(names.indexOf(decisionName)) + "\n").getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //判断决策树是否收敛
    private boolean isDecisionValuesSettled(List<ArrayList<String>> data, ArrayList<String> attrNames) {
        int index = attrNames.indexOf(decisionName);
        Set<String> result = data.stream().map(dataItem -> dataItem.get(index)).collect(Collectors.toSet());
        return result.size() == 1;
    }

    //获取删除最大增益属性后的属性值
    private void getCutNames(List<String> attrNames, List<String> names, List<ArrayList<String>> attrValues, List<ArrayList<String>> values, int index) {
        names.addAll(attrNames);
        names.remove(index);
        values.addAll(attrValues);
        values.remove(index);
    }

    /**
     * 获取具有最大信息增益率的标签后，删除数据中的该属性
     *
     * @param attr       最大信息增益率标签
     * @param data       数据集
     * @param attrNames  数据标签集
     * @param attrValues 数据标签值集
     * @return cutData(最大增益率标签值, List : 行数 ( ArrayList ( 删除最大增益率标签值后每一行的剩余标签值)))
     */
    private Map<String, List<ArrayList<String>>> getCutData(String attr, List<ArrayList<String>> data, List<String> attrNames, List<ArrayList<String>> attrValues) {
        //获取最大增益率标签的索引
        int index = attrNames.indexOf(attr);
        //待返回值
        Map<String, List<ArrayList<String>>> cutData = new HashMap<>();
        //循环最大增益率标签下的值
        for (Object value : attrValues.get(index)) {
            List<ArrayList<String>> newData = new ArrayList<>();
            //处理每一行数据，将最大属性=value数据过滤出来，删除其中最大增益率的属性，将新数据添加到新数据集中；
            data.stream().filter(dataItem -> dataItem.get(index).equals(String.valueOf(value))).forEach(dataItem -> {
                ArrayList<String> temp = new ArrayList<>();
                temp.addAll(dataItem);
                temp.remove(String.valueOf(value));
                newData.add(temp);
            });
            if (newData.size() > 0) {
                cutData.put(String.valueOf(value), newData);
            }
        }
        return cutData;
    }


    /**
     * 获取最大增益率的属性标签
     *
     * @param data       数据集
     * @param attrNames  筛选后的标签集
     * @param attrValues 筛选后的标签值
     * @return
     */
    private String getMaxGainRatioAttr(List<ArrayList<String>> data, List<String> attrNames, List<ArrayList<String>> attrValues) {
        List<String> tempAttrNames = new ArrayList<>();
        tempAttrNames.addAll(attrNames);
        tempAttrNames.remove(decisionName);
        List<List<Object>> attrGainRatio = new ArrayList<>();
        for (String attrName : tempAttrNames) {
            List<Object> temp = Arrays.asList(attrName, getGainRation(attrName, data, attrNames, attrValues));
            attrGainRatio.add(temp);
        }
        Collections.sort(attrGainRatio, (o1, o2) -> (new Double((double) o2.get(1))).compareTo((double) o1.get(1)));
        return (String) attrGainRatio.get(0).get(0);
    }

    /**
     * 求信息增益率
     * 公式：GainRation（A） = Gain（A） / Info(D)
     *
     * @param attrName   当前属性标签
     * @param attrNames  属性标签集
     * @param attrValues 属性标签值集
     * @return 信息增益率
     */
    private Object getGainRation(String attrName, List<ArrayList<String>> data, List<String> attrNames, List<ArrayList<String>> attrValues) {
        return getInfoGain(attrName, data, attrNames, attrValues) /
                getInfoEntropy(attrName, data, attrNames, attrValues);
    }

    /**
     * 求信息增益
     * 公式: gain(A) = info(D) - info_A(D)
     *
     * @param attrName   当前属性标签
     * @param data       数据集
     * @param attrNames  属性标签集
     * @param attrValues 标签值集
     * @return 信息增益值
     */
    private double getInfoGain(String attrName, List<ArrayList<String>> data, List<String> attrNames, List<ArrayList<String>> attrValues) {
        int index = attrNames.indexOf(attrName);    //获取当前属性的索引值
        int dataSize = data.size();
        double infoEntropyAttrNameDecisionName = 0.0;     //当前属性的信息增益
        Map<String, Integer> attrNum = new HashMap<>();      //当前属性下，各属性值出现的次数
        Map<List<String>, Integer> valueOfAttrDecision = new HashMap<>();    //[[当前属性，决策属性]，属性对出现次数]

        for (ArrayList<String> dataItem : data) {
            //info(Dj)
            attrNum.put(dataItem.get(index), attrNum.getOrDefault(dataItem.get(index), 0) + 1);
            List<String> attrDecision = Arrays.asList(dataItem.get(index), dataItem.get(attrNames.indexOf(decisionName)));
            valueOfAttrDecision.put(attrDecision, valueOfAttrDecision.getOrDefault(attrDecision, 0) + 1);
        }

        //Info(Dj)
        for (Map.Entry<String, Integer> entry : attrNum.entrySet()) {
            //循环[当前属性值，次数]
            Iterator<Map.Entry<List<String>, Integer>> iterator = valueOfAttrDecision.entrySet().iterator();
            double t = 0.0;
            while (iterator.hasNext()) {
                Map.Entry<List<String>, Integer> entry1 = iterator.next();   //获取[属性值,决策值],次数
                if (entry1.getKey().get(0).equals(entry.getKey())) {
                    //如果属性值对中的第一个属性值等于当前属性值
                    double t1 = (double) entry1.getValue() / (double) entry.getValue();
                    t += -t1 * getLog2_N(t1);
                }
                //Info_A(Dj)
                infoEntropyAttrNameDecisionName += ((double) entry.getValue() / (double) dataSize) * t;
            }
        }
        return getInfoEntropy(decisionName, data, attrNames, attrValues) - infoEntropyAttrNameDecisionName;
    }

    /**
     * 求信息熵
     * 公式：+= -((double) num / (double) dataSize) * getLog2_N((double) num / (double) dataSize);
     *
     * @param attrName   当前属性标签
     * @param data       数据集
     * @param attrNames  属性标签集
     * @param attrValues 标签值集
     * @return 信息熵
     */
    private double getInfoEntropy(String attrName, List<ArrayList<String>> data, List<String> attrNames, List<ArrayList<String>> attrValues) {
        int index = attrNames.indexOf(attrName);
        int dataSize = data.size();
        double infoEntropy = 0.0;
        for (Object value : attrValues.get(index)) {
            int num = 0;
            for (ArrayList<String> dataItem : data) {
                if (dataItem.get(index).equals(String.valueOf(value))) {
                    num += 1;
                }
                if (num != 0) {
                    infoEntropy += -((double) num / (double) dataSize) * getLog2_N((double) num / (double) dataSize);
                }
            }
        }
        return infoEntropy;
    }

    /**
     * 利用换底公式计算2为底，n的对数
     *
     * @param n 概率值
     * @return 对数值
     */
    private double getLog2_N(double n) {
        return (Math.log(n) / Math.log(2));
    }

    //评估分类器
    private void assess(List<String> attrNames, List<ArrayList<String>> data) {
        int TP = 0, TN = 0, FP = 0, FN = 0;
        String result;
        for (ArrayList<String> dataItem : data) {
            Node searchNode = node;
            boolean lag = true;
            while (searchNode.children.size() != 0) {
                boolean flag = false;
                for (int i = 0; i < searchNode.children.size(); i++) {
                    Node temp = searchNode.children.get(i);
                    int index = attrNames.indexOf(temp.value);
                    if (dataItem.get(index).toString().equals(temp.disvisionValue)) {
                        searchNode = temp;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    lag = flag;
                    break;
                }
            }
            if (!lag) {
                result = "no";
            } else {
                result = searchNode.decision;
            }
            int index = attrNames.indexOf(decisionName);
            if (dataItem.get(index).equals("yes")) {
                if (result.equals("yes")) {
                    TP++;
                } else if (result.equals("no")) {
                    FN++;
                }
            } else if (dataItem.get(index).equals("no")) {
                if (result.equals("no")) {
                    TN++;
                } else if (result.equals("yes")) {
                    FP++;
                }
            }
        }
        //计算分类器准确率
        //总样本数
        float total = TP + FP + TN + FN;
        //准确率（准确率）
        float accuracy = (float) (TP + TN) / (float) (TP + TN + FP + FN);
        //误分类率（错误率）
        float errorRate = (float) (FP + FN) / (float) (TP + TN + FP + FN);
        //精度
        float precision = (float)(TP)/(float)(TP+FP);
        //召回率
        float recall = (float)(TP)/(float)(TP+FN);

        System.out.println("总样本数：" + total);
        System.out.println("TP"+TP);
        System.out.println("FP"+FP);
        System.out.println("TN"+TN);
        System.out.println("FN" +FN);
        System.out.printf("准确率accuracy：%.2f", accuracy * 100);
//        System.out.printf("\n分类错误率：%.2f", errorRate * 100);
        System.out.printf("\n精度precision：%.2f", precision * 100);
        System.out.printf("\n召回率recall：%.2f", recall * 100);

    }


}
