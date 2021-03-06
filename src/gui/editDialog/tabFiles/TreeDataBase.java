package gui.editDialog.tabFiles;

import gui.utilities.LoadImageIcon;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

import utilities.ProjectParam;

public class TreeDataBase extends JPanel {

	
	ImageIcon ICON_COMPUTER = LoadImageIcon.createImageIcon("/img/computer.png","");
	ImageIcon ICON_DISK = LoadImageIcon.createImageIcon("/img/Folder_15x15.png","");
	
//	ImageIcon ICON_COMPUTER = new ImageIcon("computer.gif");
//	ImageIcon ICON_DISK = new ImageIcon(CustomConstant.ROOT_PATH  + "\\src\\img\\Folder_15x15.png");
	
	protected JTree m_tree;
	protected DefaultTreeModel m_model;
	public JTextField m_display;

	protected JPopupMenu m_popup;
	protected Action m_action;
	protected TreePath m_clickedPath;

	public TreeDataBase() {
		super();
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode(new IconData(ICON_COMPUTER, null, "Root"));
		DefaultMutableTreeNode node;
		String searchPath = "\\\\keba\\project\\gba\\abteilungen\\ae\\KePlast\\Applications";
		//File[] roots = { new File(searchPath) /*+ "//WorkBase"*/ };
		File[] roots = { new File(ProjectParam.ROOT_PATH) /*+ "//WorkBase"*/ };
		for (int k = 0; k < roots.length; k++) {
			node = new DefaultMutableTreeNode(new IconData(ICON_DISK, null,
					new FileNode(roots[k])));
			top.add(node);
			node.add(new DefaultMutableTreeNode(new Boolean(true)));
		}
		m_model = new DefaultTreeModel(top);
		m_tree = new JTree(m_model);
		m_tree.putClientProperty("JTree.lineStyle", "Angled");
		// m_tree.setSize(new Dimension(300,100));
		// m_tree.setPreferredSize(new Dimension(200,30));
		// m_tree.setBorder(new EmptyBorder(20, 5, 20, 20));
		TreeCellRenderer renderer = new IconCellRenderer();
		m_tree.setCellRenderer(renderer);
		m_tree.addTreeExpansionListener(new DirExpansionListener());
		m_tree.addTreeSelectionListener(new DirSelectionListener());
		m_tree.getSelectionModel().setSelectionMode(
		TreeSelectionModel.SINGLE_TREE_SELECTION);
		m_tree.setShowsRootHandles(true);
		m_tree.setEditable(false);
		JScrollPane s = new JScrollPane();
		//s.setPreferredSize(new Dimension(100, 120));
		s.getViewport().add(m_tree);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(0, 0, 3, 0));
		super.add(s, BorderLayout.CENTER);
		m_display = new JTextField();
		m_display.setEditable(false);
		m_display.setBorder(new EmptyBorder(0, 0, 0, 0));
		// super.add(m_display, BorderLayout.NORTH);
		m_popup = new JPopupMenu();
		
		// m_action = new AbstractAction()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// if (m_clickedPath==null)
		// return;
		// if (m_tree.isExpanded(m_clickedPath))
		// m_tree.collapsePath(m_clickedPath);
		// else
		// m_tree.expandPath(m_clickedPath);
		// }
		// };
		// m_popup.add(m_action);
		// m_popup.addSeparator();

		Action aNewFolder = new AbstractAction("New Folder") {
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog(null, "Folder Name",
						JOptionPane.PLAIN_MESSAGE);
				if (input != null) {
					File newFile = new File(m_display.getText()	+ "\\" + input);
					System.out.println(m_display.getText() + "\\" + input);
					newFile.mkdir();
					m_model.reload(top);
					//m_tree.repaint();	
				}

			}
		};
		m_popup.add(aNewFolder);
		
		Action aOpenDir = new AbstractAction("Open Directory") {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Dispplay Text:" + m_display.getText());
				if (!(m_display.getText().isEmpty())) {
					try {
						System.out.println(m_display.getText());
						Desktop.getDesktop().open(new File(m_display.getText()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				}
				//m_tree.repaint();	
			}
		};
		m_popup.add(aOpenDir);
		m_tree.add(m_popup);
		m_tree.addMouseListener(new PopupTrigger());
		setVisible(true);
	}

	DefaultMutableTreeNode getTreeNode(TreePath path) {
		return (DefaultMutableTreeNode) (path.getLastPathComponent());
	}

	FileNode getFileNode(DefaultMutableTreeNode node) {
		if (node == null)
			return null;
		Object obj = node.getUserObject();
		if (obj instanceof IconData)
			obj = ((IconData) obj).getObject();
		if (obj instanceof FileNode)
			return (FileNode) obj;
		else
			return null;
	}

	class PopupTrigger extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				int x = e.getX();
				int y = e.getY();
				TreePath path = m_tree.getPathForLocation(x, y);
				if (path != null) {
					m_popup.show(m_tree, x, y);
					m_clickedPath = path;
				}
				
			}
		}
	}

	// Make sure expansion is threaded and updating the tree model
	// only occurs within the event dispatching thread.
	class DirExpansionListener implements TreeExpansionListener {
		public void treeExpanded(TreeExpansionEvent event) {
			final DefaultMutableTreeNode node = getTreeNode(event.getPath());
			final FileNode fnode = getFileNode(node);

			Thread runner = new Thread() {
				public void run() {
					if (fnode != null && fnode.expand(node)) {
						Runnable runnable = new Runnable() {
							public void run() {
								m_model.reload(node);
							}
						};
						SwingUtilities.invokeLater(runnable);
					}
				}
			};
			runner.start();
		}

		public void treeCollapsed(TreeExpansionEvent event) {
		}
	}

	class DirSelectionListener implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent event) {
			DefaultMutableTreeNode node = getTreeNode(event.getPath());
			FileNode fnode = getFileNode(node);
			if (fnode != null)
				m_display.setText(fnode.getFile().getAbsolutePath());
			else
				m_display.setText("");
		}
	}
}

