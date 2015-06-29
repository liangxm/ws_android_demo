package com.lxm.pwhelp.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.lxm.pwhelp.App;
import com.lxm.pwhelp.R;
import com.lxm.pwhelp.utils.LockPatternUtils;
import com.lxm.pwhelp.utils.LockPatternView;
import com.lxm.pwhelp.utils.LockPatternView.Cell;
import com.lxm.pwhelp.utils.Tools;

public class UnlockGesturePasswordActivity extends Activity {
	private LockPatternView mLockPatternView;
	private int mFailedPatternAttemptsSinceLastTimeout = 0;
	private CountDownTimer mCountdownTimer = null;
	private Handler mHandler = new Handler();
	private TextView mHeadTextView;
	private TextView gesturepwd_unlock_forget;
	private Animation mShakeAnim;

	private Bundle bundle;
	private static final String RESET = "reset";
	private static boolean isReset = false;

	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturepassword_unlock);
		mLockPatternView = (LockPatternView) this
				.findViewById(R.id.gesturepwd_unlock_lockview);
		mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
		mLockPatternView.setTactileFeedbackEnabled(true);
		mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
		gesturepwd_unlock_forget = (TextView) findViewById(R.id.gesturepwd_unlock_forget);
		mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
		init();
	}

	private void init() {
		gesturepwd_unlock_forget.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(UnlockGesturePasswordActivity.this,PasswordBackActivity.class));
				finish();
			}
		});
		bundle = this.getIntent().getExtras();
		String reset = null;
		if (bundle != null)
			reset = bundle.getString("action");
		if (reset != null && RESET.equals(reset)) {
			mHeadTextView.setText(this.getResources().getString(
					R.string.verify_figtion_password));
			isReset = true;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!App.getInstance().getLockPatternUtils().savedPatternExists()) {
			startActivity(new Intent(this, GuideGesturePasswordActivity.class));
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mCountdownTimer != null)
			mCountdownTimer.cancel();
		if (progressDialog != null && progressDialog.isShowing())
			progressDialog.dismiss();
	}

	private Runnable mClearPatternRunnable = new Runnable() {
		public void run() {
			mLockPatternView.clearPattern();
		}
	};

	protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

		public void onPatternStart() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
			patternInProgress();
		}

		public void onPatternCleared() {
			mLockPatternView.removeCallbacks(mClearPatternRunnable);
		}

		public void onPatternDetected(List<LockPatternView.Cell> pattern) {
			if (pattern == null)
				return;
			if (App.getInstance().getLockPatternUtils().checkPattern(pattern)) {
				if (!isReset) {
					mLockPatternView
							.setDisplayMode(LockPatternView.DisplayMode.Correct);
					progressDialog = ProgressDialog.show(
							UnlockGesturePasswordActivity.this, "加载中...",
							"请稍等...", true, false);
					Intent intent = new Intent(
							UnlockGesturePasswordActivity.this,
							MainActivity.class);
					// 打开新的Activity
					startActivity(intent);
					UnlockGesturePasswordActivity.this.finish();
				} else {
					Intent intent = new Intent(
							UnlockGesturePasswordActivity.this,
							CreateGesturePasswordActivity.class);
					intent.putExtra("reset", "YES");
					startActivityForResult(intent, 1);
					UnlockGesturePasswordActivity.this.finish();
				}
			} else {
				mLockPatternView
						.setDisplayMode(LockPatternView.DisplayMode.Wrong);
				if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
					mFailedPatternAttemptsSinceLastTimeout++;
					int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
							- mFailedPatternAttemptsSinceLastTimeout;
					if (retry >= 0) {
						if (retry == 0)
							Tools.showToast(UnlockGesturePasswordActivity.this,
									"您已5次输错密码，请30秒后再试");
						mHeadTextView.setText("密码错误，还可以再输入" + retry + "次");
						mHeadTextView.setTextColor(Color.RED);
						mHeadTextView.startAnimation(mShakeAnim);
					}

				} else {
					Tools.showToast(UnlockGesturePasswordActivity.this,
							"输入长度不够，请重试");
				}

				if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
					mHandler.postDelayed(attemptLockout, 2000);
				} else {
					mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
				}
			}
		}

		public void onPatternCellAdded(List<Cell> pattern) {

		}

		private void patternInProgress() {
		}
	};
	Runnable attemptLockout = new Runnable() {

		@Override
		public void run() {
			mLockPatternView.clearPattern();
			mLockPatternView.setEnabled(false);
			mCountdownTimer = new CountDownTimer(
					LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1, 1000) {

				@Override
				public void onTick(long millisUntilFinished) {
					int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
					if (secondsRemaining > 0) {
						mHeadTextView.setText(secondsRemaining + " 秒后重试");
					} else {
						mHeadTextView.setText("请绘制手势密码");
						mHeadTextView.setTextColor(Color.WHITE);
					}
				}

				@Override
				public void onFinish() {
					mLockPatternView.setEnabled(true);
					mFailedPatternAttemptsSinceLastTimeout = 0;
				}
			}.start();
		}
	};
}
