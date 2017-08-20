package com.cv.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.cv.gomoku.Black;
import com.cv.gomoku.BlackStone;
import com.cv.gomoku.GoMoKu;
import com.cv.gomoku.GoMoKuInter;
import com.cv.gomoku.White;
import com.cv.gomoku.WhiteStone;
import com.cv.jishiqi.JiShiQi;

public class Window extends JPanel
{

	/**
	 * 五子棋1.0
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 面板宽
	 */
	public static final int WIDTH = 700;
	private static final int TIME = 30*60*1000;
	/*
	 * 运行状态
	 * @author xiaowei
	 *
	 */
	enum State{
		开始, 暂停, 主菜单, 结束
	}
	private enum Ac{
		黑下, 白下, 黑赢, 白赢
	}
	/*
	 * 棋子直径
	 */
	public static int D =  GoMoKuInter.D;
	private static int C = 10; //点击误差
	private static List<Point> list;//所有可以下的点
	static{
		list = new ArrayList<Point>();
		for (int i = D; i < WIDTH; i += D)
		{
			for (int j = D; j <WIDTH; j += D)
			{
				list.add(new Point(i,j));
			}
		}
	}
	
	//变量
	private Set<GoMoKu> set;//所有棋子的集合
	private State state;//状态
	private Ac go;//下棋状态
	private Timer timer;//计时器
	private JiShiQi jiB;//黑棋计时
	private JiShiQi jiW;//白棋计时
	private String strB;//黑棋时间
	private String strW;//白棋时间
	private boolean b;//控制黑棋计时  ture 计时    false 暂停
	private boolean w;
	
