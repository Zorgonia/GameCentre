package fall2018.csc2017.twentyfortyeight;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

//import fall2018.csc2017.slidingtiles.BoardManager;
import fall2018.csc2017.slidingtiles.MovementController;

public class GestureDetectGridView2048 extends GridView {
    public static final int SWIPE_MIN_DISTANCE = 100;
    public static final int SWIPE_MAX_OFF_PATH = 100;
    public static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private GestureDetector gDetector;
    private MovementController2048 mController;
    private boolean mFlingConfirmed = false;
    private float mTouchX;
    private float mTouchY;
    private BoardManager2048 boardManager;

    public GestureDetectGridView2048(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectGridView2048(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureDetectGridView2048(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) // API 21
    public GestureDetectGridView2048(Context context, AttributeSet attrs, int defStyleAttr,
                                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        mController = new MovementController2048();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent event) {
//                int position = GestureDetectGridView2048.this.pointToPosition
//                        (Math.round(event.getX()), Math.round(event.getY()));
//
//                mController.processTapMovement(context, position, true);
//                return true;
//            }


            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                final int position = GestureDetectGridView2048.this.pointToPosition
                        (Math.round(e1.getX()), Math.round(e1.getY()));

                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                    if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH
                            || Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
                        return false;
                    }
                    if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                        mController.processSwipeMovement(context, "up", true);
                    } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                        mController.processSwipeMovement(context, "down", true);
                    }
                } else {
                    if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
                        return false;
                    }
                    if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                        mController.processSwipeMovement(context, "left", true);
                    } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                        mController.processSwipeMovement(context, "right", true);
                    }
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }


            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    public void setBoardManager(BoardManager2048 boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}
