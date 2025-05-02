package ca.nhd.comm.ui;

import ca.nhd.ApplicationStateManager;
import ca.nhd.devices.interfaces.IDevice;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.table.*;
/*
 * Created by JFormDesigner on Mon Apr 28 19:36:12 ADT 2025
 */


public class MainForm extends JPanel {
	private final ApplicationStateManager asm;

	public MainForm(ApplicationStateManager asm) {
		this.asm = asm;
		initComponents();
	}

	private void menuItemFileScanDevicesMousePressed(MouseEvent e) {
		this.asm.findNetworkDevices();

		for(IDevice device : this.asm.getDevices()){
			((DefaultTableModel) deviceTable.getModel()).addRow(new Object[] {
					device.getName(), device.getIp(), false, "OSC Listener", new Date()
			});
		}



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

		//======== this ========
		setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder (
		0, 0 ,0 , 0) ,  "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder
		. BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt. Color .
		red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void propertyChange (java .
		beans. PropertyChangeEvent e) { if( "\u0062ord\u0065r" .equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );

		//======== mainMenu ========
		{

			//======== fileMenu ========
			{
				fileMenu.setText("File");

				//---- menuItemFileScanDevices ----
				menuItemFileScanDevices.setText("Scan For Devices");
				menuItemFileScanDevices.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						menuItemFileScanDevicesMousePressed(e);
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

		//======== scrollPane2 ========
		{

			//---- deviceTable ----
			deviceTable.setModel(new DefaultTableModel(
				new Object[][] {
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

		//---- btnDevicePing ----
		btnDevicePing.setText("Ping");

		//---- btnDeviceEdit ----
		btnDeviceEdit.setText("Edit");

		//---- btnDeviceForget ----
		btnDeviceForget.setText("Forget");

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addComponent(mainMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(0, 363, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup()
						.addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
						.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
							.addGap(0, 245, Short.MAX_VALUE)
							.addComponent(btnDeviceForget)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(btnDevicePing)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(btnDeviceEdit)))
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addComponent(mainMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(btnDeviceEdit)
						.addComponent(btnDevicePing)
						.addComponent(btnDeviceForget))
					.addGap(0, 8, Short.MAX_VALUE))
		);
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
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
