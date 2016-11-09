package b1013251.itarch.com.aidlpractice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private IStaticticsService service;
    private Button calcButton;
    private TextView meanText;
    private TextView varianceText;
    private TextView listText;

    private double[] list = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};

    private ServiceConnection serviceConnection =
            new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder ibinder) {
                    service = IStaticticsService.Stub.asInterface(ibinder);
                    Log.d("myDebug", "Service has connected.");
                    calcButton.setEnabled(true);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    service = null;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("myDebug", "Launched.");

        // レイアウトに対応付け
        calcButton = (Button)findViewById(R.id.calcButton);
        calcButton.setEnabled(false);
        calcButton.setOnClickListener(this);

        meanText = (TextView)findViewById(R.id.meanText);
        varianceText = (TextView)findViewById(R.id.varianceText);
        listText = (TextView)findViewById(R.id.listText);

        //リストをテキストとして表示
        StringBuilder builder = new StringBuilder("");
        for(double num : list) {
            builder.append((int)num);
            builder.append(" ");
        }
        listText.setText(builder.toString());

        //サービス呼び出し
        Intent intent = new Intent(IStaticticsService.class.getName());
        bindService(intent.setPackage("b1013251.itarch.com.aidlpractice"),
                serviceConnection, BIND_AUTO_CREATE);
        Log.d("myDebug", "Binded.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    public void onClick(View view)  {
        Log.d("myDebug", "clicked Button.");

        // AIDLで計算
        try {
            double mean = service.mean(list);
            meanText.setText("mean: " + Double.toString(mean));

            double variance = service.variance(list);
            varianceText.setText("variance: " + Double.toString(variance));

        } catch (RemoteException e) {
         e.printStackTrace();
        }
    }
}
