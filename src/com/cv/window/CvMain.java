package com.cv.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import com.cv.window.Window.State;

public class CvMain
{

	static Window w;
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		w = new Window();
		frame.add(w);
		frame.setSize(Window.WIDTH, Window.WIDTH+20);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.addMouseListener(new MouseListener()
		{
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				w.addStone(e.getPoint());
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub
				 
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
		
		frame.addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					if(w.getState() == State.主菜单){
						w.setState(State.开始);
					}else if(w.getState() == State.开始){
						w.setState(State.暂停);
					}else if(w.getState() == State.暂停){
						w.setState(State.开始);
					}else if(w.getState() == State.结束){
						w.setState(State.主菜单);
					}
				}
				
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		w.action();
	}

}
