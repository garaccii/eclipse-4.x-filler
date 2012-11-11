package jp.sourceforge.pdt_tools.hidequickaccess;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class Startup implements IStartup, IWindowListener {

	@Override
	public void earlyStartup() {
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
