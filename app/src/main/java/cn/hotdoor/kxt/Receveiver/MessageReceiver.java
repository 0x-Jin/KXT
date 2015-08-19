package cn.hotdoor.kxt.Receveiver;

import android.content.Context;
import android.widget.Toast;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import net.tsz.afinal.FinalDb;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.hotdoor.kxt.Beans.MessageItemBean;
import cn.hotdoor.kxt.Data.GlobleData;

public class MessageReceiver extends XGPushBaseReceiver {
	//private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
	public static final String LogTag = "TPushReceiver";
	private void show(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

	}

	@Override
	public void onUnregisterResult(Context context, int i) {

	}

	@Override
	public void onSetTagResult(Context context, int i, String s) {

	}

	@Override
	public void onDeleteTagResult(Context context, int i, String s) {

	}

	@Override
	public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {

	}

	@Override
	public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

	}

	// 通知展示
	@Override
	public void onNotifactionShowedResult(Context context,
			XGPushShowedResult notifiShowedRlt) {
		if (context == null || notifiShowedRlt == null) {
			return;
		}
		GlobleData.db = FinalDb.creat(context);
		MessageItemBean been= new MessageItemBean();
		been.setIcon("1");
		been.setPhone(notifiShowedRlt.getMsgId()+"");
		been.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime()));
		been.setMessage(notifiShowedRlt.getContent());
		//Toast.makeText(context,notifiShowedRlt.getContent() , Toast.LENGTH_SHORT).show();
		GlobleData.db.save(been);
		//Toast.makeText(context,"ok", Toast.LENGTH_SHORT).show();
		/*XGNotification notific = new XGNotification();
		notific.setMsg_id(notifiShowedRlt.getMsgId());
		notific.setTitle(notifiShowedRlt.getTitle());
		notific.setContent(notifiShowedRlt.getContent());
		// notificationActionType==1为Activity，2为url，3为intent
		notific.setNotificationActionType(notifiShowedRlt
				.getNotificationActionType());
		// Activity,url,intent都可以通过getActivity()获得
		notific.setActivity(notifiShowedRlt.getActivity());
		notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime()));
		*/
		//context.sendBroadcast(intent);
		//show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
	}



}
