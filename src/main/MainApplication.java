package main;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.app.client.ClientThread;
import main.app.config.ConnectedClient;
import main.app.config.Singleton;
import main.app.model.Server;
import main.app.observerdesign.client.ClientMessageSubject;
import main.app.server.ServerThread;
import main.app.server.observerdesign.ServerMessageObserver;

public class MainApplication extends JFrame {

	public static void main(String[] args)
	{
		new MainApplication();
	}
	
	/**
	 * MAIN APPLICATION COMPONENTS START
	 */
	private JButton addServer,deleteServer,sendToServer,connect,disconnect;
	private JComboBox comboBox;
	private JTextArea sendArea,receiveArea;
	private Singleton singleton;
	private ClientMessageSubject subject;
	private ServerMessageObserver observer;
	private JLabel receiveLabel,sendLabel;
	private ConnectedClient connectedClient;
	/**
	 * MAIN APPLICATION COMPONENTS END
	 */
	
	
	
	/**
	 * ON-LOAD DIALOG COMPONENTS(Initial) START
	 */
	private JDialog initialDialog;
	private JTextField initialField1, initialField2;
	private JLabel initialLabel1,initialLabel2;
	private JButton initialButtonCreate,initialButtonCancel;
	/**
	 * ON-LOAD DIALOG COMPONENTS(Initial) END
	 */
	
	/**
	 * ADD SERVER(PEER) DIALOG START
	 */
	private JDialog addServerDialog;
	private JTextField addServerField1,addServerField2,addServerField3;
	private JLabel addServerLabel1, addServerLabel2,addServerLabel3;
	private JButton addServerCreate,addServerCancel;
	
	/**
	 * ADD SERVER(PEER) DIALOG END
	 */
	
