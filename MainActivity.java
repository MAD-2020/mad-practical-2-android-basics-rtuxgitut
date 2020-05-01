package sg.edu.np.WhackAMole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import com.example.myapplication.R;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - Feel free to modify the function to suit your program.
    */
     //Declare set of variables
    private static final String TAG = "Whack-A-Mole"; //Title of game

    private TextView resultViewer; //Allow program to see result of the whack a mole
    private Button firstbutton; //Individually declare all 3 buttons
    private Button secondbutton;
    private Button thirdbutton;
    private List<Button> holeList = new ArrayList<>(); //List to store all 3 buttons
    private Integer randomisedLocation; //The "mole"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultViewer = (TextView)findViewById(R.id.resultViewer);
        firstbutton = (Button)findViewById(R.id.hole1);
        holeList.add(firstbutton);
        secondbutton = (Button)findViewById(R.id.hole2);
        holeList.add(secondbutton);
        thirdbutton = (Button)findViewById(R.id.hole3);
        holeList.add(thirdbutton);
        Log.v(TAG, "Finished Pre-Initialisation!");
    }

    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        
        View.OnClickListener clicker = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button pressedButton = (Button) v;
                Log.v(TAG,"Reached");
                switch (holeList.indexOf(pressedButton)) {
                    case 0:
                        Log.v(TAG,"Left Button Clicked!");
                        break;
                    case 1:
                        Log.v(TAG,"Middle Button Clicked!");
                        break;
                    case 2:
                        Log.v(TAG,"Right Button Clicked!");
                        break;
                    default:
                        Log.v(TAG,"No input found.");
                }

                Integer score = Integer.parseInt(resultViewer.getText().toString());
                switch (pressedButton.getText().toString()) {
                    case "*": //
                        Log.v(TAG,"Successful, points added!");
                        score = score + 1;
                        resultViewer.setText(score.toString());
                        break;
                    case "O":
                        if (score <= 0)
                        {
                            Log.v(TAG,"Reminder: To score points hit the button with the '*' in it");
                            score = 0; //Remove the occurrence of a negative number if player fails to hit target while score is at 0
                        }
                        else
                        {
                            Log.v(TAG,"Unsuccessful, points deducted!");
                            score = score - 1; //Works as normal if user gained points beforehand
                        }
                        resultViewer.setText(score.toString());
                        break;
                    default: //'Starting game' instructions. Will be overridden after user hits target for the 1st time when he launches the program
                        Log.v(TAG,"To score points hit the button with the '*' in it!");
                }
                holeList.get(randomLocation).setText("O"); //Set all other holes as distractions
                setNewMole();
            }
        };
        firstbutton.setOnClickListener(clicker);
        secondbutton.setOnClickListener(clicker);
        thirdbutton.setOnClickListener(clicker);
        
        Log.v(TAG, "Starting GUI!");
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG,"Pausing the game.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG,"Resuming the game.");
    }

    @Override //Removes all points collected when exiting the file
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG,"Exiting the game.");
    }

    public void setNewMole()
    {
        Random ran = new Random();
        randomLocation = ran.nextInt(3);
        holeList.get(randomLocation).setText("*"); //Set mole in a random hole
    }
}