class IconCellRenderer extends JLabel implements TreeCellRenderer {
	protected Color m_textSelectionColor;
	protected Color m_textNonSelectionColor;
	protected Color m_bkSelectionColor;
	protected Color m_bkNonSelectionColor;
	protected Color m_borderSelectionColor;

	protected boolean m_selected;

	public IconCellRenderer() {
		super();
		m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
		m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
		m_bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
		m_bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
		m_borderSelectionColor = UIManager
				.getColor("Tree.selectionBorderColor");
		setOpaque(false);
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus)

	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object obj = node.getUserObject();
		setText(obj.toString());

		if (obj instanceof Boolean)
			setText("Retrieving data...");

		if (obj instanceof IconData) {
			IconData idata = (IconData) obj;
			if (expanded)
				setIcon(idata.getExpandedIcon());
			else
				setIcon(idata.getIcon());
		} else
			setIcon(null);

		setFont(tree.getFont());
		setForeground(sel ? m_textSelectionColor : m_textNonSelectionColor);
		setBackground(sel ? m_bkSelectionColor : m_bkNonSelectionColor);
		m_selected = sel;
		return this;
	}

	public void paintComponent(Graphics g) {
		Color bColor = getBackground();
		Icon icon = getIcon();

		g.setColor(bColor);
		int offset = 0;
		if (icon != null && getText() != null)
			offset = (icon.getIconWidth() + getIconTextGap());
		g.fillRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);

		if (m_selected) {
			g.setColor(m_borderSelectionColor);
			g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
		}

		super.paintComponent(g);
	}
}

class IconData {
	protected Icon m_icon;
	protected Icon m_expandedIcon;
	protected Object m_data;

	public IconData(Icon icon, Object data) {
		m_icon = icon;
		m_expandedIcon = null;
		m_data = data;
	}

	public IconData(Icon icon, Icon expandedIcon, Object data) {
		m_icon = icon;
		m_expandedIcon = expandedIcon;
		m_data = data;
	}

	public Icon getIcon() {
		return m_icon;
	}

	public Icon getExpandedIcon() {
		return m_expandedIcon != null ? m_expandedIcon : m_icon;
	}

	public Object getObject() {
		return m_data;
	}

	public String toString() {
		return m_data.toString();
	}
}

class FileNode {
	
	ImageIcon ICON_FOLDER = LoadImageIcon.createImageIcon("/img/Folder_15x15.png","");
	ImageIcon ICON_EXPANDEDFOLDER = LoadImageIcon.createImageIcon("/img/Folder_15x15.png","");
	
	//ImageIcon ICON_FOLDER = new ImageIcon(CustomConstant.ROOT_PATH  + "\\src\\img\\Folder_15x15.png");
	//ImageIcon ICON_EXPANDEDFOLDER = new ImageIcon(CustomConstant.ROOT_PATH  + "\\src\\img\\Folder_15x15.png");
	
	protected File m_file;

	public FileNode(File file) {
		m_file = file;
	}

	public File getFile() {
		return m_file;
	}

	public String toString() {
		return m_file.getName().length() > 0 ? m_file.getName() : m_file
				.getPath();
	}

	public boolean expand(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode flag = (DefaultMutableTreeNode) parent
				.getFirstChild();

		//		if (flag == null) // No flag
//			return false;
//		Object obj = flag.getUserObject();
//		if (!(obj instanceof Boolean))
//			return false; // Already expanded

		parent.removeAllChildren(); // Remove Flag

		File[] files = listFiles();
		if (files == null)
			return true;

		Vector<FileNode> v = new Vector<FileNode>();

		for (int k = 0; k < files.length; k++) {
			File f = files[k];
			if (!(f.isDirectory()))
				continue;

			FileNode newNode = new FileNode(f);

			boolean isAdded = false;
			for (int i = 0; i < v.size(); i++) {
				FileNode nd = v.elementAt(i);
				if (newNode.compareTo(nd) < 0) {
					v.insertElementAt(newNode, i);
					isAdded = true;
					break;
				}
			}
			if (!isAdded)
				v.addElement(newNode);
		}

		for (int i = 0; i < v.size(); i++) {
			FileNode nd = v.elementAt(i);
			IconData idata = new IconData(ICON_FOLDER,
					ICON_EXPANDEDFOLDER, nd);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(idata);
			parent.add(node);

			if (nd.hasSubDirs())
				node.add(new DefaultMutableTreeNode(new Boolean(true)));
		}

		return true;
	}

	public boolean hasSubDirs() {
		File[] files = listFiles();
		if (files == null)
			return false;
		for (int k = 0; k < files.length; k++) {
			if (files[k].isDirectory())
				return true;
		}
		return false;
	}

	public int compareTo(FileNode toCompare) {
		return m_file.getName().compareToIgnoreCase(toCompare.m_file.getName());
	}

	protected File[] listFiles() {
		if (!m_file.isDirectory())
			return null;
		try {
			return m_file.listFiles();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error reading directory " + m_file.getAbsolutePath(), "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
}
