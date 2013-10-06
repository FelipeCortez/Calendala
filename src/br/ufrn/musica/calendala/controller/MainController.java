package br.ufrn.musica.calendala.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.undo.UndoManager;

import br.ufrn.musica.calendala.gui.FacadeSwing;
import br.ufrn.musica.calendala.gui.MainFrame;
import br.ufrn.musica.calendala.io.FileIO;
import br.ufrn.musica.calendala.io.FileIO.Extension;
import br.ufrn.musica.calendala.io.ResourceIO;
import br.ufrn.musica.calendala.mandala.Mandala;
import br.ufrn.musica.calendala.mandala.Mandala.Position;
import br.ufrn.musica.calendala.mandala.Ring.Direction;

public class MainController {
	private static MainController controller;
	private static UndoManager undoManager = new UndoManager();

	public static MainController getInstance() {
		if (controller == null) {
			MainController.controller = new MainController();
		}
		return controller;
	}
	
	public Action 

	undoAction = new UndoAction(
			"Undo", 
			"Undoes the last undoable action",
			new ImageIcon(ResourceIO.pencilIcon)),
	
	editSliceAction = new EditSliceAction(
			"Edit slice", 
			"Edits the selected slice's text",
			new ImageIcon(ResourceIO.pencilIcon)),
	
	rotateSelectionCWAction = new RotateSelectionAction(
			"Move selection (CW)", 
			"Rotates the selection clockwise",
			Direction.CW),
	
	rotateSelectionCCWAction = new RotateSelectionAction(
			"Move selection (CCW)", 
			"Rotates the selection counterclockwise", 
			Direction.CCW),
	
	shiftRingSelectionUPAction = new ShiftRingSelectionAction(
			"Move selection up",
			"Moves the selection one ring up", 
			Direction.UP),
	
	shiftRingSelectionDOWNAction = new ShiftRingSelectionAction(
			"Move selection down", 
			"Moves the selection one ring down", 
			Direction.DOWN),
	
	shiftRingCWAction = new ShiftRingAction(
			"Rotate ring (CW)", 
			"Rotates the ring clockwise", 
			Direction.CW,
			new ImageIcon(ResourceIO.cwIcon)),
	
	shiftRingCCWAction = new ShiftRingAction(
			"Rotate ring (CCW)", 
			"Rotates the ring counterclockwise", 
			Direction.CCW,
			new ImageIcon(ResourceIO.ccwIcon)),
	
	changeSelectionCWAction = new ChangeSelectionAction(
			"Move selection CW (multiple selection)", 
			"Changes the selection clockwise", 
			Direction.CW),
	
	changeSelectionCCWAction = new ChangeSelectionAction(
			"Move selection CCW (multiple selection)", 
			"Changes the selection counterclockwise", 
			Direction.CCW),
	
	insertSliceBeforeSelectionAction = new InsertSliceAction(
			"Insert slice (CCW)", 
			"Inserts a slice before the selection", 
			new ImageIcon(ResourceIO.addSliceIcon),
			Position.BEFORE),
	
	insertSliceAfterSelectionAction = new InsertSliceAction(
			"Insert slice (CW)", 
			"Inserts a slice after the selection", 
			new ImageIcon(ResourceIO.addSliceIcon),
			Position.AFTER),
	
	removeSlicesAction = new RemoveSlicesAction(
			"Remove slice", 
			new ImageIcon(ResourceIO.removeSliceIcon),
			"Removes selected slice or slices"),
	
	removeRingAction = new RemoveRingAction(
			"Remove ring", 
			new ImageIcon(ResourceIO.removeRingIcon),
			"Removes selected ring"),
	
	insertRingUPAction = new InsertRingAction(
			"Insert ring outward", 
			new ImageIcon(ResourceIO.addRingIcon),
			Direction.UP),
	
	insertRingDOWNAction = new InsertRingAction(
			"Insert ring inward", 
			new ImageIcon(ResourceIO.addRingIcon),
			Direction.DOWN),
	
	cloneRingUPAction = new CloneRingAction(
			"Clone ring (up)", 
			"Clones a ring and adds it above the selected one", 
			new ImageIcon(ResourceIO.cloneIcon),
			Direction.UP),
	