	public Window(){
		setBackground(new Color(249, 214, 91));
		set = new HashSet<GoMoKu>();
		state = State.主菜单;
		jiB = new JiShiQi(-1);
		jiW = new JiShiQi(-1);
		strB = "00:00:00";
		strW = "00:00:00";
		b = false;
		w = false;
		timer = new Timer(1000, new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				strB = jiB.jishi(b);
				strW = jiW.jishi(w);
			}
		});
	}
	
	public void f5(){
		go = Ac.黑下;
		set.clear();
		jiW.setTime(TIME);
		jiB.setTime(TIME);
		timer.start();
		b = false;
		w = false;
	}
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if(state == State.主菜单){
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("微软雅黑", Font.BOLD, 90));
			g.drawString("五子棋", 200, 300);
			g.setFont(new Font("微软雅黑", Font.BOLD, 30));
			g.drawString("开始/暂停", 400, 350);
			g.drawString("Enter->", 400, 400);
			g.setFont(new Font("微软雅黑", Font.BOLD, 18));
			g.drawString("by Cv", 500, 500);
			f5();
		}else if(state == State.开始){
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("微软雅黑", Font.BOLD, 30));
			g.drawString(strB+ "         " + "五子棋" + "        " + strW, 100, 30);
			if(go == Ac.黑下){
				g.setColor(new Color(0, 0, 0));
				g.fillOval(50, 5, 30, 30);
				b = true;
			}else if(go == Ac.白下){
				g.setColor(new Color(255, 255, 255));
				g.fillOval(WIDTH-70, 5, 30, 30);
				w = true;
			}
			g.setColor(new Color(0, 0, 0));
			g.drawOval(50, 5, 30, 30);
			g.setColor(new Color(255, 255, 255));
			g.drawOval(WIDTH-70, 5, 30, 30);
			if(jiW.getTime() <= 0){
				go = Ac.黑赢;
				state = State.结束;
			}else if(jiB.getTime() <= 0){
				go = Ac.白赢;
				state = State.结束;
			}
			
			grid(g);
			stone(g);
		}
		else if(state == State.暂停){
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("微软雅黑", Font.BOLD, 90));
			g.drawString("暂停...", 200, 300);
			g.setFont(new Font("微软雅黑", Font.BOLD, 30));
			g.drawString("开始/暂停", 400, 350);
			g.drawString("Enter->", 400, 400);
			w = false;
			b = false;
		}else if(state == State.结束){
			if(go == Ac.黑赢){
				g.setFont(new Font("微软雅黑", Font.BOLD, 30));
				g.setColor(new Color(0, 0, 0));
				g.drawString("黑棋赢！", 200, 30);
				g.drawString("Enter->", 385, 30);
			}else if(go == Ac.白赢){
				g.setFont(new Font("微软雅黑", Font.BOLD, 30));
				g.setColor(new Color(255, 255, 255));
				g.drawString("白棋赢！", 200, 30);
				g.drawString("Enter->", 385, 30);
			}else{
				g.setColor(new Color(255, 0, 0));
				g.setFont(new Font("微软雅黑", Font.BOLD, 30));
				g.drawString("未知错误！", 200, 30);
				g.drawString("Enter->", 385, 30);
			}
			timer.stop();
			w = false;
			b = false;
			grid(g);
			stone(g);
		}
	}
	public State getState()
	{
		return state;
	}
	public void setState(State s)
	{
		this.state = s;
		
	}
	//画网格
	private void grid(Graphics g){
		g.setColor(new Color(0, 0, 0));
		for (int i = D; i < WIDTH; i += D)
		{
			g.drawLine(i, D, i, WIDTH-D);
		}
		for (int i = D; i < WIDTH; i += D)
		{
			g.drawLine(D, i, WIDTH-D, i);
		}
		g.fillOval(WIDTH/2-5, WIDTH/2-5, 10, 10);
	}
	//画棋子
	private void stone(Graphics g){
			for(Iterator<GoMoKu> it = set.iterator(); it.hasNext(); ){
				GoMoKu go = it.next();
				Point p = go.getP();
				if(go instanceof Black){
					g.setColor(Black.c);
				}else if(go instanceof White){
					g.setColor(White.c);;
				}
				g.fillOval((int)p.getX()-D/2, (int)p.getY()-D/2, D, D);
			}
		}
	/**
	 * 下棋
	 * @param p 点击的位置
	 * @author xiaowei
	 */
	public void addStone(Point p){
		try
		{
			if(state == State.开始){
				//因为Graphics画圆是以圆的上方作为基点的，所以这里调整到圆的中心点做为基点储存
				//再进行转化，使其始终落在棋盘交点上
				Point p2 = thisPoint(new Point((int)p.getX(), (int)p.getY()-D/2));
				if(go == Ac.黑下){
					GoMoKu g = new BlackStone(p2);
					if(set.add(g)){
						if(win(g)){
							go = Ac.黑赢;
							state = State.结束;
						}else{
							go = Ac.白下;
							w = true;
							b = false;
						}
					}
				}else if(go == Ac.白下){
					GoMoKu g = new WhiteStone(p2);
					if(set.add(g)){
						if(win(g)){
							go = Ac.白赢;
							state = State.结束;
						}else{
							go = Ac.黑下;
							b = true;
							w = false;
						}

					}
				}
			}
		} catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	/*
	 * 判断谁赢
	 * </p>
	 * 两个while循环判断四个方向上的棋子是否有全是黑或白一方的棋子，
	 * 通过sum计数，若有五个连成一起的同一方的棋子，则sum>=5，返回获胜。否则返回false，让对方下棋。
	 * 下棋编程一般有两种：穷举和一定范围内穷举，这样做出来的下棋程序比较有和真人下棋的感觉。
	 * </p>
	 * @param g 当下下的那个棋子
	 * @return true 当前下的赢 
	 * @author xiaowei
	 */
	private boolean win(GoMoKu g){
		int c = 0;
		if(g instanceof Black)
			c = 1;
		else if(g instanceof White)
			c = 0;
		else c = -1;
		int n = WIDTH;
		int dx[] = {D, 0, D, D};
		int dy[] = {0, D, D, -D};
		for(int i=0; i<4; i++){
			int sum = 1;
			int tx = (int) (g.getP().getX() + dx[i]);
			int ty = (int) (g.getP().getY() + dy[i]);
			while(tx>=D && tx<=n && ty>=D && ty<=n && col(tx, ty) == c){
				tx += dx[i];
				ty += dy[i];
				++ sum;
			}
			tx = (int) (g.getP().getX() - dx[i]);
			ty = (int) (g.getP().getY() - dy[i]);
			while(tx>=D && tx<=n && ty>=D && ty<=n && col(tx, ty) == c){
				tx -= dx[i];
				ty -= dy[i];
				++ sum;
			}
			if(sum >= 5){
				return true;
			}
		}
		return false;

	}
	
	/**
	 * 运行
	 * 
	 * @author xiaowei
	 */
	public void action(){
		while(true){
			repaint();
		}
	}
	/*
	 * 工具类
	 */
	/*
	 * 点击误差判断，始终下在棋盘交点上
	 * @param p 鼠标传过来的位置
	 * @return
	 * @author xiaowei
	 */
	private Point thisPoint(Point p){
		for(Iterator<Point> it = list.iterator(); it.hasNext(); ){
			Point listP = it.next();
			if(listP.getX()-p.getX()<=C && listP.getX()-p.getX()>=-C && listP.getY()-p.getY()<=C && listP.getY()-p.getY()>=-C){
				return listP;
			}
		}
		return null;
	}
	/*
	 * 传入一个棋子的坐标，判断它是黑棋还是白棋
	 * @param g
	 * @return
	 * @author xiaowei
	 */
	private int col(int x, int y){
		GoMoKu g = new GoMoKu(new Point(x, y));
		for(Iterator<GoMoKu> it = set.iterator(); it.hasNext(); ){
			GoMoKu g2 = it.next();
			Point c = g2.getP();
			if(c.equals(g.getP())){
				if(g2 instanceof Black){
					return 1;
				}else if(g2 instanceof White){
					return 0;
				}
			}
		}
		return -1;
	}


	
}
