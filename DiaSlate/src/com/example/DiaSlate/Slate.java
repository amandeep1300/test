package com.example.DiaSlate;

import java.io.*;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import com.example.DiaSlate.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Slate extends Activity implements OnTouchListener, OnClickListener, OnLongClickListener
{
	// Fields
	
	DrawPanel dp; // dp is canvas on which user draws
	/*
	 * pointsToDraw is an array list of array list of CWPath
	 * each array list contans a set of paths
	 * each array list belongs to one page
	 */
    private ArrayList<ArrayList<CWPath>> pointsToDraw = new ArrayList<ArrayList<CWPath>>();
    public static Paint mPaint; // this object is used to draw on canvas by setting width, color , stroke width
    public static int foregroundColor; // foreground color
    public static int previousForegroundColor; // previous foreground color
    public static int backgroundColor; // background color
    public static int drawingWidth; // drawing width
    public static int eraserWidth; // eraser width
    public static int pageNo = 0; // present page number 
    public static int noOfPages = 1; // total number of pages
    // this object represents a path with its color and width
    public CWPath p;
    // represents whether we are in draw mode or erase mode
    public boolean drawMode; 
    // horizontal layout that appears on screen at top
    RelativeLayout l, l1, l2, l3, l11, l12, l13, l21, l22, l23, l31, l32, l33;
    // buttons on horizontal layout
    Button full_screen, draw_mode, erase_mode,  next_page, previous_page;
    // textview on horizontal layout which shows current page number in which user is in
    TextView pagesInfo;
    // below seekbar is shown on horizontal layout to scroll among pages
    SeekBar pageScrollBar;
    
    // this layout appears in full screen mode with only on button at top left corner
    RelativeLayout m;
    // button on layout m
    Button menu;
    
    // this is vertical layout that appears on screen at left
    RelativeLayout n, n1,n2, n3, n11, n12, n13, n21, n22, n23, n31, n32, n33;
    // buttons on vertical layout
    Button  save, erase_all, fcolor, bcolor, add_new_page, delete_page, help;
    
    // all above 3 layouts(l, m, n) and dp are added to below frame layout
    FrameLayout f;
    
    // below soundpool object is used to play sound when buttons are clicked
    SoundPool sp;
    int explosion;
    
    /*
     *  hButtonSize represents both width and height of buttons on horizontal layout
     *  vButtonSize represents both width and height of buttons on vertical layout
     */
    int hButtonSize, vButtonSize;
    
    /*
     * folder represents path in which images are stored
     * file represents name of image that is stored in folder
     * if there are more pages, then pages will be saved as file1.png, file2.png, ....
     */
    String folder, file;
    
    // preferencesFileName is the name of shared preferences file
    public static String preferencesFileName = "preferences";
    /*
     * shared preferences object used to store drawing width, eraser width, drawing color, 
     * background color permanently and restores those previous setting when application
     * is restarted
     */
    
    SharedPreferences preferences;
    
    /*
     * dwdialog is a dialog box to change drawing width
     * ewdialog is a dialog box to change eraser width
     */
    AlertDialog.Builder dwdialog, ewdialog;
    /** Called when the activity is first created. */
    
    // below objects are used to display popup menu when save button is clicked
    private LayoutInflater inflater;
	private PopupWindow pw;
	private View popupView;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        // instanticating variables related to popup menu
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popupView = inflater.inflate(R.layout.popup_menu, null, false);
        // loading sound file
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        explosion =  sp.load(this, R.raw.move, 1);
        
        // instantiating drawing panel
        dp = new DrawPanel(this); 
        
        //setting full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        
        // getting screen height and width
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        //calculating button sizes on horizontal layout and vertical layout
        int screenWidth = metrics.widthPixels;
        int screenHeight=metrics.heightPixels;
        int layoutsize=screenWidth*1/9;
        hButtonSize=layoutsize*4/10;
        int layoutsize1 = screenHeight/9;
        vButtonSize = layoutsize*4/10;
        
        RelativeLayout.LayoutParams p1 =new RelativeLayout.LayoutParams(hButtonSize,hButtonSize);
        p1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        
        RelativeLayout.LayoutParams p2 =new RelativeLayout.LayoutParams(hButtonSize,hButtonSize);
        p2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        
        RelativeLayout.LayoutParams p3 =new RelativeLayout.LayoutParams(hButtonSize,hButtonSize);
        p3.addRule(RelativeLayout.CENTER_VERTICAL);
        
        RelativeLayout.LayoutParams p4 =new RelativeLayout.LayoutParams(hButtonSize,hButtonSize);
        p4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        
        RelativeLayout.LayoutParams p5 =new RelativeLayout.LayoutParams(hButtonSize,hButtonSize);
        p5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        
        RelativeLayout.LayoutParams p6 =new RelativeLayout.LayoutParams(hButtonSize,hButtonSize);
        p6.addRule(RelativeLayout.CENTER_HORIZONTAL);
      
        // below code creates horizontal layout(menu bar)
        l=new RelativeLayout(this);
        l.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,hButtonSize));
        l.setBackgroundColor( Color.argb(255,238, 224, 229));
        
       
        
        l1=new RelativeLayout(this);
        RelativeLayout.LayoutParams pt1 =new RelativeLayout.LayoutParams(layoutsize*3,layoutsize);
        pt1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        l1.setLayoutParams(pt1);
        
       
        
        l2=new RelativeLayout(this);
        RelativeLayout.LayoutParams pt2 =new RelativeLayout.LayoutParams(layoutsize*3,layoutsize);
        pt2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        l2.setLayoutParams(pt2);
        
       
        
        
        l3=new RelativeLayout(this);
        RelativeLayout.LayoutParams pt3 =new RelativeLayout.LayoutParams(layoutsize*3,layoutsize);
        pt3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        l3.setLayoutParams(pt3);
        
       
        
        RelativeLayout.LayoutParams pt11 =new RelativeLayout.LayoutParams(layoutsize,layoutsize);
        pt11.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        RelativeLayout.LayoutParams pt22 =new RelativeLayout.LayoutParams(layoutsize,layoutsize);
        pt22.addRule(RelativeLayout.CENTER_HORIZONTAL);
        RelativeLayout.LayoutParams pt33 =new RelativeLayout.LayoutParams(layoutsize,layoutsize);
        pt33.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); 
        
        
      //full screen
        full_screen = new Button(this);
        full_screen.setLayoutParams(p4);
        full_screen.setId(1);
        full_screen.setBackgroundResource(R.drawable.fullscreen);
        l11 = new RelativeLayout(this);
        l11.setLayoutParams(pt11);
        l11.addView(full_screen);
        full_screen.setOnClickListener(this);
        
        // draw mode button
        draw_mode = new Button(this);
        draw_mode.setLayoutParams(p6);
        draw_mode.setId(2);
        draw_mode.setBackgroundResource(R.drawable.writer1);
        l12 = new RelativeLayout(this);
        l12.setLayoutParams(pt22);
        l12.addView(draw_mode);
        draw_mode.setOnClickListener(this);
        draw_mode.setOnLongClickListener(this);
        
        
        
        //erase mode button
        erase_mode = new Button(this);
        erase_mode.setLayoutParams(p6);
        erase_mode.setId(3);
        erase_mode.setBackgroundResource(R.drawable.eraser2);
        l13 = new RelativeLayout(this);
        l13.setLayoutParams(pt33);
        l13.addView(erase_mode);
        erase_mode.setOnClickListener(this);
        erase_mode.setOnLongClickListener(this);
        
        // previous page button
        previous_page = new Button(this);
        previous_page.setLayoutParams(p6);
        previous_page.setId(4);
        previous_page.setBackgroundResource(R.drawable.previous);
        l21 = new RelativeLayout(this);
        l21.setLayoutParams(pt11);
        l21.addView(previous_page);
        previous_page.setOnClickListener(this);
        
        // next page button
        next_page = new Button(this);
        next_page.setLayoutParams(p6);
        next_page.setId(5);
        next_page.setBackgroundResource(R.drawable.next);
        l22 = new RelativeLayout(this);
        l22.setLayoutParams(pt22);
        l22.addView(next_page);
        next_page.setOnClickListener(this);
        
        // a textview which shows present page number in which user is drawing
        pagesInfo = new TextView(this);
        pagesInfo.setText("1/1");
        pagesInfo.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams p7 =new RelativeLayout.LayoutParams(layoutsize,layoutsize);
        p7.addRule(RelativeLayout.CENTER_HORIZONTAL);
        pagesInfo.setLayoutParams(p7);
        pagesInfo.setGravity(Gravity.CENTER);
        pagesInfo.setId(6);
        l23 = new RelativeLayout(this);
        l23.setLayoutParams(pt33);
        l23.addView(pagesInfo);
        
        // a seekbar used scroll among pages
        pageScrollBar = new SeekBar(this);
        pageScrollBar.setMax(noOfPages-1);
        pageScrollBar.setId(7);
        pageScrollBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() 
        {
            @Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                // when user changes seekbar value, then current page number will be set to progress
            	pageNo = progress;
            	pagesInfo.setText("" + (pageNo+1) + "/" + noOfPages);
            }

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
        });
        
        RelativeLayout.LayoutParams p8 =new RelativeLayout.LayoutParams(layoutsize*3-hButtonSize,hButtonSize);
        p8.addRule(RelativeLayout.CENTER_HORIZONTAL);
        pageScrollBar.setLayoutParams(p8);
       
        
        l1.addView(l11);
        l1.addView(l12);
        l1.addView(l13);
        
       
        
        l2.addView(l21);
        l2.addView(l22);
        l2.addView(l23);
        
        l3.addView(pageScrollBar);
        
       
        l.addView(l1);
        l.addView(l2);
        l.addView(l3);
        
        
        // below code creates a layout which is shown when user is in full screen mode
        RelativeLayout.LayoutParams m6 =new RelativeLayout.LayoutParams(hButtonSize,hButtonSize);
        m6.addRule(RelativeLayout.CENTER_HORIZONTAL);
       
        m=new RelativeLayout(this);
        m.setLayoutParams(new LayoutParams(hButtonSize,0));
        m.setBackgroundColor( Color.argb(255,255, 255, 255));
        menu = new Button(this);
        menu.setId(21);
        menu.setBackgroundResource(R.drawable.home);
        menu.setLayoutParams(m6);
        menu.setOnClickListener(this);
        m.addView(menu);
        
       
        
        // below code creates vertical layout(menu bar)
        RelativeLayout.LayoutParams n6 =new RelativeLayout.LayoutParams(vButtonSize,vButtonSize);
        n6.addRule(RelativeLayout.CENTER_VERTICAL);
        
        n=new RelativeLayout(this);
        n.setLayoutParams(new LayoutParams(vButtonSize,LayoutParams.MATCH_PARENT));
        n.setBackgroundColor( Color.argb(255,238, 224, 229));
        
        
        
        n1=new RelativeLayout(this);
        RelativeLayout.LayoutParams nt1 =new RelativeLayout.LayoutParams(layoutsize1,layoutsize1*3);
        nt1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        n1.setLayoutParams(nt1);
        
        
       
        
        n2=new RelativeLayout(this);
        RelativeLayout.LayoutParams nt2 =new RelativeLayout.LayoutParams(layoutsize1,layoutsize1*3);
        nt2.addRule(RelativeLayout.CENTER_VERTICAL);
        n2.setLayoutParams(nt2);
        
       
        n3 = new RelativeLayout(this);
        RelativeLayout.LayoutParams nt3 =new RelativeLayout.LayoutParams(layoutsize1,layoutsize1*3);
        nt3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        n3.setLayoutParams(nt3);
        
       
        
        RelativeLayout.LayoutParams nt11 =new RelativeLayout.LayoutParams(layoutsize1,layoutsize1);
        nt11.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        RelativeLayout.LayoutParams nt22 =new RelativeLayout.LayoutParams(layoutsize1,layoutsize1);
        nt22.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams nt33 =new RelativeLayout.LayoutParams(layoutsize1,layoutsize1);
        nt33.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        
        
        n11 = new RelativeLayout(this);
        n11.setLayoutParams(nt11);
        
        n12 = new RelativeLayout(this);
        n12.setLayoutParams(nt22);
        
        
     // save button
        save = new Button(this);
        save.setLayoutParams(n6);
        save.setId(11);
        save.setBackgroundResource(R.drawable.save);
        n13 = new RelativeLayout(this);
        n13.setLayoutParams(nt33);
        n13.addView(save);
        save.setOnClickListener(this);
        
        erase_all = new Button(this);
        erase_all.setLayoutParams(n6);
        erase_all.setId(12);
        erase_all.setBackgroundResource(R.drawable.erase_all);
        n21 = new RelativeLayout(this);
        n21.setLayoutParams(nt11);
        n21.addView(erase_all);
        erase_all.setOnClickListener(this);
       
       
        fcolor = new Button(this);
        fcolor.setLayoutParams(n6);
        fcolor.setId(13);
        fcolor.setBackgroundResource(R.drawable.fcolor);
        n22 = new RelativeLayout(this);
        n22.setLayoutParams(nt22);
        n22.addView(fcolor);
        fcolor.setOnClickListener(this);
        
        
        bcolor = new Button(this);
        bcolor.setLayoutParams(n6);
        bcolor.setId(14);
        bcolor.setBackgroundResource(R.drawable.bcolor);
        n23 = new RelativeLayout(this);
        n23.setLayoutParams(nt33);
        n23.addView(bcolor);
        bcolor.setOnClickListener(this);
        
        
        add_new_page = new Button(this);
        add_new_page.setLayoutParams(n6);
        add_new_page.setId(15);
        add_new_page.setBackgroundResource(R.drawable.addnewpage);
        n31 = new RelativeLayout(this);
        n31.setLayoutParams(nt11);
        n31.addView(add_new_page);
        add_new_page.setOnClickListener(this);
        
        
        delete_page = new Button(this);
        delete_page.setLayoutParams(n6);
        delete_page.setId(16);
        delete_page.setBackgroundResource(R.drawable.deletepage);
        n32 = new RelativeLayout(this);
        n32.setLayoutParams(nt22);
        n32.addView(delete_page);
        delete_page.setOnClickListener(this);
        
        help = new Button(this);
        help.setLayoutParams(n6);
        help.setId(17);
        help.setBackgroundResource(R.drawable.help);
        n33 = new RelativeLayout(this);
        n33.setLayoutParams(nt33); 
        n33.addView(help);
        help.setOnClickListener(this);
        
        
        n1.addView(n11);
        n1.addView(n12);
        n1.addView(n13);
        
        n2.addView(n21);
        n2.addView(n22);
        n2.addView(n23);
        
        n3.addView(n31);
        n3.addView(n32);
        n3.addView(n33);
        
        n.addView(n1);
        n.addView(n2);
        n.addView(n3);
        f = new FrameLayout(this);  
        f.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));  
        f.addView(dp);
        f.addView(m);
        f.addView(n);
        f.addView(l);
        setContentView(f);
        
       
        
        drawMode = true;
       
        dp.setOnTouchListener(this);
        
        
       
        mPaint = new Paint();
        mPaint.setDither(true);
        
        
        // below code opens preferences file and restores previous settings
        preferences = getSharedPreferences(preferencesFileName, 0);
        foregroundColor = preferences.getInt("fcolor", 0xffffffff);
        backgroundColor = preferences.getInt("bcolor", 0xff000000);
        drawingWidth = preferences.getInt("dwidth", 10);
        eraserWidth = preferences.getInt("ewidth", 20);
        
	    previousForegroundColor = foregroundColor;
        mPaint.setColor(foregroundColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(drawingWidth);
        pointsToDraw.add(new ArrayList<CWPath>());
       
        folder = Environment.getExternalStorageDirectory()+"/slate";
        file = "";
    }
    
   
    @Override
    protected void onPause() 
    {
        // TODO Auto-generated method stub
        super.onPause();
        dp.pause();
    }
    @Override
    protected void onResume() 
    {
        // TODO Auto-generated method stub
        super.onResume();
        dp.resume();
    }
    
    //this method adds paths that are drawn by user to current page
    @Override
	public boolean onTouch(View v, MotionEvent me) 
    {
    	
        // TODO Auto-generated method stub
    		synchronized(pointsToDraw)
            {
    	        if(me.getAction() == MotionEvent.ACTION_DOWN)
    	        {
    	            Path path = new Path();
    	            path.moveTo(me.getX(), me.getY());
    	            p = new CWPath(path, foregroundColor, drawingWidth, eraserWidth, drawMode);
    	            //path.lineTo(me.getX(), me.getY());
    	            pointsToDraw.get(pageNo).add(p);
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_MOVE)
    	        {
    	            p.getPath().lineTo(me.getX(), me.getY());
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_UP)
    	        {
    	           
    	        }
            }       
        return true;
    }
    
    //This is the inner class on which user actually draws
    public class DrawPanel extends SurfaceView implements Runnable
    {

        Thread t = null;
        SurfaceHolder holder;
        boolean isItOk = false ;

        public DrawPanel(Context context) 
        {
            super(context);
            // TODO Auto-generated constructor stub
            holder = getHolder();
        }

        @Override
		public void run() 
        {
            // TODO Auto-generated method stub
            while( isItOk == true)
            {

                if(!holder.getSurface().isValid())
                {
                    continue;
                }
                
                Canvas c = holder.lockCanvas();
                c.drawColor(backgroundColor);
                onDraw(c);
                holder.unlockCanvasAndPost(c);
            }
        }

        // below method draws all paths on canvas screen
        @Override
        protected void onDraw(Canvas canvas) 
        {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            synchronized(pointsToDraw)
            {
            	for (CWPath path : pointsToDraw.get(pageNo)) 
            	{
            		if(path.getDrawMode())
            		{
            			mPaint.setColor(path.getPathColor());
            			mPaint.setStrokeWidth(path.getPathWidth());
            			canvas.drawPath(path.getPath(), mPaint);
            		}
            		else
            		{
            			mPaint.setColor(backgroundColor);
            			mPaint.setStrokeWidth(path.getEraserWidth());
            			canvas.drawPath(path.getPath(), mPaint);
            		}
                }
             }
        }

        public void pause()
        {
            isItOk = false;
            while(true)
            {
                try
                {
                    t.join();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }

        public void resume()
        {
            isItOk = true;  
            t = new Thread(this);
            t.start();

        }
    }
    
    /*
     * This inner class is used to create a thread which stores all pages as a pdf
     * It stores all pages as images and adds all images to a pdf, then removes all images
   	 */
    public class SaveAsPdf extends AsyncTask<String, Integer, String>
	{
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute()
		{
			dialog = new ProgressDialog(Slate.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(100);
			dialog.setTitle("Saving PDF File");
			
			dialog.setMessage(folder + "/" + file + ".pdf");
			dialog.show();
		}
		@Override
		protected String doInBackground(String... arg0) 
		{
			
			
			float increment = (float) (100.0/(2*noOfPages));
			float progress = 0;
			
			// below code saves all pages as images in /mnt/slate folder
			for(int i=1;i<=noOfPages;i++)
	        {
	        	
	        
		        Bitmap returnedBitmap = Bitmap.createBitmap(dp.getWidth(), dp.getHeight(),Bitmap.Config.ARGB_8888);
	            //Drawable bgDrawable =fl.getBackground();
	           
	               // bgDrawable.draw(canvas);
	            Canvas canvas = new Canvas(returnedBitmap);
	            dp.draw(canvas);
	            canvas.drawColor(backgroundColor);
	            
	      
	            for (CWPath pth : pointsToDraw.get(i-1)) 
            	{
            		
            		if(pth.getDrawMode())
            		{
            			mPaint.setStrokeWidth(pth.getPathWidth());
            			mPaint.setColor(pth.getPathColor());
            		}
            		else
            		{
            			mPaint.setStrokeWidth(pth.getEraserWidth());
            			mPaint.setColor(backgroundColor);
            		}
            		
            		canvas.drawPath(pth.getPath(), mPaint);
            			
                }
	           
     			File f = new File(folder, " " + file + i +".png");
     			FileOutputStream fos;
     			try 
     			{
     				fos = new FileOutputStream(f);
     				returnedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
     				fos.close();
     				progress += increment;
     				publishProgress((int)progress);
     				//Toast.makeText(Slate.this, file.toString(), Toast.LENGTH_LONG).show();
		            
     			} 
     			catch (FileNotFoundException e) 
     			{
     				Log.e("Panel", "FileNotFoundException", e);
     				Toast.makeText(Slate.this, e.toString(), Toast.LENGTH_LONG).show();
     				
     			} 
     			catch (IOException e)
     			{
     				Log.e("Panel", "IOEception", e);
     				Toast.makeText(Slate.this, e.toString(), Toast.LENGTH_LONG).show();
     			}
	        }
			//Code to save images as pdf
	        Document document=new Document();
	        document.addTitle(file);
			FileOutputStream fop = null;
			
			try 
			{
				fop=new FileOutputStream(folder+"/"+file+".pdf");
				PdfWriter.getInstance(document,fop);
			}
			catch (FileNotFoundException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			document.open();
			Image im;
			int count;
			
			/*
			 *  below code adds all images to created pdf document and deletes each image
			 *  after it is added to the document
			 */
			try
			{
				for(count=1;count<=noOfPages;count++)
				{
					im = Image.getInstance(folder+"/" + " " + file +count+".png");
					float w = im.getScaledWidth();
					float h = im.getScaledHeight();
					
					if(w>500 || h>450)
						im.scaleToFit(500,450);
					
					
					im.setSpacingBefore(5f);
					document.add(im);
					
					im.setSpacingAfter(10f);
					File f = new File(folder, " " + file + count + ".png");
					f.delete();
					progress += increment;
     				publishProgress((int)progress);
				}	
			}
			catch (BadElementException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  // Replace logo.png with your image name with extension 
			catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			document.close();
			try
			{
				fop.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();	
			}
			dialog.setProgress(100);
			dialog.dismiss();
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer...progress)
		{
			
			dialog.setProgress(progress[0]);
		}
		@Override
		protected void onPostExecute(String result)
		{
			Toast.makeText(Slate.this, "File is Saved ", Toast.LENGTH_SHORT).show();
					
		}
		
	}

    
    /*
     * This inner class is used to create a thread which stores all pages as .png images
     */
    public class SaveAsImages extends AsyncTask<String, Integer, String>
	{
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute()
		{
			dialog = new ProgressDialog(Slate.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(100);
			dialog.setTitle("Saving Images");
			
			dialog.setMessage("images are being stored as " + folder + "/" + file + "1.png, " + folder + "/" + file + "2.png, .... ");
			dialog.show();
		}
		@Override
		protected String doInBackground(String... arg0) 
		{
			
			float increment = (float) (100.0/(noOfPages));
			int progress = 0;
			
			// below code saves all pages as images in /mnt/slate folder
			for(int i=1;i<=noOfPages;i++)
	        {
	        	
	        
		        Bitmap returnedBitmap = Bitmap.createBitmap(dp.getWidth(), dp.getHeight(),Bitmap.Config.ARGB_8888);
	            //Drawable bgDrawable =fl.getBackground();
	           
	               // bgDrawable.draw(canvas);
	            Canvas canvas = new Canvas(returnedBitmap);
	            dp.draw(canvas);
	            canvas.drawColor(backgroundColor);
	            
	      
	            for (CWPath pth : pointsToDraw.get(i-1)) 
            	{
            		
            		if(pth.getDrawMode())
            		{
            			mPaint.setStrokeWidth(pth.getPathWidth());
            			mPaint.setColor(pth.getPathColor());
            		}
            		else
            		{
            			mPaint.setStrokeWidth(pth.getEraserWidth());
            			mPaint.setColor(backgroundColor);
            		}
            		
            		canvas.drawPath(pth.getPath(), mPaint);
            			
                }
	           
     			File f = new File(folder,  file + i +".png");
     			FileOutputStream fos;
     			try 
     			{
     				fos = new FileOutputStream(f);
     				returnedBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
     				fos.close();
     				progress += increment;
     				publishProgress(progress);
     				//Toast.makeText(Slate.this, file.toString(), Toast.LENGTH_LONG).show();
		            
     			} 
     			catch (FileNotFoundException e) 
     			{
     				Log.e("Panel", "FileNotFoundException", e);
     				Toast.makeText(Slate.this, e.toString(), Toast.LENGTH_LONG).show();
     				
     			} 
     			catch (IOException e)
     			{
     				Log.e("Panel", "IOEception", e);
     				Toast.makeText(Slate.this, e.toString(), Toast.LENGTH_LONG).show();
     			}
	        }
			
			dialog.setProgress(100);
			dialog.dismiss();
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Integer...progress)
		{
			dialog.setProgress(progress[0]);
		}
		@Override
		protected void onPostExecute(String result)
		{
			Toast.makeText(Slate.this, "Images are Saved ", Toast.LENGTH_SHORT).show();
					
		}
		
	}


	@Override
	public void onClick(View v) 
	{
		
		if(explosion != 0)
			sp.play(explosion, 1, 1, 0, 0, 1);
		switch(v.getId())
		{
			// full screen
			case 1:
				/*
				 *  below code sets horizontal layout height to 0, vertical layout width to 0
				 *  full screen layout height from o to horizontal button size
				 */
				LayoutParams p1 = l.getLayoutParams();
				p1.height = 0;
				l.setLayoutParams(p1);
				LayoutParams p2 = n.getLayoutParams();
				p2.width = 0;
				n.setLayoutParams(p2);
				LayoutParams p3 = m.getLayoutParams();
				p3.height = hButtonSize;
				m.setLayoutParams(p3);
				break;
				
			//draw mode
			case 2:
				if(!drawMode)
				{
					draw_mode.setBackgroundResource(R.drawable.writer1);
					erase_mode.setBackgroundResource(R.drawable.eraser2);
					drawMode = true;
					foregroundColor = previousForegroundColor;
					mPaint.setColor(foregroundColor);
				}
				break;
			//erase mode
			case 3:
				if(drawMode)
				{
					draw_mode.setBackgroundResource(R.drawable.writer2);
					erase_mode.setBackgroundResource(R.drawable.eraser1);
					drawMode = false;
					previousForegroundColor = foregroundColor;
					foregroundColor = backgroundColor;
					mPaint.setColor(foregroundColor);
				}
				break;
			//previous page
			case 4:
				if(pageNo != 0)
				{
					pageNo--;
					//Toast.makeText(this, "" + (pageNo+1) + "/" + noOfPages, Toast.LENGTH_SHORT).show();
				}
				else
				{
					//Toast.makeText(this, "You are in first page. You can't go to previous page.", Toast.LENGTH_SHORT).show();
				}
				updatePagesInfo();
				break;
			//next page
			case 5:
				if(pageNo+1 != noOfPages)
				{
					pageNo++;
					//Toast.makeText(this, "" + (pageNo+1) + "/" + noOfPages, Toast.LENGTH_SHORT).show();
				}
				else
				{
					//Toast.makeText(this, "You are in last page. You can't go to next page.", Toast.LENGTH_SHORT).show();
				}
				updatePagesInfo();
				break;	
			// save
			case 11:
				//showPopup(save);
				ActionItem saImage 	= new ActionItem(31	, "Save as Images", getResources().getDrawable(R.drawable.image));
				ActionItem saPdf 	= new ActionItem(32	, "Save as Pdf", getResources().getDrawable(R.drawable.pdf));
				final QuickAction quickAction = new QuickAction(this, QuickAction.VERTICAL);
				quickAction.addActionItem(saImage);
				quickAction.addActionItem(saPdf);
				quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() 
				{			
					@Override
					public void onItemClick(QuickAction source, int pos, int actionId) 
					{				
						ActionItem actionItem = quickAction.getActionItem(pos);
						if (actionId == 31)
						{
							saveAsImages();
						}
						else if (actionId == 32) 
						{
							saveAsPdf();
						}	
					}
				});
				quickAction.show(save);
				break;
			//erase all
			case 12:
				// below code sets current page to an empty array list of paths
				pointsToDraw.set(pageNo, new ArrayList<CWPath>());
				break;
			//foreground color
			case 13:
				draw_mode.callOnClick();
				AmbilWarnaDialog fcdialog = new AmbilWarnaDialog(Slate.this, foregroundColor, new OnAmbilWarnaListener() {
 	               @Override
				public void onOk(AmbilWarnaDialog dialog, int color) 
 	               {
 	                   
 	            	  SharedPreferences.Editor editor = preferences.edit();
 	            	  editor.putInt("fcolor", color);
 	            	  editor.commit();
 	            	  foregroundColor = color;
 	                }
 	                        
 	                //@Override
 	                @Override
					public void onCancel(AmbilWarnaDialog dialog) {
 	                       
 	                }
 	        });

 	        fcdialog.show();
				break;
			//background color
			case 14:
				AmbilWarnaDialog bcdialog = new AmbilWarnaDialog(Slate.this,backgroundColor, new OnAmbilWarnaListener() {
	 	               @Override
					public void onOk(AmbilWarnaDialog dialog, int color) 
	 	               {
	 	                  
	 	            	  SharedPreferences.Editor editor = preferences.edit();
	 	            	  editor.putInt("bcolor", color);
	 	            	  editor.commit();
	 	            	  backgroundColor = color;
	 	                }
	 	                        
	 	                //@Override
	 	                @Override
						public void onCancel(AmbilWarnaDialog dialog) {
	 	                        // cancel was selected by the user
	 	                }
	 	        });

	 	        bcdialog.show();
				break;
			
			//add new page
			case 15:
				pointsToDraw.add(new ArrayList<CWPath>());
				noOfPages++;
				//Toast.makeText(this, "New page has been added successfully.", Toast.LENGTH_SHORT).show();
				updatePagesInfo();
				break;
			//delete page
			case 16:
				if(noOfPages == 1)
				{
					//Toast.makeText(this, "You have only one page. It can't be deleted.", Toast.LENGTH_SHORT).show();
				}
				else
				{
					noOfPages--;
					pointsToDraw.remove(pageNo);
					if(pageNo != 0)
					{
						pageNo--;
						//Toast.makeText(this, "Page " + (pageNo+2) + " has been deleted successfully.", Toast.LENGTH_SHORT).show();
					}
					else
					{
						//Toast.makeText(this, "Page " + (pageNo+1) + " has been deleted successfully.", Toast.LENGTH_SHORT).show();
					}
				}
				updatePagesInfo();
				break;
			
			// help
			case 17:
				Intent i = new Intent("com.example.DiaSlate.HELPACTIVITY");
				startActivity(i);
			//menu
			case 21:
				p3 = m.getLayoutParams();
				p3.height = 0;
				m.setLayoutParams(p3);
				p2 = n.getLayoutParams();
				p2.width = vButtonSize;
				n.setLayoutParams(p2);
				p1 = l.getLayoutParams();
				p1.height = hButtonSize;
				l.setLayoutParams(p1);
				break;
			
			
		}
		
	}
	
	// below method validates file name
	public boolean validateFileName(String fileName)
	{
		if(fileName.equals(""))
			return false;
		else if(fileName.charAt(0) == ' ')
		{
			return false;
		}
		else if(fileName.contains("\n"))
		{
			return false;
		}
		else
			return true;
	}
	
	// below method updates pagesInfo textview text to current page number
	public void updatePagesInfo()
	{
		pagesInfo.setText("" + (pageNo+1) + "/" + noOfPages);
		pageScrollBar.setMax(noOfPages-1);
		pageScrollBar.setProgress(pageNo);
		
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View v) 
	{
		switch(v.getId())
		{
			//drawing width
			case 2:
				draw_mode.callOnClick();
				AlertDialog.Builder dwdialog  = new AlertDialog.Builder(Slate.this);
				dwdialog.setTitle("Drawing Width");
				final SeekBar dwseekbar = new SeekBar(this);
				dwseekbar.setProgress(drawingWidth);
				dwseekbar.setMax(100);
				dwdialog.setView(dwseekbar);
				dwdialog.setPositiveButton("ok", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int whichBtton)
					{
						SharedPreferences.Editor editor = preferences.edit();
	 	            	editor.putInt("dwidth", dwseekbar.getProgress());
	 	            	editor.commit();
	 	            	drawingWidth = dwseekbar.getProgress();
	 	            	
					}
				});
				dwdialog.setNegativeButton("cancel", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int whichBtton)
					{
						
					}
				});
				dwdialog.show();
				break;
			//eraser width
			case 3:
				erase_mode.callOnClick();
				 AlertDialog.Builder ewdialog  = new AlertDialog.Builder(Slate.this);
				ewdialog.setTitle("Eraser Width");
				
				final SeekBar ewseekbar = new SeekBar(this);
				
				ewseekbar.setProgress(eraserWidth);
				ewseekbar.setMax(100);
				ewdialog.setView(ewseekbar);
				ewdialog.setPositiveButton("ok", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int whichBtton)
					{
						SharedPreferences.Editor editor = preferences.edit();
	 	            	editor.putInt("ewidth", ewseekbar.getProgress());
	 	            	editor.commit();
	 	            	eraserWidth = ewseekbar.getProgress();
					}
				});
				ewdialog.setNegativeButton("cancel", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int whichBtton)
					{
						
					}
				});
				ewdialog.show();
				break;
		}
		return false;
	}
	
	/*
	 * this method is called when save button is clicked
	 * this method shows a popup menu 
	 * when user clicks on save as pdf savdAsPdf(View v) method will be called
	 * when user clicks on save as images savdAsImages(View v) method will be called
	 */
