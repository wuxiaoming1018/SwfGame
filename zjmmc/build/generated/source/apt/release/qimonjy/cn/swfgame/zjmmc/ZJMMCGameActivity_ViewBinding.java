// Generated code from Butter Knife. Do not modify!
package qimonjy.cn.swfgame.zjmmc;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ZJMMCGameActivity_ViewBinding implements Unbinder {
  private ZJMMCGameActivity target;

  private View view2131427463;

  private View view2131427464;

  @UiThread
  public ZJMMCGameActivity_ViewBinding(ZJMMCGameActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ZJMMCGameActivity_ViewBinding(final ZJMMCGameActivity target, View source) {
    this.target = target;

    View view;
    target.time = Utils.findRequiredViewAsType(source, R.id.time, "field 'time'", TextView.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progress'", TextView.class);
    target.chiness = Utils.findRequiredViewAsType(source, R.id.chiness, "field 'chiness'", TextView.class);
    target.closeVoice = Utils.findRequiredViewAsType(source, R.id.closeVoice, "field 'closeVoice'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.playVoice, "field 'playVoice' and method 'onPlayVoiceClicked'");
    target.playVoice = Utils.castView(view, R.id.playVoice, "field 'playVoice'", ImageView.class);
    view2131427463 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayVoiceClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.cancel, "field 'cancel' and method 'onCancelClicked'");
    target.cancel = Utils.castView(view, R.id.cancel, "field 'cancel'", ImageView.class);
    view2131427464 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCancelClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ZJMMCGameActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.time = null;
    target.progress = null;
    target.chiness = null;
    target.closeVoice = null;
    target.playVoice = null;
    target.cancel = null;

    view2131427463.setOnClickListener(null);
    view2131427463 = null;
    view2131427464.setOnClickListener(null);
    view2131427464 = null;
  }
}
