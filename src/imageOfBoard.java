import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class imageOfBoard extends JFrame {
	
	public int w = 15, h = 15;

	public boolean playing = false;
	
	public boolean[][] board = new boolean[w][h];
	
	public JLabel outImage = new JLabel();
	
	public JButton reset = new JButton("Reset Grid!"), toggleSim = new JButton("Toggle Game!");
	
	public BufferedImage boardImage;
	
	public JTextField size = new JTextField(15);
	
	public JLabel sizeText = new JLabel("Grid Size:");
	
	public imageOfBoard(){
		setTitle("Conway's Game Of Life");
		getContentPane().setLayout(null);
		updateBoard();
		
		outImage.setBounds(0, 0, 750, 750);
		add(outImage);
		
		sizeText.setBounds(25, 788, 200, 50);
		sizeText.setFont(new Font("Arial", Font.BOLD, 30));
		add(sizeText);
		
		size.setBounds(175, 788, 100, 50);
		size.setFont(new Font("Arial", Font.PLAIN, 24));
		add(size);
		
		reset.setBounds(300, 775, 200, 75);
		reset.setFont(new Font("Arial", Font.BOLD, 24));
		reset.addActionListener(new ActionListener() {
			   @Override
			   public void actionPerformed(ActionEvent e) {
				   playing = false;
				   try{
					   board = new boolean[Integer.valueOf(size.getText())][Integer.valueOf(size.getText())];
				   } catch (Exception q) {
					   board = new boolean[15][15];
				   }
			   }
			});
		add(reset);
		
		toggleSim.setBounds(525, 775, 200, 75);
		toggleSim.setFont(new Font("Arial", Font.BOLD, 24));
		toggleSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            playing = !playing;
	            if(playing){
	            	setTitle("Conway's Game Of Life (Playing)");
	            } else {
	            	setTitle("Conway's Game Of Life");
	            }
			}
		});

		add(toggleSim);
		
		setSize(750,900);
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
			LifeEngine.updateBoard(board);
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
}
