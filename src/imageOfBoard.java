import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("serial")
public class imageOfBoard extends JFrame {
	
	public int w = 15, h = 15;

	public boolean playing = false;
	
	public boolean[][] board = new boolean[w][h];
	
	public JLabel outImage = new JLabel();
	
	public JButton reset = new JButton("Reset Grid!"), toggleSim = new JButton("Turn On!"),
			save = new JButton("Save Board!"), load = new JButton("Load Board!");
	
	public BufferedImage boardImage;
	
	public JTextField size = new JTextField(15), saveName = new JTextField(15);
	
	public JLabel sizeText = new JLabel("Grid Size:"), saveText = new JLabel("Board Name:"),
			speedText = new JLabel("Game Speed:");
	
	public JComboBox<String> speed = new JComboBox<String>();
	
	public JCheckBox loop = new JCheckBox("Loop");
	
	public String font = "Calibri";
	
	public imageOfBoard() {
		setTitle("Conway's Game Of Life");
		getContentPane().setLayout(null);
		updateBoard();
		
		/*** BOARD ***/
		
		outImage.setBounds(0, 0, 750, 750);
		add(outImage);
		
		/*** GAME ON AND OFF BUTTION ***/
		
		toggleSim.setBounds(775, 25, 200, 75);
		toggleSim.setFont(new Font(font, Font.BOLD, 36));
		toggleSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            playing = !playing;
	            if(playing){
	            	toggleSim.setText("Turn Off!");
	            	setTitle("Conway's Game Of Life (ON)");
	            } else {
	            	toggleSim.setText("Turn On!");
	            	setTitle("Conway's Game Of Life (OFF)");
	            }
			}
		});
		add(toggleSim);
		
		/*** LOOP BUTTON ***/
		
		loop.setBounds(825, 125, 100, 50);
		loop.setFont(new Font(font, Font.BOLD, 30));
		add(loop);
		
		/*** SIZE AND RESET ***/
		
		sizeText.setBounds(815, 185, 200, 50);
		sizeText.setFont(new Font(font, Font.BOLD, 30));
		add(sizeText);
		
		size.setBounds(775, 225, 200, 50);
		size.setFont(new Font(font, Font.PLAIN, 24));
		size.setText("15");
		add(size);
		
		reset.setBounds(775, 300, 200, 75);
		reset.setFont(new Font(font, Font.BOLD, 30));
		reset.addActionListener(new ActionListener() {
			   @Override
			   public void actionPerformed(ActionEvent e) {
				   	playing = false;
				   	toggleSim.setText("Turn On!");
	            	setTitle("Conway's Game Of Life (OFF)");
				   try{
					   board = new boolean[Integer.valueOf(size.getText())][Integer.valueOf(size.getText())];
				   } catch (Exception q) {
					   board = new boolean[15][15];
				   }
			   }
			});
		add(reset);
		
		/*** SPEED SETTINGS ***/
		
		speedText.setBounds(795, 410, 200, 50);
		speedText.setFont(new Font(font, Font.BOLD, 30));
		add(speedText);
		
		String[] speeds = {"Slowest (2000ms)","Slow (1000ms)","Normal (500ms)","Fast (250ms)","Faster (100ms)","Fastest (25)", "Maximum"};
		speed = new JComboBox<String>(speeds);
		speed.setSelectedItem(speeds[3]);
		speed.setBounds(775, 460, 200, 35);
		speed.setFont(new Font(font, Font.PLAIN, 20));
		add(speed);
		
		/*** SAVING STUFF ***/
		
		saveText.setBounds(795, 525, 200, 50);
		saveText.setFont(new Font(font, Font.BOLD, 30));
		add(saveText);
		
		saveName.setBounds(775, 575, 200, 50);
		saveName.setFont(new Font(font, Font.ITALIC, 24));
		saveName.setText("World Name");
		add(saveName);
		
		save.setBounds(785, 635, 175, 40);
		save.setFont(new Font(font, Font.BOLD, 20));
		save.addActionListener(new ActionListener() {
			   @Override
			   public void actionPerformed(ActionEvent e) {
				   save();
			   }
			   });
		add(save);
		
		load.setBounds(785, 685, 175, 40);
		load.setFont(new Font(font, Font.BOLD, 20));
		load.addActionListener(new ActionListener() {
			   @Override
			   public void actionPerformed(ActionEvent e) {
				   playing = false;
				   load();
			   }
			});
		add(load);
		
		setSize(1015,790);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            	int roundX = Math.round(e.getX()/((750/board[0].length)));
            	int roundY = Math.round(e.getY()/((750/board[1].length)));
                try{
	                if(!playing){
		                board[roundX][roundY] = !board[roundX][roundY];
		                updateBoard();
		            }
                } catch (ArrayIndexOutOfBoundsException a) {}
            }
        });
	}
	
	public void updateBoard(){
		Color white = new Color(255, 255, 255);
		Color blue = new Color(0, 0, 255);
		Color red = new Color(255, 0, 0);
		Color blueGrey = new Color(64, 64, 255);
		Color grey = new Color(192, 192, 192);
		
		boardImage = new BufferedImage(750, 750, BufferedImage.TYPE_INT_RGB);
		if(playing){
			LifeEngine.updateBoard(board, loop.isSelected());
		}
		for(int x = 0; x < 750; x++){
			for(int y = 0; y < 750; y++){
				try{
					
				int tX = Math.round(x/(750/board[0].length)), tY = Math.round(y/(750/board[1].length));
				boolean temp = board[tX][tY];
				
				if(x%(750/board[0].length) == 0 || y%(750/board[0].length) == 0){
					if (temp) { boardImage.setRGB(x, y, blueGrey.getRGB()); }
					else { boardImage.setRGB(x, y, grey.getRGB()); }
				} else if(temp){
					boardImage.setRGB(x, y, blue.getRGB());
				} else {
					boardImage.setRGB(x, y, white.getRGB());
				}
				} catch (ArrayIndexOutOfBoundsException e){boardImage.setRGB(x, y, red.getRGB());}
			}
		}
		outImage.setPreferredSize(new Dimension(750, 750));
		outImage.setIcon(new ImageIcon(boardImage));
		outImage.revalidate();
		outImage.repaint();
		outImage.update(outImage.getGraphics());
	}
	
	public void save() {
		try{
			boolean temp = playing;
			playing = false;
			FileOutputStream stream = new FileOutputStream(new File("LifeBoard (" + saveName.getText() + ").bin"));
			boolean[] board1D = new boolean[board[0].length*board[1].length];
		   
			for(int i = 0; i < board[0].length; i++){
			   for(int ii = 0; ii < board[1].length; ii++){
				   board1D[(i*board[1].length)+ii] = board[i][ii];
			   }
			}
		   
			for (boolean item : board1D)
			{
		       stream.write(item ? 1 : 0);
			}
			stream.close();
			playing = temp;
			updateBoard();
		} catch(IOException e){}
	}
	
	public void load() {
		try{
			playing = false;
			toggleSim.setText("Turn On!");
        	setTitle("Conway's Game Of Life (OFF)");
		    File file = new File("LifeBoard (" + saveName.getText() + ").bin");
		    FileInputStream inputStream = new FileInputStream(file);
		    int fileLength = (int) file.length();

		    byte[] data = new byte[fileLength];
		    boolean[] temp = new boolean[fileLength];

		    inputStream.read(data);
		    for (int X = 0; X < data.length; X++)
		    {
		        if (data[X] != 0)
		        {
		        	temp[X] = true;
		            continue;
		        }
		        temp[X] = false;
		    }
		    
		    board = new boolean[(int) Math.sqrt(temp.length)][(int) Math.sqrt(temp.length)];
		    for(int i = 0; i < Math.sqrt(temp.length); i++){
				for(int ii = 0; ii < Math.sqrt(temp.length); ii++){
					board[i][ii] = temp[(int) ((i*Math.sqrt(temp.length))+ii)];
				}
			}
		    inputStream.close();
		} catch(Exception e){
			try{
			   board = new boolean[Integer.valueOf(size.getText())][Integer.valueOf(size.getText())];
			} catch (Exception q) {
			   board = new boolean[15][15];
			}
		}
	}
	
	public int getDelay(){
			switch(speed.getSelectedIndex()){
			case 0:
				return 2000;
			case 1:
				return 1000;
			case 2:
				return 500;
			case 4:
				return 100;
			case 5:
				return 25;
			case 6:
				return 0;
			default:		
				return 250;
		}
	}
}
