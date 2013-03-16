package jp.sourceforge.pdt_tools.hidequickaccess;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

public class Startup implements IStartup, IWindowListener {

	@Override
	public void earlyStartup() {
		new UIJob("") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				IWorkbenchWindow window = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();
				if (window != null) {
					boolean state = HideQuickAccess.getCommandState(window);
					HideQuickAccess.hideQuickAccess(window, !state);
				}
				return Status.OK_STATUS;
			}
		}.schedule();
		PlatformUI.getWorkbench().addWindowListener(this);
	}

	@Override
	public void windowActivated(IWorkbenchWindow window) {
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
	}

	@Override
	public void windowClosed(IWorkbenchWindow window) {
	}

	@Override
	public void windowOpened(IWorkbenchWindow window) {
		boolean state = HideQuickAccess.getCommandState(window);
		HideQuickAccess.hideQuickAccess(window, !state);
	}

}
