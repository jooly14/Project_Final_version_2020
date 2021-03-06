package client;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ChatRoomDetailDialog extends JDialog implements ActionListener{
	ChatClient chatClient;
	JLabel lblNumAndName, lblOwner;
	
	JTable tableParticipants;
	DefaultTableModel modelParticipants;
	String[] headerParticipants = {"채팅방 접속자"};
	String[][] contentParticipants = {};
	
	JButton btnOk, btnCancel;
	JPasswordField pwField;
	JLabel lblPassword;
	String[] split;
	
	String secret;	
	String roomNum;
	String roomName;
	public ChatRoomDetailDialog(ChatClient chatClient,String[] split,String roomName) {
		super(chatClient,true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(null);
		this.chatClient = chatClient;
		this.roomName = roomName;
		this.split = split.clone();
		roomNum = split[1];
		
		lblNumAndName = new JLabel("["+roomNum+"] "+roomName);
		lblOwner = new JLabel("방장 : " + split[3]);
		
		
		//값 수정 안되도록
		modelParticipants = new DefaultTableModel(contentParticipants, headerParticipants){
			public boolean isCellEditable(int rowIndex, int colIndex){
				return false;
			}
		};
		tableParticipants = new JTable(modelParticipants);
		JScrollPane pane_tableParticipants = new JScrollPane(tableParticipants);
		
		for (int i = 4; i < split.length; i++) {
			modelParticipants.addRow(new String[]{split[i]});
		}
		
		tableParticipants.getTableHeader().setReorderingAllowed(false);
		tableParticipants.getTableHeader().setResizingAllowed(false);
		tableParticipants.setRowSorter(new TableRowSorter<DefaultTableModel>(modelParticipants));
		
		
		btnOk = new JButton("입장");
		btnCancel = new JButton("취소");
		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);
		
		JLabel lblTitle = new JLabel("대화방 정보");
		lblTitle.setFont(new Font(null,Font.BOLD,30));
		lblTitle.setBounds(0,0,280,50);
		lblNumAndName.setBounds(0, 50, 280, 30);
		lblOwner.setBounds(0, 80, 280, 30);
		pane_tableParticipants.setBounds(0, 110, 280, 150);
		
		tableParticipants.getTableHeader().setBackground(Color.white);
		pane_tableParticipants.getViewport().setBackground(Color.white);
		btnOk.setBackground(Color.WHITE);
		btnCancel.setBackground(Color.white);
		
		
		add(lblTitle);
		add(btnOk);
		add(btnCancel);
		add(lblNumAndName);
		add(lblOwner);
		add(pane_tableParticipants);
		
		secret = split[2];
		if(secret.equals("YES")){
			secretRoom(split);
		}else{
			notSecretRoom(split);
		}
		setVisible(true);
	}
	public void secretRoom(String[] split){
		setSize(300,370);
		
		pwField = new JPasswordField();
		lblPassword = new JLabel("비밀번호 : ");
		lblPassword.setBounds(0, 260, 80, 30);
		pwField.setBounds(90, 260, 190, 30);
		
		btnOk.setBounds(80, 290, 100, 30);
		btnCancel.setBounds(180, 290, 100, 30);
		
		add(pwField);
		add(lblPassword);
		pwField.requestFocus();
	}
	public void notSecretRoom(String[] split){
		setSize(300,370);
		btnOk.setBounds(80, 290, 100, 30);
		btnCancel.setBounds(180, 290, 100, 30);
		
	}
	public void correctPassword(){	//비밀 대화방의 비밀번호가 일치하는 경우
		dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOk){
			if(secret.equals("YES")){
				if(pwField.getPassword().length==0){
					JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요");
					pwField.requestFocus();
				}else{
					chatClient.sendMsgEnterSecretRoom(roomNum,pwField.getPassword());
					dispose();
				}
			}else{
				chatClient.sendMsgEnterRoom(roomNum);
				dispose();
			}
		}else if(e.getSource() == btnCancel){
			dispose();
		}
	}
	public String[] getSplit() {
		return split;
	}
	public String getRoomName() {
		return roomName;
	}
	
	
}
