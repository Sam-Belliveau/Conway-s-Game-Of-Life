public class LifeInterface {
	public static void main(String[] args) throws InterruptedException{
		imageOfBoard image = new imageOfBoard();
		image.setVisible(true);
		for(;;){
			image.updateBoard();
			image.validate();
			Thread.sleep(250);
		}
	}
}
