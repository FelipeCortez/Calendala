package br.ufrn.musica.calendala.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import br.ufrn.musica.calendala.controller.MainController;
import br.ufrn.musica.calendala.io.ResourceIO;
import br.ufrn.musica.calendala.mandala.Group;
import br.ufrn.musica.calendala.mandala.Mandala;
import br.ufrn.musica.calendala.mandala.Ring;
import br.ufrn.musica.calendala.mandala.Slice;

/**
 * @author Felipe Cortez de S�
 * @version 0.1
 * @since 0.1
 */

public class MandalaPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final int DOC_MARGIN = 50;
	private BasicStroke mandalaStroke = new BasicStroke(1.5f);
	private int selectedBoundsX, selectedBoundsY;
	private BufferedImage bi, overlay;
	private JTextField editField;
	private boolean showHelpOverlay = false;
	private boolean showSelection = true;
	
    public MandalaPanel() {
        
    	/** TODO 
    	 * Replace input/action maps with accelerators (when needed)
    	 */
        /* Input map */
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2Key");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "leftKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "rightKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "upKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "downKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,
                InputEvent.SHIFT_DOWN_MASK),
        "shiftLeftKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,
                InputEvent.SHIFT_DOWN_MASK),
        "shiftRightKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,
                InputEvent.CTRL_DOWN_MASK),
        "ctrlLeftKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,
                InputEvent.CTRL_DOWN_MASK),
        "ctrlRightKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "insertKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,
                InputEvent.SHIFT_DOWN_MASK),
        "shiftInsertKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deleteKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 
    			InputEvent.CTRL_DOWN_MASK), "ctrlDeleteKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,
                InputEvent.CTRL_DOWN_MASK),
        "ctrlInsertKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,
                (InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK)),
        "ctrlShiftInsertKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_DOWN_MASK),
        "ctrlSKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                InputEvent.CTRL_DOWN_MASK),
        "ctrlOKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), "hKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "fKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "cKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 
    			InputEvent.SHIFT_DOWN_MASK), "shiftCKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "eKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), "gKey");
    	getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 
    			InputEvent.SHIFT_DOWN_MASK), "shiftGKey");
    	
    	/* Action map */
        getActionMap().put("F2Key", 
        		MainController.getInstance().editSliceAction);
        getActionMap().put("rightKey", 
        		MainController.getInstance().rotateSelectionCWAction);
        getActionMap().put("leftKey", 
        		MainController.getInstance().rotateSelectionCCWAction);
        getActionMap().put("upKey", 
        		MainController.getInstance().changeSelectedRingUPAction);
        getActionMap().put("downKey", 
        		MainController.getInstance().changeSelectedRingDOWNAction);
        getActionMap().put("ctrlRightKey", 
        		MainController.getInstance().rotateRingCWAction);
        getActionMap().put("ctrlLeftKey", 
        		MainController.getInstance().rotateRingCCWAction);
        getActionMap().put("shiftRightKey", 
        		MainController.getInstance().changeSelectionCWAction);
        getActionMap().put("shiftLeftKey", 
        		MainController.getInstance().changeSelectionCCWAction);
        getActionMap().put("insertKey", 
        		MainController.getInstance().insertSliceAfterSelectionAction);
        getActionMap().put("shiftInsertKey", 
        		MainController.getInstance().insertSliceBeforeSelectionAction);
        getActionMap().put("deleteKey", 
        		MainController.getInstance().removeSlicesAction);
        getActionMap().put("ctrlDeleteKey", 
        		MainController.getInstance().removeRingAction);
        getActionMap().put("ctrlInsertKey", 
        		MainController.getInstance().insertRingUPAction);
        getActionMap().put("ctrlShiftInsertKey", 
        		MainController.getInstance().insertRingDOWNAction);
        getActionMap().put("gKey", 
        		MainController.getInstance().insertGroupBeforeSelectionAction);
        getActionMap().put("shiftGKey", 
        		MainController.getInstance().insertGroupAfterSelectionAction);
        getActionMap().put("cKey", 
        		MainController.getInstance().cloneRingUPAction);
        getActionMap().put("shiftCKey", 
        		MainController.getInstance().cloneRingDOWNAction);
        getActionMap().put("ctrlSKey", 
        		MainController.getInstance().saveFileAction);
        getActionMap().put("ctrlOKey", 
        		MainController.getInstance().openFileAction);
        getActionMap().put("hKey", 
        		MainController.getInstance().toggleHelpAction);
        getActionMap().put("fKey", 
        		MainController.getInstance().insideOutAction);
        getActionMap().put("eKey", 
        		MainController.getInstance().enumerateSelectionAction);
        getActionMap().put("pKey", 
        		MainController.getInstance().colorSelectionAction);
    				
    	// Tries to import the overlay image
        try {
        	overlay = ImageIO.read(ResourceIO.overlay);
        } catch(IOException e) {
        	System.out.println("Could not load file");
        }
        
        editField = new JTextField();
		editField.setHorizontalAlignment(JTextField.CENTER);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(MainFrame.WIDTH, MainFrame.WIDTH));
        setFocusable(true);
        setLayout(null); // Allows absolute positioning of components
        
        editField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String finalText = editField.getText();
				if(finalText.equals(""))
					finalText = " ";
				
				Mandala.getInstance().getFirstSelectedSlice().setTitle(finalText);
				remove(editField);
				repaint();
			}
		});
        
        Timer timer = new Timer(1000, this);
        timer.start();
        
    }
    
    public void actionPerformed(ActionEvent ae) {
    	repaint();
    }
    
    public void editField() {
		editField.setBounds(selectedBoundsX - 31, selectedBoundsY - 2, 70, 20);
		add(editField);
		editField.setText(editField.getText());
		editField.selectAll();
		editField.grabFocus();
    }
    
	public void drawMandala() {
		Graphics2D g2d = bi.createGraphics();
		Composite original = g2d.getComposite();
		Composite translucentDarker = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);	
		Composite translucentDark = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);	
		Composite translucentBrighter = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f);	
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        g2d.setColor(Color.black);
        g2d.setStroke(mandalaStroke);
        
        int width = getWidth();
        int ringsNum = Mandala.getInstance().getRings().size();
        int ringSize = (width - DOC_MARGIN) / ringsNum;
        
        for(int i = 0; i < ringsNum; i++) {
        	Ring currentRing = Mandala.getInstance().getRings().get(i);
    		double arcRadius = (ringSize * (ringsNum - i)) / 2;
        	double groupsNum = currentRing.getGroups().size();
			double groupAngle = 360 / groupsNum;
			
			Arc2D.Double groupArc = new Arc2D.Double();
    		groupArc.setArcByCenter(width / 2, width / 2, arcRadius, 90, 0, Arc2D.PIE);
    		
			Arc2D.Double arc = new Arc2D.Double();
    		arc.setArcByCenter(width / 2, width / 2, arcRadius, 90, 0, Arc2D.PIE);
    		
        	for(int j = 0; j < groupsNum; j++) {
        		Group currentGroup = currentRing.getGroups().get(j);
				double slicesNum = currentGroup.getSlices().size();
        		double sliceAngle = groupAngle / slicesNum;
        		
        		groupArc.start -= groupAngle;
        		groupArc.setAngleExtent(groupAngle);
        		
        		
				for(int k = 0; k < slicesNum; k++) {
        			Slice currentSlice = currentGroup.getSlices().get(k);
        			
	    			arc.start -= sliceAngle;
	    			arc.setAngleExtent(sliceAngle);
	    			
	    			g2d.setColor(currentSlice.getColor());
	        		g2d.fill(arc);
	        		
	    			if(Mandala.getInstance().getSelectedRing() == currentRing 
	    					&& showSelection) {
						g2d.setColor(Color.gray);
						g2d.setComposite(translucentBrighter);
		        		g2d.fill(arc);
		        		
						if(Mandala.getInstance().getSelectedSlices().contains(currentSlice)) {
							g2d.setComposite(translucentDark);
			        		g2d.fill(arc);
		    			}
						
		        		g2d.setComposite(original);
	    			}
	    			// Draws the slice arc outline
	    			g2d.setColor(currentSlice.getColor().darker());
	        		g2d.draw(arc);
        		}
				//Draws the group arc outline
				g2d.setComposite(translucentDarker);
    			g2d.setColor(Color.darkGray);
        		g2d.draw(groupArc);
        		g2d.setComposite(original);
        	}
        }
        
        // Renders the text
        for(int i = 0; i < ringsNum; i++) {
        	Ring currentRing = Mandala.getInstance().getRings().get(i);
			double textRadius = ((ringSize * (ringsNum - i)) / 2) - (ringSize / 2) / 2;    			
    		double arcRadius = (ringSize * (ringsNum - i)) / 2;
        	double groupsNum = currentRing.getGroups().size();
			double groupAngle = 360 / groupsNum;
			
			Arc2D.Double arc = new Arc2D.Double();
    		arc.setArcByCenter(width / 2, width / 2, arcRadius, 90, 0, Arc2D.PIE);
    		
        	for(int j = 0; j < groupsNum; j++) {
        		Group currentGroup = currentRing.getGroups().get(j);
				double slicesNum = currentGroup.getSlices().size();
        		double sliceAngle = groupAngle / slicesNum;
        		
				for(int k = 0; k < slicesNum; k++) {
        			Slice currentSlice = currentGroup.getSlices().get(k);
        			
	    			arc.start -= sliceAngle;
	    			arc.setAngleExtent((sliceAngle/*+ (offset / slicesNum)) * -1*/));
	    			
		        // Draws selection arrows
    			/*
		        g2d.setColor(Color.black);
		        int arrowSize = 15;
		        
		        AffineTransform at = new AffineTransform();
		        Path2D p = new Path2D.Double();
		        p.moveTo(0, 0);
		        p.lineTo(arrowSize, 0);
		        p.lineTo(arrowSize, arrowSize);
		        
		        at.translate(width / 2, width / 2);
		        at.rotate(Math.toRadians(90));
		        at.translate(-arrowSize/2, 0);
		        
		        at.rotate(Math.toRadians(arc.start));
		        
		        at.translate(150, -arrowSize / 2);
		        at.rotate(Math.toRadians(135));
		        
		        Shape s = at.createTransformedShape(p);
		        g2d.draw(s);
		        */
	    			
	    			double textAngle = -arc.start - (arc.extent / 2);
	        		
	    			int strWidth = 
	    					(int) g2d.getFontMetrics()
	    					.getStringBounds(currentSlice.getTitle(), g2d).getWidth();
	    			double textX = ((textRadius * Math.cos(Math.toRadians(textAngle)))) - (strWidth / 2);
	    			double textY = ((textRadius * Math.sin(Math.toRadians(textAngle))));
	    			
	    			g2d.setColor(Color.black);
	    			g2d.drawString(currentSlice.getTitle(), (int) ((width / 2) + textX), (int) ((width / 2) + textY));
	    			
			        if(Mandala.getInstance().getFirstSelectedSlice() == currentSlice) {
						editField.setText(currentSlice.getTitle());
						selectedBoundsX = (int) (((width / 2) + textX - 3) + (strWidth / 2));
						selectedBoundsY = (int) ((width / 2) + textY - 12);
					}
			        
				}
        	}
        }
    			
        
        // Draws the overlay
        if(showHelpOverlay) {
        	g2d.drawImage(overlay, 5, 5, null);
        }
        
        g2d.rotate(Math.PI / 5);
        
        g2d.dispose();
    }
        
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        bi = new BufferedImage(MainFrame.WIDTH, MainFrame.WIDTH, 
        		BufferedImage.TYPE_INT_ARGB); 
        drawMandala();
        g.drawImage(bi, 0, 0, null);
        g.dispose();
    }
    
    public BufferedImage getBI() {
    	return bi;
    }
    
    public boolean getShowHelpOverlay() {
    	return showHelpOverlay;
    }
    
    public void toggleHelpOverlay() {
    	showHelpOverlay = !showHelpOverlay;
    	
    	if(showHelpOverlay) {
    		showSelection = false;
    	} else {
    		showSelection = true;
    	}
    }
    
    public void setShowSelection(boolean showSelection) {
    	this.showSelection = showSelection;
    }

}