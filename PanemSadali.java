import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Arrays;  


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanemSadali implements ActionListener {
	Random random = new Random();
	JFrame frame = new JFrame();
	JPanel title_panel = new JPanel();
	JPanel score_panel = new JPanel();
	JPanel game_panel = new JPanel();
	JLabel numbers[] = new JLabel[5];
	JLabel text = new JLabel();
	JButton players[] = new JButton[2];
	JButton dalit = new JButton();
	JButton panemt = new JButton();
	boolean player1_turn;
	int p1, p2 = 0;
	int sk[] = {3,2,1};
	PanemSadali(){ // tik ieveidots spēles lauks ar visiem atribūtiem tajā
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,400);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		
		text.setBackground(new Color(25,25,25));
		text.setForeground(new Color(255,255,255));
		text.setFont(new Font("Ariel", Font.BOLD,75));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setText("Paņem vai Sadali");
		text.setOpaque(true);
		
		title_panel.setLayout(new BorderLayout());
		title_panel.setPreferredSize(new Dimension(800,100));
		title_panel.add(text);
		
		game_panel.setLayout(new GridLayout());
		game_panel.setPreferredSize(new Dimension(800,100));
		
		score_panel.setLayout(new GridLayout(1,5,10,0));
		
		for(int i=0;i<2;i++) {
			players[i] = new JButton();
			game_panel.add(players[i]);
			players[i].setText("Player " + (i+1));
			players[i].setFont(new Font("Ariel", Font.BOLD,50));
			players[i].addActionListener(this);
			if(i==0)
				players[i].setForeground(Color.GREEN);
			else 
				players[i].setForeground(Color.RED);
		}
		
		frame.add(title_panel,BorderLayout.NORTH);
		frame.add(score_panel,BorderLayout.CENTER);
		frame.add(game_panel,BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) { //tiek izsaukta brīdī, kad tiek piespiesta kāda poga
		
		boolean choice; 
		
		if(e.getSource() == players[0]){
			player1_turn = true;
			setBoard();
		}
		else if(e.getSource() == players[1]){
			player1_turn = false;
			setBoard();
		}
		if(e.getSource() == panemt) {
			Panemt();
		}
		if(e.getSource() == dalit) {
			Dalit();
		}
		if(player1_turn==false) {           //ja false, tad PC izpilda gājienu
			
			choice = bestMove();
			
			if(choice == true || dalit.isEnabled() == false) {
				Panemt();
			}else {
				Dalit();
			}
		}
	}
	
	public boolean bestMove() { //tiek veikts lēmums par labāko gājienu
		int[] temp = sk;
        int min = temp[0];
        int max = temp[0];

        for (int i = 0; i < temp.length; i++) {  
            if(temp[i] < min) { 
                min = temp[i];
            }
        }
        
        for (int i = 0; i < temp.length; i++) {  
           if(temp[i] > max) { 
               max = temp[i];
           }
        }
		
        // te min un max var izmantot kā nepieciešams
        // ja bestMove atgriež true tad būs Panemt ja false tad Dalit
		if(Math.abs(min+p2) > Math.abs(max-p1)) {
			return true;
		}
		return false;
	}
	
	public void setBoard() { //pēc tam kad spēlētājs veic izvēli par Player1 vai Player2, laukums tiek notīrīts un uzstādīts spēlēs laukums
		game_panel.removeAll();
		for(int i=0;i<5;i++) {
			numbers[i] = new JLabel();
			numbers[i].setFont(new Font("Ariel",Font.BOLD,50));
			numbers[i].setHorizontalAlignment(JLabel.CENTER);
			numbers[i].setVerticalAlignment(JLabel.CENTER);
			score_panel.add(numbers[i]);
		}
		dalit.setText("Dalīt");
		dalit.setFont(new Font("Ariel",Font.BOLD,30));
		dalit.addActionListener(this);
		game_panel.add(dalit);
		panemt.setFont(new Font("Ariel",Font.BOLD,30));
		panemt.setText("Paņemt");
		panemt.addActionListener(this);
		game_panel.add(panemt);
		update();
		frame.revalidate();
		frame.repaint();
	}
	public void update() { //atjauno spēles laukumu ar jaunām vērtībām, kas tiek izmainītas spēles gaitā
		for(int i=0;i<5;i++) {
			if(i==0) {
				numbers[i].setForeground(Color.GREEN);
				numbers[i].setText(Integer.toString(p1));
			}else if(i==4) {
				numbers[i].setForeground(Color.RED);
				numbers[i].setText(Integer.toString(p2));
			}else{if(sk[i-1]==0)
					numbers[i].setText("-");
				else
					numbers[i].setText(Integer.toString(sk[i-1]));
			}
	}
		if(sk[0]==0) {
		panemt.setEnabled(false);
		dalit.setEnabled(false);
		if(p1>p2) {
			text.setForeground(Color.GREEN);
			text.setText("Player 1 uzvar");
		}else if(p1==p2) {
			text.setForeground(Color.GRAY);
			text.setText("Neizšķirts");
		}else {
			text.setForeground(Color.RED);
			text.setText("Player 2 uzvar");
		}
	}else {
		if(player1_turn) {
			text.setText("Player 1 gājiens");
			text.setForeground(Color.GREEN);
		}else{
			text.setText("Player 2 gājiens");
			text.setForeground(Color.RED);
		}
	}
	}
	public void Panemt() { //Metode ar kuru tiek izpildīts gājiens paņemt
		int temp=sk[0], temp_sk = 0;
		for(int i=0;i<sk.length-1;i++) {
			if(sk[i]>=sk[i+1] & sk[i+1]!=0) {
				temp = sk[i+1];
				temp_sk = i+1;
			}
		}
		if(player1_turn){
			p1 = p1 + temp;
			player1_turn = false;
		}else{
			p2 = p2 + temp;
			player1_turn = true;
		}
		sk[temp_sk] = 0;
		update();
	}
	public void Dalit() { //Metode ar kuru tiek izpildīts gājiens dalit
		int temp=sk[0], temp_sk = 0;
		
		if(sk[0]!=1) {
			for(int i=0;i<sk.length-1;i++) {
				if(sk[i]>=sk[i+1] & sk[i+1]>1) {
					temp = sk[i+1];
					temp_sk = i+1;
				}
			}
			if(player1_turn){
				p2 = p2 - temp;
				player1_turn = false;
			}else{
				p1 = p1 - temp;
				player1_turn = true;
			}
			
			sk[temp_sk] = --temp;
			
		}
		if(sk[0]==1) {
			dalit.setEnabled(false);
		}
		update();
	}
	public static void main(String[] args) { //spēles sākums
			PanemSadali panemsadali = new PanemSadali();
			 //tiek izveidots spēles objekts
	}
	
}
