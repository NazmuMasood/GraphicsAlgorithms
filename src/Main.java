//package cse423_lab02;
/**
 *
 * @author Nazmuddin Al Masood - nazmumasood96@gmail.com - 16101272
 */
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import java.lang.Math;
import javax.swing.JFrame;

class ThirdGLEventListener implements GLEventListener {
    /**
     * Interface to the GLU library.
     */
    private GLU glu;

    private void doIt(GL2 gl){
//        gl.glColor3d(1,0.5, 0.7);
//        gl.glPointSize(10.0f);
//        gl.glBegin(GL2.GL_POINTS);
//
//        gl.glVertex2d(10, -50);
//
//        gl.glEnd();

        //BruteForceDrawLine(gl, -100, -50, -50, 180);
        //DDA_Algo(gl, -100, -50, -50, 180);
        MidpointLineDrawingAlgo(gl, 100, 50, 200, 80);
    }

    //MidpointLineDrawing_Algo
    private void MidpointLineDrawingAlgo(GL2 gl, double x1, double y1, double x2, double y2){
        double dx = x2-x1;
        double dy = y2-y1;
        double d= 2*dy-dx;
        double dE = 2*dy;
        double dNE = 2*(dy-dx);

        double x=x1, y=y1;

        gl.glBegin(GL2.GL_POINTS);

        while(x<=x2){

            gl.glVertex2d(x, y);

            if(d<=0){
                d = d + dE;
            }
            else{
                y++;
                d = d + dNE;
            }
            x++;
        }

        gl.glEnd();
    }

    //BruteForce_Algo
    private void BruteForceDrawLine(GL2 gl, double x1, double y1, double x2, double y2){
        double m = (y2 - y1) / (x2 - x1); //slope calc
        double c = y1 - m*x1;//intercept calc

        gl.glBegin(GL2.GL_POINTS);

        if(m<=1){
            for(double x=x1; x<=x2; x++){
                double y = m*x + c;
                gl.glVertex2d(x, y);
            }
        }
        else{
            for(double y=y1; y<=y2; y++){
                double x = (y - c)/ m ;
                gl.glVertex2d(x, y);
            }
        }
        gl.glEnd();
    }

    //DDA_Algo
    private void DDA_Algo(GL2 gl, double x1, double y1, double x2, double y2){
        double m = (y2 - y1) / (x2 - x1); //slope calc

        gl.glBegin(GL2.GL_POINTS);

        double x=x1, y=y1;
        if(m<=1){
            while(x<=x2){
                x++; y+=m;
                gl.glVertex2d(x, y);
            }
        }
        else{
            while(y<=y2){
                y++; x+=1/m;
                gl.glVertex2d(x, y);
            }
        }
        gl.glEnd();
    }

    /**
     * Take care of initialization here.
     */
    @Override
    public void init(GLAutoDrawable gld) {
        GL2 gl = gld.getGL().getGL2();
        glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(-250, -150, 250, 150);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-250.0, 250.0, -150.0, 150.0);
    }

    /**
     * Take care of drawing here.
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        /*
         * put your code here
         */
        doIt(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                        int height) {
    }

    public void displayChanged(GLAutoDrawable drawable,
                               boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void dispose(GLAutoDrawable arg0)
    {

    }
}

public class Main
{
    public static void main(String args[])
    {
        //getting the capabilities object of GL2 profile
        final GLProfile profile=GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities=new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas=new GLCanvas(capabilities);
        ThirdGLEventListener b=new ThirdGLEventListener();
        glcanvas.addGLEventListener(b);
        glcanvas.setSize(900, 600);
        //creating frame
        final JFrame frame=new JFrame("My frame");
        //adding canvas to frame
        frame.add(glcanvas);
        frame.setSize(640,480);
        frame.setVisible(true);
    }
}




/*public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}*/
