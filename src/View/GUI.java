package View;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class GUI extends JFrame{

	public JTextField tfRefSig;
	public JTextField tfTestSig;
	private ActionListener controller;
	private JPanel panel;
	//*********************************************
	// variables updated by controller
	//*********************************************
	public JLabel rTotalTime;
	public JLabel sdTotalTime;
	public JLabel trTotalTime;
	public JLabel rPenUpTime;
	public JLabel sdPenUpTime;
	public JLabel trPenUpTime;
	public JLabel rNoPenUps;
	public JLabel sdNoPenUps;
	public JLabel trNumPenUps;
	public JLabel rTotalLength;
	public JLabel sdTotalLength;
	public JLabel trTotalLength;
	public JLabel rAvVelX;
	public JLabel sdAvVelX;
	public JLabel trAvVelX;
	public JLabel rAvVelY;
	public JLabel sdAvVelY;
	public JLabel trAvVelY;
	public JLabel rAvVelBoth;
	public JLabel sdAvVelBoth;
	public JLabel trAvVelBoth;
	public JLabel rMinVelX;
	public JLabel sdMinVelX;
	public JLabel trMinVelX;
	public JLabel rMinVelY;
	public JLabel sdMinVelY;
	public JLabel trMinVelY;
	public JLabel rMinVelBoth;
	public JLabel sdMinVelBoth;
	public JLabel trMinVelBoth;
	public JLabel rMaxVelX;
	public JLabel sdMaxVelX;
	public JLabel trMaxVelX;
	public JPanel pnlSigProfile;
	public JPanel pnlSigShape;
	public JLabel rMaxVelY;
	public JLabel sdMaxVelY;
	public JLabel trMaxVelY;
	public JLabel rMaxVelBoth;
	public JLabel sdMaxVelBoth;
	public JLabel trMaxVelBoth;
	public JLabel rAvAccelX;
	public JLabel sdAvAccelX;
	public JLabel trAvAccelX;
	public JLabel rAvAccelY;
	public JLabel sdAvAccelY;
	public JLabel trAvAccelY;
	public JLabel rAvAccelBoth;
	public JLabel sdAvAccelBoth;
	public JLabel trAvAccelBoth;
	public JLabel rMinAccelX;
	public JLabel sdMinAccelX;
	public JLabel trMinAccelX;
	public JLabel rMinAccelY;
	public JLabel sdMinAccelY;
	public JLabel trMinAccelY;
	public JLabel rMinAccelBoth;
	public JLabel sdMinAccelBoth;
	public JLabel trMinAccelBoth;
	public JLabel rMaxAccelX;
	public JLabel sdMaxAccelX;
	public JLabel trMaxAccelX;
	public JLabel rMaxAccelY;
	public JLabel sdMaxAccelY;
	public JLabel trMaxAccelY;
	public JLabel rMaxAccelBoth;
	public JLabel sdMaxAccelBoth;
	public JLabel trMaxAccelBoth;
	public JLabel rWED;
	public JLabel sdWED;
	public JLabel trWED;
	public JLabel lblResult;
	//****************************************
	
	/**
	 * Create the application.
	 */
	public GUI(ActionListener controller) {
		this.controller = controller;		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		setTitle("Signature Verification");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 825);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		
		final JFileChooser fc = new JFileChooser();
		panel.setLayout(new MigLayout("", "[136px][4px][150px][21px][134px][4px][51px][8px][61px][4px][11px][136px][442.00px]", "[25px][23px][4px][23px][1px][15px][14px][4px][1px][4px][14px][4px][14px][4px][14px][4px][14px][13px][14px][12px][14px][4px][14px][4px][14px][4px][14px][0.00px][14px][14px][14px][4px][14px][4px][14px][13px][14px][13px][14px][4px][14px][4px][14px][4px][14px][4px][14px][4px][14px][4px][14px][4px][14px][14px][14.00][14px][15.00][14px]"));
		
		JLabel lblOnlineHandwrittenSignature = new JLabel("Online Handwritten Signature Verification");
		lblOnlineHandwrittenSignature.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblOnlineHandwrittenSignature, "cell 2 0 7 1,alignx left,aligny top");
		
		JLabel lblRefSig = new JLabel("Reference Signature File");
		panel.add(lblRefSig, "cell 0 1,alignx left,aligny center");
		
		tfRefSig = new JTextField();
		tfRefSig.setColumns(10);
		panel.add(tfRefSig, "cell 2 1 3 1,growx,aligny center");
		
		//*******************************************
		// handle the file chooser
		//*******************************************
		JButton btnFileFindRef = new JButton("...");
		btnFileFindRef.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//file browser
				//tfRefSig
				int returnVal = fc.showOpenDialog(panel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            tfRefSig.setText(file.getPath());
		        } 
			}
		});
		panel.add(btnFileFindRef, "cell 6 1,growx,aligny top");
		
		JButton btnFileFindTest = new JButton("...");
		btnFileFindTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//file browser
				//tfRefSig
				int returnVal = fc.showOpenDialog(panel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            tfTestSig.setText(file.getPath());
		        } 
			}
		});
		panel.add(btnFileFindTest, "cell 6 3,growx,aligny top");
		//**********************************************
		
		JButton btnVerify = new JButton("Verify");
		btnVerify.setActionCommand("Verify");
		btnVerify.addActionListener(controller);
		panel.add(btnVerify, "cell 8 1 3 3,grow");
		
		lblResult = new JLabel("Click 'Verify'");
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel.add(lblResult, "cell 11 1 2 3,alignx center,aligny center");
		
		JLabel lblTestSig = new JLabel("Test Signature File");
		panel.add(lblTestSig, "cell 0 3,growx,aligny center");
		
		tfTestSig = new JTextField();
		tfTestSig.setColumns(10);
		panel.add(tfTestSig, "cell 2 3 3 1,growx,aligny center");
		
		JSeparator separator = new JSeparator();
		panel.add(separator, "cell 0 4 13 1,grow");
		
		JLabel lblStats = new JLabel("Stats");
		lblStats.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblStats, "cell 4 5,alignx left,aligny top");
		
		JLabel lblFeature = new JLabel("Feature");
		lblFeature.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblFeature, "cell 0 6,alignx left,aligny top");
		
		JLabel lblReferenceSigValue = new JLabel("Reference Signature Value");
		lblReferenceSigValue.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblReferenceSigValue, "cell 2 6,alignx left,aligny top");
		
		JLabel lblStandardDeviation = new JLabel("Standard Deviation");
		lblStandardDeviation.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblStandardDeviation, "cell 4 6,alignx left,aligny top");
		
		JLabel lblTestSignatureValue = new JLabel("Test Signature Value");
		lblTestSignatureValue.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(lblTestSignatureValue, "cell 6 6 3 1,alignx left,aligny top");
		
		pnlSigShape = new JPanel();
		pnlSigShape.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(pnlSigShape, "cell 10 6 3 23,grow");
		
		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1, "cell 0 8 9 1,grow");
		
		JLabel lblTotalTime = new JLabel("Total Time");
		panel.add(lblTotalTime, "cell 0 10,alignx left,aligny top");
		rTotalTime = new JLabel("-");
		panel.add(rTotalTime, "cell 2 10,alignx left,aligny top");
		sdTotalTime = new JLabel("-");
		panel.add(sdTotalTime, "cell 4 10,alignx left,aligny top");
		trTotalTime = new JLabel("-");
		panel.add(trTotalTime, "cell 6 10,alignx left,aligny top");
		
		JLabel lblPenUpTime = new JLabel("Pen Up Time");
		panel.add(lblPenUpTime, "cell 0 12,alignx left,aligny top");
		rPenUpTime = new JLabel("-");
		panel.add(rPenUpTime, "cell 2 12,alignx left,aligny top");
		sdPenUpTime = new JLabel("-");
		panel.add(sdPenUpTime, "cell 4 12,alignx left,aligny top");
		trPenUpTime = new JLabel("-");
		panel.add(trPenUpTime, "cell 6 12,alignx left,aligny top");
		
		JLabel lblNumberOfPen = new JLabel("Number of Pen Ups");
		panel.add(lblNumberOfPen, "cell 0 14,alignx left,aligny top");
		rNoPenUps = new JLabel("-");
		panel.add(rNoPenUps, "cell 2 14,alignx left,aligny top");
		sdNoPenUps = new JLabel("-");
		panel.add(sdNoPenUps, "cell 4 14,alignx left,aligny top");
		trNumPenUps = new JLabel("-");
		panel.add(trNumPenUps, "cell 6 14,alignx left,aligny top");
		
		JLabel lblTotalLength = new JLabel("Total Length");
		panel.add(lblTotalLength, "cell 0 16,alignx left,aligny top");
		rTotalLength = new JLabel("-");
		panel.add(rTotalLength, "cell 2 16,alignx left,aligny top");
		sdTotalLength = new JLabel("-");
		panel.add(sdTotalLength, "cell 4 16,alignx left,aligny top");
		trTotalLength = new JLabel("-");
		panel.add(trTotalLength, "cell 6 16,alignx left,aligny top");
		
		JLabel lblVelocity = new JLabel("Velocity");
		lblVelocity.setForeground(Color.GRAY);
		panel.add(lblVelocity, "cell 0 18,alignx left,aligny top");
		
		JLabel lblAverageInX = new JLabel("Average in X");
		panel.add(lblAverageInX, "cell 0 20,alignx left,aligny top");
		rAvVelX = new JLabel("-");
		panel.add(rAvVelX, "cell 2 20,alignx left,aligny top");
		sdAvVelX = new JLabel("-");
		panel.add(sdAvVelX, "cell 4 20,alignx left,aligny top");
		trAvVelX = new JLabel("-");
		panel.add(trAvVelX, "cell 6 20,alignx left,aligny top");
		
		JLabel lblAverageInY = new JLabel("Average in Y");
		panel.add(lblAverageInY, "cell 0 22,alignx left,aligny top");
		rAvVelY = new JLabel("-");
		panel.add(rAvVelY, "cell 2 22,alignx left,aligny top");
		sdAvVelY = new JLabel("-");
		panel.add(sdAvVelY, "cell 4 22,alignx left,aligny top");
		trAvVelY = new JLabel("-");
		panel.add(trAvVelY, "cell 6 22,alignx left,aligny top");
		
		JLabel lblAverageInBoth = new JLabel("Average in Both");
		panel.add(lblAverageInBoth, "cell 0 24,alignx left,aligny top");
		rAvVelBoth = new JLabel("-");
		panel.add(rAvVelBoth, "cell 2 24,alignx left,aligny top");
		sdAvVelBoth = new JLabel("-");
		panel.add(sdAvVelBoth, "cell 4 24,alignx left,aligny top");
		trAvVelBoth = new JLabel("-");
		panel.add(trAvVelBoth, "cell 6 24,alignx left,aligny top");
		
		JLabel lblMinimumInX = new JLabel("Minimum in X");
		panel.add(lblMinimumInX, "cell 0 26,alignx left,aligny top");
		rMinVelX = new JLabel("-");
		panel.add(rMinVelX, "cell 2 26,alignx left,aligny top");
		sdMinVelX = new JLabel("-");
		panel.add(sdMinVelX, "cell 4 26,alignx left,aligny top");
		trMinVelX = new JLabel("-");
		panel.add(trMinVelX, "cell 6 26,alignx left,aligny top");
		
		JLabel lblMinimumInY = new JLabel("Minimum in Y");
		panel.add(lblMinimumInY, "cell 0 28,alignx left,aligny top");
		rMinVelY = new JLabel("-");
		panel.add(rMinVelY, "cell 2 28,alignx left,aligny top");
		sdMinVelY = new JLabel("-");
		panel.add(sdMinVelY, "cell 4 28,alignx left,aligny top");
		trMinVelY = new JLabel("-");
		panel.add(trMinVelY, "cell 6 28,alignx left,aligny top");
		
		JLabel lblMinimumInBoth = new JLabel("Minimum in Both");
		panel.add(lblMinimumInBoth, "cell 0 29,alignx left,aligny top");
		rMinVelBoth = new JLabel("-");
		panel.add(rMinVelBoth, "cell 2 29,alignx left,aligny top");
		sdMinVelBoth = new JLabel("-");
		panel.add(sdMinVelBoth, "cell 4 29,alignx left,aligny top");
		trMinVelBoth = new JLabel("-");
		panel.add(trMinVelBoth, "cell 6 29,alignx left,aligny top");
		
		JLabel lblMaximumInX = new JLabel("Maximum in X");
		panel.add(lblMaximumInX, "cell 0 30,alignx left,aligny top");
		rMaxVelX = new JLabel("-");
		panel.add(rMaxVelX, "cell 2 30,alignx left,aligny top");
		sdMaxVelX = new JLabel("-");
		panel.add(sdMaxVelX, "cell 4 30,alignx left,aligny top");
		trMaxVelX = new JLabel("-");
		panel.add(trMaxVelX, "cell 6 30,alignx left,aligny top");
		
		pnlSigProfile = new JPanel();
		pnlSigProfile.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(pnlSigProfile, "cell 10 30 3 24,grow");
		
		JLabel lblMaximumInY = new JLabel("Maximum in Y");
		panel.add(lblMaximumInY, "cell 0 32,alignx left,aligny top");
		rMaxVelY = new JLabel("-");
		panel.add(rMaxVelY, "cell 2 32,alignx left,aligny top");
		sdMaxVelY = new JLabel("-");
		panel.add(sdMaxVelY, "cell 4 32,alignx left,aligny top");
		trMaxVelY = new JLabel("-");
		panel.add(trMaxVelY, "cell 6 32,alignx left,aligny top");
		
		JLabel lblMaximumInBoth = new JLabel("Maximum in Both");
		panel.add(lblMaximumInBoth, "cell 0 34,alignx left,aligny top");
		rMaxVelBoth = new JLabel("-");
		panel.add(rMaxVelBoth, "cell 2 34,alignx left,aligny top");
		sdMaxVelBoth = new JLabel("-");
		panel.add(sdMaxVelBoth, "cell 4 34,alignx left,aligny top");
		trMaxVelBoth = new JLabel("-");
		panel.add(trMaxVelBoth, "cell 6 34,alignx left,aligny top");
		
		JLabel lblAcceleration = new JLabel("Acceleration");
		lblAcceleration.setForeground(Color.GRAY);
		panel.add(lblAcceleration, "cell 0 36,alignx left,aligny top");
		
		JLabel lblAverageInX_1 = new JLabel("Average in X");
		panel.add(lblAverageInX_1, "cell 0 38,alignx left,aligny top");
		rAvAccelX = new JLabel("-");
		panel.add(rAvAccelX, "cell 2 38,alignx left,aligny top");
		sdAvAccelX = new JLabel("-");
		panel.add(sdAvAccelX, "cell 4 38,alignx left,aligny top");
		trAvAccelX = new JLabel("-");
		panel.add(trAvAccelX, "cell 6 38,alignx left,aligny top");
		
		JLabel lblAverageInY_1 = new JLabel("Average in Y");
		panel.add(lblAverageInY_1, "cell 0 40,alignx left,aligny top");
		rAvAccelY = new JLabel("-");
		panel.add(rAvAccelY, "cell 2 40,alignx left,aligny top");
		sdAvAccelY = new JLabel("-");
		panel.add(sdAvAccelY, "cell 4 40,alignx left,aligny top");
		trAvAccelY = new JLabel("-");
		panel.add(trAvAccelY, "cell 6 40,alignx left,aligny top");
		
		JLabel lblAverageInBoth_1 = new JLabel("Average in Both");
		panel.add(lblAverageInBoth_1, "cell 0 42,alignx left,aligny top");
		rAvAccelBoth = new JLabel("-");
		panel.add(rAvAccelBoth, "cell 2 42,alignx left,aligny top");
		sdAvAccelBoth = new JLabel("-");
		panel.add(sdAvAccelBoth, "cell 4 42,alignx left,aligny top");
		trAvAccelBoth = new JLabel("-");
		panel.add(trAvAccelBoth, "cell 6 42,alignx left,aligny top");
		
		JLabel lblMinimumInX_1 = new JLabel("Minimum in X");
		panel.add(lblMinimumInX_1, "cell 0 44,alignx left,aligny top");
		rMinAccelX = new JLabel("-");
		panel.add(rMinAccelX, "cell 2 44,alignx left,aligny top");
		sdMinAccelX = new JLabel("-");
		panel.add(sdMinAccelX, "cell 4 44,alignx left,aligny top");
		trMinAccelX = new JLabel("-");
		panel.add(trMinAccelX, "cell 6 44,alignx left,aligny top");
		
		JLabel lblMinimumInY_1 = new JLabel("Minimum in Y");
		panel.add(lblMinimumInY_1, "cell 0 46,alignx left,aligny top");
		rMinAccelY = new JLabel("-");
		panel.add(rMinAccelY, "cell 2 46,alignx left,aligny top");
		sdMinAccelY = new JLabel("-");
		panel.add(sdMinAccelY, "cell 4 46,alignx left,aligny top");
		trMinAccelY = new JLabel("-");
		panel.add(trMinAccelY, "cell 6 46,alignx left,aligny top");
		
		JLabel lblMinimumInBoth_1 = new JLabel("Minimum in Both");
		panel.add(lblMinimumInBoth_1, "cell 0 48,alignx left,aligny top");
		rMinAccelBoth = new JLabel("-");
		panel.add(rMinAccelBoth, "cell 2 48,alignx left,aligny top");
		sdMinAccelBoth = new JLabel("-");
		panel.add(sdMinAccelBoth, "cell 4 48,alignx left,aligny top");
		trMinAccelBoth = new JLabel("-");
		panel.add(trMinAccelBoth, "cell 6 48,alignx left,aligny top");
		
		JLabel lblMaximumInX_1 = new JLabel("Maximum in X");
		panel.add(lblMaximumInX_1, "cell 0 50,alignx left,aligny top");
		rMaxAccelX = new JLabel("-");
		panel.add(rMaxAccelX, "cell 2 50,alignx left,aligny top");
		sdMaxAccelX = new JLabel("-");
		panel.add(sdMaxAccelX, "cell 4 50,alignx left,aligny top");
		trMaxAccelX = new JLabel("-");
		panel.add(trMaxAccelX, "cell 6 50,alignx left,aligny top");
		
		JLabel lblMaximumInY_1 = new JLabel("Maximum in Y");
		panel.add(lblMaximumInY_1, "cell 0 52,alignx left,aligny top");
		rMaxAccelY = new JLabel("-");
		panel.add(rMaxAccelY, "cell 2 52,alignx left,aligny top");
		sdMaxAccelY = new JLabel("-");
		panel.add(sdMaxAccelY, "cell 4 52,alignx left,aligny top");
		trMaxAccelY = new JLabel("-");
		panel.add(trMaxAccelY, "cell 6 52,alignx left,aligny top");
		
		JLabel lblMaximumInBoth_1 = new JLabel("Maximum in Both");
		panel.add(lblMaximumInBoth_1, "cell 0 53,alignx left,aligny top");
		rMaxAccelBoth = new JLabel("-");
		panel.add(rMaxAccelBoth, "cell 2 53,alignx left,aligny top");
		sdMaxAccelBoth = new JLabel("-");
		panel.add(sdMaxAccelBoth, "cell 4 53,alignx left,aligny top");
		trMaxAccelBoth = new JLabel("-");
		panel.add(trMaxAccelBoth, "cell 6 53,alignx left,aligny top");
		
		JLabel lblWed = new JLabel("WED");
		lblWed.setForeground(Color.GRAY);
		panel.add(lblWed, "cell 0 55,alignx left,aligny top");
		
		JLabel lblWeightedEuclieanDistance = new JLabel("Weighted Eucliean Distance");
		panel.add(lblWeightedEuclieanDistance, "cell 0 57,alignx left,aligny top");
		rWED = new JLabel("-");
		panel.add(rWED, "cell 2 57,alignx left,aligny top");
		sdWED = new JLabel("-");
		panel.add(sdWED, "cell 4 57,alignx left,aligny top");
		trWED = new JLabel("-");
		panel.add(trWED, "cell 6 57,alignx left,aligny top");
	}
}
