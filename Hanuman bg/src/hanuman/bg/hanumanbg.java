package hanuman.bg;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class hanumanbg extends Activity {
	private Button buttonPlayStop;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    
    private final Handler handler = new Handler();
 
    // Here i override onCreate method.
    //
    // setContentView() method set the layout that you will see then
    // the application will starts
    //
    // initViews() method i create to init views components.
    @Override
    public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            setContentView(R.layout.main);
            ImageView imggallry=(ImageView)findViewById(R.id.btn_gallery);
            imggallry.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
				Intent in=new Intent(hanumanbg.this,galleryact.class);
				startActivity(in);
				
					
				}
			});
            
            initViews();  
            
            
            
 
    }
 
    // This method set the setOnClickListener and method for it (buttonClick())
    private void initViews() {
        buttonPlayStop = (Button) findViewById(R.id.ButtonPlayStop);
        buttonPlayStop.setOnClickListener(new OnClickListener() {public void onClick(View v) {buttonClick();}});
 
        mediaPlayer = MediaPlayer.create(this, R.raw.chalisa); 
 
        seekBar = (SeekBar) findViewById(R.id.SeekBar01);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnTouchListener(new OnTouchListener() {public boolean onTouch(View v, MotionEvent event) {
        	seekChange(v);
			return false; }
		});
 
    }
    
    public void startPlayProgressUpdater() {
    	seekBar.setProgress(mediaPlayer.getCurrentPosition());
    	
		if (mediaPlayer.isPlaying()) {
			Runnable notification = new Runnable() {
		        public void run() {
		        	startPlayProgressUpdater();
				}
		    };
		    handler.postDelayed(notification,1000);
    	}else{
    		mediaPlayer.pause();
    		buttonPlayStop.setText(getString(R.string.play_str));
    		seekBar.setProgress(0);
    	}
    } 
 
    // This is event handler thumb moving event
    private void seekChange(View v){
    	if(mediaPlayer.isPlaying()){
	    	SeekBar sb = (SeekBar)v;
			mediaPlayer.seekTo(sb.getProgress());
		}
    }
 
    // This is event handler for buttonClick event
    private void buttonClick(){
        if (buttonPlayStop.getText() == getString(R.string.play_str)) {
            buttonPlayStop.setText(getString(R.string.pause_str));
            try{
            	mediaPlayer.start();
                startPlayProgressUpdater(); 
            }catch (IllegalStateException e) {
            	mediaPlayer.pause();
            }
        }else {
            buttonPlayStop.setText(getString(R.string.play_str));
            mediaPlayer.pause();
        }
    }
}