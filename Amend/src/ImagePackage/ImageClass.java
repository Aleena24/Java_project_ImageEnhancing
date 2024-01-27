package ImagePackage;

import DebuggingPackage.DebuggingClass1;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import MainPackage.MainClass;
//import java.util.logging.Level;
//import java.util.logging.Logger;
/**
 *
 * @author Aleena
 */
public class ImageClass {
    static JFrame imageFrame;
    static JPanel imagePanel;
    static Image loadedImage = null;
    static JLabel toolsBoxLabel, filtersBoxLabel;
    static JSlider sharpnessSlider, vignettSlider, claritySlider;
    static int sharpnessValue = 0;
    static JComboBox toolsBox, filtersBox;
    static JButton imageCropBtn, filtersApplyBtn, saveBtn, backBtn, exitBtn, undoBtn, redoBtn;
    static ActionListenerClass listener;
    static Stack undoStack, redoStack;
    static boolean editFlag = false, imageCropped = false;
//    static MouseListenerClass mouseListener;
    static ToolsClass drawTool;
    static Dimension screenSize;
    static int screenHeight, screenWidth;
    protected static JSlider vignetteSlider;
      
    public ImageClass(String filepath)
    {
        SwingUtilities.invokeLater(() -> {
            try {
                undoStack = new Stack();
                redoStack = new Stack();
                imageFrame = new JFrame("Amend");
                imageFrame.pack();
                imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //imageFrame.setIconImage(ImageIO.read(new File("D:\\Projects\\Image Editing Program\\ImageEditorIcon.png")));
                imageFrame.setBackground(Color.BLACK);
                screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                screenHeight = (int) screenSize.getHeight();
                screenWidth = (int) screenSize.getWidth();
                int w = 5*screenWidth/100;
                int h = 5*screenHeight/100;
                imageFrame.setLocation(w, h);
                imageFrame.setLayout(null);
                imageFrame.setResizable(false);
                imageFrame.setVisible(true);
                imageFrame.setSize(900, 600);
                
                imagePanel = new JPanel();
                imagePanel.setBounds(0, 0, 500, 500);
                imageFrame.add(imagePanel);
                
                loadedImage = ImageIO.read(new File(filepath));
                loadedImage = loadedImage.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
                drawTool = new ToolsClass(loadedImage);
                imagePanel.add(drawTool);
                
                
                toolsBoxLabel = new JLabel("Tool: ");
                toolsBoxLabel.setBounds(550, 10, 150, 30);
                imageFrame.add(toolsBoxLabel);
                
                String[] tools = {"None", "Rectangle", "Circle"};
                toolsBox = new JComboBox(tools);
                toolsBox.setSelectedIndex(0);
                toolsBox.setBounds(590, 10, 150, 30);
                imageFrame.add(toolsBox);
                listener = new ActionListenerClass();
                toolsBox.addActionListener(listener);
                
                imageCropBtn = new JButton("Crop Image");
                imageCropBtn.setBounds(750, 10, 110, 30);
                imageFrame.add(imageCropBtn);
                imageCropBtn.addActionListener(listener);
                
                filtersBoxLabel = new JLabel("Filter: ");
                filtersBoxLabel.setBounds(550, 100, 150, 30);
                imageFrame.add(filtersBoxLabel);
                
                String[] filters = {"None", "Light", "Dark", "Blur", "Invert"};
                filtersBox = new JComboBox(filters);
                filtersBox.setSelectedIndex(0);
                filtersBox.setBounds(590, 100, 150, 30);
                imageFrame.add(filtersBox);
                
                filtersApplyBtn = new JButton("Apply Filter");
                filtersApplyBtn.setBounds(750, 100, 110, 30);
                imageFrame.add(filtersApplyBtn);
                filtersApplyBtn.addActionListener(listener);

                JLabel sharpnessLabel = new JLabel("Sharpness:");
                sharpnessLabel.setBounds(550, 170, 80, 30); 
                imageFrame.add(sharpnessLabel);
                sharpnessSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
                sharpnessSlider.setMajorTickSpacing(50);
                sharpnessSlider.setMinorTickSpacing(10);
                sharpnessSlider.setPaintTicks(true);
                sharpnessSlider.setPaintLabels(true);
                sharpnessSlider.setBounds(550, 200, 300, 50);
                imageFrame.add(sharpnessSlider);

                JLabel vignetteLabel = new JLabel("Vignette:");
                vignetteLabel.setBounds(550, 270, 80, 30); 
                imageFrame.add(vignetteLabel);
                JSlider vignetteSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
                vignetteSlider.setMajorTickSpacing(25);
                vignetteSlider.setMinorTickSpacing(5);
                vignetteSlider.setPaintTicks(true);
                vignetteSlider.setPaintLabels(true);
                vignetteSlider.setBounds(550, 300, 300, 50);
                imageFrame.add(vignetteSlider);

                JLabel clarityLabel = new JLabel("Clarity:");
                clarityLabel.setBounds(550, 370, 80, 30); 
                imageFrame.add(clarityLabel);
                JSlider claritySlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
                claritySlider.setMajorTickSpacing(50);
                claritySlider.setMinorTickSpacing(10);
                claritySlider.setPaintTicks(true);
                claritySlider.setPaintLabels(true);
                claritySlider.setBounds(550, 400, 300, 50);
                imageFrame.add(claritySlider);

        

                undoBtn = new JButton("Undo");
                undoBtn.setBounds(600, 470, 90, 30);
                imageFrame.add(undoBtn);
                undoBtn.addActionListener(listener);
                
                redoBtn = new JButton("Redo");
                redoBtn.setBounds(750, 470, 90, 30);
                imageFrame.add(redoBtn);
                redoBtn.addActionListener(listener);

                saveBtn = new JButton("Save");
                saveBtn.setBounds(550, 520, 90, 30);
                imageFrame.add(saveBtn);
                saveBtn.addActionListener(listener);
                drawTool.setImage(loadedImage);
                
                backBtn = new JButton("Back");
                backBtn.setBounds(650, 520, 90, 30);
                imageFrame.add(backBtn);
                backBtn.addActionListener(listener);
                
                exitBtn = new JButton("Exit");
                exitBtn.setBounds(750, 520, 90, 30);
                imageFrame.add(exitBtn);
                exitBtn.addActionListener(listener);
                
            }
            catch(HeadlessException | IOException ex){
                DebuggingClass1 err =  new DebuggingClass1();
                err.logException(ex.toString()); //Store exception in error ArrayList

                
            }
        });
        sharpnessSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sharpnessValue = sharpnessSlider.getValue();
        // Apply sharpness filter to the image using the updated sharpness value
                applySharpnessFilter();
    }
});
        claritySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int clarityValue = claritySlider.getValue();
                // Apply clarity filter to the image using the updated clarity value
                applyClarityFilter(clarityValue);
    }
});

        vignetteSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int vignetteValue = vignetteSlider.getValue();
                // Apply vignette effect to the image using the updated vignette value
                applyVignetteEffect(vignetteValue);
    }
});

    }

    private static void applySharpnessFilter() {
        if (loadedImage != null) {
            FiltersClass filter = new FiltersClass();
            loadedImage = filter.sharpenImage(toBufferedImage(drawTool.getImage()), sharpnessValue);
            drawTool.setImage(loadedImage);
            drawTool.repaint();
            editFlag = true;
        }
    }

    private static void applyClarityFilter(int clarityValue) {
        if (loadedImage != null) {
            FiltersClass filter = new FiltersClass();
            loadedImage = filter.applyClarityFilter(toBufferedImage(drawTool.getImage()), clarityValue);
            drawTool.setImage(loadedImage);
            drawTool.repaint();
            editFlag = true;
        }
    }

    private static void applyVignetteEffect(int vignetteValue) {
        if (loadedImage != null) {
            FiltersClass filter = new FiltersClass();
            loadedImage = filter.applyVignetteEffect(toBufferedImage(drawTool.getImage()), vignetteValue);
            drawTool.setImage(loadedImage);
            drawTool.repaint();
            editFlag = true;
        }
    }
    
    public static BufferedImage toBufferedImage(Image img)
    {
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    static class ActionListenerClass implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == toolsBox)
            {
                try
                {
                    if(toolsBox.getSelectedIndex() != 0)
                        drawTool.setToolType(toolsBox.getSelectedIndex());
                }
                catch(Exception ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
            }
            if (actionEvent.getSource() == imageCropBtn)
            {
                try
                {
                    if(toolsBox.getSelectedIndex() != 0)
                    {
                            undoStack.push(drawTool.getImage());
                            drawTool.setImage(drawTool.cropImage(toBufferedImage(drawTool.getImage())));
                            toolsBox.setSelectedIndex(0);
                            editFlag = true;
                    }
                    else
                        JOptionPane.showMessageDialog(imageFrame, "Please Choose a tool to cut with");
                }
                catch(HeadlessException | RasterFormatException ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
            }
            else if (actionEvent.getSource() == filtersApplyBtn)
            {
                FiltersClass filterTool = new FiltersClass();
                try
                {
                    
                        switch (filtersBox.getSelectedIndex()) {
                            case 1:
                                undoStack.push(drawTool.getImage());
                                loadedImage = filterTool.lightenImage(toBufferedImage(drawTool.getImage()));
//                                loadedImage = loadedImage.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
                                drawTool.setImage(loadedImage);
                                redoStack.clear();
                                filtersBox.setSelectedIndex(0);
                                editFlag = true;
                                break;
                            case 2:
                                undoStack.push(drawTool.getImage());
                                loadedImage = filterTool.darkenImage(toBufferedImage(drawTool.getImage()));
//                                loadedImage = loadedImage.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
                                drawTool.setImage(loadedImage);
                                redoStack.clear();
                                filtersBox.setSelectedIndex(0);
                                editFlag = true;
                                break;
                            case 3:
                                undoStack.push(drawTool.getImage());
                                loadedImage = filterTool.blurImage(toBufferedImage(drawTool.getImage()));
//                                loadedImage = loadedImage.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
                                drawTool.setImage(loadedImage);
                                redoStack.clear();
                                filtersBox.setSelectedIndex(0);
                                editFlag = true;
                                break;
                            case 4:
                                undoStack.push(drawTool.getImage());
                                loadedImage = filterTool.invertImage(toBufferedImage(drawTool.getImage()));
//                                loadedImage = loadedImage.getScaledInstance(500, 500, Image.SCALE_DEFAULT);
                                drawTool.setImage(loadedImage);
                                redoStack.clear();
                                filtersBox.setSelectedIndex(0);
                                editFlag = true;
                                break;

                            case 5:
                                undoStack.push(drawTool.getImage());
                                loadedImage = filterTool.sharpenImage(toBufferedImage(drawTool.getImage()), sharpnessValue);
                                drawTool.setImage(loadedImage);
                                redoStack.clear();
                                filtersBox.setSelectedIndex(0);
                                editFlag = true;
                                break;

                            case 6: 
                                undoStack.push(drawTool.getImage());
                                loadedImage = filterTool.applyClarityFilter(toBufferedImage(drawTool.getImage()), claritySlider.getValue());
                                drawTool.setImage(loadedImage);
                                redoStack.clear();
                                filtersBox.setSelectedIndex(0);
                                editFlag = true;
                                break;
                
                            case 7: 
                                undoStack.push(drawTool.getImage());
                                loadedImage = filterTool.applyVignetteFilter(toBufferedImage(drawTool.getImage()), vignetteSlider.getValue());
                                drawTool.setImage(loadedImage);
                                redoStack.clear();
                                filtersBox.setSelectedIndex(0);
                                editFlag = true;
                                break;

                            default:
                                JOptionPane.showMessageDialog(imageFrame, "Please Choose a filter to apply");
                                break;
                        }
                    
                }
                catch(HeadlessException ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
            }
            else if (actionEvent.getSource() == saveBtn)
            {
                try
                {
                    if(editFlag)
                    {
                        String filePath = null;
                        JFileChooser fileChooser = new JFileChooser(filePath);
                        imageFrame.setVisible(false);
                        int choosenBtn = fileChooser.showSaveDialog(fileChooser);
                        if(choosenBtn == JFileChooser.APPROVE_OPTION)
                        {
                            File tempFile = new File(fileChooser.getSelectedFile().toString()+".png");
                            ImageIO.write(toBufferedImage(drawTool.getImage()), "png", tempFile);
                            imageFrame.setVisible(true);
                            
                        } else {
                            imageFrame.setVisible(true);
                        }
                    }
                    else JOptionPane.showMessageDialog(imageFrame, "You can NOT do this right now!");
                }
                catch(HeadlessException | IOException ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
            }
            else if (actionEvent.getSource() == undoBtn)
            {
                try
                {
                    if(!undoStack.empty() && editFlag == true)
                    {
                        redoStack.push(drawTool.getImage());
                        loadedImage = toBufferedImage((Image)undoStack.pop());
//                        loadedImage = loadedImage.getScaledInstance(700, 700, Image.SCALE_DEFAULT);
                        drawTool.setImage(loadedImage);
                        drawTool.repaint();
                    }
                    else
                        JOptionPane.showMessageDialog(imageFrame, "You Can NOT do this right now!");
                }
                catch(HeadlessException ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
            }
            else if (actionEvent.getSource() == redoBtn)
            {
                try
                {
                    if(!redoStack.empty() && editFlag == true)
                    {
                        undoStack.push(drawTool.getImage());
                        loadedImage = toBufferedImage((Image)redoStack.pop());
//                        loadedImage = loadedImage.getScaledInstance(700, 700, Image.SCALE_DEFAULT);
                        drawTool.setImage(loadedImage);
                        drawTool.repaint();
                    }
                    else
                        JOptionPane.showMessageDialog(imageFrame, "You Can NOT do this right now!");
                }
                catch(HeadlessException ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
            }
            else if (actionEvent.getSource() == backBtn)
            {
                try
                {
                    if (JOptionPane.showConfirmDialog( imageFrame,"Are you sure?","Query", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                    {
                        imageFrame.setVisible(false);
                        MainClass.main(null); // To back to main frame
                    }
                }
                catch(HeadlessException ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
            }
            else if (actionEvent.getSource() == exitBtn)
            {
                try
                {
                    if (JOptionPane.showConfirmDialog( imageFrame,"Are you sure?","Query", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
                    {
                        System.exit(0);
                    }
                }
                catch(HeadlessException ex)
                {
                    DebuggingClass1 err =  new DebuggingClass1();
                    err.logException(ex.toString()); //Store exception in error ArrayList
                }
            }
        }
    }
}
