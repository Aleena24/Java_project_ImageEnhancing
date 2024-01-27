package ImagePackage;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
/**
 *
 * @author Aleena
 */
public class FiltersClass {
    
    public BufferedImage sharpenImage(BufferedImage BI, int sharpnessValue) {
        float[] sharpenKernel = {
                0, -1, 0,
                -1, 5, -1,
                0, -1, 0
        };
        Kernel kernel = new Kernel(3, 3, sharpenKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(BI, null);
    }

    public BufferedImage applyClarityFilter(BufferedImage BI, int clarityValue) {
        // Adjust clarity using Unsharp Masking
        float[] clarityKernel = {
                -1, -1, -1,
                -1, 9, -1,
                -1, -1, -1
        };
        Kernel kernel = new Kernel(3, 3, clarityKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(BI, null);
    }

    public BufferedImage applyVignetteEffect(BufferedImage BI, int vignetteValue) {
        int width = BI.getWidth();
        int height = BI.getHeight();
        Ellipse2D vignetteShape = new Ellipse2D.Float(width * 0.1f, height * 0.1f, width * 0.8f, height * 0.8f); // Ellipse shape for vignette

        // Create vignette mask
        BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = mask.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fill(new java.awt.Rectangle(0, 0, width, height));
        g2d.setColor(Color.BLACK);
        g2d.fill(vignetteShape);
        g2d.dispose();

        // Apply vignette effect using mask
        BufferedImageOp vignetteOp = new RescaleOp(1f - vignetteValue / 100f, 0, null);
        BufferedImage vignetteImage = vignetteOp.filter(mask, null);

        // Composite the filtered image with the vignette mask
        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = filteredImage.createGraphics();
        g.drawImage(BI, 0, 0, null);
        g.setComposite(AlphaComposite.DstOut);
        g.drawImage(vignetteImage, 0, 0, null);
        g.dispose();

        return filteredImage;
    }

    public BufferedImage blurImage(BufferedImage BI)
    {
        Kernel k = new Kernel(3, 3, new float[]{1f/(3*3),1f/(3*3),1f/(3*3),
                                                1f/(3*3),1f/(3*3),1f/(3*3),
                                                1f/(3*3),1f/(3*3),1f/(3*3)});
        ConvolveOp op = new ConvolveOp(k);
         return op.filter(BI, null);
    }


    public Image lightenImage(BufferedImage BI)
    {
        RescaleOp op = new RescaleOp(2f, 0, null);
        return op.filter(BI, null);
    }
    public Image darkenImage(BufferedImage BI)
    {
        RescaleOp op = new RescaleOp(.5f, 0, null);
        return op.filter(BI, null);
    }
    public Image invertImage(BufferedImage BI)
    {

        for (int x = 0; x < BI.getWidth(); x++) {
            for (int y = 0; y < BI.getHeight(); y++) {
                int rgba = BI.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                BI.setRGB(x, y, col.getRGB());
            }
        }
        return BI;
    }

    public Image applyVignetteFilter(BufferedImage bufferedImage, int value) {

        throw new UnsupportedOperationException("Unimplemented method 'applyVignetteFilter'");
    }
}
