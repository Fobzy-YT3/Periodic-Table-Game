
/**
 * Periodic Table Guessing Game
 *
 * @eBoy 
 * @1/11/2020
 */ 
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Vector;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
public class PeriodicGame extends JFrame
{ 
    KeyboardInput keyboard = new KeyboardInput();
    MouseInput1 mouse;
    Canvas canvas;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
   
    int HEIGHT = screenSize.height-25;
    int WIDTH = screenSize.width;
    
    static periodicTable[] table = new periodicTable[118];
    
    int ranNum = (int)(Math.random() * ((117) + 1));
    int correct = 0;
    
    boolean crctBool = false;
    boolean hint1 = false;
    boolean hint2 = false;
    boolean notElErr = false;
    boolean answer = false;
    boolean nCorrect = false;
    String input = "";
    
    ArrayList<String> incorrect = new ArrayList<String>();
    public PeriodicGame() 
    {
        setIgnoreRepaint( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        canvas = new Canvas();
        canvas.setIgnoreRepaint( true );
        canvas.setSize( WIDTH, HEIGHT );
        add( canvas );
        pack();
        // for keyboard using
        addKeyListener( keyboard );
        canvas.addKeyListener( keyboard );
        // for mouse using
        mouse = new MouseInput1();
        addMouseListener( mouse );
        addMouseMotionListener( mouse );
        canvas.addMouseListener( mouse );
        canvas.addMouseMotionListener( mouse );
    }
    
    
    public void run() throws InterruptedException
    {
      
        canvas.createBufferStrategy( 2 );
        BufferStrategy buffer = canvas.getBufferStrategy();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage bi = gc.createCompatibleImage( WIDTH, HEIGHT );
        
        Graphics graphics = null;
        Graphics2D g2d = null;
        Color background = Color.BLACK;
        
        
        while(keyboard.keyDown( KeyEvent.VK_ESCAPE ) == false && incorrect.size() < 5) {
          try {
            keyboard.poll();
            mouse.poll();
            g2d = bi.createGraphics();
            g2d.setColor( background );
            g2d.fillRect( 0, 0, WIDTH, HEIGHT );
            
            g2d.setColor( Color.GREEN );
            //g2d.drawString( "Input 2:", 450, 500 ); 
            g2d.setColor( Color.LIGHT_GRAY );
            g2d.drawLine(0 ,HEIGHT - 50, WIDTH , HEIGHT - 50);
            g2d.drawString( "Input:" + input, 0 ,HEIGHT - 35);
            
            Font currentFont = g2d.getFont();
            Font newFont = currentFont.deriveFont(currentFont.getSize() * 12F);
            g2d.setFont(newFont);
            
            String x = (ranNum+1)+"";
            int x2 = x.length();
            if (x2 == 1)
            {
                x = "00" + x;
            }
            else if (x2 == 2)
            {
                x = "0" + x;
            }
            g2d.drawRect(WIDTH*3/8 ,HEIGHT/8, WIDTH/4, HEIGHT/2);
            g2d.drawString( x , WIDTH*3/8 + WIDTH/16,HEIGHT/3);
            
            g2d.setFont(currentFont);
            g2d.setColor( Color.LIGHT_GRAY );
            g2d.drawRoundRect(WIDTH/8 ,HEIGHT - 150, WIDTH/8 , 75, 75, 75);
            g2d.drawString( "Submit Answer" , WIDTH/8 + WIDTH/24,HEIGHT - 105);
            g2d.drawRoundRect(WIDTH/8 * 6 ,HEIGHT - 150, WIDTH/8 , 75, 75, 75);
            g2d.drawString( "Get The Answer" , WIDTH/8 * 6 + WIDTH/24,HEIGHT - 105);
            g2d.drawRoundRect(25 ,25, 75 , 50, 45,45);
            g2d.drawString( "Quit" , 50,55);
            g2d.drawRoundRect(25 ,125, 125, 50, 45,45);
            g2d.drawString( "New Number" , 50,150);
            
            if (crctBool == true)
            {
                g2d.drawString( "Correct!" , WIDTH/2, HEIGHT* 3/4);
            }
            if (hint1 == true)
            {
                g2d.drawString( "It is to the right" , WIDTH/2, HEIGHT* 3/4);
            }
            if (hint2 == true)
            {
                g2d.drawString( "It is to the left" , WIDTH/2, HEIGHT* 3/4 );
            }
            if (notElErr == true)
            {
                g2d.drawString( "That's not a valid input" , WIDTH/2 - 13, HEIGHT* 3/4 + 12);
            }
            if (answer == true)
            {
                g2d.drawString("The element is: " + table[ranNum].getN() , WIDTH/2 - 25, HEIGHT* 3/4 + 24);
            } 
            if (nCorrect == true)
            {
                g2d.drawString("Incorrect", WIDTH/2, HEIGHT* 3/4 -12);
            }
            g2d.drawString( "Correct: " + correct, 25, 200);
            processInput();
            
            graphics = buffer.getDrawGraphics();
            graphics.drawImage( bi, 0, 0, null );
            if( !buffer.contentsLost() )
              buffer.show();
            
              
            Thread.sleep(10);
            
          } finally 
          {
            // Release resources
            if( graphics != null ) 
              graphics.dispose();
            if( g2d != null ) 
              g2d.dispose();
              
          }
          
          
        }
    }
    
    
    protected void processInput() throws InterruptedException
    {
        Point p = MouseInfo.getPointerInfo().getLocation();
        int x = p.x;
        int y = p.y;
        if( mouse.buttonDownOnce( 1 ) &&  x < 150 && x > 25 && y < 125 && y > 25) 
        {
           System.exit(0);
        }
        if( mouse.buttonDownOnce( 1 ) &&  x < 175 && x > 25 && y < 200 && y > 150) 
        {
            ranNum = (int)(Math.random() * ((117) + 1));
            crctBool = false;
            answer = false;
        }
        if( (mouse.buttonDownOnce( 1 ) || keyboard.keyDown( KeyEvent.VK_ENTER )  )&&  x > WIDTH/8 && x < WIDTH/4 && y < HEIGHT - 50 && y > HEIGHT - 150) 
        {
            if(input.equalsIgnoreCase(table[ranNum].getN()) || input.equalsIgnoreCase(table[ranNum].getS()))
            {
                ranNum = (int)(Math.random() * ((117) + 1));
                crctBool = true;
                hint1 = false;
                hint2 = false;
                notElErr = false;
                answer = false;
                nCorrect = false;
                input = "";
                correct++;
            }
            else
            {
                int what = -1;
                nCorrect = true;
                if (incorrect.size() > 0 && input.equalsIgnoreCase(incorrect.get(incorrect.size() - 1)))
                {
                    
                }
                else
                {
                    incorrect.add(input);
                }
                
                for (int i = 0; i < 118;i++)
                {
                    if (input.equalsIgnoreCase(table[i].getN()))
                    {
                        what = table[i].getaN();
                    }
                }
                for (int i = 0; i < 118;i++)
                {
                    if (input.equalsIgnoreCase(table[i].getS()))
                    {
                        what = table[i].getaN();
                    }
                }
                if (what < table[ranNum].getaN() && what > -1)
                {
                    hint1 = true;
                    hint2 = false;
                    notElErr = false;
                    answer = false;
                    crctBool = false;
                }
                else if (what > table[ranNum].getaN() && what > -1)
                {
                    hint2 = true;
                    hint1 = false;
                    notElErr = false;
                    answer = false;
                    crctBool = false;
                }
                else if (what == -1)
                {
                    notElErr = true;
                    crctBool = false;
                }
            }
        }
        if( mouse.buttonDownOnce( 1 ) &&  x < WIDTH/8 * 7 && x > WIDTH/8 * 6 && y < HEIGHT - 50 && y > HEIGHT - 150) 
        {
            answer = true;
            hint2 = false;
            hint1 = false;
            notElErr = false;
        }
        
        int sec = 150;
            if (keyboard.keyDown( KeyEvent.VK_A ))
            {
                input = input + "a";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_B ))
            {
                input = input + "b";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_C ))
            {
                input = input + "c";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_D ))
            {
                input = input + "d";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_E ))
            {
                input = input + "e";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_F ))
            {
                input = input + "f";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_G ))
            {
                input = input + "g";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_H ))
            {
                input = input + "h";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_I ))
            {
                input = input + "i";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_J ))
            {
                input = input + "j";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_K ))
            {
                input = input + "k";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_L ))
            {
                input = input + "l";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_M ))
            {
                input = input + "m";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_N ))
            {
                input = input + "n";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_O ))
            {
                input = input + "o";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_P ))
            {
                input = input + "p";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_Q ))
            {
                input = input + "q";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_R ))
            {
                input = input + "r";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_S ))
            {
                input = input + "s";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_T ))
            {
                input = input + "t";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_U ))
            {
                input = input + "u";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_V ))
            {
                input = input + "v";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_W ))
            {
                input = input + "w";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_X ))
            {
                input = input + "x";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_Y ))
            {
                input = input + "y";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_Z ))
            {
                input = input + "z";
                Thread.sleep(sec);
            }
            else if (keyboard.keyDown( KeyEvent.VK_BACK_SPACE ) && input.length() > 0)
            {
                input = input.substring(0, input.length()-1);
                Thread.sleep(sec);
            }
    }
    
    public static void main() throws InterruptedException
    { 
        String[] elements = new String[118];
        elements[0] = "Hydrogen";
        elements[1] = "Helium";
        elements[2] = "Lithium";
        elements[3] = "Beryllium";
        elements[4] = "Boron";
        elements[5] = "Carbon";
        elements[6] = "Nitrogen";
        elements[7] = "Oxygen";
        elements[8] = "Fluorine";
        elements[9] = "Neon";
        elements[10] = "Sodium";
        elements[11] = "Magnesium";
        elements[12] = "Aluminium";
        elements[13] = "Silicon";
        elements[14] = "Phosphorus";
        elements[15] = "Sulfur";
        elements[16] = "Chlorine";
        elements[17] = "Argon";
        elements[18] = "Potassium";
        elements[19] = "Calcium";
        elements[20] = "Scandium";
        elements[21] = "Titanium";
        elements[22] = "Vanadium";
        elements[23] = "Chromium";
        elements[24] = "Manganese";
        elements[25] = "Iron";
        elements[26] = "Cobalt";
        elements[27] = "Nickel";
        elements[28] = "Copper";
        elements[29] = "Zinc";
        elements[30] = "Gallium";
        elements[31] = "Germanium";
        elements[32] = "Arsenic";
        elements[33] = "Selenium";
        elements[34] = "Bromine";
        elements[35] = "Krypton";
        elements[36] = "Rubidium";
        elements[37] = "Strontium";
        elements[38] = "Yttrium";
        elements[39] = "Zirconium";
        elements[40] = "Niobium";
        elements[41] = "Molybdenum";
        elements[42] = "Technetium";
        elements[43] = "Ruthenium";
        elements[44] = "Rhodium";
        elements[45] = "Palladium";
        elements[46] = "Silver";
        elements[47] = "Cadmium";
        elements[48] = "Indium";
        elements[49] = "Tin";
        elements[50] = "Antimony";
        elements[51] = "Tellurium";
        elements[52] = "Iodine";
        elements[53] = "Xenon";
        elements[54] = "Cesium";
        elements[55] = "Barium";
        elements[56] = "Lanthanum";
        elements[57] = "Cerium";
        elements[58] = "Praseodymium";
        elements[59] = "Neodymium";
        elements[60] = "Promethium";
        elements[61] = "Samarium";
        elements[62] = "Europium";
        elements[63] = "Gadolinium";
        elements[64] = "Terbium";
        elements[65] = "Dysprosium";
        elements[66] = "Holmium";
        elements[67] = "Erbium";
        elements[68] = "Thulium";
        elements[69] = "Ytterbium";
        elements[70] = "Lutetium";
        elements[71] = "Hafnium";
        elements[72] = "Tantalum";
        elements[73] = "Tungsten";
        elements[74] = "Rhenium";
        elements[75] = "Osmium";
        elements[76] = "Iridium";
        elements[77] = "Platinum";
        elements[78] = "Gold";
        elements[79] = "Mercury";
        elements[80] = "Thallium";
        elements[81] = "Lead";
        elements[82] = "Bismuth";
        elements[83] = "Polonium";
        elements[84] = "Astatine";
        elements[85] = "Radon";
        elements[86] = "Francium";
        elements[87] = "Radium";
        elements[88] = "Actinium";
        elements[89] = "Thorium";
        elements[90] = "Protactinium";
        elements[91] = "Uranium";
        elements[92] = "Neptunium";
        elements[93] = "Plutonium";
        elements[94] = "Americium";
        elements[95] = "Curium";
        elements[96] = "Berkelium";
        elements[97] = "Californium";
        elements[98] = "Einsteinium";
        elements[99] = "Fermium";
        elements[100] = "Mendelevium";
        elements[101] = "Nobelium";
        elements[102] = "Lawrencium";
        elements[103] = "Rutherfordium";
        elements[104] = "Dubnium";
        elements[105] = "Seaborgium";
        elements[106] = "Bohrium";
        elements[107] = "Hassium";
        elements[108] = "Meitnerium";
        elements[109] = "Darmstadtium";
        elements[110] = "Roentgenium";
        elements[111] = "Copernicium";
        elements[112] = "Nihonium";
        elements[113] = "Flerovium";
        elements[114] = "Moscovium";
        elements[115] = "Livermorium";
        elements[116] = "Tennessee";
        elements[117] = "Oganesson";
        
        String[] element = new String[118];
        element[0] = "H";
        element[1] = "He";
        element[2] = "Li";
        element[3] = "Be";
        element[4] = "B";
        element[5] = "C";
        element[6] = "N";
        element[7] = "O";
        element[8] = "F";
        element[9] = "Ne";
        element[10] = "Na";
        element[11] = "Mg";
        element[12] = "Al";
        element[13] = "Si";
        element[14] = "P";
        element[15] = "S";
        element[16] = "Cl";
        element[17] = "Ar";
        element[18] = "K";
        element[19] = "Ca";
        element[20] = "Sc";
        element[21] = "Ti";
        element[22] = "V";
        element[23] = "Cr";
        element[24] = "Mn";
        element[25] = "Fe";
        element[26] = "Co";
        element[27] = "Ni";
        element[28] = "Cu";
        element[29] = "Zn";
        element[30] = "Ga";
        element[31] = "Ge";
        element[32] = "As";
        element[33] = "Se";
        element[34] = "Br";
        element[35] = "Kr";
        element[36] = "Rb";
        element[37] = "Sr";
        element[38] = "Y";
        element[39] = "Zr";
        element[40] = "Nb";
        element[41] = "Mo";
        element[42] = "Te";
        element[43] = "Ru";
        element[44] = "Rh";
        element[45] = "Pd";
        element[46] = "Ag";
        element[47] = "Cd";
        element[48] = "In";
        element[49] = "Sn";
        element[50] = "Sb";
        element[51] = "Te";
        element[52] = "I";
        element[53] = "Xe";
        element[54] = "Cs";
        element[55] = "Ba";
        element[56] = "La";
        element[57] = "Ce";
        element[58] = "Pr";
        element[59] = "Nd";
        element[60] = "Pm";
        element[61] = "Sm";
        element[62] = "Eu";
        element[63] = "Gd";
        element[64] = "Tb";
        element[65] = "Dy";
        element[66] = "Ho";
        element[67] = "Er";
        element[68] = "Tm";
        element[69] = "Yb";
        element[70] = "Lu";
        element[71] = "Hf";
        element[72] = "Ta";
        element[73] = "W";
        element[74] = "Re";
        element[75] = "Os";
        element[76] = "Ir";
        element[77] = "Pt";
        element[78] = "Au";
        element[79] = "Hg";
        element[80] = "Tl";
        element[81] = "Pb";
        element[82] = "Bi";
        element[83] = "Po";
        element[84] = "At";
        element[85] = "Rn";
        element[86] = "Fr";
        element[87] = "Ra";
        element[88] = "Ac";
        element[89] = "Th";
        element[90] = "Pa";
        element[91] = "U";
        element[92] = "Np";
        element[93] = "Pu";
        element[94] = "Am";
        element[95] = "Cm";
        element[96] = "Bk";
        element[97] = "Cf";
        element[98] = "Es";
        element[99] = "Fm";
        element[100] = "Md";
        element[101] = "No";
        element[102] = "Lr";
        element[103] = "Rf";
        element[104] = "Db";
        element[105] = "Sg";
        element[106] = "Bh";
        element[107] = "Hs";
        element[108] = "Mt";
        element[109] = "Ds";
        element[110] = "Rg";
        element[111] = "Cn";
        element[112] = "Nh";
        element[113] = "Fl";
        element[114] = "Me";
        element[115] = "Lv";
        element[116] = "Ts";
        element[117] = "Og";
        for (int i = 0; i < 118; i++)
        {
            table[i] = new periodicTable(i, elements[i], element[i]);
        }
        
        PeriodicGame app = new PeriodicGame();
        app.setTitle( "Video Game" );
        app.setVisible( true );
        app.run();
        System.exit( 0 );
    } 
}
