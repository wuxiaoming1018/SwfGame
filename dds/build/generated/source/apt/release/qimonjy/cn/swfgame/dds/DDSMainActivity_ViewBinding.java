// Generated code from Butter Knife. Do not modify!
package qimonjy.cn.swfgame.dds;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DDSMainActivity_ViewBinding implements Unbinder {
  private DDSMainActivity target;

  private View view2131361896;

  private View view2131361892;

  @UiThread
  public DDSMainActivity_ViewBinding(DDSMainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DDSMainActivity_ViewBinding(final DDSMainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.help, "field 'help' and method 'onHelpClicked'");
    target.help = Utils.castView(view, R.id.help, "field 'help'", ImageView.class);
    view2131361896 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onHelpClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.start, "field 'start' and method 'onStartClicked'");
    target.start = Utils.castView(view, R.id.start, "field 'start'", ImageView.class);
    view2131361892 = view;
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
    DDSMainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.help = null;
    target.start = null;

    view2131361896.setOnClickListener(null);
    view2131361896 = null;
    view2131361892.setOnClickListener(null);
    view2131361892 = null;
  }
}
