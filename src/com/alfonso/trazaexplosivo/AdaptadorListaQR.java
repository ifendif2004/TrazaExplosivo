package com.alfonso.trazaexplosivo;

import java.util.ArrayList;

import com.alfonso.modelo.DatoQR;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class AdaptadorListaQR extends BaseAdapter {

	private Context mContext;
	private ArrayList<DatoQR> mArray = new ArrayList<DatoQR>();

	public AdaptadorListaQR(Context c, ArrayList<DatoQR> mArray) {
		mContext = c;
		this.mArray = mArray;
		System.out.println("AdaptadorListaQR constructor: " + (mArray.get(0).getCodigo()));
	}

	@Override
	public int getCount() {
		return mArray.size();
	}

	@Override
	public Object getItem(int position) {
		return mArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.datoqr_layout, null);

		} else {
			view = convertView;
		}
		if (mArray.size()==0) {
			System.out.println("AdaptadorListaQR getView esta vacio");
			return view;
		}
		TextView tQR = (TextView) view.findViewById(R.id.codigo);
		System.out.println("AdaptadorListaQR getView" + (mArray.get(position).getCodigo()));
		tQR.setText(mArray.get(position).getCodigo());
		CheckBox cbValidado = (CheckBox) view.findViewById(R.id.cbValidado);
		cbValidado.setChecked(mArray.get(position).isValidado());
		System.out.println("AdaptadorLista: " + mArray.get(position).isValidado());
		cbValidado.setText((mArray.get(position).isValidado()) ? "Validado" : "No validado");
		cbValidado.setTextColor((mArray.get(position).isValidado()) ? Color.BLUE : Color.RED);
		cbValidado.setActivated(false);
		cbValidado.setClickable(false);
		return view;
	}
}
