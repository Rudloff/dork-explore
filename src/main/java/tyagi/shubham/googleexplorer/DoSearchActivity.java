package tyagi.shubham.googleexplorer;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.CheckBox;
import android.widget.EditText;

public class DoSearchActivity extends AppCompatActivity {
    private static final String GOOGLE_IMAGE_SEARCH_URL = "https://www.google.com/search?tbm=isch&q=";
    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=";
    private boolean[] checkboxCheck = new boolean[]{false, false, false, false, false, false, false};
    private String[] extensionArray = new String[]{"", "mkv|mp4|avi|mov|mpg|wmv|3gp|", "mp3|wav|ac3|ogg|flac|wma|m4a|", "jpg|png|bmp|gif|tif|tiff|psd|", "gif|", "MOBI|CBZ|CBR|CBC|CHM|EPUB|FB2|LIT|LRF|ODT|PDF|PRC|PDB|PML|RB|RTF|TCR|DOC|DOCX|", "exe|iso|tar|rar|zip|apk|"};
    private static final String inUrlPages=" -inurl:(jsp|pl|php|html|aspx|htm|cf|shtml) intitle:index.of";
    private static final String inUrlAudio=" -inurl:(listen77|mp3raid|mp3toss|mp3drug|index_of|wallywashis) ";
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_search);
        searchText=(EditText)findViewById(R.id.search);

        searchText.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent event) {
               if (event.getAction() != 1 || event.getRawX() < ((float) (searchText.getRight() - searchText.getCompoundDrawables()[2].getBounds().width()))) {
                   return false;
               }
               String url = "";
               String query = searchText.getText().toString();
               String extensions = "";
               int totalCheckedBoxes = 0;
               boolean imageChecked = false;

               for (int i = 1; i < extensionArray.length; i++) {
                   if (checkboxCheck[i]) {
                       extensions = extensions + extensionArray[i];
                       totalCheckedBoxes++;
                       if (i == 3) {
                           imageChecked = true;
                       }
                   }
               }
               if (extensions.length() > 0) {
                   extensions = extensions.substring(0, extensions.length() - 1);
               }
               if (query.trim().length() != 0) {
                   if (checkboxCheck[0] || extensions.length() == 0) {
                       url=url+query+inUrlPages;
                       if(checkboxCheck[2]) {
                           url = url + inUrlAudio;
                       }

                   }else {
                       url = url + query + " +(" + extensions + ")"+inUrlPages;
                       if(checkboxCheck[2]){
                           url = url + inUrlAudio;
                       }

                   }
                   url = Uri.encode(url);
                   if (totalCheckedBoxes == 1 && imageChecked) {
                       DoSearchActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(GOOGLE_IMAGE_SEARCH_URL + url)));
                   } else {
                       DoSearchActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(GOOGLE_SEARCH_URL + url)));
                   }
               }
               return true;

           }
       });
    }


    public void checkboxClick(View v) {
        CheckBox checkBox = (CheckBox) v;
        int index = Integer.parseInt(checkBox.getTag().toString());
        this.checkboxCheck[index] = checkBox.isChecked();
    }





    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share) {
            Intent sharingIntent = new Intent("android.intent.action.SEND");
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra("android.intent.extra.SUBJECT", "GoogleExplorer App : For downloading Movies,TV Series etc.");
            sharingIntent.putExtra("android.intent.extra.TEXT", "Download it from www.geocities.ws/shubhamtyagi");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;


        }
        if (id == R.id.about) {
             AlertDialog.Builder builder ;
            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
                builder=new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert);
            }else{
                builder=new AlertDialog.Builder(this);
            }
            builder.setTitle("About me");
            builder.setMessage("This app is made by Shubham tyagi(www.geocities.ws/shubhamtyagi)").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).setIcon(android.R.drawable.ic_dialog_info).show();

        }
        return super.onOptionsItemSelected(item);
    }

}
