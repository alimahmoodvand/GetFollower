package ir.alimahmoodvan.getfollower;


import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import AsyncTask.SettingAsync;
import I4A.Auth;
import I4A.I4A;
import ListViewAdapter.CommentAdapter;
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;

public class SettingActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<String> chips=new ArrayList<>();
    ArrayList<String> comment=new ArrayList<>();
    public static String TAG="SettingActivityTAG";
    AppCompatButton sourceButton;
    AppCompatButton commentButton;
    AppCompatButton actionButton;
    AppCompatButton otherButton;
    NachoTextView sourceNacho;
    private LinearLayout sourceLayout;
    private LinearLayout commentLayout;

    private RecyclerView commentList;
    private AppCompatEditText commentText;
    private ImageButton commentAddButton;
    private TextInputLayout commentLayoutText;
    private LinearLayout actionLayout;
    private AppCompatCheckBox followCheckBox;
    private AppCompatCheckBox likeCheckBox;
    private AppCompatCheckBox commentCheckBox;
    private AppCompatCheckBox privateCheck;
    private LinearLayout otherLayout;
    private RangeBar unfollowRange;
    private RangeBar fetchRange;
    private TextView unfollowText;
    private TextView fetchText;
    private FloatingActionButton fab;
    private ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pbar = findViewById(R.id.pbar);
        //region Comments

        comment.add("like");
        comment.add("nice");
        comment.add("good");

        final CommentAdapter commentAdapter=new CommentAdapter(comment);
        commentButton=findViewById(R.id.comment_btn);
        commentButton.setOnClickListener(selectButton);
        commentLayout=findViewById(R.id.comment_lyt);
        commentLayoutText=findViewById(R.id.comment_lyt_txt);
        commentText=findViewById(R.id.comment_inp_txt);
        commentAddButton=findViewById(R.id.comment_add_btn);
        commentAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(commentText.getText().toString().isEmpty()){
                    commentLayoutText.setErrorEnabled(true);
                    commentLayoutText.setError("enter comment");
                }else {
                    commentLayoutText.setErrorEnabled(false);
                    commentAdapter.addItem(commentText.getText().toString());
                }
            }
        });


        commentList=findViewById(R.id.comment_list);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        commentList.setLayoutManager(llm);
        commentList.setHasFixedSize(true);
        commentList.setAdapter(commentAdapter);
        //endregion
        //region Source
        sourceButton=findViewById(R.id.source_btn);
        sourceButton.setOnClickListener(selectButton);
        sourceLayout=findViewById(R.id.source_lyt);
        sourceNacho=findViewById(R.id.source_nacho);
        sourceNacho.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
        sourceNacho.setOnChipClickListener(new NachoTextView.OnChipClickListener() {
            @Override
            public void onChipClick(Chip chip, MotionEvent motionEvent) {
                Log.d(TAG, "onChipClick: "+chip);
            }
        });
        //endregion
        //region Action
        actionButton=findViewById(R.id.action_btn);
        actionButton.setOnClickListener(selectButton);
        actionLayout=findViewById(R.id.action_lyt);
        followCheckBox=findViewById(R.id.action_follow_chkbox) ;
        likeCheckBox=findViewById(R.id.action_like_chkbox) ;
        commentCheckBox=findViewById(R.id.action_comment_chkbox);
        //endregion
        //region Other
        otherButton=findViewById(R.id.other_btn);
        otherLayout=findViewById(R.id.other_lyt);
        otherButton.setOnClickListener(selectButton);
        privateCheck=findViewById(R.id.other_private_chkbox);
        unfollowRange=findViewById(R.id.unfollow_rng);
        fetchRange=findViewById(R.id.fetch_rng);
        unfollowText=findViewById(R.id.unfollow_txt);
        fetchText=findViewById(R.id.fetch_txt);

        unfollowRange.setSeekPinByIndex(0);
        unfollowRange.setPinColor(getResources().getColor(R.color.colorRangeBarPin));
        unfollowRange.setBarColor(getResources().getColor(R.color.colorInfo));
        unfollowRange.setTickColor(getResources().getColor(R.color.colorRangeBarPin));
        unfollowRange.setSelectorColor(getResources().getColor(R.color.colorInfo));
        unfollowRange.setConnectingLineColor(getResources().getColor(R.color.colorRangeBarPin));
        unfollowRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                unfollowText.setText(getResources().getString(R.string.other_unfollow)+" : "+rightPinValue);
            }
        });
        fetchRange.setSeekPinByIndex(0);
        fetchRange.setPinColor(getResources().getColor(R.color.colorRangeBarPin));
        fetchRange.setBarColor(getResources().getColor(R.color.colorInfo));
        fetchRange.setTickColor(getResources().getColor(R.color.colorRangeBarPin));
        fetchRange.setSelectorColor(getResources().getColor(R.color.colorInfo));
        fetchRange.setConnectingLineColor(getResources().getColor(R.color.colorRangeBarPin));
        fetchRange.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                fetchText.setText(getResources().getString(R.string.other_fetch)+" : "+rightPinValue +"K");
            }
        });

        //endregion
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> data = new HashMap<>();
//                let acc= {
//                        source:cleanArray(document.getElementById('sources').value.split("\n")),
//                        private:document.getElementById('private').checked,
//                        twicecomment:document.getElementById('twicecomment').checked,
//                        unfollow:getRadioValue('unfollow','no'),
//                        hours:document.getElementById('hours').value,
//                        limit:document.getElementById('limit').value,
//                        minimumFollower:document.getElementById('minimumFollower').value,
//                        actions:getCheckValue('actions'),
//                        comments:getCheckValue('select-comment'),
//                        _id:account._id
//        };
                try {
                    pbar.setVisibility(View.VISIBLE);
                    List<String> source = new ArrayList<>();
                    List<String> actions = new ArrayList<>();
                    List<String> comments = new ArrayList<>();
                    source = sourceNacho.getChipValues();
                    boolean privateAccount = privateCheck.isChecked();
                    int hours = Integer.parseInt(unfollowRange.getRightPinValue());
                    int limit = Integer.parseInt(fetchRange.getRightPinValue());
                    if (followCheckBox.isChecked()) {
                        actions.add("follow");
                    }
                    if (likeCheckBox.isChecked()) {
                        actions.add("like");
                    }
                    if (commentCheckBox.isChecked()) {
                        actions.add("comment");
                    }
                    Auth auth = new Auth(SettingActivity.this);
                    auth.reLogin();
                    I4A instagram = auth.getI4A();
                    comments = commentAdapter.getSelected();
                    data.put("source", source);
                    data.put("actions", actions);
                    data.put("comments", comments);
                    data.put("private", privateAccount);
                    data.put("hours", hours);
                    data.put("limit", limit);
                    data.put("_id", instagram.getUserId());
                    String url="http://5.152.223.138:1082/savesetting";
                    new SettingAsync(url,data).execute("");
//                    Log.d(TAG, "onClick: " + data);
                }catch (Exception ex){
                    Log.d(TAG, "onClick: "+ex.getMessage());
                }
            }
        });
    }
    public static void saveSetting(){
        Log.d(TAG , "saveSetting: ");
    }
    View.OnClickListener selectButton = new View.OnClickListener() {
        public void onClick(View v) {
            sourceLayout.setVisibility(View.GONE);
            actionLayout.setVisibility(View.GONE);
            commentLayout.setVisibility(View.GONE);
            otherLayout.setVisibility(View.GONE);
            if(v.getId()==R.id.source_btn){
                sourceLayout.setVisibility(View.VISIBLE);
                sourceNacho.requestFocus();
            }else if(v.getId()==R.id.comment_btn){
                commentLayout.setVisibility(View.VISIBLE);
            }else if(v.getId()==R.id.action_btn){
                actionLayout.setVisibility(View.VISIBLE);
            }else if(v.getId()==R.id.other_btn){
                otherLayout.setVisibility(View.VISIBLE);
            }
        }
    };
    @Override
    public void finish() {
        Log.d(TAG, "finish: ");
        super.finish();
        overridePendingTransition(R.anim.slideltr, R.anim.slidertl);
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "setupWindowAnimations: ");
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
