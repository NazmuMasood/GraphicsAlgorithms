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

import java.awt.event.WindowEvent;
import java.lang.Math;
import javax.swing.JFrame;

class ThirdGLEventListener implements GLEventListener {
    /**
     * Interface to the GLU library.
     */
    private GLU glu; private GL2 gl;

    private void doIt(GL2 gl){
        /*gl.glColor3d(1,0.5, 0.7);
        gl.glPointSize(10.0f);
        gl.glBegin(GL2.GL_POINTS);

        gl.glVertex2d(10, -50);

        gl.glEnd();*/

        //Drawing the axes
        BruteForceDrawLine(gl, -100, 0, 100, 0);
        DDA_Algo(gl, 0, -100, 0, 100);

        //BruteForceDrawLine(gl, -100, -50, -50, 180);
        //DDA_Algo(gl, -100, -50, -50, 180);
        //MidpointLineForZone0(gl, 100, 50, 200, 80);
        //MidpointLineForAnyZone(50, -100, 100, -190);
        //MidpointCircleForZone1Arc(50,-50, 100);
        MidpointCircleForAllZone(100, 50, 100); //change x, y here to get interesting images
    }

    //MidpointCircleDrawing_Algo irrespective of zones
    private void MidpointCircleForAllZone(double x1, double y1, double r){
        int zone = 0;
        while (zone<8) {
            switch (zone) {
                //encoding the zones to zone 0
                case 0:
                    MidpointCircleForZone1ArcButModified(0, x1, y1, r);
                    break;
                case 1:
                    MidpointCircleForZone1ArcButModified(1, x1, y1, r);
                    break;
                case 2:
                    MidpointCircleForZone1ArcButModified(2, x1, y1, r);
                    break;
                case 3:
                    MidpointCircleForZone1ArcButModified(3, x1, y1, r);
                    break;
                case 4:
                    MidpointCircleForZone1ArcButModified(4, x1, y1, r);
                    break;
                case 5:
                    MidpointCircleForZone1ArcButModified(5, x1, y1, r);
                    break;
                case 6:
                    MidpointCircleForZone1ArcButModified(6, x1, y1, r);
                    break;
                case 7:
                    MidpointCircleForZone1ArcButModified(7, x1, y1, r);
                    break;
            }//switch
            zone++;
        }//while
    }
    private void MidpointCircleForZone1ArcButModified(int zone, double x_center, double y_center, double r){
        double d= 1-r;
        double dE = 3;
        double dSE = 5 - 2*r;
        double x=0, y=r;
        gl.glBegin(GL2.GL_POINTS);

        while (Math.abs(x)<(r/1.4142)){

            //x+=x_center; y+=y_center;
            System.out.println(x+", "+y);
            switch (zone){
                //decoding the zones from zone 1
                case 0:
                    gl.glVertex2d(y+y_center, x+x_center);
                    break;
                case 1:
                    gl.glVertex2d(x+x_center, y+y_center);
                    break;
                case 2:
                    gl.glVertex2d(-(x+x_center), y+y_center);
                    break;
                case 3:
                    gl.glVertex2d(-(y+y_center), x+x_center);
                    break;
                case 4:
                    gl.glVertex2d(-(y+y_center), -(x+x_center));
                    break;
                case 5:
                    gl.glVertex2d(-(x+x_center), -(y+y_center));
                    break;
                case 6:
                    gl.glVertex2d(x+x_center, -(y+y_center));
                    break;
                case 7:
                    gl.glVertex2d(y+y_center, -(x+x_center));
                    break;
            }

            if (d<0){ //dE
                d = d + dE;
                dE += 2;
                dSE +=2;
            }

            else { //dSE
                d = d + dSE;
                dE += 2;
                dSE +=4;
                y--;
            }

            x++;
        }// while
        gl.glEnd();
    }

    //MidpointCircleDrawing_Algo  [ pixel draw starts clock-wise from (0,r) ]
    private void MidpointCircleForZone1Arc(double x_center, double y_center, double r){
        double d= 1-r;
        double dE = 3;
        double dSE = 5 - 2*r;
        double x=0, y=r;
        gl.glBegin(GL2.GL_POINTS);

        while (Math.abs(x)<(r/1.4142)){
            System.out.println(x+x_center+", "+y+y_center);
            gl.glVertex2d(x+x_center, y+y_center);

            if (d<0){ //dE
                d = d + dE;
                dE += 2;
                dSE +=2;
            }

            else { //dSE
                d = d + dSE;
                dE += 2;
                dSE +=4;
                y--;
            }

            x++;
        }// while
        gl.glEnd();

    }

