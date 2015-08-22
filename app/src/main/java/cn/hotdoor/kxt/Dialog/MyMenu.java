package cn.hotdoor.kxt.Dialog;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import cn.hotdoor.kxt.Activities.MessageActivity;
import cn.hotdoor.kxt.R;


/**
 * ˽��(�Զ���Dialog)
 * 
 * @author Administrator
 * 
 */
public class MyMenu extends Dialog implements OnItemClickListener
{
	private Context context;

	private GridView menuGrid;

	private MessageActivity Menu_Item;

	// --------------------------------------.9ͼƬ����
	// private Bitmap bmp_9path;
	//
	// private NinePatch np;

	public MyMenu(Context context, int myMenuBit[],
				  MessageActivity Menu_Item)
	{
		super(context, R.style.dialog_fullscreen);
		setContentView(R.layout.mymenu);
		this.context = context;
		this.Menu_Item = Menu_Item;
		setProperty();

		menuGrid = (GridView) this.findViewById(R.id.GridView_toolbar);
		menuGrid.setAdapter(getMenuAdapter(myMenuBit));
		menuGrid.setOnItemClickListener(this);

		// ���ñ���͸����
		View view = findViewById(R.id.mainlayout);
		view.getBackground().setAlpha(0);// 120Ϊ͸���ı���
		//view.setBackgroundResource(R.drawable.bg);// ���ñ���ͼƬ

		// Dialog���Ի�������ȡ������
		this.setCanceledOnTouchOutside(true);

	}

	/**
	 * ���ô�������
	 */

	private void setProperty()
	{
		// // TODO Auto-generated method stub
		// window = getWindow();
		// WindowManager.LayoutParams wl = window.getAttributes();
		// // wl.alpha=0.6f;
		// wl.screenBrightness = 1;// ���õ�ǰ��������
		// wl.gravity = Gravity.CENTER_VERTICAL;
		// wl.setTitle("�ֻ����������趨");
		// window.setAttributes(wl);

		Window w = getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		//DisplayMetrics metric = new DisplayMetrics();
 
		// lp.width = (int) (metric.widthPixels * 0.2);
		
		//lp.alpha = 1.0f;// ���õ�ǰ�Ի���� ͸����

		// lp.dimAmount = 0.0f;// ���öԻ��� �����Ļ��͸����
		
		// lp.gravity = Gravity.CENTER_VERTICAL;
		WindowManager m =w.getWindowManager();
		Display d = m.getDefaultDisplay();  //Ϊ��ȡ��Ļ���� 

		lp.height = (int) (d.getHeight() * 0.3);   //�߶�����Ϊ��Ļ��0.5

		lp.width = (int) (d.getWidth() * 0.98);    //�������Ϊ��Ļ��0.95
		 lp.y = (int)(d.getHeight() * 0.00001); // �Ի������ʾλ��
		w.setAttributes(lp);

	}

	/**
	 * ����˵�Adapter
	 * 

	 *            ����
	 * @param imageResourceArray
	 *            ͼƬ
	 * @return SimpleAdapter
	 */
	public SimpleAdapter getMenuAdapter(
			int[] imageResourceArray)
	{
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 6; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", imageResourceArray[i]);
			//map.put("itemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(context, data,
				R.layout.item_menu, new String[] { "itemImage" },
				new int[] { R.id.item_image});
		return simperAdapter;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		Menu_Item.ItemClickListener(position);
		this.dismiss();
	}

}
