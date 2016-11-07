package b1013251.itarch.com.aidlpractice;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import static java.lang.System.in;

public class StaticticsService extends Service {
//    private IStaticticsService.Stub stab =
//
//    };
    private IStaticticsService.Stub stub
        = new IStaticticsService.Stub() {
        @Override
        public double mean(double[] numList) {
            double l = 0;
            for(Object num: numList) {
                double doubleNum = (double)num;
                l += doubleNum;
            }

            Log.d("debug", Double.toString(l / numList.length));
            return l / numList.length;
        }

        @Override
        public double variance(double[] numList) {
            double mean = this.mean(numList);

            double l = 0;
            for(Object num: numList) {
                double doubleNum = (double)num;
                l += (doubleNum - mean) * (doubleNum - mean);
            }

            Log.d("debug", Double.toString(l / numList.length));
            return l / numList.length;
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        Log.d("myDebug", "called IBinder");
        return stub;
    }
}
