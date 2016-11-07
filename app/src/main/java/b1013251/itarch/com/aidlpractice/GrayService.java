package b1013251.itarch.com.aidlpractice;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;

import java.util.List;

import static java.lang.System.in;

public class GrayService extends Service {
//    private IStaticticsService.Stub stab =
//
//    };
    private IStaticticsService.Stub stub
        = new IStaticticsService.Stub() {
        @Override
        public double mean(List numList) {
            int l = 0;
            for(Object num: numList) {
                double doubleNum = (double)num;
                l += doubleNum;
            }
            return l / numList.size();
        }

        @Override
        public double variance(List numList) {
            double mean = this.mean(numList);

            int l = 0;
            for(Object num: numList) {
                double doubleNum = (double)num;
                l += (doubleNum - mean) * (doubleNum - mean);
            }
            return l / numList.size();
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
