package sig;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable {
    public int pixel[];
    public int width=1280;
    public int height=720; 
    private Thread thread;
    private Image imageBuffer;   
    private MemoryImageSource mImageProducer;   
    private ColorModel cm;   
    int scanLine=0;
    int nextScanLine=0;

    public Panel() {
        super(true);
        thread = new Thread(this, "MyPanel Thread");
    }

    /**
     * Get Best Color model available for current screen.
     * @return color model
     */
    protected static ColorModel getCompatibleColorModel(){        
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();        
        return gfx_config.getColorModel();
    }

    /**
     * Call it after been visible and after resizes.
     */
    public void init(){        
        cm = getCompatibleColorModel();
        int screenSize = width * height;
        if(pixel == null || pixel.length < screenSize){
            pixel = new int[screenSize];
        }        
        if(thread.isInterrupted() || !thread.isAlive()){
            thread.start();
        }
        mImageProducer =  new MemoryImageSource(width, height, cm, pixel,0, width);
        mImageProducer.setAnimated(true);
        mImageProducer.setFullBufferUpdates(true);  
        imageBuffer = Toolkit.getDefaultToolkit().createImage(mImageProducer);        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // perform draws on pixels
        render();
        // ask ImageProducer to update image
        mImageProducer.newPixels();            
        // draw it on panel          
        g.drawImage(this.imageBuffer, 0, 0, this);  
    }
    
    /**
     * Overrides ImageObserver.imageUpdate.
     * Always return true, assuming that imageBuffer is ready to go when called
     */
    @Override
    public boolean imageUpdate(Image image, int a, int b, int c, int d, int e) {
        return true;
    }
    /**
    * Do your draws in here !!
    * pixel is your canvas!
    */
    public /* abstract */ void render(){
        int[] p = pixel; // this avoid crash when resizing
        //a=h/w
        
        for (int x=0;x<1280;x++) {
        	for (int y=0;y<720;y++) {
        		p[y*1280+x]=(0<<16)+(0<<8)+0;
        	}
        }
        
        FillPolygon(new Point[] {
        		new Point(135,2),
        		new Point(166,96),
        		new Point(265,97),
        		new Point(185,156),
        		new Point(215,251),
        		new Point(134,192),
        		new Point(54,251),
        		new Point(84,156),
        		new Point(4,97),
        		new Point(103,96),
        });
    }
    
    public void FillPolygon(Point...points) {
    	Edge[] edges = new Edge[points.length];
    	List<Edge> edges_sorted = new ArrayList<Edge>();
    	for (int i=0;i<points.length;i++) {
    		edges[i] = new Edge(points[i],points[(i+1)%points.length]);
    		if (edges_sorted.size()==0) {
    			edges_sorted.add(edges[i]);
    		} else {
    			boolean inserted=false;
    			for (int j=0;j<edges_sorted.size();j++) {
    				Edge e2 = edges_sorted.get(j);
    				if (e2.min_y>edges[i].min_y) {
    					edges_sorted.add(j,edges[i]);
    					inserted=true;
    					break;
    				} else 
    				if (e2.min_y==edges[i].min_y){
    					if (e2.min_x>edges[i].min_x) {
    						edges_sorted.add(j,edges[i]);
    						inserted=true;
    						break;
    					}
    				}
    			}
    			if (!inserted) {
    				edges_sorted.add(edges[i]);
    			}
    		}
    	}
    	List<Edge> active_edges = new ArrayList<Edge>();
    	scanLine = edges_sorted.get(0).min_y-1;
    	nextScanLine = scanLine+1;
    	do {
    		if (active_edges.size()%2==0) {
	    		for (int i=0;i<active_edges.size();i+=2) {
	    			Edge e1 = active_edges.get(i);
	    			Edge e2 = active_edges.get(i+1);
	    			for (int x=(int)Math.round(e1.x_of_min_y);x<=e2.x_of_min_y;x++) {
	    				pixel[scanLine*width+x]=(255<<16)+(0<<8)+0;
	    			}
	    		}
	    		for (int i=0;i<active_edges.size();i++) {
	    			Edge e = active_edges.get(i);
	    			if (e.max_y==scanLine+1) {
	    				active_edges.remove(i--);
	    			} else {
	    				e.x_of_min_y+=e.inverse_slope;
	    			}
	    		}
	    		scanLine++;
	    		GetNextScanLineEdges(edges_sorted, active_edges);
    		}
    	}
    	while (active_edges.size()>0);
    }

	private void GetNextScanLineEdges(List<Edge> edges_sorted, List<Edge> active_edges) {
		if (scanLine==nextScanLine) {
	    	for (int i=0;i<edges_sorted.size();i++) {
	    		Edge e = edges_sorted.get(i);
	    		if (e.min_y==scanLine) {
	    			e = edges_sorted.remove(i--);
	    			boolean inserted=false;
	    			for (int j=0;j<active_edges.size();j++) {
	    				if (e.x_of_min_y<active_edges.get(j).x_of_min_y) {
	    					active_edges.add(j,e);
	    					inserted=true;
	    					break;
	    				}
	    			}
	    			if (!inserted) {
	    				active_edges.add(e);
	    			}
	    			
	    		} else
	    		if (e.min_y>scanLine) {
	    			nextScanLine=e.min_y;
	    			break;
	    		}
	    	}
    	}
	}

	@Override
	public void run() {
		while (true) {
            // request a JPanel re-drawing
            repaint();                                  
            try {Thread.sleep(5);} catch (InterruptedException e) {}
        }
	}
}