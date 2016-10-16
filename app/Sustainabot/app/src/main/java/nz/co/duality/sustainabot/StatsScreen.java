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

    byte[] response = new byte[] {0, 0, 0, 0, 0, 0, 0, 0};
    int total_energy_points = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_screen);

        total_energy = (TextView) findViewById(R.id.maxpoints_TextView);
        top_speed = (TextView) findViewById(R.id.topspeedPoints);
        reverse_speed = (TextView) findViewById(R.id.revspeedPoints);
        acceleration = (TextView) findViewById(R.id.accelerationPoints);
        turn_speed = (TextView) findViewById(R.id.turnspeedPoints);
        kick_power = (TextView) findViewById(R.id.kickpowerPoints);
        auto_aim = (TextView) findViewById(R.id.autoaimPoints);

        auto_aim_box = (CheckBox) findViewById(R.id.autoaim_checkbox);
        auto_aim_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (total_energy_points > 35) {
                        auto_aim_box.setChecked(false);
                    } else {
                        total_energy_points += 15;
                        total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
                        response[5] = 1;
                        auto_aim.setText("15 / 15");
                    }
                } else {
                    if (response[5] == 1) {
                        response[5] = 0;
                        auto_aim.setText("0 / 15");
                        total_energy_points -= 15;
                        total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
                    }
                }
            }
        });
    }

    public void inc_topspeed(View view) {
        if (response[0] < 15 && total_energy_points < 50) {
            response[0]++;
            total_energy_points++;
            top_speed.setText(Integer.toString(response[0]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void dec_topspeed(View view) {
        if (response[0] > 0 && total_energy_points > 0) {
            response[0]--;
            total_energy_points--;
            top_speed.setText(Integer.toString(response[0]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void dec_revspeed(View view) {
        if (response[1] > 0) {
            response[1]--;
            total_energy_points--;
            reverse_speed.setText(Integer.toString(response[1]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void inc_revspeed(View view) {
        if (response[1] < 15 && total_energy_points < 50) {
            response[1]++;
            total_energy_points++;
            reverse_speed.setText(Integer.toString(response[1]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void dec_acceleration(View view) {
        if (response[2] > 0) {
            response[2]--;
            total_energy_points--;
            acceleration.setText(Integer.toString(response[2]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void inc_acceleration(View view) {
        if (response[2] < 15 && total_energy_points < 50) {
            response[2]++;
            total_energy_points++;
            acceleration.setText(Integer.toString(response[2]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void dec_turnspeed(View view) {
        if (response[3]  > 0) {
            response[3]--;
            total_energy_points--;
            turn_speed.setText(Integer.toString(response[3]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void inc_turnspeed(View view) {
        if (response[3] < 15 && total_energy_points < 50) {
            response[3]++;
            total_energy_points++;
            turn_speed.setText(Integer.toString(response[3]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void dec_kickpower(View view) {
        if (response[4] > 0) {
            response[4]--;
            total_energy_points--;
            kick_power.setText(Integer.toString(response[4]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void inc_kickpower(View view) {
        if (response[4] < 15 && total_energy_points < 50) {
            response[4]++;
            total_energy_points++;
            kick_power.setText(Integer.toString(response[4]) + "/ 15");
            total_energy.setText(Integer.toString(total_energy_points) + "/ 50");
        }
    }

    public void save(View view) {
        Intent returnIntent = getIntent();
        String responseString = new String(response);
        returnIntent.putExtra("response", responseString);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