    //MidpointLineDrawing_Algo irrespective of zones
    private void MidpointLineForAnyZone(double x1, double y1, double x2, double y2) {
        int zone = getZone(x1, y1, x2, y2);
        if (zone == -1){return;}
        switch (zone){
            //encoding the zones to zone 0
            case 0:
                MidpointLineForZone0ButModified(0, x1, y1, x2, y2);
                break;
            case 1:
                MidpointLineForZone0ButModified(1, y1, x1, y2, x2);
                break;
            case 2:
                MidpointLineForZone0ButModified(2, y1, -x1, y2, -x2);
                break;
            case 3:
                MidpointLineForZone0ButModified(3, -x1, y1, -x2, y2);
                break;
            case 4:
                MidpointLineForZone0ButModified(4, -x1, -y1, -x2, -y2);
                break;
            case 5:
                MidpointLineForZone0ButModified(5, -y1, -x1, -y2, -x2);
                break;
            case 6:
                MidpointLineForZone0ButModified(6, -y1, x1, -y2, x2);
                break;
            case 7:
                MidpointLineForZone0ButModified(7, x1, -y1, x2, -y2);
                break;
        }
    }
    private void MidpointLineForZone0ButModified(int zone, double x1, double y1, double x2, double y2) {
        System.out.println("encoded zone 0 points : ("+x1+","+y1+"), ("+x2+","+y2+")");
        double dx = x2-x1;
        double dy = y2-y1;
        double d= 2*dy-dx;
        double dE = 2*dy;
        double dNE = 2*(dy-dx);

        double x=x1, y=y1;

        gl.glBegin(GL2.GL_POINTS);

        while(x<=x2){
            switch (zone){
                //decoding the zones from zone 0
                case 0:
                    gl.glVertex2d(x, y);
                    break;
                case 1:
                    gl.glVertex2d(y, x);
                    break;
                case 2:
                    gl.glVertex2d(-y, x);
                    break;
                case 3:
                    gl.glVertex2d(-x, y);
                    break;
                case 4:
                    gl.glVertex2d(-x, -y);
                    break;
                case 5:
                    gl.glVertex2d(-y, -x);
                    break;
                case 6:
                    gl.glVertex2d(y, -x);
                    break;
                case 7:
                    gl.glVertex2d(x, -y);
                    break;
            }

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
    private int getZone(double x1, double y1, double x2, double y2){
        double dx = x2-x1;
        double dy = y2-y1;
        int zone=-1;

        //1st quadrant
        if (dx>0 && dy>0 && Math.abs(dx)>Math.abs(dy)){zone = 0;}
        if (dx>0 && dy>0 && Math.abs(dy)>Math.abs(dx)){zone = 1;}
        //2nd quadrant
        if (dx<0 && dy>0 && Math.abs(dy)>Math.abs(dx)){zone = 2;}
        if (dx<0 && dy>0 && Math.abs(dx)>Math.abs(dy)){zone = 3;}
        //3rd quadrant
        if (dx<0 && dy<0 && Math.abs(dx)>Math.abs(dy)){zone = 4;}
        if (dx<0 && dy<0 && Math.abs(dy)>Math.abs(dx)){zone = 5;}
        //4th quadrant
        if (dx>0 && dy<0 && Math.abs(dy)>Math.abs(dx)){zone = 6;}
        if (dx>0 && dy<0 && Math.abs(dx)>Math.abs(dy)){zone = 7;}

        System.out.println("zone = "+zone);
        return zone;
    }

    //MidpointLineDrawing_Algo for zone = 0
    private void MidpointLineForZone0(GL2 gl, double x1, double y1, double x2, double y2){
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
        glu.gluOrtho2D(-350.0, 350.0, -250.0, 250.0);
    }

    /**
     * Take care of drawing here.
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        /*
         * put your code here
         */
        doIt(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    @Override
    public void dispose(GLAutoDrawable arg0) { }

} // 'ThirdGLEventListener' class

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
        final JFrame frame=new MyJFrame();
        frame.setTitle("LifeView frame");
        //adding canvas to frame
        frame.add(glcanvas);
        frame.setSize(640,480);
        frame.setVisible(true);
    }

    static class MyJFrame extends JFrame {
        protected void processWindowEvent(WindowEvent e) {
            if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                System.exit(0);
            }
        }
    }
}// 'Main' class


