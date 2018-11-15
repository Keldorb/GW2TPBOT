import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class GUImain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	String[] itemList;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUImain window = new GUImain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUImain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		DefaultListModel<String> model = new DefaultListModel<>();

		for ( int i = 0; i < 120; i++ ){
		  model.addElement( "hallo"+i );
		}
		
		 String categories[] = { "Household", "Office", "Extended Family",
			        "Company (US)", "Company (World)", "Team", "Will",
			        "Birthday Card List", "High School", "Country", "Continent",
			        "Planet" };
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBackground(Color.GRAY);
		frame.setBounds(100, 100, 762, 868);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("MFB - MysticForgeBuddy");
		frame.getContentPane().setLayout(null);
		
		JList list = new JList(categories);
		JScrollPane scrollableList = new JScrollPane(list);
		scrollableList.setBounds(81, 348, 156, 128);
		frame.getContentPane().add(scrollableList);
		
		Settings.imagePath = "D:\\Backup Windows\\21.06.2018\\eclipse-workspace\\GW2TPBOT\\screenshots\\";
		System.out.println(Settings.imagePath+"item_0.jpg");
		ImageIcon item0 = new ImageIcon(Settings.imagePath+"item_0.png");
		JButton button = new JButton(item0);
		button.setBounds(45, 50, 50, 50);
		frame.getContentPane().add(button);
		
		
	}
}