	cloneRingDOWNAction = new CloneRingAction(
			"Clone ring (below)", 
			"Clones a ring and adds it below the selected one", 
			new ImageIcon(ResourceIO.cloneIcon),
			Direction.DOWN),
	
	saveFileAction = new SaveFileAction(
			"Save as...", 
			"Saves a file",
			new ImageIcon(ResourceIO.disquetteIcon)),
			
			/** TODO */
			/*
	saveFileAction = new SaveFileAction(
			"Save...", 
			"Saves a file"),
			*/
			
	exportAsPNGAction = new ExportAsPNGAction(
			"Export as PNG", 
			new ImageIcon(ResourceIO.picturesIcon),
			"Exports the mandala as a .png file"),
	
	openFileAction = new OpenFileAction(
			"Open file", 
			"Opens a file",
			new ImageIcon(ResourceIO.folderIcon)),
	
	toggleHelpAction = new ToggleHelpAction(
			"Toggle help", 
			new ImageIcon(ResourceIO.helpIcon),
			"Toggles the help overlay"),
	
	insideOutAction = new InsideOutAction(
			"Turn inside out", 
			new ImageIcon(ResourceIO.insideOutIcon),
			"Turns the mandala inside out"),
	
	enumerateSelectionAction = new EnumerateSelectionAction(
			"Enumerate selection", 
			"Enumerates the selected slices"),
			
	colorSelectionAction = new ColorSelectionAction(
			"Color selection", 
			"Colors the selected slices with the currently selected color"),
	
	showAboutDialogAction = new ShowAboutDialogAction(
			"About", 
			new ImageIcon(ResourceIO.paintBrushIcon)),
			
	changeMandalaTitleAction = new ChangeMandalaTitleAction(
			"Change title", 
			"Opens up a dialog for changing the mandala's title");
    	
	public class UndoAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public UndoAction(String name, String shortDescription, Icon icon) {
			super(name, icon);
			putValue(Action.NAME, undoManager.getPresentationName());
		}
		
	    public void actionPerformed(ActionEvent e) {
	    	undoManager.undo();
	    }

