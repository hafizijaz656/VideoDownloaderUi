package com.code.studio.allvideodownui.customviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ClassForZoomImgView extends AppCompatImageView {

    public static final float FLING_DAMPING_FACTOR = 0.9f;
    public static final int PINCH_MODE_FREE = 0;
    public static final int PINCH_MODE_SCALE = 2;
    public static final int PINCH_MODE_SCROLL = 1;
    public static final int SCALE_ANIMATOR_DURATION = 200;
    private static final float MAX_SCALE = 4.0f;
    private final PointF mLastMovePoint = new PointF();
    private final PointF mScaleCenter = new PointF();

    public OnClickListener mOnClickListener;

    public OnLongClickListener mOnLongClickListener;

    public Matrix mOuterMatrix = new Matrix();

    public int mPinchMode = 0;

    public ScaleAnimator mScaleAnimator;
    private int mDispatchOuterMatrixChangedLock;
    private FlingAnimator mFlingAnimator;
    private final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (mPinchMode == 1 && (mScaleAnimator == null || !mScaleAnimator.isRunning())) {
                doubleTap(motionEvent.getX(), motionEvent.getY());
            }
            return true;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (mPinchMode != 0) {
                return true;
            }
            if (mScaleAnimator != null && mScaleAnimator.isRunning()) {
                return true;
            }
            fling(f, f2);
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            if (mOnLongClickListener != null) {
                mOnLongClickListener.onLongClick(ClassForZoomImgView.this);
            }
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (mOnClickListener == null) {
                return true;
            }
            mOnClickListener.onClick(ClassForZoomImgView.this);
            return true;
        }
    });
    private RectF mMask;
    private List<OuterMatrixChangedListener> mOuterMatrixChangedListeners;
    private List<OuterMatrixChangedListener> mOuterMatrixChangedListenersCopy;
    private float mScaleBase = 0.0f;

    public ClassForZoomImgView(Context context) {
        super(context);
        initView();
    }

    public ClassForZoomImgView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public ClassForZoomImgView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    private void cancelAllAnimator() {
        ScaleAnimator scaleAnimator = this.mScaleAnimator;
        if (scaleAnimator != null) {
            scaleAnimator.cancel();
            this.mScaleAnimator = null;
        }
        FlingAnimator flingAnimator = this.mFlingAnimator;
        if (flingAnimator != null) {
            flingAnimator.cancel();
            this.mFlingAnimator = null;
        }
    }

    /* access modifiers changed from: private */
    public void dispatchOuterMatrixChanged() {
        List<OuterMatrixChangedListener> list;
        List<OuterMatrixChangedListener> list2 = this.mOuterMatrixChangedListeners;
        if (list2 != null) {
            this.mDispatchOuterMatrixChangedLock++;
            for (OuterMatrixChangedListener onOuterMatrixChanged : list2) {
                onOuterMatrixChanged.onOuterMatrixChanged(this);
            }
            int i = this.mDispatchOuterMatrixChangedLock - 1;
            this.mDispatchOuterMatrixChangedLock = i;
            if (i == 0 && (list = this.mOuterMatrixChangedListenersCopy) != null) {
                this.mOuterMatrixChangedListeners = list;
                this.mOuterMatrixChangedListenersCopy = null;
            }
        }
    }

    /* access modifiers changed from: private */
    public void doubleTap(float f, float f2) {
        if (isReady()) {
            Matrix matrixTake = MathUtils.matrixTake();
            getInnerMatrix(matrixTake);
            float f3 = MathUtils.getMatrixScale(matrixTake)[0];
            float f4 = MathUtils.getMatrixScale(this.mOuterMatrix)[0];
            float f5 = f3 * f4;
            float width = (float) getWidth();
            float height = (float) getHeight();
            float maxScale = getMaxScale();
            float calculateNextScale = calculateNextScale(f3, f4);
            if (calculateNextScale <= maxScale) {
                maxScale = calculateNextScale;
            }
            if (maxScale >= f3) {
                f3 = maxScale;
            }
            Matrix matrixTake2 = MathUtils.matrixTake(this.mOuterMatrix);
            float f6 = f3 / f5;
            matrixTake2.postScale(f6, f6, f, f2);
            float f7 = width / 2.0f;
            float f8 = height / 2.0f;
            matrixTake2.postTranslate(f7 - f, f8 - f2);
            Matrix matrixTake3 = MathUtils.matrixTake(matrixTake);
            matrixTake3.postConcat(matrixTake2);
            float f9 = 0.0f;
            RectF rectFTake = MathUtils.rectFTake(0.0f, 0.0f, (float) getDrawable().getIntrinsicWidth(), (float) getDrawable().getIntrinsicHeight());
            matrixTake3.mapRect(rectFTake);
            float f10 = rectFTake.right;
            float f11 = rectFTake.left;
            float f12 = f10 - f11 < width ? f7 - ((f10 + f11) / 2.0f) : f11 > 0.0f ? -f11 : f10 < width ? width - f10 : 0.0f;
            float f13 = rectFTake.bottom;
            float f14 = rectFTake.top;
            if (f13 - f14 < height) {
                f9 = f8 - ((f13 + f14) / 2.0f);
            } else if (f14 > 0.0f) {
                f9 = -f14;
            } else if (f13 < height) {
                f9 = height - f13;
            }
            matrixTake2.postTranslate(f12, f9);
            cancelAllAnimator();
            ScaleAnimator scaleAnimator = new ScaleAnimator(this, this.mOuterMatrix, matrixTake2);
            this.mScaleAnimator = scaleAnimator;
            scaleAnimator.start();
            MathUtils.rectFGiven(rectFTake);
            MathUtils.matrixGiven(matrixTake3);
            MathUtils.matrixGiven(matrixTake2);
            MathUtils.matrixGiven(matrixTake);
        }
    }

    public void fling(float f, float f2) {
        if (isReady()) {
            cancelAllAnimator();
            FlingAnimator flingAnimator = new FlingAnimator(f / 60.0f, f2 / 60.0f);
            this.mFlingAnimator = flingAnimator;
            flingAnimator.start();
        }
    }

    private void initView() {
        super.setScaleType(ScaleType.MATRIX);
    }

    private boolean isReady() {
        return getDrawable() != null && getDrawable().getIntrinsicWidth() > 0 && getDrawable().getIntrinsicHeight() > 0 && getWidth() > 0 && getHeight() > 0;
    }

    private void saveScaleContext(float f, float f2, float f3, float f4) {
        this.mScaleBase = MathUtils.getMatrixScale(this.mOuterMatrix)[0] / MathUtils.getDistance(f, f2, f3, f4);
        float[] inverseMatrixPoint = MathUtils.inverseMatrixPoint(MathUtils.getCenterPoint(f, f2, f3, f4), this.mOuterMatrix);
        this.mScaleCenter.set(inverseMatrixPoint[0], inverseMatrixPoint[1]);
    }

    private void scale(PointF pointF, float f, float f2, PointF pointF2) {
        if (isReady()) {
            float f3 = f * f2;
            Matrix matrixTake = MathUtils.matrixTake();
            matrixTake.postScale(f3, f3, pointF.x, pointF.y);
            matrixTake.postTranslate(pointF2.x - pointF.x, pointF2.y - pointF.y);
            this.mOuterMatrix.set(matrixTake);
            MathUtils.matrixGiven(matrixTake);
            dispatchOuterMatrixChanged();
            invalidate();
        }
    }

    private void scaleEnd() {
        throw new UnsupportedOperationException("Method not decompiled: p013hd.video.downloader.allvideodownloader.app.tool.ClassForZoomImgView.scaleEnd():void");
    }

    public boolean scrollBy(float r9, float r10) {
        throw new UnsupportedOperationException("Method not decompiled: p013hd.video.downloader.allvideodownloader.app.tool.ClassForZoomImgView.scrollBy(float, float):boolean");
    }

    public float calculateNextScale(float f, float f2) {
        return f2 * f < MAX_SCALE ? MAX_SCALE : f;
    }

    public boolean canScrollHorizontally(int i) {
        if (this.mPinchMode == 2) {
            return true;
        }
        RectF imageBound = getImageBound((RectF) null);
        if (imageBound == null || imageBound.isEmpty()) {
            return false;
        }
        if (i > 0) {
            if (imageBound.right > ((float) getWidth())) {
                return true;
            }
            return false;
        } else if (imageBound.left < 0.0f) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canScrollVertically(int i) {
        if (this.mPinchMode == 2) {
            return true;
        }
        RectF imageBound = getImageBound((RectF) null);
        if (imageBound == null || imageBound.isEmpty()) {
            return false;
        }
        if (i > 0) {
            if (imageBound.bottom > ((float) getHeight())) {
                return true;
            }
            return false;
        } else if (imageBound.top < 0.0f) {
            return true;
        } else {
            return false;
        }
    }

    public Matrix getCurrentImageMatrix(Matrix matrix) {
        Matrix innerMatrix = getInnerMatrix(matrix);
        innerMatrix.postConcat(this.mOuterMatrix);
        return innerMatrix;
    }

    public RectF getImageBound(RectF rectF) {
        if (rectF == null) {
            rectF = new RectF();
        } else {
            rectF.setEmpty();
        }
        if (!isReady()) {
            return rectF;
        }
        Matrix matrixTake = MathUtils.matrixTake();
        getCurrentImageMatrix(matrixTake);
        rectF.set(0.0f, 0.0f, (float) getDrawable().getIntrinsicWidth(), (float) getDrawable().getIntrinsicHeight());
        matrixTake.mapRect(rectF);
        MathUtils.matrixGiven(matrixTake);
        return rectF;
    }

    public Matrix getInnerMatrix(Matrix matrix) {
        if (matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }
        if (isReady()) {
            RectF rectFTake = MathUtils.rectFTake(0.0f, 0.0f, (float) getDrawable().getIntrinsicWidth(), (float) getDrawable().getIntrinsicHeight());
            RectF rectFTake2 = MathUtils.rectFTake(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
            matrix.setRectToRect(rectFTake, rectFTake2, Matrix.ScaleToFit.CENTER);
            MathUtils.rectFGiven(rectFTake2);
            MathUtils.rectFGiven(rectFTake);
        }
        return matrix;
    }

    public float getMaxScale() {
        return MAX_SCALE;
    }

    public void onDraw(Canvas canvas) {
        if (isReady()) {
            Matrix matrixTake = MathUtils.matrixTake();
            setImageMatrix(getCurrentImageMatrix(matrixTake));
            MathUtils.matrixGiven(matrixTake);
        }
        if (this.mMask != null) {
            canvas.save();
            canvas.clipRect(this.mMask);
            super.onDraw(canvas);
            canvas.restore();
            return;
        }
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        ScaleAnimator scaleAnimator;
        super.onTouchEvent(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action == 1 || action == 3) {
            if (this.mPinchMode == 2) {
                scaleEnd();
            }
            this.mPinchMode = 0;
        } else if (action == 6) {
            if (this.mPinchMode == 2 && motionEvent.getPointerCount() > 2) {
                if ((motionEvent.getAction() >> 8) == 0) {
                    saveScaleContext(motionEvent.getX(1), motionEvent.getY(1), motionEvent.getX(2), motionEvent.getY(2));
                } else if ((motionEvent.getAction() >> 8) == 1) {
                    saveScaleContext(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(2), motionEvent.getY(2));
                }
            }
        } else if (action == 0) {
            ScaleAnimator scaleAnimator2 = this.mScaleAnimator;
            if (scaleAnimator2 == null || !scaleAnimator2.isRunning()) {
                cancelAllAnimator();
                this.mPinchMode = 1;
                this.mLastMovePoint.set(motionEvent.getX(), motionEvent.getY());
            }
        } else if (action == 5) {
            cancelAllAnimator();
            this.mPinchMode = 2;
            saveScaleContext(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
        } else if (action == 2 && ((scaleAnimator = this.mScaleAnimator) == null || !scaleAnimator.isRunning())) {
            int i = this.mPinchMode;
            if (i == 1) {
                scrollBy(motionEvent.getX() - this.mLastMovePoint.x, motionEvent.getY() - this.mLastMovePoint.y);
                this.mLastMovePoint.set(motionEvent.getX(), motionEvent.getY());
            } else if (i == 2 && motionEvent.getPointerCount() > 1) {
                float distance = MathUtils.getDistance(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
                float[] centerPoint = MathUtils.getCenterPoint(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
                this.mLastMovePoint.set(centerPoint[0], centerPoint[1]);
                scale(this.mScaleCenter, this.mScaleBase, distance, this.mLastMovePoint);
            }
        }
        this.mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.mOnLongClickListener = onLongClickListener;
    }

    public void setScaleType(ScaleType scaleType) {
    }

    public interface OuterMatrixChangedListener {
        void onOuterMatrixChanged(ClassForZoomImgView classForZoomImgView);
    }

    public static class MathUtils {
        private static MatrixPool mMatrixPool = new MatrixPool(16);
        private static RectFPool mRectFPool = new RectFPool(16);

        public static float[] getCenterPoint(float f, float f2, float f3, float f4) {
            return new float[]{(f + f3) / 2.0f, (f2 + f4) / 2.0f};
        }

        public static float getDistance(float f, float f2, float f3, float f4) {
            float f5 = f - f3;
            float f6 = f2 - f4;
            return (float) Math.sqrt((double) ((f6 * f6) + (f5 * f5)));
        }

        public static float[] getMatrixScale(Matrix matrix) {
            if (matrix == null) {
                return new float[2];
            }
            float[] fArr = new float[9];
            matrix.getValues(fArr);
            return new float[]{fArr[0], fArr[4]};
        }

        public static float[] inverseMatrixPoint(float[] fArr, Matrix matrix) {
            if (fArr == null || matrix == null) {
                return new float[2];
            }
            float[] fArr2 = new float[2];
            Matrix matrixTake = matrixTake();
            matrix.invert(matrixTake);
            matrixTake.mapPoints(fArr2, fArr);
            matrixGiven(matrixTake);
            return fArr2;
        }

        public static void matrixGiven(Matrix matrix) {
            mMatrixPool.given(matrix);
        }

        public static Matrix matrixTake() {
            return (Matrix) mMatrixPool.take();
        }

        public static void rectFGiven(RectF rectF) {
            mRectFPool.given(rectF);
        }

        public static RectF rectFTake() {
            return (RectF) mRectFPool.take();
        }

        public static Matrix matrixTake(Matrix matrix) {
            Matrix matrix2 = (Matrix) mMatrixPool.take();
            if (matrix != null) {
                matrix2.set(matrix);
            }
            return matrix2;
        }

        public static RectF rectFTake(float f, float f2, float f3, float f4) {
            RectF rectF = (RectF) mRectFPool.take();
            rectF.set(f, f2, f3, f4);
            return rectF;
        }
    }

    public static class MatrixPool extends ObjectsPool<Matrix> {
        public MatrixPool(int i) {
            super(i);
        }

        public Matrix newInstance() {
            return new Matrix();
        }

        public Matrix resetInstance(Matrix matrix) {
            matrix.reset();
            return matrix;
        }
    }

    public static abstract class ObjectsPool<T> {
        private Queue<T> mQueue = new LinkedList();
        private int mSize;

        public ObjectsPool(int i) {
            this.mSize = i;
        }

        public void given(T t) {
            if (t != null && this.mQueue.size() < this.mSize) {
                this.mQueue.offer(t);
            }
        }

        public abstract T newInstance();

        public abstract T resetInstance(T t);

        public T take() {
            if (this.mQueue.size() == 0) {
                return newInstance();
            }
            return resetInstance(this.mQueue.poll());
        }
    }

    public static class RectFPool extends ObjectsPool<RectF> {
        public RectFPool(int i) {
            super(i);
        }

        public RectF newInstance() {
            return new RectF();
        }

        public RectF resetInstance(RectF rectF) {
            rectF.setEmpty();
            return rectF;
        }
    }

    public class FlingAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
        private float[] mVector;

        public FlingAnimator(float f, float f2) {
            setFloatValues(new float[]{0.0f, 1.0f});
            setDuration(1000000);
            addUpdateListener(this);
            this.mVector = new float[]{f, f2};
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            ClassForZoomImgView classForZoomImgView = ClassForZoomImgView.this;
            float[] fArr = this.mVector;
            boolean access$600 = classForZoomImgView.scrollBy(fArr[0], fArr[1]);
            float[] fArr2 = this.mVector;
            fArr2[0] = fArr2[0] * 0.9f;
            fArr2[1] = fArr2[1] * 0.9f;
            if (!access$600 || MathUtils.getDistance(0.0f, 0.0f, fArr2[0], fArr2[1]) < 1.0f) {
                valueAnimator.cancel();
            }
        }
    }

    public class ScaleAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {
        private final float[] mEnd;
        private final float[] mResult;
        private final float[] mStart;

        public ScaleAnimator(ClassForZoomImgView classForZoomImgView, Matrix matrix, Matrix matrix2) {
            this(matrix, matrix2, 200);
        }

        public ScaleAnimator(Matrix matrix, Matrix matrix2, long j) {
            this.mStart = new float[9];
            this.mEnd = new float[9];
            this.mResult = new float[9];
            setFloatValues(new float[]{0.0f, 1.0f});
            setDuration(j);
            addUpdateListener(this);
            matrix.getValues(this.mStart);
            matrix2.getValues(this.mEnd);
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float floatValue = (Float) valueAnimator.getAnimatedValue();
            for (int i = 0; i < 9; i++) {
                float[] fArr = this.mResult;
                float[] fArr2 = this.mStart;
                // fArr[i] = GeneratedOutlineSupport.outline0(this.mEnd[i], fArr2[i], floatValue, fArr2[i]);
            }
            mOuterMatrix.setValues(this.mResult);
            dispatchOuterMatrixChanged();
            invalidate();
        }
    }
}
