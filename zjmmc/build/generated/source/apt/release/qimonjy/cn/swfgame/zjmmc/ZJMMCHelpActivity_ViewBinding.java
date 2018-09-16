// Generated code from Butter Knife. Do not modify!
package qimonjy.cn.swfgame.zjmmc;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ZJMMCHelpActivity_ViewBinding implements Unbinder {
  private ZJMMCHelpActivity target;

  private View view2131427476;

  @UiThread
  public ZJMMCHelpActivity_ViewBinding(ZJMMCHelpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ZJMMCHelpActivity_ViewBinding(final ZJMMCHelpActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.start, "method 'onStartClicked'");
    view2131427476 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onStartClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131427476.setOnClickListener(null);
    view2131427476 = null;
  }
}
