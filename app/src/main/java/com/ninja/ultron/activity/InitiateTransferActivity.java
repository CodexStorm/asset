package com.ninja.ultron.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ninja.ultron.R;
import com.ninja.ultron.entity.InitiateTransferEntity;
import com.ninja.ultron.entity.TransferReasonsMiniEntity;

/**
 * Created by Prabhu Sivanandam on 22-May-17.
 */

public class InitiateTransferActivity extends AppCompatActivity {

    public String selectedName, selectedToName, comments;
    public int selectedId;
    int loggedUserId;
    TextView tvAssetId, tvAssetName, tvTransferTo, tvEmployeeId, tvInitiate;
    RelativeLayout rlInitateButton;
    Spinner spinnerReason;
    EditText etComments;
    TransferReasonsMiniEntity transferReasonsMiniEntity;
    InitiateTransferEntity initiateTransferEntity;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_transfer);
       /* transferReasonsMiniEntity = new TransferReasonsMiniEntity();
        loggedUserId = UserDetails.getAsgardUserId(InitiateTransferFragment.this);
        spinnerReason = (Spinner) findViewById(R.id.spinnerRequestType);
        etComments = (EditText) findViewById(R.id.etCommentsBox);
        comments = etComments.getText().toString();
        rlInitateButton = (RelativeLayout) findViewById(R.id.rlInitiateButton);
       createTransferEntity();

        rlInitateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callInitiateTransferApi();

            }
        });
        RestClientImplementation.getTransferReasonsApi(transferReasonsMiniEntity,new TransferReasonsMiniEntity.UltronRestClientInterface()
        {
            @Override
            public void onInitialize(TransferReasonsMiniEntity transferReasonsMiniEntity, VolleyError error) {

                if(error==null)
                {
                    if(transferReasonsMiniEntity.getReponse()!=null)
                    {

                        List<String> reasons=new ArrayList<>();
                        List<TransferReasonsEntity> transferReasonsEntities=transferReasonsMiniEntity.getReponse();
                        for(int i=0;i<transferReasonsEntities.size();i++)
                        {
                            reasons.add(transferReasonsEntities.get(i).getName());
                            Log.d("efwf",reasons.get(i));
                        }
                        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(InitiateTransferFragment.this,android.R.layout.simple_spinner_dropdown_item,reasons);
                        spinnerReason.setAdapter(dataAdapter);
                        spinnerReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                String itemSelected=parent.getItemAtPosition(position).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }
                    else
                    {
                        Log.d("Tag","error in response");
                    }
                }
                else
                {

                    if(transferReasonsMiniEntity.getStatusCode()==401)
                    {
                        CommonFunctions.toastString("UnAuthorized",InitiateTransferFragment.this);
                    }

                }

            }
        },InitiateTransferFragment.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createTransferEntity()
    {
        int issueTypeId=1,assetId,requestedBy,approver=3;
        String dateOfRequest,comments;
        assetId=selectedId;
        requestedBy=loggedUserId;
        int reasonTypeId=1;
        if(this.comments.isEmpty()||this.comments.equals(""))
        {
            comments="";
        }
        else
        {
            comments=this.comments;
        }
        CommentEntity commentEntity=new CommentEntity(loggedUserId,comments);
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        dateOfRequest=calendar.get(java.util.Calendar.YEAR)+"/"+calendar.get(java.util.Calendar.MONTH)+"/"+calendar.get(java.util.Calendar.DAY_OF_MONTH);
        initiateTransferEntity=new InitiateTransferEntity(issueTypeId,assetId,requestedBy,approver,dateOfRequest,commentEntity,reasonTypeId);
        Log.d("test",initiateTransferEntity.getDateOfRequest()+"");
    }

    public void callInitiateTransferApi()
    {
        RestClientImplementation.initiateTransferApi(initiateTransferEntity,new InitiateTransferEntity.UltronRestClientInterface()
        {
            @Override
            public void onInitialize(InitiateTransferEntity initiateTransferEntity, VolleyError error) {

                if(error==null)
                {
                    int code=initiateTransferEntity.getCode();
                    Log.d("code",code+"");
                    if(code==200)
                    {
                        CommonFunctions.toastString("Initiated Transfer",InitiateTransferFragment.this);
                    }
                    else
                    {
                        CommonFunctions.toastString("Error",InitiateTransferFragment.this);
                    }
                }
                else
                {
                    if(initiateTransferEntity.getCode()==401)
                    {
                        CommonFunctions.toastString("Already in Transit",InitiateTransferFragment.this);
                    }
                }

            }
        },InitiateTransferFragment.this);
    }

*/
    }
}