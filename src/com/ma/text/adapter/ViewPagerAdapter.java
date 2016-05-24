package com.ma.text.adapter;


//public class ViewPagerAdapter extends PagerAdapter {
//	ArrayList<ParkInfoEntity> parkList;
//	SparseArray<View> viewList;
//	Context con;
//	@Override
//	public void notifyDataSetChanged() {
//		super.notifyDataSetChanged();
//		viewList.clear();
//	}
//	public ViewPagerAdapter(Context con, ArrayList<ParkInfoEntity> parkList) {
//		viewList = new SparseArray<View>();
//		this.parkList = parkList;
//		this.con = con;
//	}
//	@Override
//	public int getCount() {
//		return parkList.size();
//	}
//	@Override
//	public boolean isViewFromObject(View arg0, Object arg1) {
//		return arg0 == arg1;
//	}
//
//	public void destroyItem(ViewGroup container, int position, Object object) {
//		container.removeView(viewList.get(position));
//	}
//	@Override
//	public Object instantiateItem(View view, int position) {
//		if (viewList.get(position) != null) {
//			return viewList.get(position);
//		} else {
//			Holder holder = null;
//			if (view == null) {
//				view = LayoutInflater.from(con).inflate(R.layout.layout_map_park, null);
//				holder = new Holder();
//				holder.name = ViewHolders.get(view, R.id.map_park_name);
//				holder.address = ViewHolders.get(view, R.id.map_park_address);
//				holder.distance = ViewHolders.get(view, R.id.map_park_distance);
//				holder.lotTotal = ViewHolders.get(view, R.id.map_park_total);
//				holder.lotLeft = ViewHolders.get(view, R.id.map_park_left);
//				holder.fee = ViewHolders.get(view, R.id.map_park_fee);
//				holder.feeOrder = ViewHolders.get(view, R.id.map_park_fee_order);
//				holder.gps = ViewHolders.get(view, R.id.layout_map_navigat);
//				holder.order = ViewHolders.get(view, R.id.layout_map_order);
//			}
//			final ParkInfoEntity info = parkList.get(position);
//			holder.name.setText(info.getName());
//			holder.address.setText(info.getAddress());
//			String unKnown = ResUtil.getStr(R.string.key_unknown);
//			int lotTal = info.getLotTotal();
//			holder.lotTotal.setText(ResUtil.getStr(R.string.key_lots_total)
//					+ (lotTal == 0 ? unKnown : lotTal));
//			int isPart = info.getIspart();
//			holder.lotLeft.setText(ResUtil.getStr(R.string.key_lots_left)
//					+ (isPart == 0 ? unKnown : info.getLotLeft()));
//			holder.order.setVisibility(isPart == 0 ? View.GONE : View.VISIBLE);
//			holder.feeOrder.setVisibility(isPart == 0 ? View.GONE : View.VISIBLE);
//			// line_order.setVisibility(isPart == 0 ? View.GONE : View.VISIBLE);
//			double op = info.getUnitPrice();
//			holder.feeOrder.setText(ResUtil.getStr(R.string.key_order_price)
//					+ (op == 0 ? ResUtil.getStr(R.string.key_free) : op
//							+ ResUtil.getStr(R.string.key_price_per_hour)));
//			double p = info.getPrice();
//			holder.fee.setText(ResUtil.getStr(R.string.key_charge2)
//					+ (p == -1 ? unKnown : (p == 0
//							? ResUtil.getStr(R.string.key_free)
//							: p + ResUtil.getStr(R.string.key_price_per_hour))));
////			if (ll != null) {
////				double dis = DistanceUtil.getDistance(info.getLongitude(),
////						info.getLatitude(), ll.longitude, ll.latitude);
////				totalParkArray.get(info.getParkID()).setJuli(dis);
////				info.setJuli(dis);
////				holder.distance.setText(IntToStringUtil.getStringFromInt((int) dis));
////			} else
//			holder.distance.setText(unKnown);
//			OnClickListener parkListener = new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					switch (v.getId()) {
//					case R.id.layout_map_navigat : {
//						Intent intent = new Intent(getActivity(),
//								MapNavigationActivity.class);
//						intent.putExtra(K.intent.LATITUDE, info.getLatitude() + "");
//						intent.putExtra(K.intent.LONGITUDE, info.getLongitude() + "");
//						intent.putExtra(K.intent.PARK_NAME, info.getName());
//						intent.putExtra(K.intent.PARK_DISTANCE,
//								IntToStringUtil.getStringFromInt((int) info.getJuli()));
//						startActivity(intent);
//						break;
//					}
//					case R.id.map_park_name : {
//						Intent intent = new Intent(getActivity(), ParkDetailActivity.class);
//						intent.putExtra(K.intent.PARK_ID, info.getParkID());
//						startActivityForResult(intent, REQUEST_DETAIL);
//						break;
//					}
//					case R.id.layout_map_order : {
//						Intent intent = new Intent();
//						if (LoginUtil.isLogin()) {
//							intent.setClass(getActivity(), ParkOrderActivity.class);
//							intent.putExtra(K.intent.PARK_ID, info.getParkID());
//							intent.putExtra(K.intent.PARK_NAME, info.getName());
//							intent.putExtra(K.intent.PARK_ADDRESS, info.getAddress());
//						} else
//							intent.setClass(getActivity(), Login2Activity.class);
//						startActivity(intent);
//					}
//					default :
//						break;
//					}
//
//				}
//			};
//			holder.gps.setOnClickListener(parkListener);
//			holder.order.setOnClickListener(parkListener);
//			holder.name.setOnClickListener(parkListener);
//			if (info.getWhetherbook() == 2 && info.getLotBookLeft() == 0) {
//				// 非合作机构，不可点击，置灰"2".
//				// whetherbook = 1 ，非合作，lotLeft= -1
//				TextView or = (TextView) view.findViewById(R.id.map_park_order);
//				or.setTextColor(ResUtil.getColor(R.color.grey_1));
//				or.setText(ResUtil.getStr(R.string.order_full));
//				or.setEnabled(false);
//			}
//		}
//		return view;
//	}
//	private View getViewByIndex(int position) {
//		return new View(con);
//	}
//	class Holder {
//		TextView name;
//		TextView address;
//		TextView distance;
//		TextView lotTotal;
//		TextView lotLeft;
//		TextView fee;
//		TextView feeOrder;
//		LinearLayout gps;
//		LinearLayout order;
//	}
//}
