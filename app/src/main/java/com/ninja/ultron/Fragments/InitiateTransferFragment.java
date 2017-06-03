package com.ninja.ultron.Fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ninja.ultron.R;
import com.ninja.ultron.entity.CommentEntity;
import com.ninja.ultron.entity.InitiateTransferEntity;
import com.ninja.ultron.entity.TransferReasonsEntity;
import com.ninja.ultron.entity.TransferReasonsMiniEntity;
import com.ninja.ultron.functions.CommonFunctions;
import com.ninja.ultron.functions.UserDetails;
import com.ninja.ultron.restclient.RestClientImplementation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prabhu Sivanandam on 22-May-17.
 */

public class InitiateTransferFragment extends Fragment{

    public String selectedName,selectedToName,comments;
    public int selectedId;
    int loggedUserId;
    TextView tvAssetId,tvAssetName,tvTransferTo,tvEmployeeId,tvInitiate;
    RelativeLayout rlInitateButton;
    Spinner spinnerReason;
    EditText etComments;
    TransferReasonsMiniEntity transferReasonsMiniEntity;
    InitiateTransferEntity initiateTransferEntity;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_initiate_transfer,container,false);
        transferReasonsMiniEntity=new TransferReasonsMiniEntity();
        tvAssetId=(TextView)view.findViewById(R.id.tvAssetId);
        tvAssetName=(TextView)view.findViewById(R.id.tvAssetName);
        tvTransferTo=(TextView)view.findViewById(R.id.tvTransferTo);
        tvInitiate=(TextView)view.findViewById(R.id.tvInitiate);
        tvEmployeeId=(TextView)view.findViewById(R.id.tvEmployeeId);
        tvAssetName.setText(selectedName);
        tvAssetId.setText(selectedId+"");
        tvTransferTo.setText(selectedToName+"");
        loggedUserId= UserDetails.getAsgardUserId(getContext());
        tvEmployeeId.setText(loggedUserId+"");
        tvEmployeeId=(TextView)view.findViewById(R.id.tvEmployeeId);
        spinnerReason=(Spinner)view.findViewById(R.id.spinnerRequestType);
        etComments=(EditText)view.findViewById(R.id.etCommentsBox);
        comments=etComments.getText().toString();
        rlInitateButton = (RelativeLayout)view.findViewById(R.id.rlInitiateButton);
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
                        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,reasons);
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
                        CommonFunctions.toastString("UnAuthorized",getContext());
                    }

                }

            }
        },getContext());
        return view;
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
                        CommonFunctions.toastString("Initiated Transfer",getContext());
                    }
                    else
                    {
                        CommonFunctions.toastString("Error",getContext());
                    }
                }
                else
                {
                    if(initiateTransferEntity.getCode()==401)
                    {
                        CommonFunctions.toastString("Already in Transit",getContext());
                    }
                }

            }
        },getContext());
    }


}
