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

public class ZJMMCMainActivity_ViewBinding implements Unbinder {
  private ZJMMCMainActivity target;

  private View view2131427477;

  private View view2131427476;

  @UiThread
  public ZJMMCMainActivity_ViewBinding(ZJMMCMainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ZJMMCMainActivity_ViewBinding(final ZJMMCMainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.help, "method 'onHelpClicked'");
    view2131427477 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onHelpClicked();
      }
    });
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


    view2131427477.setOnClickListener(null);
    view2131427477 = null;
    view2131427476.setOnClickListener(null);
    view2131427476 = null;
  }
}