/*	public void showPopup(View view) 
	{
		pw = new PopupWindow(getApplicationContext());
		pw.setTouchable(true);
		pw.setFocusable(true);
		pw.setOutsideTouchable(true);
		pw.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					pw.dismiss();

					return true;
				}

				return false;
			}
		});
		pw.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		pw.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		pw.setOutsideTouchable(false);
		pw.setContentView(popupView);
		pw.showAsDropDown(view, 0, 0);
	}
*/
	/*
	 * below method shows a dialog box to take file name and validates that file name.
	 * If file name is valid, then it calls SaveAsPdf inner class to save as a PDF File 
	 */
	public void saveAsPdf()
	{
	//	final View view = v;
//		pw.dismiss();
		AlertDialog.Builder fileNameInputDialog = new AlertDialog.Builder(this);
		fileNameInputDialog.setTitle("FILE NAME");
		//alert.setMessage("enter file name to save as a pdf file");
		final EditText name = new EditText(this);
		fileNameInputDialog.setView(name);
		fileNameInputDialog.setPositiveButton("save", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int whichButton)
			{
				 file = name.getText().toString();
				boolean isNameOk = validateFileName(file);
				if(isNameOk)
				{
					 
					File direct = new File(folder);
			        if(!direct.exists())
			         {
			        	 Toast.makeText(Slate.this, "directory is not present" , Toast.LENGTH_SHORT).show();
			             if(direct.mkdir())
			             {
			            	 Toast.makeText(Slate.this, "directory  created" , Toast.LENGTH_SHORT).show();
			            	 //directory is created;
			             }
			             else
			             {
			            	 Toast.makeText(Slate.this, "unable to create directory" , Toast.LENGTH_SHORT).show();
			            	 
			             }

			         }
			        boolean isAFileWithSameNameAlreadyExist = false;
			        FileInputStream fis = null;
			        try
			        {
			        	fis = new FileInputStream(folder+"/" + file + ".pdf");
			        	isAFileWithSameNameAlreadyExist = true;
			        	fis.close();
			        }
			        catch(FileNotFoundException e)
			        {
			        	
			        }
			        catch(IOException e)
			        {
			        	
			        }
			        if(!isAFileWithSameNameAlreadyExist)
			        {
			        	new SaveAsPdf().execute();
			        	
			        }
			        else
			        {
						AlertDialog.Builder fileOverWriteDialog  = new AlertDialog.Builder(Slate.this);
						fileOverWriteDialog.setMessage("Another file already exists with name " + file + ".pdf\nDo you want to overwrite it?");
						fileOverWriteDialog.setPositiveButton("ok", new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int whichBtton)
							{
								new SaveAsPdf().execute();
							}
						});
						fileOverWriteDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int whichBtton)
							{
								saveAsPdf();
							}
						});
						fileOverWriteDialog.show();
			       }
				}
				else
				{
					AlertDialog.Builder invalidFileNameDialog  = new AlertDialog.Builder(Slate.this);
					invalidFileNameDialog.setTitle("Invalid File Name");
					TextView rules = new TextView(Slate.this);
					rules.setText("1. Empty name is not allowed.\n2. Space at the beginning of a file name is not allowed.\n3. New line in filname is not allowed.");
					invalidFileNameDialog.setView(rules);
					invalidFileNameDialog.setPositiveButton("ok", new DialogInterface.OnClickListener()
					{
						@Override
						@SuppressLint("NewApi")
						public void onClick(DialogInterface dialog, int whichBtton)
						{
							saveAsPdf();
						}
					});
					invalidFileNameDialog.show();
					
				}
			}
		});
		fileNameInputDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int whichBtton)
			{
				
			}
		});
		fileNameInputDialog.show();
		
	}
	
	/*
	 * below method shows a dialog box to take file name and validates that file name.
	 * If file name is valid, then it call SaveAsImages inner class to save as images 
	 */
	public void saveAsImages()
	{
	
	
		AlertDialog.Builder fileNameInputDialog = new AlertDialog.Builder(this);
		fileNameInputDialog.setTitle("FILE NAME");
		//alert.setMessage("enter file name to save as a pdf file");
		final EditText name = new EditText(this);
		fileNameInputDialog.setView(name);
		
		fileNameInputDialog.setPositiveButton("save", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int whichButton)
			{
				 file = name.getText().toString();
				boolean isNameOk = validateFileName(file);
				if(isNameOk)
				{
					 
					File direct = new File(folder);
			        if(!direct.exists())
			         {
			        	 Toast.makeText(Slate.this, "directory is not present" , Toast.LENGTH_SHORT).show();
			             if(direct.mkdir())
			             {
			            	 Toast.makeText(Slate.this, "directory  created" , Toast.LENGTH_SHORT).show();
			            	 //directory is created;
			             }
			             else
			             {
			            	 Toast.makeText(Slate.this, "unable to create directory" , Toast.LENGTH_SHORT).show();
			            	 
			             }

			         }
			        boolean isAFileWithSameNameAlreadyExist = false;
			        FileInputStream fis = null;
			        try
			        {
			        	fis = new FileInputStream(folder+"/" + file + "1" + ".png");
			        	isAFileWithSameNameAlreadyExist = true;
			        	fis.close();
			        }
			        catch(FileNotFoundException e)
			        {
			        	
			        }
			        catch(IOException e)
			        {
			        	
			        }
			        if(!isAFileWithSameNameAlreadyExist)
			        {
			        	new SaveAsImages().execute();
			        	
			        }
			        else
			        {
						AlertDialog.Builder fileOverWriteDialog  = new AlertDialog.Builder(Slate.this);
						fileOverWriteDialog.setMessage("Some images are already stored with name " + file + "\nDo you want to overwrite them?");
						fileOverWriteDialog.setPositiveButton("ok", new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int whichBtton)
							{
								new SaveAsImages().execute();
							}
						});
						fileOverWriteDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int whichBtton)
							{
								saveAsImages();
							}
						});
						fileOverWriteDialog.show();
			       }
				}
				else
				{
					AlertDialog.Builder invalidFileNameDialog  = new AlertDialog.Builder(Slate.this);
					invalidFileNameDialog.setTitle("Invalid File Name");
					TextView rules = new TextView(Slate.this);
					rules.setText("1. Empty name is not allowed.\n2. Space at the beginning of a file name is not allowed.\n3. New line in filname is not allowed.");
					invalidFileNameDialog.setView(rules);
					invalidFileNameDialog.setPositiveButton("ok", new DialogInterface.OnClickListener()
					{
						@Override
						@SuppressLint("NewApi")
						public void onClick(DialogInterface dialog, int whichBtton)
						{
							saveAsImages();
						}
					});
					invalidFileNameDialog.show();
					
				}
			}
		});
		fileNameInputDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int whichBtton)
			{
				
			}
		});
		fileNameInputDialog.show();
	}
	
	@Override
	public void onBackPressed()
	{
		finish();
	}
}