	//==========================================================================================
	public MainApplication()
	{
		singleton = Singleton.getSingletonInstance();
		connectedClient = ConnectedClient.getConnectedClientInstance();
		initialize();
		onLoad();
		
		JPanel panel = new JPanel();
		JPanel panelMiddle = new JPanel();
		JPanel panelMother = new JPanel();
		panelMother.setLayout(new BorderLayout());
		
		receiveLabel=new JLabel("Received:");
		sendLabel= new JLabel("Sent:");
		
		addServer = new JButton("Add Server");
		deleteServer = new JButton("Delete Server");
		sendToServer = new JButton("Send");
		connect = new JButton("Connect");
		disconnect = new JButton("Disconnect");
		
		
		comboBox = new JComboBox();
		comboBox.addItem("Peers");
		//comboBox.addItemListener(e -> showSelectedItem(e));
		
		addServer.addActionListener(e -> addServer());
		connect.addActionListener(e -> connectServer());
		disconnect.addActionListener(e -> disconnectServer());
		sendToServer.addActionListener(e -> sendMessageToServer());
		
		sendArea = new JTextArea(12,10);
		receiveArea = new JTextArea(12,10);
		sendArea.setLineWrap(true);
		sendArea.setWrapStyleWord(true);
		receiveArea.setLineWrap(true);
		receiveArea.setWrapStyleWord(true);
		
		
		JScrollPane pane = new JScrollPane(sendArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollPane pane2 = new JScrollPane(receiveArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		panel.add(addServer);
		panel.add(deleteServer);
		panel.add(comboBox);
		panel.add(connect);
		panel.add(disconnect);
		
		panelMiddle.add(receiveLabel);
		panelMiddle.add(pane2);
		panelMiddle.add(sendLabel);
		panelMiddle.add(pane);
		panelMiddle.add(sendToServer);
		
		panelMother.add(panel,BorderLayout.NORTH);
		panelMother.add(panelMiddle,BorderLayout.CENTER);
		
		observer = new ServerMessageObserver(receiveArea);
		this.add(panelMother);
	}
	//===========================================================================================
	public void showSelectedItem(ItemEvent e)
	{
		if(e.getSource()==null)
		{
		
			String comb = comboBox.getSelectedItem().toString();
			if(!comb.equals("Peers"))
			{
				System.out.println("The selected item comboBox is -"+comb);
			}
		}
		
	}
	/**
	 * @author zukaLover
	 * Description: Initializes Application configurations.
	 */
	public void initialize()
	{
		this.setSize(600,450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(false);
	}
	
	
	/**
	 * @author zukaLover
	 * Description: This onLoad method creates an initial dialog to capture details of APP
	 */
	public void onLoad()
	{
		initialDialog = new JDialog(this,"Create Peer");
		initialDialog.setSize(240, 240);
		JPanel panel = new JPanel();
		
		
		initialLabel1= new JLabel("Name: ");
		initialLabel2= new JLabel("Port: ");
		initialField1= new JTextField(14);
		initialField2= new JTextField(14);
		initialButtonCreate= new JButton("Create");
		initialButtonCancel= new JButton("Cancel");
		
		initialButtonCreate.addActionListener(e -> initialCreateServer());
		initialButtonCancel.addActionListener(e -> initialCancelCreate());
		
		panel.add(initialLabel1);
		panel.add(initialField1);
		panel.add(initialLabel2);
		panel.add(initialField2);
		panel.add(initialButtonCreate);
		panel.add(initialButtonCancel);
		
		
		initialDialog.add(panel);
		initialDialog.setVisible(true);
		
	}

	public void initialCreateServer()
	{
		String name = initialField1.getText().trim();
		String port = initialField2.getText().trim();
		
		if(!name.isEmpty() && !port.isEmpty())
		{
			singleton.setServerHostName("localhost");
			singleton.setServerPeerName(name);
			singleton.setServerPortNumber(Integer.parseInt(port));
			new Thread(new ServerThread(observer)).start();
			
			this.setVisible(true);
			this.setTitle("Peer Network Application: "+singleton.getServerPeerName()+"-"+singleton.getServerPortNumber());
			initialDialog.setVisible(false);
			
		}else {
			JOptionPane.showMessageDialog(this, "Input All Details", "All Details Needed", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public void initialCancelCreate()

	{
		System.exit(0);
	}

	/**
	 * @author zukaLover
	 * Description: This method adds a server we will be able to send messages TO
	 */
	public void addServer()

	{
		addServerDialog = new JDialog(this,"Add Server");
		addServerDialog.setSize(240, 240);
		
		JPanel panel = new JPanel();
		
		addServerField1 = new JTextField(15);
		addServerField2 = new JTextField(15);
		addServerField3 = new JTextField(15);
		addServerLabel1 = new JLabel("Host: ");
		addServerLabel2 = new JLabel("Port: ");
		addServerLabel3 = new JLabel("Name: ");
		addServerCreate = new JButton("Add");
		addServerCancel = new JButton("Cancel");
		
		addServerCreate.addActionListener(e -> addServerCreate());
		addServerCancel.addActionListener(e -> addServerCancel());
		
		panel.add(addServerLabel1);
		panel.add(addServerField1);
		panel.add(addServerLabel2);
		panel.add(addServerField2);
		panel.add(addServerLabel3);
		panel.add(addServerField3);
		panel.add(addServerCreate);
		panel.add(addServerCancel);
		
		addServerDialog.add(panel);
		addServerDialog.setVisible(true);
	}

	public void connectServer()

	{
		String combo = comboBox.getSelectedItem().toString();
		if(combo!=null)
		{
			if(!combo.equals("Peers"))
			{
				subject = new ClientMessageSubject();
				Server server = null;
				server = singleton.getServerByName(combo);
				new Thread(new ClientThread(server,subject)).start();
				
				JOptionPane.showMessageDialog(this,"The name is-"+server.getServerPeerName()+", The hostname is: "+server.getServerHostName()+", the Port is: "+server.getPort());
			}else {
				JOptionPane.showMessageDialog(this,"Please add a peer to send messages to","Add Peer",JOptionPane.INFORMATION_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(this,"Please add a peer to send messages to","Add Peer",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void disconnectServer()
	{
		String combo = comboBox.getSelectedItem().toString();
		if(combo!=null)
		{
			if(!combo.equals("Peers"))
			{
				String[] splitString= combo.split("-");
				
				Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
				
				for(Thread thread: threadSet)
				{
					String threadName = thread.getName();
					String[] threadSplit = threadName.split("-");
					System.out.println(threadName);
					if(threadSplit.length>=2)
					{
						;
						int port = Integer.parseInt(threadSplit[1]);
						if(port == Integer.parseInt(splitString[1]))
						{
							thread.interrupt();
							System.out.println("THREAD INTERRUPTED:"+port);
						}
					}
					
					
				}
			}
		}
	}
	
	
	
	public void sendMessageToServer()
	{
		String message = sendArea.getText();
		if(message != null)
		{
			String combo = comboBox.getSelectedItem().toString();
			if(combo!=null)
			{
				subject.sendMessage(message);
			}
		}else {
			JOptionPane.showMessageDialog(this,"No Message Available","Add Message",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void addServerCreate() 
	{
		String host = addServerField1.getText().trim();
		String port = addServerField2.getText().trim();
		String name = addServerField3.getText().trim();
		
		if(!host.isEmpty() && !port.isEmpty() && !name.isEmpty())
		{
			Server server = new Server();
			server.setPort(Integer.parseInt(port));
			server.setServerHostName(host);
			server.setServerPeerName(name);
			singleton.addServer(server);
			addServerDialog.setVisible(false);
			updateCombo();
			
		}else {
			JOptionPane.showMessageDialog(this,"Provide all Details","All Details Required",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void updateCombo()
	{
		List<Server> servers = new ArrayList<>();
		comboBox.removeAllItems();
		
		for(Server server: singleton.getAllServers())
		{
			comboBox.addItem(server.getServerPeerName()+"-"+server.getPort());
		}
	}
	
	public void addServerCancel()
	{
		addServerDialog.setVisible(false);
	}
	
	
}
