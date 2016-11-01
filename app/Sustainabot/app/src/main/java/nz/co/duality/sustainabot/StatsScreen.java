package nz.co.duality.sustainabot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;


public class StatsScreen extends AppCompatActivity {

    TextView total_energy;
    TextView top_speed;
    TextView reverse_speed;
    TextView acceleration;
    TextView turn_speed;
    TextView kick_power;
    TextView auto_aim;
    CheckBox auto_aim_box;

    byte[] response;

    int total_energy_points = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_screen);

        total_energy = (TextView) findViewById(R.id.maxpoints_TextView);
        top_speed = (TextView) findViewById(R.id.topspeedPoints);
        reverse_speed = (TextView) findViewById(R.id.revspeedPoints);
        turn_speed = (TextView) findViewById(R.id.turnspeedPoints);
        kick_power = (TextView) findViewById(R.id.kickpowerPoints);
        auto_aim = (TextView) findViewById(R.id.autoaimPoints);

        auto_aim_box = (CheckBox) findViewById(R.id.autoaim_checkbox);
        auto_aim_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (total_energy_points > 25) {
                        auto_aim_box.setChecked(false);
                    } else {
                        total_energy_points += 15;
                        total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
                        response[4] = 16;
                        auto_aim.setText("15 / 15");
                    }
                } else {
                    if (response[4] == 16) {
                        response[4] = 1;
                        auto_aim.setText("0 / 15");
                        total_energy_points -= 15;
                        total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
                    }
                }
            }
        });

        response = getIntent().getByteArrayExtra("START_STATS");
        total_energy_points = -1;
        for (byte i : response)
            total_energy_points += i;
        total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
        top_speed.setText(Byte.toString(response[0]) + "/ 15");
        reverse_speed.setText(Byte.toString(response[1]) + "/ 15");
        turn_speed.setText(Byte.toString(response[2]) + "/ 15");
        kick_power.setText(Byte.toString(response[3]) + "/ 15");
        if (response[4] == 16) {
            auto_aim.setText("15 / 15");
        } else {
            auto_aim.setText("0 / 15");
        }
    }

    public void inc_topspeed(View view) {
        if (response[0] < 15 && total_energy_points < 40) {
            response[0]++;
            total_energy_points++;
            top_speed.setText(Integer.toString(response[0]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
        }
    }

    public void dec_topspeed(View view) {
        if (response[0] > 1 && total_energy_points > 0) {
            response[0]--;
            total_energy_points--;
            top_speed.setText(Integer.toString(response[0]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
        }
    }

    public void dec_revspeed(View view) {
        if (response[1] > 1) {
            response[1]--;
            total_energy_points--;
            reverse_speed.setText(Integer.toString(response[1]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
        }
    }

    public void inc_revspeed(View view) {
        if (response[1] < 15 && total_energy_points < 40) {
            response[1]++;
            total_energy_points++;
            reverse_speed.setText(Integer.toString(response[1]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
        }
    }

    public void dec_turnspeed(View view) {
        if (response[2]  > 1) {
            response[2]--;
            total_energy_points--;
            turn_speed.setText(Integer.toString(response[3]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
        }
    }

    public void inc_turnspeed(View view) {
        if (response[2] < 15 && total_energy_points < 40) {
            response[2]++;
            total_energy_points++;
            turn_speed.setText(Integer.toString(response[3]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
        }
    }

    public void dec_kickpower(View view) {
        if (response[3] > 1) {
            response[3]--;
            total_energy_points--;
            kick_power.setText(Integer.toString(response[4]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
        }
    }

    public void inc_kickpower(View view) {
        if (response[3] < 15 && total_energy_points < 40) {
            response[3]++;
            total_energy_points++;
            kick_power.setText(Integer.toString(response[4]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 40");
        }
    }

    public void save(View view) {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("response", response);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
