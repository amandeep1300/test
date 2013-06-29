package com.example.DiaSlate;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toast;

public class FileList extends ListActivity 
{
private File file;
private List<String> myList;
//File imageFile;
static String imageFilePath;

String androidpaintdirectory;

@Override
public void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);

    myList = new ArrayList<String>();   

    String root_sd = Environment.getExternalStorageDirectory().toString();
    androidpaintdirectory = root_sd + "/androidpaint";
    //file = new File( root_sd + "/androidpaint" ) ;
    file = new File(androidpaintdirectory); 
    File list[] = file.listFiles();

    for( int i=0; i< list.length; i++)
    {
            myList.add( list[i].getName() );
    }

    setListAdapter(new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, myList ));

}

@Override
protected void onListItemClick(ListView l, View v, int position, long id) 
{
    super.onListItemClick(l, v, position, id);
    
    //imageFile = new File(file, myList.get(position));
    
    File temp_file = new File( file, myList.get( position ) ); 
    
    

    if( !temp_file.isFile())        
    {
        file = new File( file, myList.get( position ));
        File list[] = file.listFiles();

        myList.clear();

        for( int i=0; i< list.length; i++)
        {
            myList.add( list[i].getName() );
        }
        //Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_LONG).show(); 
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myList ));
        
    }
    
    else if(StartingActivity.isToBeViewd == true){
    	 String fileName = temp_file.getName();
    	 imageFilePath = androidpaintdirectory + "/" + fileName;
    	 Intent i = new Intent();
	    i.setClassName("com.example.DiaSlate", "com.example.DiaSlate.viewImageActivity");
	    startActivity(i); 
    }
    else{	
    	String fileName = temp_file.getName();
        imageFilePath = androidpaintdirectory + "/" + fileName;
    	 Intent i = new Intent();
	    i.setClassName("com.example.DiaSlate", "com.example.DiaSlate.MainActivity");
	    startActivity(i);
    }
   

}

/*
@Override
public void onBackPressed() {
            String parent = file.getParent().toString();
            file = new File( parent ) ;         
            File list[] = file.listFiles();

            myList.clear();

            for( int i=0; i< list.length; i++)
            {
                myList.add( list[i].getName() );
            }
            //Toast.makeText(getApplicationContext(), parent,Toast.LENGTH_LONG).show(); 
            setListAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, myList ));
		Intent i = new Intent();
		i.setClassName("com.example.trial", "com.example.trial.StartingActivity");
		startActivity(i);
		
}
		*/
}
