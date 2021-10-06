package space.lopatkin.spb.testtask_exchangerate.utils;

//public class AsyncLoader extends AsyncTaskLoader<List<Valute>> {
//    private Network network = new Network();
//    private List<Valute> mData;
//    private String mUrl = new String();
//
//    public AsyncLoader(@NonNull Context context, Bundle args) {
//        super(context);
//        if (args != null) {
//            mUrl = args.getString(MainActivity.KEY_HTTP);
//        }
//    }
//
//    @Nullable
//    @Override
//    public List<Valute> loadInBackground() {
//        List<Valute> data = null;
//        try {
//            data = network.getExchangeRates(mUrl);
//        } catch (XmlPullParserException | IOException e) {
//            e.printStackTrace();
//        }
//
//        if (data == null) {
//            data = new ArrayList<>();
//        }
//        return data;
//    }
//
//    @Override
//    public void deliverResult(@Nullable List<Valute> data) {
//
//        if (isReset()) {
//            if (data != null) {
//                onReleaseResources(data);
//            }
//        }
//        List<Valute> oldData = mData;
//        mData = data;
//
//        if (isStarted()) {
//            super.deliverResult(data);
//        }
//        if (oldData != null) {
//            onReleaseResources(oldData);
//        }
//    }
//
//    @Override
//    protected void onStartLoading() {
//        if (mData != null) {
//            deliverResult(mData);
//        }
//        if (takeContentChanged()) {
//            forceLoad();
//        }
//    }
//
//    @Override
//    protected void onStopLoading() {
//        cancelLoad();
//    }
//
//    @Override
//    public void onCanceled(@Nullable List<Valute> data) {
//        super.onCanceled(data);
//
//        onReleaseResources(data);
//    }
//
//    @Override
//    protected void onReset() {
//        super.onReset();
//        onStopLoading();
//        if (mData != null) {
//            onReleaseResources(mData);
//            mData = null;
//        }
//    }
//
//    protected void onReleaseResources(List<Valute> data) {
//        //метод для закрытия сложного устройства, например курсора
//    }
//}
