package mg.studio.pedometer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    private Context mContext;
    private int choose;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

    }

    public void btnchange(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //通过布局填充器获login_layout
        View view2 = view.inflate(this, R.layout.dialog, null);
        builder.setView(view2);
        final AlertDialog dlg = builder.create();
        final SeekBar seekbar;
        final TextView description;
        final TextView now;
        final Button ok;
        final Button can;
        SharedPreferences sharedPreferences = getSharedPreferences("choose", MODE_PRIVATE);
        choose=Integer.parseInt(sharedPreferences.getString("progress","0"));
        ok = (Button) view2.findViewById(R.id.success);//拿到控件实例
        can = (Button) view2.findViewById(R.id.fail);//拿到控件实例
        seekbar = (SeekBar) view2.findViewById(R.id.progress);//拿到控件实例
        seekbar.setProgress(choose);
        description = (TextView) view2.findViewById(R.id.xy);//拿到控件实例
        now = (TextView) view2.findViewById(R.id.cho);//拿到控件实例
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {


            }

            /**
             * 拖动条开始拖动的时候调用
             */

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * 拖动条进度改变的时候调用
             */

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                choose=progress;
                now.setText(Integer.toString(choose));
            }
        });
        ok.setOnClickListener(new Button.OnClickListener() {
                                  public void onClick(View view) {
                                      SharedPreferences choo= getSharedPreferences("choose", MODE_PRIVATE);
                                      SharedPreferences.Editor editor = choo.edit();
                                      editor.putString("progress", Integer.toString(choose));
                                      editor.commit();
                                      dlg.cancel();
                                  }
                              });
        can.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                dlg.cancel();
            }
        });
        dlg.show();

    }


}
