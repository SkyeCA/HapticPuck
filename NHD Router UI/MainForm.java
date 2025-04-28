import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Mon Apr 28 13:29:00 ADT 2025
 */



/**
 * @author connoroliver
 */
public class MainForm extends JPanel {
	public MainForm() {
		initComponents();
	}

	private void menuItemFileScanDevicesMouseClicked(MouseEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
		// Generated using JFormDesigner Evaluation license - Sidney Oliver
		mainMenu = new JMenuBar();
		fileMenu = new JMenu();
		menuItemFileScanDevices = new JMenuItem();
		menuItemFileLogs = new JMenuItem();
		menuItemFileExit = new JMenuItem();
		oscMenu = new JMenu();
		menuItemOscListenerConfig = new JMenuItem();
		menuItemOscOscSettings = new JMenuItem();
		menuHelp = new JMenu();
		menuItemHelpGithub = new JButton();
		scrollPane2 = new JScrollPane();
		deviceTable = new JTable();
		btnDevicePing = new JButton();
		btnDeviceEdit = new JButton();
		btnDeviceForget = new JButton();
		btnExit = new JButton();

		//======== this ========
		setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.
		swing.border.EmptyBorder(0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border
		.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("D\u0069alog"
		,java.awt.Font.BOLD,12),java.awt.Color.red), getBorder
		())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java
		.beans.PropertyChangeEvent e){if("\u0062order".equals(e.getPropertyName()))throw new RuntimeException
		();}});
		setLayout(new FormLayout(
			"default, $lcgap, 34dlu, 5*($lcgap, default)",
			"3*(default, $lgap), default"));

		//======== mainMenu ========
		{

			//======== fileMenu ========
			{
				fileMenu.setText("File");

				//---- menuItemFileScanDevices ----
				menuItemFileScanDevices.setText("Scan For Devices");
				menuItemFileScanDevices.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						menuItemFileScanDevicesMouseClicked(e);
					}
				});
				fileMenu.add(menuItemFileScanDevices);

				//---- menuItemFileLogs ----
				menuItemFileLogs.setText("Logs");
				fileMenu.add(menuItemFileLogs);
				fileMenu.addSeparator();

				//---- menuItemFileExit ----
				menuItemFileExit.setText("Exit");
				fileMenu.add(menuItemFileExit);
			}
			mainMenu.add(fileMenu);

			//======== oscMenu ========
			{
				oscMenu.setText("OSC");

				//---- menuItemOscListenerConfig ----
				menuItemOscListenerConfig.setText("Listener Config");
				oscMenu.add(menuItemOscListenerConfig);

				//---- menuItemOscOscSettings ----
				menuItemOscOscSettings.setText("OSC Settings");
				oscMenu.add(menuItemOscOscSettings);
			}
			mainMenu.add(oscMenu);

			//======== menuHelp ========
			{
				menuHelp.setText("Help");

				//---- menuItemHelpGithub ----
				menuItemHelpGithub.setText("GitHub");
				menuHelp.add(menuItemHelpGithub);
			}
			mainMenu.add(menuHelp);
		}
		add(mainMenu, CC.xy(1, 1));

		//======== scrollPane2 ========
		{

			//---- deviceTable ----
			deviceTable.setModel(new DefaultTableModel(
				new Object[][] {
					{"Unknown", null, true, null, null},
				},
				new String[] {
					"Name", "IP", "Online", "OSC Listener", "Last Message"
				}
			) {
				Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class, Boolean.class, String.class, Date.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false, false, false, false
				};
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}
			});
			scrollPane2.setViewportView(deviceTable);
		}
		add(scrollPane2, CC.xywh(1, 3, 13, 1));

		//---- btnDevicePing ----
		btnDevicePing.setText("Ping");
		add(btnDevicePing, CC.xy(9, 5));

		//---- btnDeviceEdit ----
		btnDeviceEdit.setText("Edit");
		add(btnDeviceEdit, CC.xy(11, 5));

		//---- btnDeviceForget ----
		btnDeviceForget.setText("Forget");
		add(btnDeviceForget, CC.xy(13, 5));

		//---- btnExit ----
		btnExit.setText("Exit");
		add(btnExit, CC.xy(13, 7));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
	// Generated using JFormDesigner Evaluation license - Sidney Oliver
	private JMenuBar mainMenu;
	private JMenu fileMenu;
	private JMenuItem menuItemFileScanDevices;
	private JMenuItem menuItemFileLogs;
	private JMenuItem menuItemFileExit;
	private JMenu oscMenu;
	private JMenuItem menuItemOscListenerConfig;
	private JMenuItem menuItemOscOscSettings;
	private JMenu menuHelp;
	private JButton menuItemHelpGithub;
	private JScrollPane scrollPane2;
	private JTable deviceTable;
	private JButton btnDevicePing;
	private JButton btnDeviceEdit;
	private JButton btnDeviceForget;
	private JButton btnExit;
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