    	public void update() {
	    	this.putValue(Action.NAME, undoManager.getUndoPresentationName());
	    	this.setEnabled(undoManager.canUndo());
    	}
	}

	public class EditSliceAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public EditSliceAction(String name, String shortDescription, Icon icon) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	FacadeSwing.singleton().getMandalaPanel().editField();
	    }
	  }
		
	 public class RotateSelectionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public RotateSelectionAction(String name, String shortDescription, Direction direction) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().rotateSelectedSlice(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class ShiftRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public ShiftRingAction(String name, String shortDescription, 
				Direction direction, Icon icon) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().getRings().get(Mandala.getInstance().getSelectedRing()).rotate(direction);
	    	Mandala.getInstance().rotateSelectedSlice(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class ShiftRingSelectionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public ShiftRingSelectionAction(String name, String shortDescription, Direction direction) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().shiftRingSelection(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class ChangeSelectionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public ChangeSelectionAction(String name, String shortDescription, Direction direction) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().changeSelection(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class InsertSliceAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Position position;
	
		public InsertSliceAction(String name, String shortDescription, Icon icon, Position position) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.position = position;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().insertSlice(position);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class RemoveSlicesAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public RemoveSlicesAction(String name, Icon icon, String shortDescription) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().removeSlices();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	  }
	 
	 public class RemoveRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public RemoveRingAction(String name, Icon icon, String shortDescription) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().removeRing();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
	    
	  }
	 
	 public class InsertRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public InsertRingAction(String name, Icon icon, Direction direction) {
	      super(name, icon);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().insertRing(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
		 
	 }
	 
	 public class CloneRingAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private Direction direction;
	
		public CloneRingAction(String name, String shortDescription, Icon icon, Direction direction) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	      this.direction = direction;
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().cloneRing(direction);
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    }
		 
	 }
	 
	public class OpenFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public OpenFileAction(String name, String shortDescription, Icon icon) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
			FileDialog openFileDialog = new FileDialog(
					MainFrame.getInstance(), 
					"Open file", 
					FileDialog.LOAD);
			openFileDialog.setFile("*.cdl");
			openFileDialog.setVisible(true);
			
			if(openFileDialog.getFile() != null) {
			FileIO.openFile(new File(
			openFileDialog.getDirectory() + 
			System.getProperty("file.separator") + 
			openFileDialog.getFile()));
			FacadeSwing.singleton().getMandalaPanel().repaint();
			}
	    }
   }
	 
	 public class SaveFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public SaveFileAction(String name, String shortDescription, Icon icon) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	FileDialog saveFileDialog = new FileDialog(
	    					MainFrame.getInstance(), 
	    					"Save as...", 
	    					FileDialog.SAVE);
	    	saveFileDialog.setFile("*.cdl");
	    	saveFileDialog.setVisible(true);
	    	
	    	if(saveFileDialog.getFile() != null) {
		    	FileIO.saveFile(new File(
		    			saveFileDialog.getDirectory() + 
		    			System.getProperty("file.separator") + 
		    			saveFileDialog.getFile()), Extension.CDL);
		    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    	}
		}
	}
	 
	public class ExportAsPNGAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ExportAsPNGAction(String name, Icon icon, String shortDescription) {
			super(name, icon);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
	    public void actionPerformed(ActionEvent e) {
	    	//Hides the overlay
			if(FacadeSwing.singleton().getMandalaPanel().getShowHelpOverlay())
				FacadeSwing.singleton().getMandalaPanel().toggleHelpOverlay();
			
	    	FacadeSwing.singleton().getMandalaPanel().setShowSelection(false);
	    	FacadeSwing.singleton().getMandalaPanel().drawMandala();
			
	    	FileDialog saveFileDialog = new FileDialog(
	    					MainFrame.getInstance(), 
	    					"Export PNG", 
	    					FileDialog.SAVE);
	    	saveFileDialog.setFile("*.png");
	    	saveFileDialog.setVisible(true);
	    	
	    	if(saveFileDialog.getFile() != null) {
		    	FileIO.saveFile(new File(
		    			saveFileDialog.getDirectory() + 
		    			System.getProperty("file.separator") + 
		    			saveFileDialog.getFile()), Extension.PNG);
		    	FacadeSwing.singleton().getMandalaPanel().repaint();
	    	}
			
			FacadeSwing.singleton().getMandalaPanel().setShowSelection(true);
			FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	 
	public class ToggleHelpAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ToggleHelpAction(String name, Icon icon, String shortDescription) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	FacadeSwing.singleton().getMandalaPanel().toggleHelpOverlay();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	
	public class InsideOutAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public InsideOutAction(String name, Icon icon, String shortDescription) {
	      super(name, icon);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().insideOut();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	
	public class EnumerateSelectionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public EnumerateSelectionAction(String name, String shortDescription) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().enumerateSelection();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
	   	}
	}
	
	public class ChangeMandalaTitleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ChangeMandalaTitleAction(String name, String shortDescription) {
	      super(name);
	      putValue(SHORT_DESCRIPTION, shortDescription);
	    }
		
	    public void actionPerformed(ActionEvent e) {
	    	String r = (String) JOptionPane.showInputDialog("Enter a title for the mandala");
	    	if(r != null && !r.equals("")) {
	    		Mandala.getInstance().setTitle(r);
		    	MainFrame.getInstance().updateTitle();
	    	}
	   	}
	}
	
	public class ColorSelectionAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ColorSelectionAction(String name, String shortDescription) {
			super(name);
			putValue(SHORT_DESCRIPTION, shortDescription);
		}
		
	    public void actionPerformed(ActionEvent e) {
	    	Mandala.getInstance().colorSelection();
	    	FacadeSwing.singleton().getMandalaPanel().repaint();
		}
	}
	
	public class ShowAboutDialogAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
	
		public ShowAboutDialogAction(String name, Icon icon) {
			super(name, icon);
		}
		
	    public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(
					MainFrame.getInstance(), 
					"Calendala is a free open-source software mandala and circular diagram editor\n\nTeam: Alexandre Reche, Felipe Cortez de S�\nEntypo pictograms by Daniel Bruce � www.entypo.com",
					"About", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ResourceIO.cogIcon));
		}
	}
}