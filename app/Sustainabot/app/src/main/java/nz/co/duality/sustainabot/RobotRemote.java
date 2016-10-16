package nz.co.duality.sustainabot;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class RobotRemote extends AppCompatActivity {

    SeekBar horiBar;
    SeekBar vertBar;

    BluetoothSPP mBt;
    static final int CHANGE_STATS = 26;

    boolean btConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_remote);

        // set custom seekbar listeners to send bt packets
        horiBar = (SeekBar) findViewById(R.id.seekBarHori);
        vertBar = (SeekBar) findViewById(R.id.seekBarVert);
        horiBar.setOnSeekBarChangeListener(handleSeekBarChange);
        vertBar.setOnSeekBarChangeListener(handleSeekBarChange);

        // initialize the bt context
        mBt = new BluetoothSPP(getApplicationContext());

        setupBluetooth();
    }

    private void setupBluetooth() {
        btConnected = false;
        if (!mBt.isBluetoothAvailable()) {
            DialogFragment newFragment = new MissingBluetoothDialogFragment();
            newFragment.show(getSupportFragmentManager(), "noBluetooth");
        }
        if (!mBt.isBluetoothEnabled()) {
            DialogFragment newFragment = new MissingBluetoothDialogFragment();
            newFragment.show(getSupportFragmentManager(), "noBluetooth");
        }

        mBt.setupService();
        mBt.startService(BluetoothState.DEVICE_OTHER);

        final Intent intent = new Intent(getApplicationContext(), DeviceList.class);
        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);

        mBt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) {
                btConnected = true;
                Toast.makeText(RobotRemote.this, "Bot Connected!", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() {
                btConnected = false;
                Toast.makeText(RobotRemote.this, "Bot Disconnected", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
            }

            public void onDeviceConnectionFailed() {
                btConnected = false;
                Toast.makeText(RobotRemote.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                mBt.connect(data);
                btConnected = true;
            }
        }  else if (requestCode == CHANGE_STATS) {
            if (resultCode == Activity.RESULT_OK) {
                if (btConnected) {
                    mBt.send(new byte[] {(byte) 255, 1, 10, 100, 0}, false);
                    mBt.send(data.getStringExtra("response"), false);
                    mBt.send(new byte[] {(byte) 255, 100, 10, 1, 0}, true);
                }
            }
        }
    }

    private SeekBar.OnSeekBarChangeListener handleSeekBarChange =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (btConnected)
                        mBt.send(new byte[] {1,
                                             (byte) (vertBar.getProgress() + 1),
                                             (byte) (horiBar.getProgress() + 1),
                                             0}, false);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    seekBar.setProgress(127);
                }
            };

    public void kick(View view) {
        if (btConnected)
            mBt.send(new byte[] {2, 0}, false);
    }

    public void changeStats(View view) {
        if (btConnected) {
            Intent intent = new Intent(getApplicationContext(), StatsScreen.class);
            startActivityForResult(intent, CHANGE_STATS);
        } else {
            Toast.makeText(this, "Connect your device first!", Toast.LENGTH_SHORT).show();
        }
    }
}
