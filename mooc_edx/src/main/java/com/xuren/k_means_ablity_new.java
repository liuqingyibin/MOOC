package com.xuren;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class k_means_ablity_new {
	private int k;// 分类数
	private int m;// 迭代次数
	private int dataSetLength;// 数据集长度
	private ArrayList<Data> dataSet;// 数据集
	private ArrayList<Data> center;// 中心集合
	private ArrayList<ArrayList<Data>> cluster; // 簇集合
	private ArrayList<ArrayList<XYZ>> xyzCluster;
	private ArrayList<Double> jc;// 准则函数集合
	private Random random;


	public ArrayList<Double> getJC() {
		return jc;
	}

	public int getM() {
		return m;
	}

	public ArrayList<ArrayList<Data>> getCluster() {
		return cluster;
	}

	public ArrayList<Data> getCenters() {
		return center;
	}

	public k_means_ablity_new(int k) {
		if (k <= 0) {
			k = 1;
		}
		this.k = k;
	}
	private void initData(){
		DBUtils db = new DBUtils("123.206.205.246", "student", "liuqing", "liuqing123456");
		String sql = "select edx_id,Hcourse_id,userid_DI,registered,viewed,explored,certified,final_cc_cname_DI,LoE_DI,YoB,gender,grade,start_time_DI,last_event_DI,nevents,ndays_act,nplay_video,nchapters,nforum_posts from edx where hcourse_id='HarvardX/PH278x/2013_Spring' and incomplete_flag!='1' and grade!='0'";
		dataSet=new ArrayList<Data>();
		try {
			ResultSet set=db.getSta().executeQuery(sql);

			while(set.next()) {
				Data data=new Data();
				data.setEdx_id(set.getInt(1));
				data.setCourse_id(set.getString(2));
				data.setUserid_DI(set.getString(3));
				data.setRegisitered(set.getString(4));
				data.setViewed(set.getString(5));
				data.setExplored(set.getString(6));
				data.setCertified(set.getString(7));
				data.setFinall_cc_cname_DI(set.getString(8));
				data.setLoE_DI(set.getString(9));
				data.setYoB(set.getString(10));
				data.setGender(set.getString(11));
				data.setGrade(Double.parseDouble(set.getString(12)));
				data.setStart_time_DI(set.getString(13));
				data.setLast_event_DI(set.getString(14));
				data.setNevents(Math.log(Double.parseDouble(set.getString(15))+1)/Math.log(14668+1));
				data.setNdays_act((Double.parseDouble(set.getString(16))-1)/(99-1));
				data.setNplay_video(Math.log(Double.parseDouble(set.getString(17))+1)/Math.log(5128+1));
				data.setNchapters((Double.parseDouble(set.getString(18))-1)/(11-1));
				data.setNforum_posts(Double.parseDouble(set.getString(19))/3);
				dataSet.add(data);

			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			db.close();
		}

	}
	private void init() {
		m = 0;
		random = new Random();
			initData();
		System.out.println("init");
			dataSetLength = dataSet.size();
			if (k > dataSetLength) {
				k = dataSetLength;
			}
			center = initCenters();
			xyzCluster=new ArrayList<ArrayList<XYZ>>();
			cluster = initCluster();
			jc = new ArrayList<Double>();
		if(dataSet.get(dataSet.size()-1).getCourse_id()!=null) {
			for(int i=0;i<dataSet.size();i++) {
				Data data=dataSet.get(i);
				data.setX(data.getGrade());
				data.setY(data.getNplay_video()*0.8+data.getNchapters()*0.2);//
				data.setZ(data.getNevents()*0.8+data.getNdays_act()*0.2);//
			}
		}
	}

	private ArrayList<Data> initCenters() {
		ArrayList<Data> center = new ArrayList<Data>();
		int length = 0;
		length = dataSet.size();
		double[][] distance = new double[length][length];// 距离juzhen
		for (int i = 0; i < dataSet.size(); i++) {// 构建距离矩阵
			for (int j = 0; j < dataSet.size(); j++) {
				distance[i][j] = distance(dataSet.get(i), dataSet.get(j));
			}
		}
		int x = 0, y = 0;
		double max = distance[0][0];
//		ArrayList<DXY> ldxy=new ArrayList<DXY>();
		for (int i = 0; i < distance.length; i++) {// 找到最远距两点
			for (int j = 0; j < distance[i].length; j++) {
				if (max < distance[i][j]) {
					max = distance[i][j];
					x = i;
					y = j;
//					DXY dxy=new DXY();
//					dxy.setDistance(max);
//					dxy.setX(x);
//					dxy.setY(y);
//					ldxy.add(dxy);
				}
			}
		}
		center.add(dataSet.get(x));
		center.add(dataSet.get(y));
//		center.add(dataSet.get(ldxy.get(ldxy.size()*3/5).getX()));
//		center.add(dataSet.get(ldxy.get(ldxy.size()*3/5).getY()));
		while (center.size() < k) {// 找后面的点
			double[] juli = new double[length];
			for (int i = 0; i < dataSet.size(); i++) {
				for (int j = 0; j < center.size(); j++) {
					juli[i] += distance(dataSet.get(i), center.get(j));
				}
			}
			max = juli[0];
			int max_location = 0;
			for (int z = 0; z < length; z++) {
				if (max < juli[z]) {
					max = juli[z];
					max_location = z;
				}else if(max==juli[z]){
					double he=0;
					double fangchahe=0,fangchahe2=0;
					for(int nn=0;nn<center.size();nn++) {
						 he+= distance(dataSet.get(max_location), center.get(nn));
					}
					for(int nn=0;nn<center.size();nn++) {
						fangchahe+=distance(dataSet.get(max_location), center.get(nn))-he/center.size();
					}
					
					for(int nn=0;nn<center.size();nn++) {
						fangchahe2+=distance(dataSet.get(z), center.get(nn))-he/center.size();
					}
					if(fangchahe2<fangchahe) {
						max_location=z;
					}
				}
			}
			center.add(dataSet.get(max_location));
		}
//		for (int i = 0; i < center.size(); i++) {
//			System.out.println(center.get(i).getX() + " " + center.get(i).getY() + " " + center.get(i).getX());
//		}
		return center;
	}

	private ArrayList<ArrayList<Data>> initCluster() {
		ArrayList<ArrayList<Data>> cluster = new ArrayList<ArrayList<Data>>();
		for (int i = 0; i < k; i++) {
			cluster.add(new ArrayList<Data>());
		}
		return cluster;
	}

	private double distance(Data element, Data center) {
		// double distance = 0.0;
		// double x = element.getX() - center.getX();
		// double y = element.getY() - center.getY();
		// double z = x * x + y * y;
		// distance = Math.sqrt(z);
		// return distance;
		double distance = 0.0;
		double x = element.getX() - center.getX();
		double y = element.getY() - center.getY();
		double s = element.getZ() - center.getZ();
		double z = x * x + s * s + y * y;
		distance = Math.sqrt(z);
		return distance;
	}

	private int minDistance(double[] distance) {
		double minDistance = distance[0];
		int minLocation = 0;
		for (int i = 1; i < distance.length; i++) {
			if (distance[i] < minDistance) {
				minDistance = distance[i];
				minLocation = i;
			} else if (distance[i] == minDistance) {
				if (random.nextInt(10) < 5) {
					minLocation = i;
				}
			}
		}
		return minLocation;
	}

	private void clusterSet() {
		double[] distance = new double[k];
		for (int i = 0; i < dataSetLength; i++) {
			for (int j = 0; j < k; j++) {
				distance[j] = distance(dataSet.get(i), center.get(j));
			}
			int minLocation = minDistance(distance);
			cluster.get(minLocation).add(dataSet.get(i));
		}
	}

	private double errorSquare(Data element, Data center) {
		// double x = element.getX() - center.getX();
		// double y = element.getY() - center.getY();
		// double errSquare = x * x + y * y;
		// return errSquare;
		double x = element.getX() - center.getX();
		double y = element.getY() - center.getY();
		double s = element.getZ() - center.getZ();
		double errSquare = x * x + s * s + y * y;
		return errSquare;
	}

	private void countRule() {
		double jcF = 0;
		for (int i = 0; i < cluster.size(); i++) {
			for (int j = 0; j < cluster.get(i).size(); j++) {
				jcF += errorSquare(cluster.get(i).get(j), center.get(i));

			}

		}
		jc.add(jcF);
	}

	private void setNewCenter() {
		for (int i = 0; i < k; i++) {
			int n = cluster.get(i).size();
			if (n != 0) {
				Data newCenter = new Data();
				double a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;
				double x = 0, y = 0, z = 0;
				for (int j = 0; j < n; j++) {

					a += cluster.get(i).get(j).getGrade();

					d += cluster.get(i).get(j).getNchapters();
					e += cluster.get(i).get(j).getNdays_act();
					f += cluster.get(i).get(j).getNevents();
					g += cluster.get(i).get(j).getNforum_posts();
					h += cluster.get(i).get(j).getNplay_video();
					x += cluster.get(i).get(j).getX();
					y += cluster.get(i).get(j).getY();
					z += cluster.get(i).get(j).getZ();

				}
				newCenter.setGrade(a / n);

				newCenter.setNchapters(d / n);
				newCenter.setNdays_act(e / n);
				newCenter.setNevents(f / n);
				newCenter.setNforum_posts(g / n);
				newCenter.setNplay_video(h / n);
				newCenter.setX(x / n);
				newCenter.setY(y / n);
				newCenter.setZ(z / n);
				center.set(i, newCenter);
			}
		}
	}

	public void printDataArray(ArrayList<double[]> dataArray, String dataArrayName) {
		for (int i = 0; i < dataArray.size(); i++) {
			System.out.println("print:(" + dataArray.get(i)[0] + "," + dataArray.get(i)[1] + ")");
		}
		System.out.println("===================================");
	}

	public void kmeans() {
		init();
		while (true) {
			clusterSet();
			countRule();
			if (m != 0) {
				if (jc.get(m) - jc.get(m - 1) == 0) {
					break;
				}
			}

			setNewCenter();
			// }
			m++;
			cluster.clear();
			cluster = initCluster();
		}
		for (int i=0;i<cluster.size();i++){
			Data data=null;
			ArrayList<XYZ> ll=new ArrayList<XYZ>();
			for (int j=0;j<cluster.get(i).size();j++){
				data=cluster.get(i).get(j);
				XYZ xyz=new XYZ();
				xyz.setX(data.getX());
				xyz.setY(data.getY());
				xyz.setZ(data.getZ());
				xyz.setEdxid(data.getEdx_id());
				ll.add(xyz);
			}
			xyzCluster.add(ll);
		}

	}
	public ArrayList<ArrayList<XYZ>> getXyzCluster(){
		return xyzCluster;
	}
	public void getClusterCount() {
		System.out.println(cluster.size());
	}
}
